package org.aurora.shared

import org.scalatest._
import wordspec._
import matchers._

class FirstTestShared extends AnyWordSpec with should.Matchers:

  import zio.json.*
  import org.aurora.shared.given

  val person = Person("Arnold",50) 

  "Json <-> case class conversions" should {
    "case class to jsonstring" in {
      val json = person.toJson
      json should be("""{"name":"Arnold","age":50}""")
    }

    "jsonstring to case class" in {

      val p =  """{"name":"Arnold","age":50}""".fromJson[Person]     // """["Arnold",50] """.fromJson[Person]
      p match
        case Left(e)    =>  fail("failed to parse")
        case Right(p) => p should be(Person("Arnold", 50))
    }
  }        