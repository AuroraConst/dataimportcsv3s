package org.aurora.dataimport

import org.scalatest._, wordspec._, matchers._

class ImportPatientTest extends AnyWordSpec with should.Matchers{
  "import Patient list " should {
    "contain more than 100 entities" in {
      importpatients.size should be > 100
    }

    "encode to json and back to List[Patient]" in {
      import org.aurora.shared.dto.Patient
      import zio.json._
      val patients = importpatients
      val jsonPatients = patients.toJson
      
      val eitherList = jsonPatients.fromJson[List[Patient]]
      eitherList.getOrElse(Nil) should be (patients)
    }
  }


}
