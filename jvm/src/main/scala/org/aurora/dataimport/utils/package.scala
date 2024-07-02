import org.aurora.dataimport
import ru.johnspade.csv3s._, codecs._, parser._ , instances.given
import scala.util.Try
import java.time.LocalDate
import java.time.format.DateTimeFormatter  


package object utils:
  trait PadSize:
    val s:String  
    val padSize:Int
    def padded = s.padTo(padSize,' ')
    lazy val trimmed = s.trim()

  trait JavaTimeLocalDate:
    def localdate:LocalDate

  import java.time._
  val dateFormat = format.DateTimeFormatter.ofPattern("dd/MM/yyyy")

  object javatime:
    def localDate(s:String):LocalDate = LocalDate.parse(s,dateFormat )
    def formattedString(d:LocalDate):String =  dateFormat.format(d)
    
  

  case class AccountNumber(s:String) extends PadSize:
    override val padSize = 11 
    


  case class UnitNumber(s:String)   extends PadSize:
    override val padSize = 10 

  case class Name(s:String) extends PadSize:
    override val padSize = 30
    lazy val lastName = s.split(",")(0).trim()
    lazy val firstName = s.split(",")(1).trim()

  case class BirthDate(s:String) extends PadSize :
    override val padSize = 10
    def optLocalDate = Try( java.time.LocalDate.parse(s,dateFormat) ).toOption

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

  case class FamilyPrivileges(s:String)  extends PadSize:
    override val padSize = 1
  case class HospitalistFlag(s:String)  extends PadSize:
    override val padSize = 1
  case class Flag(s:String) extends PadSize:
    override val padSize = 1

  case class Service(s:String)  extends PadSize:
    override val padSize = 40  

  case class Field1(s:String)   extends PadSize:
    override val padSize = 1
  case class Field8(s:String)  extends PadSize:
    override val padSize = 8  
  
  case class Field10(s:String)  extends PadSize:
    override val padSize = 10  

  case class Field18(s:String)  extends PadSize:
    override val padSize = 18
  
  case class Field20(s:String)  extends PadSize:
    override val padSize = 20
  
  case class Field30(s:String)  extends PadSize:
    override val padSize = 30
  

  case class Field40(s:String)  extends PadSize:
    override val padSize = 40  




 
  given StringEncoder[Field1] = _.padded
  given StringDecoder[Field1] =  s => Try(Field1(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Field8] = _.padded
  given StringDecoder[Field8] =  s => Try(Field8(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Field10] = _.padded
  given StringDecoder[Field10] =  s => Try(Field10(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Field18] = _.padded
  given StringDecoder[Field18] =  s => Try(Field18(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Field20] = _.padded
  given StringDecoder[Field20] =  s => Try(Field20(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Field30] = _.padded
  given StringDecoder[Field30] =  s => Try(Field30(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Field40] = _.padded
  given StringDecoder[Field40] =  s => Try(Field40(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))


  given StringEncoder[HospitalistFlag] = _.padded
  given StringDecoder[HospitalistFlag] =  s => Try(HospitalistFlag(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Flag] = _.padded
  given StringDecoder[Flag] =  s => Try(Flag(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[Service] = _.padded
  given StringDecoder[Service] =  s => Try(Service(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  

  given StringEncoder[AccountNumber] = _.padded
  given StringDecoder[AccountNumber] =  s => Try(AccountNumber(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[UnitNumber] = _.padded
  given StringDecoder[UnitNumber] =  s => Try(UnitNumber(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))

  given StringEncoder[BirthDate] = _.padded
  given StringDecoder[BirthDate] =  s => Try(BirthDate(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))


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
  
  given StringEncoder[FamilyPrivileges] = _.padded
  given StringDecoder[FamilyPrivileges] =  s => Try(FamilyPrivileges(s)).toEither.left.map(e => DecodeError.TypeError(e.getMessage))
  


  import java.time.LocalDate

  val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

  given StringEncoder[LocalDate] = d => dateFormatter.format(d)
  given StringDecoder[LocalDate] =  s => Try(LocalDate.parse(s,dateFormatter))
      .toEither
      .left
      .map(e => DecodeError.TypeError(e.getMessage))    

end utils