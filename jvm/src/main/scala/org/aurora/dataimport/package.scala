package org.aurora
import org.aurora.shared.dto.Patient
import dataimport.admcodec.{ADM, given}
import org.aurora.dataimport.hospadmcodec.HospADM
import dataimport.hospadmcodec.given

package object dataimport:
  import admcodec._
  import com.typesafe.config._
  import better.files._, Dsl._
  import ru.johnspade.csv3s._, ru.johnspade.csv3s.parser._, codecs._

  val config: Config = ConfigFactory.load()
  
  lazy val admFile =  File(config.getString("app.adm.path"))
  lazy val hospadmFile = File(config.getString("app.hospadm.path"))
  val csvParser = CsvParser(';')
  
  def parseLine[T] (line:String)(using decoder:RowDecoder[T]) =
    val result = for (
      row <- parseRow(line,csvParser);
      adm  <- decoder.decode(row)
    ) yield adm
    result

  private def importAdm():List[ADM] = 
    import admcodec.given
    val lineIterator = admFile.lineIterator
    lineIterator.next() //skip header
    lineIterator.map( parseLine[ADM](_)).collect{ case Right(adm) => adm}.toList
  end importAdm

  private def importHospAdm():List[HospADM] = 
    import hospadmcodec.given
    val lineIterator = hospadmFile.lineIterator
    lineIterator.next() //skip header
    lineIterator.map( parseLine[HospADM](_)).collect{ case Right(adm) => adm}.toList
  end importHospAdm

  private def adm(hospadm:HospADM): ADM = 
    import utils._
    ADM(
    hospadm.accountNumber,hospadm.unitNumber,hospadm.name,hospadm.sex,
    hospadm.birthDate,hospadm.healthCard,hospadm.admitDate,hospadm.floor,hospadm.room,hospadm.bed,
    hospadm.mrp,hospadm.admittingPhysician,hospadm.primaryCare,hospadm.familyPrivileges,
    hospadm.hospitalistFlag,hospadm.flag,hospadm.service,
    Field40(""),Field30(""),Field30(""),Field20(""),Field1(""),Field10(""),Field18(""),Field18(""),Field10(""),Field8("")
    )

  private def patient(adm:ADM):Patient = 
    import java.time._
    def stringOrNone(opt:String) = opt match {
      case "" => None
      case _ =>  Some(opt)
    }

    Patient(
      accountNumber = adm.accountNumber.trimmed,
      unitNumber = adm.unitNumber.trimmed,
      lastName = adm.name.lastName,
      firstName = adm.name.firstName,
      sex = adm.sex,
      dob = adm.birthDate.toString,
      hcn = Some(adm.healthCard.trimmed),
      admitDate = Some(adm.admitDate.atStartOfDay),
      floor = Some(adm.floor.trimmed),
      room =  Some(adm.room.trimmed),
      bed = Some(adm.bed.trimmed),
      family = Some(adm.primaryCare.trimmed),
      famPriv = Some(adm.familyPrivileges.trimmed).flatMap(stringOrNone),
      hosp = Some(adm.hospitalistFlag.trimmed).flatMap(stringOrNone),
      flag = Some(adm.flag.trimmed).flatMap(stringOrNone),
      address1 = Some(adm.f17.trimmed).flatMap(stringOrNone),
      address2 = Some(adm.f18.trimmed).flatMap(stringOrNone),
      city = Some(adm.f19.trimmed).flatMap(stringOrNone),
      province = Some(adm.f20.trimmed).flatMap(stringOrNone),
      postalCode =  Some(adm.f21.trimmed).flatMap(stringOrNone),
      homePhoneNumber = Some(adm.f22.trimmed).flatMap(stringOrNone),
      workPhoneNumber = Some(adm.f23.trimmed).flatMap(stringOrNone),
      OHIP = Some(adm.f24.trimmed).flatMap(stringOrNone),
      familyPhysician = Some(adm.primaryCare.trimmed).flatMap(stringOrNone),
      attending = Some(adm.mrp.trimmed).flatMap(stringOrNone),
      collab1 = None,
      collab2 = None
      )
  end patient

  def importpatients:List[Patient] = importAdm().map(patient(_))
end dataimport
