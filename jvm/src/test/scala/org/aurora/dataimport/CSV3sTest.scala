package org.aurora.dataimport

import org.scalatest._
import wordspec._
import matchers._




import better.files._
import ru.johnspade.csv3s.parser.*


class CSV3sTest extends AnyWordSpec with should.Matchers{
  val resourceDir = ""/"jvm"/"src"/"test"/"resources"
  val file = resourceDir/"adm.txt"
  val parser = CsvParser(';')

  "csv3s parser" should {
    "reveal 28 fields to a row" in {


      //skip header line
      val i = file.lineIterator.drop(1) 
      val line = i.next()

      val result = for {
        result <- parseRow(line,parser)
      }
      yield(result.l.size)
      result.isRight should be( true)
      result.getOrElse(0) should be (28)
    }

    
      "decode to ADM case class based on givens" in {
        import codec.{*,given}
        
        val i = file.lineIterator.drop(1) 
        val line = i.next()

        val result = for {
          result <- parseRow(line,parser)
        }
        yield(result)

        val adm = result.map(decoder.decode(_))
        adm.isRight should be (true)
        adm.right.get.right.get shouldBe a[ADM]
      }
  }
}
