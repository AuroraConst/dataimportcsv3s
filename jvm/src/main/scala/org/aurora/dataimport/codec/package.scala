package org.aurora.dataimport

package object codec:
  import ru.johnspade.csv3s.codecs.*
  import ru.johnspade.csv3s.codecs.*
  import ru.johnspade.csv3s.codecs.instances.given
  import ru.johnspade.csv3s.parser.*
  import scala.util.Try
  import java.time.LocalDate
  import java.time.format.DateTimeFormatter  

  trait PadSize:
    val s:String  
    val padSize:Int
    def padded = s.padTo(padSize,' ')
    lazy val trimmed = s.trim()

  case class AccountNumber(s:String) extends PadSize:
    override val padSize = 11 
    

  case class UnitNumber(s:String)   extends PadSize:
    override val padSize = 10 

  case class Name(s:String) extends PadSize:
    override val padSize = 30

  case class HealthCard(s:String) extends PadSize:
    override val padSize = 13

  case class Floor(s:String)  extends PadSize:
    override val padSize = 10

  case class Room(s:String)  extends PadSize:
    override val padSize = 10

  case class Bed(s:String) extends PadSize:
    override val padSize = 3  
  case class MRP(s:String) extends PadSize:
    override val padSize = 10
  case class AdmittingPhysician(s:String)  extends PadSize:
    override val padSize = 10
  case class PrimaryCare(s:String)  extends PadSize:
    override val padSize = 10



  case class ADM(accountNumber:AccountNumber,unitNumber:UnitNumber,name:Name,sex:String,birthDate:LocalDate,healthCard:HealthCard,admitDate:LocalDate,floor:Floor,room:Room,bed:Bed,
    mrp:MRP, admittingPhysician:AdmittingPhysician,primaryCare:PrimaryCare ,familyPrivileges:String ,hospitalistFlag:String ,service:String ,f17:String,f18:String,f19:String,f20:String,
    f21:String,f22:String,f23:String,f24:String,f25:String,f26:String,f27:String,f28:String  )

  given decoder: RowDecoder[ADM] = RowDecoder.derived
  given encoder: RowEncoder[ADM] = RowEncoder.derived

  given StringEncoder[AccountNumber] = _.padded
  given StringDecoder[AccountNumber] =  s => Try(AccountNumber(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[UnitNumber] = _.padded
  given StringDecoder[UnitNumber] =  s => Try(UnitNumber(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Name] = _.padded
  given StringDecoder[Name] =  s => Try(Name(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[HealthCard] = _.padded
  given StringDecoder[HealthCard] =  s => Try(HealthCard(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Floor] = _.padded
  given StringDecoder[Floor] =  s => Try(Floor(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Room] = _.padded
  given StringDecoder[Room] =  s => Try(Room(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Bed] = _.padded
  given StringDecoder[Bed] =  s => Try(Bed(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[MRP] = _.padded
  given StringDecoder[MRP] =  s => Try(MRP(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[AdmittingPhysician] = _.padded
  given StringDecoder[AdmittingPhysician] =  s => Try(AdmittingPhysician(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[PrimaryCare] = _.padded
  given StringDecoder[PrimaryCare] =  s => Try(PrimaryCare(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  import java.time.LocalDate

  val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

  given StringEncoder[LocalDate] = d => dateFormatter.format(d)
  given StringDecoder[LocalDate] =  s => Try(LocalDate.parse(s,dateFormatter))
      .toEither
      .left
      .map(e => DecodeError.TypeError(e.getMessage))