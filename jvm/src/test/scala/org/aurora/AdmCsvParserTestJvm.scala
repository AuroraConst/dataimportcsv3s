package org.aurora.dataimport

import org.scalatest._
import wordspec._, matchers._

class AdmCsvParserTestJvm extends AnyWordSpec with should.Matchers{
  "header field count and line field count" should  {
    "be 16 and 27 respectively" in {
      import better.files._, File._, Dsl._ 
      val f  =  this.getClass().getResource("/adm.txt"). toFile 
      f.isEmpty should be (false)
      val it = f.get.lineIterator
      val header = it.next
      val line1 = it.next
      val line2 = it.next
      header.count(_ == ';') should be (16)
      line1.count(_ == ';') should be (27)
      line2.count(_ == ';') should be (27)
    }
  }



  "Parsing a row to ADM" should {
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
        i.next

        import org.aurora.dataimport._, admcodec.given
        import admcodec.given
        
        parseRow(i.next,csvParser).map { decoder.decode(_)  } 
        match {
          case Right(admRow) => info(s"row: ${admRow}")
          case Left(e) => fail()
        }

      }




    }
  }
}
