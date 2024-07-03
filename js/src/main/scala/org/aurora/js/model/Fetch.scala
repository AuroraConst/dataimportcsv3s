package org.aurora.js.model

import zio.json._
import org.aurora.model.shared._, dto._
import com.raquo.laminar.api.L.{*, given}

object Fetch :
  def patients = 
    FetchStream.get("http://localhost:8080/patientsjson")
    .map(s => s.fromJson[List[Patient]])
    .map(p => p.toOption)

end Fetch

  
