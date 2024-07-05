package org.aurora.model.js

import org.scalatest._, wordspec._, matchers._
import org.aurora.model.shared.dto.Patient


class GivenFilterTest extends AnyWordSpec with should.Matchers{
  "this" should {
    "work" in {

      import org.aurora.model.js.filter.{*,given}
      import org.aurora.model.patientfilter
      val searchterms = patientfilter.parseSearchTerms("")  

      val filterF = filter.filterPredicate[Patient](searchterms)

      List[Patient]().filter(filterF) should be(List[Patient]())
    }
  }
}
