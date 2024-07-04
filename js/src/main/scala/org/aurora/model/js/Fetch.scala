package org.aurora.model.js

import zio.json._
import org.aurora.model.shared._, dto._
import com.raquo.laminar.api.L.{*, given}
import org.aurora.model.shared.dto.Patient

object Fetch :
  def patients = 
    FetchStream.get("http://localhost:8080/patientsjson")
    .map(s => s.fromJson[List[Patient]])
    .map(p => p.toOption)

end Fetch

  
