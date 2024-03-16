package org.aurora.dataimport

import org.scalatest._
import wordspec._
import matchers._




import better.files._


class DataImportTest extends AnyWordSpec with should.Matchers{
  val resourceDir = ""/"jvm"/"src"/"test"/"resources"

  "adm.txt file" should {
    "exist" in {

      val file = resourceDir/"adm.txt"
      file.exists shouldBe true
    }

    "contain more than 100 lines" in {

      val file = resourceDir/"adm.txt"
      file.lines.size should be > 100
    }
  }
}
