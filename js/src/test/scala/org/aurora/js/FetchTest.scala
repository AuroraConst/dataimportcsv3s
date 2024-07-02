package org.aurora.js

import org.scalatest._, Assertions._, funspec.AsyncFunSpec 
import org.scalatest.flatspec._, Assertions.*
import scala.util.Try

import com.raquo.laminar.api.L.{*, given}
import com.raquo.airstream.ownership.OneTimeOwner

import scala.scalajs._
import scala.scalajs.concurrent.JSExecutionContext
import scala.concurrent.{Future,Promise}

import zio.json._
import org.aurora.shared._, dto._
import org.aurora.js.model.Fetch



/**
 * Trait AsyncFlatSpec is so named because your specification text and tests line up flat 
 * against the left-side indentation level, with no nesting needed. 
 */
class FetchTest extends AsyncFlatSpec {

  info("this is my FetchTest")
  implicit override def executionContext = JSExecutionContext.queue   
  
  given Owner = new OneTimeOwner(()=>())

  /** 
   * see
   * https://stackoverflow.com/questions/46617946/sleep-inside-future-in-scala-js
   */
  def delay(milliseconds: Int): Future[Unit] = 
    val p = Promise[Unit]()
    js.timers.setTimeout(milliseconds) {
      p.success(())
    }
    p.future

  
  /**
    * @deprecated
    * this is not used
    * 
    */
  def observer =   new Observer[Option[List[Patient]]] {
    override def onError(err: Throwable): Unit = info(fail(err.getMessage()))
    override def onTry(nextValue: Try[Option[List[Patient]]]): Unit = ()
    override def onNext(nextValue: Option[List[Patient]]):Unit =
      import org.aurora.shared._, dto._
      import zio.json._
      nextValue.foreach( p => info(s"Observer reporting: List[Patient].size = ${p.size}") )
    }
  
  
  behavior of "fetch"
    it should ("work with var observer") in {
      val patientsVar = Var[Option[List[Patient]]](Some(List[Patient]()))

      // patients --> patientsVar.writer  would be how you express this in Lamainar for front-end development
      // this does not work outside of front-end environments 
      //also note that adding the observer triggers patients to fetch and transmit to observers
      Fetch.patients.addObserver(patientsVar.writer)
      patientsVar.signal.foreach( p => info(s"patientVar size ${p.get.size}") )
      

      // composes the delay with then reviewing desired results afterwards
      for {    _ <- delay(1500) } yield {      
        if (patientsVar.now().get.size >0) 
          assert(true)
        else
          fail("no patients fetched from server")
      }
    }


    it should ("work with writing to var observer via foreach") in {
      val patientsVar = Var[Option[List[Patient]]](Some(List[Patient]()))
      val eventBusCounter = 

      Fetch.patients.foreach{r => 
        r match {
          case s:Some[List[Patient]] => patientsVar.writer.onNext(s)
          case None => info("no patients fetched from server")
        }
      }

      //remember foreach is adding an observer and thus activates the event stream
      patientsVar.signal.foreach( p => info(s"patientVar size ${p.get.size}") )
      

      for {    _ <- delay(1500) } yield {     
        if (patientsVar.now().get.size >0) 
          assert(true)
        else
          fail("no patients fetched from server")
      }
    }


    it should ("count number of fetches to be 3") in {
      val patientsVar = Var[Option[List[Patient]]](Some(List[Patient]()))
      val eventBusCounter = Var[Int](0)

      //fetch 3 x and update patientsVar and eventBusCounter
      (1 to 3) foreach { _ => 
        Fetch.patients.foreach{r => 
          r match {
            case s:Some[List[Patient]] => 
              patientsVar.writer.onNext(s)
              eventBusCounter.writer.onNext(eventBusCounter.now() + 1)
            case None => info("no patients fetched from server")
          }
        }
      }

      eventBusCounter.signal.foreach( p => info(s"eventBusCounter size ${p}") )

      for {    _ <- delay(1500) } yield {  

        if (patientsVar.now().get.size > 0) 
          assert(true)
          else
          fail("no patients fetched from server")

        if (eventBusCounter.now() == 3) 
          assert(true)
        else
          fail("total number of fetches was not 3")
      }
    }
}