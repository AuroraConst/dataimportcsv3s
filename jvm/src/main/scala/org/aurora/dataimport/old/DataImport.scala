package org.aurora.dataimport.old

// import java.io.BufferedReader

// import scala.util.Failure
// import scala.util.Success
// import scala.util.Try

// import org.platonics.scala.data.utils.FieldConvertors.{ ADM, EDM, HOSPADM, LOINC, NOTE, SNOMED, DRUG, USER, BILLINGCODES }
// import org.platonics.datatransferobject.{ Patient, Account, PatientAccountData, Loinc, Drug, Pcm, Snomed, BillingCode }
// import org.platonics.scala.patientdata.ImportCases.ImportReader
// import org.platonics.scala.patientdata.ImportCases.ImportType
// import org.platonics.scala.patientdata.ImportCases.PackageReader

// import com.typesafe.scalalogging.LazyLogging

// /**
//  * @author Praveen
//  */
// object DataImport extends LazyLogging {

//   private[DataImport] case class Record(patient: Try[Patient], account: Try[Account])

//   def reader[T <: ImportType](adm: ImportReader[T]): Try[BufferedReader] = {
//     adm.reader
//   }

//   def testPatientAccountData(adm: PackageReader): List[PatientAccountData] = {
//     adm.reader match {
//       case Success(x) => IO.importCaseToBeanList[ADM](adm)
//       case Failure(x) => { logger.error("failed to open buffer"); Nil }
//     }
//   }

//   def testEDMPatientAccountData(edm: PackageReader): List[PatientAccountData] = {
//     edm.reader match {
//       case Success(x) => IO.importCaseToBeanList[EDM](edm)
//       case Failure(x) => { logger.error("failed to open buffer"); Nil }
//     }
//   }

//   def testHOSPADMPatientAccountData(hospadm: PackageReader): List[PatientAccountData] = {
//     hospadm.reader match {
//       case Success(x) => IO.importCaseToBeanList[HOSPADM](hospadm)
//       case Failure(x) => { logger.error("failed to open buffer"); Nil }
//     }
//   }

//   def testNoteData(note: PackageReader): List[Pcm] = {
//     note.reader match {
//       case Success(x) => IO.importCaseToBeanList[NOTE](note)
//       case Failure(x) => { logger.error("failed to open buffer"); Nil }
//     }
//   }

//   def snowmedCTTerms(snowmed: PackageReader): List[Snomed] = {
//     snowmed.reader match {
//       case Success(x) => IO.importCaseToBeanList[SNOMED](snowmed)
//       case Failure(x) => { logger.error("failed to open buffer"); Nil }
//     }
//   }

//   def loincTerms(loinc: PackageReader): List[Loinc] = {
//     loinc.reader match {
//       case Success(x) => IO.importCaseToBeanList[LOINC](loinc)
//       case Failure(x) => { logger.error("failed to open buffer"); Nil }
//     }
//   }

//   def drugTerms(drug: PackageReader): List[Drug] = {
//     drug.reader match {
//       case Success(x) => IO.importCaseToBeanList[DRUG](drug)
//       case Failure(x) => { logger.error("failed to open buffer"); Nil }
//     }
//   }

//   def billingCodeTerms(billingCode: PackageReader): List[BillingCode] = {
//     billingCode.reader match {
//       case Success(x) => IO.importCaseToBeanList[BILLINGCODES](billingCode);
//       case Failure(x) => { logger.error("failed to open buffer"); Nil }
//     }
//   }

// }