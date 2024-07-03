package org.aurora.js.model

import org.scalatest._, wordspec._, matchers._
import org.aurora.model.shared.dto.Patient


class GivenFilterTest extends AnyWordSpec with should.Matchers{
  "this" should {
    "work" in {

      import org.aurora.js.model.filter.{*,given}
      import org.aurora.model.patientfilter
      val searchterms = patientfilter.parseSearchTerms("")  

      val filterF = filter.pfilter[Patient](searchterms)

      List[Patient]().filter(filterF) should be(List[Patient]())
    }
  }
}
