package org.aurora.dataimport.old

// import scala.collection.mutable.HashMap


// object FieldConvertors {
//   sealed abstract class ConvertorType
//   case class ADM() extends ConvertorType
//   case class EDM() extends ConvertorType
//   case class SNOMED() extends ConvertorType
//   case class DRUG() extends ConvertorType
//   case class NOTE() extends ConvertorType
//   case class HOSPADM() extends ConvertorType
//   case class BILLINGCODES() extends ConvertorType
//   case class LOINC() extends ConvertorType
//   case class USER() extends ConvertorType

//   trait FieldConvertorProvider[T <: ConvertorType] {
//     def convertorMap: HashMap[String, FieldConvertor[_]] = {
//       convertors.foldLeft(HashMap[String, FieldConvertor[_]]()) { (m, c) => m(c.name) = c; m }
//     }
//     def convertors: List[FieldConvertor[_]]
//   }

//   def provideConvertors[T <: ConvertorType](implicit p: FieldConvertorProvider[T]): List[FieldConvertor[_]] = {
//     p.convertors
//   }
//   def provideConvertorMap[T <: ConvertorType](implicit p: FieldConvertorProvider[T]): HashMap[String, FieldConvertor[_]] = {
//     p.convertorMap
//   }
//   def provideConvertorKeys[T <: ConvertorType](implicit p: FieldConvertorProvider[T]): List[String] = {
//     p.convertors.map { _.name }
//   }

//   import Conversion._

//   implicit def implicitADMConvertor = new FieldConvertorProvider[ADM] {
//     def convertors: List[FieldConvertor[_]] = {
//       List(
//         FieldConvertor("Account", 11, plain),
//         FieldConvertor("UnitNumber", 10, plain),
//         FieldConvertor("PatientName", 30, names),
//         FieldConvertor("Sex", 1, plain),
//         FieldConvertor("DOB", 10, ddmmyyyyToDate),
//         FieldConvertor("HCN", 13, textOpt),
//         FieldConvertor("Admit Date", 10, ddmmyyyyToDateOpt),
//         FieldConvertor("Location", 10, plain),
//         FieldConvertor("Room", 10, plain),
//         FieldConvertor("Bed", 3, plain),
//         FieldConvertor("Admitting", 10, plain),
//         FieldConvertor("Attending", 10, plain),
//         FieldConvertor("Family", 10, textOpt),
//         FieldConvertor("Fam Priv?", 1, textOpt),
//         FieldConvertor("Hosp?", 1, textOpt),
//         FieldConvertor("Flag", 1, textOpt),
//         FieldConvertor("Service", 10, plain),
//         FieldConvertor("Address1", 30, textOpt),
//         FieldConvertor("Address2", 30, textOpt),
//         FieldConvertor("City", 20, textOpt),
//         FieldConvertor("Province", 2, textOpt),
//         FieldConvertor("Postal", 10, textOpt),
//         FieldConvertor("Home Phone", 18, textOpt),
//         FieldConvertor("Work Phone", 18, textOpt),
//         FieldConvertor("ALC Status", 10, plain),
//         FieldConvertor("ALCDate", 8, textOpt))
//     }
//   }

//   implicit def implicitEDMConvertor = new FieldConvertorProvider[EDM] {
//     def convertors: List[FieldConvertor[_]] = {
//       List(
//         FieldConvertor("EDRegistrationDate", 8, ddmmyyToDate),
//         FieldConvertor("EDRegistrationTime", 4, plain),
//         FieldConvertor("Skip1", 10, plain),
//         FieldConvertor("LastName", 15, plain),
//         FieldConvertor("FirstName", 15, plain),
//         FieldConvertor("UnitNumber", 10, plain),
//         FieldConvertor("AccNumber", 12, plain),
//         FieldConvertor("DOB", 8, ddmmyyToDate),
//         FieldConvertor("AgeSex", 9, plain),
//         FieldConvertor("Address1", 30, textOpt),
//         FieldConvertor("Address2", 30, textOpt),
//         FieldConvertor("Address3", 7, textOpt),
//         FieldConvertor("Address4", 2, textOpt),
//         FieldConvertor("Phone", 12, textOpt),
//         FieldConvertor("HCN", 13, textOpt),
//         FieldConvertor("Skip2", 13, plain),
//         FieldConvertor("Skip3", 10, plain),
//         FieldConvertor("OHIP", 10, textOpt),
//         FieldConvertor("Skip4", 10, plain),
//         FieldConvertor("EDDischargeDate", 8, ddmmyyToDate),
//         FieldConvertor("EDDischargeTime", 4, plain),
//         FieldConvertor("Skip5", 10, plain),
//         FieldConvertor("Skip6", 13, plain),
//         FieldConvertor("FPhysician", 10, textOpt),
//         FieldConvertor("EDPhysician", 10, plain),
//         FieldConvertor("EDNurse", 10, plain),
//         FieldConvertor("Skip7", 1, plain))
//     }
//   }

//   implicit def implicitHOSPADMConvertor = new FieldConvertorProvider[HOSPADM] {
//     def convertors: List[FieldConvertor[_]] = {
//       List(
//         FieldConvertor("Account", 11, plain),
//         FieldConvertor("UnitNumber", 10, plain),
//         FieldConvertor("PatientName", 30, names),
//         FieldConvertor("Sex", 1, plain),
//         FieldConvertor("DOB", 10, ddmmyyyyToDate),
//         FieldConvertor("HCN", 13, textOpt),
//         FieldConvertor("Admit Date", 10, ddmmyyyyToDateOpt),
//         FieldConvertor("Location", 10, plain),
//         FieldConvertor("Room", 10, plain),
//         FieldConvertor("Bed", 3, plain),
//         FieldConvertor("Admitting", 10, plain),
//         FieldConvertor("Attending", 10, plain),
//         FieldConvertor("Family", 10, textOpt),
//         FieldConvertor("Fam Priv?", 1, textOpt),
//         FieldConvertor("Hosp?", 1, textOpt),
//         FieldConvertor("Flag", 1, textOpt),
//         FieldConvertor("Service", 10, plain))
//     }
//   }
  
//   implicit def implicitLOINCConvert = new FieldConvertorProvider[LOINC] {
//     def convertors: List[FieldConvertor[_]] = {
//       List(
//           FieldConvertor("LOINC_NUM", 0, plain),
//           FieldConvertor("LONG_COMMON_NAME", 0, plain),
//           FieldConvertor("SHORT_NAME", 0, plain),
//           FieldConvertor("CLASS", 0, plain),
//           FieldConvertor("RELATED_TERMS", 0, plain),
//           FieldConvertor("RANK", 0, plain))
//     }
//   }

//   implicit def implicitSNOMEDConvert = new FieldConvertorProvider[SNOMED] {
//     def convertors: List[FieldConvertor[_]] = {
//       List(
//         FieldConvertor("SNOMED_CID", 0, plain),
//         FieldConvertor("SNOMED_FSN", 0, fsnCategory),
//         FieldConvertor("SNOMED_CONCEPT_STATUS", 0, plain),
//         FieldConvertor("UMLS_CUI", 0, plain),
//         FieldConvertor("OCCURENCE", 0, plain),
//         FieldConvertor("USAGE", 0, plain),
//         FieldConvertor("FIRST_IN_SUBSET", 0, plain),
//         FieldConvertor("IS_RETIRED_FROM_SUBSET", 0, plain),
//         FieldConvertor("LAST_IN_SUBSET", 0, plain),
//         FieldConvertor("REPLACED_BY_SNOMED_CID", 0, plain))
//     }
//   }

//     implicit def implicitDRUGConvert = new FieldConvertorProvider[DRUG] {
//     def convertors: List[FieldConvertor[_]] = {
//       List(FieldConvertor("DRUG_CODE", 0, plain), 
//            FieldConvertor("PRODUCT_CATEGORIZATION", 0, plain), 
//            FieldConvertor("DRUG_CLASS", 0, plain), 
//            FieldConvertor("DRUG_IDENTIFICATION_NUMBER", 0, plain), 
//            FieldConvertor("BRAND_NAME", 0, DPDTerm), 
//            FieldConvertor("DESCRIPTOR", 0, plain), 
//            FieldConvertor("PEDIATRIC_FLAG", 0, plain), 
//            FieldConvertor("ACCESSION_NUMBER", 0, plain), 
//            FieldConvertor("NUMBER_OF_AIS", 0, plain), 
//            FieldConvertor("LAST_UPDATE_DATE", 0, plain), 
//            FieldConvertor("AI_GROUP_NO", 0, plain), 
//            FieldConvertor("CLASS_F", 0, plain), 
//            FieldConvertor("BRAND_NAME_F", 0, plain), 
//            FieldConvertor("DESCRIPTOR_F", 0, plain)
//        )}
//     }
  
//   implicit def implicitNOTEConvert = new FieldConvertorProvider[NOTE] {
//     def convertors: List[FieldConvertor[_]] = {
//       List(
//         FieldConvertor("SEQNUMBER", 10, number),
//         FieldConvertor("ACCNUMBER", 11, plain),
//         FieldConvertor("TIMESTAMP", 10, ddmmyyyyToDate),
//         FieldConvertor("MODEL", 10, plain))
//     }
//   }
  
  
//   implicit def implicitBILLINGConvert = new FieldConvertorProvider[BILLINGCODES] {
//     def convertors: List[FieldConvertor[_]] = {
//       List(
//         FieldConvertor("BILLINGCODE", 6, plain),
//         FieldConvertor("LABEL", 35, plain),
//         FieldConvertor("SPECIALTY", 35, plain),
//         FieldConvertor("AMOUNT", 5, plain),
//         FieldConvertor("NOTE", 115, plain))
//     }
//   }
  
//   implicit def implicitUSERConvert = new FieldConvertorProvider[USER] {
//     def convertors: List[FieldConvertor[_]] = {
//       List(
//         FieldConvertor("USERNAME", 0, plain),
//         FieldConvertor("PASSWORD", 0, plain),
//         FieldConvertor("FIRSTNAME", 0, textOpt),
//         FieldConvertor("LASTNAME", 0, textOpt),
//         FieldConvertor("EMAIL", 0, textOpt),
//         FieldConvertor("AVATAR", 0, textOpt),
//         FieldConvertor("ROLE", 0, plain))
//     }
//   }

// }