package org.aurora.dataimport
import utils.{_,given}


object admcodec:
  import ru.johnspade.csv3s._, codecs._, parser._ , instances.given
  import java.time.LocalDate


  case class ADM(accountNumber:AccountNumber,unitNumber:UnitNumber,name:Name,sex:String,
    birthDate:BirthDate,healthCard:HealthCard,admitDate:LocalDate,floor:Floor,room:Room,bed:Bed,
    mrp:MRP, admittingPhysician:AdmittingPhysician,primaryCare:PrimaryCare ,familyPrivileges:FamilyPrivileges,
    hospitalistFlag:HospitalistFlag,flag:Flag ,service:Service ,f17:Field40,f18:Field30,f19:Field30,f20:Field20,
    f21:Field1,f22:Field10,f23:Field18,f24:Field18,f25:Field10,f26:Field8)

  given decoder: RowDecoder[ADM] = RowDecoder.derived
  given encoder: RowEncoder[ADM] = RowEncoder.derived

end admcodec  

