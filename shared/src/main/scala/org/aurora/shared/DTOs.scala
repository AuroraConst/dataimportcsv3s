package org.aurora.shared
import zio.json._

case class Person(name:String,age:Int)

object Person :
  implicit val encoder: JsonEncoder[Person] = DeriveJsonEncoder.gen[Person]
  implicit val decoder: JsonDecoder[Person] = DeriveJsonDecoder.gen[Person]