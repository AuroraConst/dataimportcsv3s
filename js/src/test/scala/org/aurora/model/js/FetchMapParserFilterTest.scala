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

  
  behavior of "filtering fetch result"
    it should ("work with var observer") in {
      import org.aurora.model.patientfilter
      import filter.{*,given}
      val searchterms = patientfilter.parseSearchTerms("300  ")
      info(s"#################$searchterms ##############")
     
      val filterF = filterPredicate[Patient](searchterms)

      val patientsVar = Var[List[Patient]](List[Patient]())
      val filteredPatients = Var[List[Patient]](List[Patient]())
      
      patientsVar.signal.map{ l => l.filter(filterF)}
        .addObserver(filteredPatients.writer)

      Fetch.patients.map{ 
        case Some(l) => l
        case None => List[Patient]()
      }.addObserver(patientsVar.writer)


      for {    _ <- delay(1500) } yield {
        info(s"${patientsVar.now().size} vs ${filteredPatients.now().size}")  
        if(patientsVar.now().size > filteredPatients.now().size) 
          filteredPatients.now().foreach{ p => 
            info(s"${p.mrp} ${p.floor} ${p.firstName} ${p.lastName}")
          }
          assert(true)
        else  
          fail("no patients fetched from server")
      }
    }


}