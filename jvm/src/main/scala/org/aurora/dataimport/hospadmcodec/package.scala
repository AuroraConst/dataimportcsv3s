package org.aurora.dataimport

import scala.compiletime.ops.boolean

package object hospadmcodec:

  import utils._
  import java.time.LocalDate
  import ru.johnspade.csv3s._, codecs._, parser._ , instances.given
  import java.time.LocalDate
  import utils.{_,given}

  case class HospADM(accountNumber:AccountNumber,unitNumber:UnitNumber,name:Name,sex:String,
    birthDate:BirthDate,healthCard:HealthCard,admitDate:LocalDate,floor:Floor,room:Room,bed:Bed,
    mrp:MRP, admittingPhysician:AdmittingPhysician,primaryCare:PrimaryCare ,familyPrivileges:FamilyPrivileges,
    hospitalistFlag:HospitalistFlag,flag:Flag ,service:Service
    )

  given decoder: RowDecoder[HospADM] = RowDecoder.derived
  given encoder: RowEncoder[HospADM] = RowEncoder.derived


  