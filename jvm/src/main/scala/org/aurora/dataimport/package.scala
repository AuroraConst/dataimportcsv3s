package org.aurora
import org.aurora.shared.dto.Patient
import dataimport.admcodec.{ADM, given}

package object dataimport:
  import admcodec._
  
  private def admFile = 
    import com.typesafe.config._
    import better.files._, Dsl._
    val config: Config = ConfigFactory.load()
    val admpath = config.getString("app.adm.path")
    File(admpath)
  
  private def importAdm():List[ADM] = 
    import better.files._, Dsl._
    import ru.johnspade.csv3s._, parser._
    import admcodec.given
    
    val csvParser = CsvParser(';')
    def parseLine(line:String) = 
      val result = for (
        row <- parseRow(line,csvParser);
        adm  <- decoder.decode(row)
      ) yield adm
      result
    end parseLine

    val lineIterator = admFile.lineIterator
    lineIterator.next() //skip header
    lineIterator.map( parseLine(_)).collect{ case Right(adm) => adm}.toList
  end importAdm


  private def patient(adm:ADM):Patient = 
    Patient(
      unitNumber = adm.unitNumber.trimmed,
      lastName = adm.name.lastName,
      firstName = adm.name.firstName,
      sex = adm.sex,
      dob = adm.birthDate.toString,
      hcn = Some(adm.healthCard.trimmed),
      family = Some(adm.primaryCare.trimmed),
      famPriv = Some(adm.familyPrivileges.trimmed),
      hosp = Some(adm.hospitalistFlag.trimmed),
      flag = Some(adm.flag.trimmed),
      address1 = Some(adm.f17.trimmed),
      address2 = Some(adm.f18.trimmed),
      city = Some(adm.f19.trimmed),
      province = Some(adm.f20.trimmed),
      postalCode =  Some(adm.f21.trimmed),
      homePhoneNumber = Some(adm.f22.trimmed),
      workPhoneNumber = Some(adm.f23.trimmed),
      OHIP = Some(adm.f24.trimmed),
      familyPhysician = Some(adm.primaryCare.trimmed),
      attending = Some(adm.mrp.trimmed),
      collab1 = None,
      collab2 = None
      )
  end patient

  def importpatients:List[Patient] = importAdm().map(patient(_))
end dataimport
