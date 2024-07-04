package org.aurora.model.js

import com.raquo.airstream.ownership.OneTimeOwner
import com.raquo.laminar.api.L.{*, given}
import org.aurora.model.shared.dto._
import org.aurora.model.shared.dto.Patient

object DataModel :

  private given Owner = new OneTimeOwner(()=>())

  val patientTableData = Var[Option[List[Patient]]](Some(List[Patient]()))
  
  
  val patientTableFilter = Var[String]("")

  val filteredPatientTableData = patientTableData.signal
    .map( p => p.get.filter( item => ??? ))




  
  
  def populateTable = Fetch.patients.foreach( p => patientTableData.set(p) )




end DataModel
  
