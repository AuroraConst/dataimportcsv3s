package org.aurora.shared

package object dto:
  import zio.json._
  import java.time._ //cross scalajs and jvm compatible


  case class Patient(
    unitNumber: String,
    lastName: String,
    firstName: String,
    sex: String,
    dob: String,
    hcn: Option[String],
    admitDate: Option[LocalDateTime],
    floor: Option[String],
    room: Option[String],
    bed: Option[String],
    family: Option[String],
    famPriv: Option[String],
    hosp: Option[String],
    flag: Option[String],
    address1: Option[String],
    address2: Option[String],
    city: Option[String],
    province: Option[String],
    postalCode: Option[String],
    homePhoneNumber: Option[String],
    workPhoneNumber: Option[String],
    OHIP: Option[String],
    familyPhysician: Option[String],
    attending: Option[String],
    collab1: Option[String],
    collab2: Option[String]
  )

  object Patient:
    given JsonDecoder[Patient] = DeriveJsonDecoder.gen[Patient]
    given JsonEncoder[Patient] = DeriveJsonEncoder.gen[Patient]
      


end dto
