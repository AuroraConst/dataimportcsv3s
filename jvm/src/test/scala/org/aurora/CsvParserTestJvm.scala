package org.aurora

import org.scalatest._
import wordspec._, matchers._

class CsvParserTestJvm extends AnyWordSpec with should.Matchers{
  "this" should {
    "work" in {
      import better.files._, File._, Dsl._ 
      val f  =  this.getClass().getResource("/adm.txt"). toFile 

      f.isEmpty should be (false)

      val lineIterator = f.map( _.lineIterator)
      for {
        i <- lineIterator
      } yield {

        import ru.johnspade.csv3s._, parser._, core.CSV._, codecs._, instances.given

        val csvParser = CsvParser(';') 
        i.drop(1)
        parseRow(i.next(),csvParser) match {
          case Right(row) => info(s"row: ${row.l}")
          case Left(e) => fail()
        }

      }
    }
  }
}
