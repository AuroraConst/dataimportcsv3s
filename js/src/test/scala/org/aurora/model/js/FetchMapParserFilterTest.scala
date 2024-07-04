package org.aurora.model.js

import org.scalatest._, Assertions._, funspec.AsyncFunSpec 
import org.scalatest.flatspec._, Assertions.*
import scala.util.Try

import com.raquo.laminar.api.L.{*, given}
import com.raquo.airstream.ownership.OneTimeOwner

import scala.scalajs._
import scala.scalajs.concurrent.JSExecutionContext
import scala.concurrent.{Future,Promise}

import zio.json._
import org.aurora.model.shared.dto.Patient
import org.aurora.model.js.Fetch



/**
 * Trait AsyncFlatSpec is so named because your specification text and tests line up flat 
 * against the left-side indentation level, with no nesting needed. 
 */
class FetchMapParserFilterTest extends AsyncFlatSpec {

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

  
  behavior of "fetchx"
    it should ("fetchx") in {

      val patientsVar = Var[List[Patient]](List[Patient]())
      Fetch.patients.map{ 
        case Some(l) => l
        case None => List[Patient]()
      }.addObserver(patientsVar.writer)


      for {    _ <- delay(1500) } yield {      
        if (patientsVar.now().size >0) 
          assert(true)
        else  
          fail("no patients fetched from server")
      }
    }

    it should ("work with var observer") in {
      import org.aurora.model.patientfilter
      import filter.{*,given}
      val searchterms = patientfilter.parseSearchTerms("smith")
     
      val filterF = pfilter[Patient](searchterms)

      val patientsVar = Var[List[Patient]](List[Patient]())
      val filteredPatients = Var[List[Patient]](List[Patient]())

      patientsVar.signal.foreach{ l => 
        info(s"patient list size = ${l.size}")
        val uniqueFloors = l.map(_.floor).distinct
        info(s"unique floors $uniqueFloors"  )
      }
      filteredPatients.signal.foreach{l => 
        info(s"filtered patient list size = ${l.size}")
        l.foreach(i => info(i.floor.getOrElse("")))
      }
      
      patientsVar.signal.map{ l => l.filter(filterF)}
        .addObserver(filteredPatients.writer)

      Fetch.patients.map{ 
        case Some(l) => l
        case None => List[Patient]()
      }.addObserver(patientsVar.writer)


      for {    _ <- delay(1500) } yield {      
        if (patientsVar.now().size >0) 
          assert(true)
        else
        if(patientsVar.now().size > filteredPatients.now().size)  
          assert(true)
        else  
          fail("no patients fetched from server")
      }
    }


}