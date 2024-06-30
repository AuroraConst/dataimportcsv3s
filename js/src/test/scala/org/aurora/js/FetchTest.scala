package org.aurora.js

import org.scalatest._, Assertions._, funspec.AsyncFunSpec 
import scala.util.Try

import com.raquo.laminar.api.L.{*, given}
import com.raquo.airstream.ownership.OneTimeOwner
import scala.scalajs._
import org.scalatest.flatspec._, Assertions.*
import scala.concurrent.Future
import scala.runtime.stdLibPatches.language.future

import zio.json._
import org.aurora.shared._, dto._
import scala.concurrent.Promise
import scala.scalajs.concurrent.JSExecutionContext

//TODO https://stackoverflow.com/questions/46617946/sleep-inside-future-in-scala-js


/**
 * Trait AsyncFlatSpec is so named because your specification text and tests line up flat 
 * against the left-side indentation level, with no nesting needed. 
 */
class FetchTest extends AsyncFlatSpec {

  info("this is my FetchTest")
  given Owner = new OneTimeOwner(()=>())
  def delay(milliseconds: Int): Future[Unit] = {
    val p = Promise[Unit]()
    js.timers.setTimeout(milliseconds) {
      p.success(())
    }
    p.future
  }

  
  
  val patients = 
    FetchStream.get("http://localhost:8080/patientsjson").
    map(s => s.fromJson[List[Patient]]).
    map(p => p.toOption)
    


  def listObserver =   new Observer[Option[List[Patient]]] {
          override def onError(err: Throwable): Unit = info(fail(err.getMessage()))
          override def onTry(nextValue: Try[Option[List[Patient]]]): Unit = ()
          override def onNext(nextValue: Option[List[Patient]]):Unit =
            import org.aurora.shared._, dto._
            import zio.json._
            nextValue.foreach( p => info(s"$p") )
        }
  
  implicit override def executionContext = JSExecutionContext.queue
  behavior of "fetch"

    it should ("fetch from server") in {
      patients.addObserver(listObserver)

      // val varoptPatientList = Var[Option[List[Patient]]](Option.empty[List[Patient]])
      // varoptPatientList.signal.addObserver(listObserver)

      // patients --> varoptPatientList.writer

      for {
        _ <- delay(500)
      } yield {
        assert(true)
      }
      //  Future(assert(true))
    }  
}