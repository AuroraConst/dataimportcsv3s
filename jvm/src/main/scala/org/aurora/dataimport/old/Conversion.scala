package org.aurora.dataimport.old

import java.sql.Date
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

import scala.util.Failure
import scala.util.Success
import scala.util.Try
import java.util.UnknownFormatConversionException
import com.typesafe.scalalogging.LazyLogging

/**
 * @author Arnold
 */
object Conversion extends LazyLogging {

  /* Creating fields */
  case class FieldConvertor[A](name: String, fieldSize: Int, convertor: String => Try[A]) {
    var text: String = ""
    def get: Try[A] = convertor(text) 
  }

  case class PatientName(lastName: String, firstName: String)

  def correctDOB(dateO: Option[Date], ageSex: String): Option[Date] = {

    if (None != dateO) {
      val date = dateO.get;
      val cal: Calendar = Calendar.getInstance();
      val curYear = cal.get(Calendar.YEAR);
      val curDay = cal.get(Calendar.DAY_OF_MONTH);
      val curMonth = cal.get(Calendar.MONTH) + 1;

      val cal1: Calendar = Calendar.getInstance();
      cal1.setTime(date);
      val month = cal1.get(Calendar.MONTH) + 1;
      val day = cal1.get(Calendar.DAY_OF_MONTH);
      val year = cal1.get(Calendar.YEAR);

      val ageYM = ageSex.slice(0, ageSex.indexOf("/"));
      var ageY = 0;
      var ageM = 0;
      if (ageYM.contains(" ") == true) {
        ageY = ageYM.slice(0, ageYM.indexOf("Y")).toInt
        ageM = ageYM.slice(ageYM.indexOf("Y") + 2, ageYM.indexOf("M")).toInt
      } else {
        ageY = ageYM.toInt;
      }

      val diffYear = ageY;
      if (curYear > year) {
        var diffYear = Math.abs(year - curYear);
        if (month > curMonth) {
          //Age Month calculations can go here if needed
          diffYear = diffYear - 1;
        } else if ((month == curMonth) && (day >= curDay)) {

          diffYear = diffYear - 1;
        }

        if (diffYear != ageY) {
          val newDateInStr = (year - 100).toString() + "-" + month.toString() + "-" + day.toString();
          return Some(java.sql.Date.valueOf(newDateInStr));
        } else {
          return dateO;
        }
      } else {
        val newDateInStr = (year - 100).toString() + "-" + month.toString() + "-" + day.toString();
        return Some(java.sql.Date.valueOf(newDateInStr));
      }
    }
    dateO;
  }

  val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
  val dateTimeFormatter2 = DateTimeFormatter.ofPattern("dd/MM/yy")
  val dateTimeFormatddMMyyyyHHmmss = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

  /* conversion functions */
  def ddmmyyyyToDateOpt: String => Try[Option[Date]] = text => {
    if (text.trim().isEmpty()) {
      Success(null)
    } else {
      Try(Option(Date.valueOf(LocalDate.parse(text, dateTimeFormatter))))
    }
  }
  
  def ddmmyyyyToDate: String => Try[Date] = text => {
    if(text.trim().isEmpty()) Failure(null)
    else
      Try(Date.valueOf(LocalDate.parse(text, dateTimeFormatter)))
    
  }

  def ddmmyyToDate: String => Try[Option[Date]] = text => {
    if (text.trim().isEmpty()) {
      Success(null)
    } else {
      Try(Option(Date.valueOf(LocalDate.parse(text, dateTimeFormatter2))))
    }
  }
  
  def ddmmyyyyyhhmmss (s: String): Try[Date]  = Try{Date.valueOf(LocalDate.parse(s,dateTimeFormatddMMyyyyHHmmss))}

  def tryOptionToOption[T](t: Try[Option[T]]): Option[T] = t match {
    case Success(v) => v
    case Failure(_) => None
  }

  def tryOptionIntToInt(t: Try[Option[Int]]): Int = t match {
    case Success(v) => v.get
    case Failure(_) => 0
  }

  def plain: String => Try[String] = text => Try(text.trim)
  def textOpt: String => Try[Option[String]] = text => text.trim match {
    case t if(t.size==0) => Try(None)
    case default => Try(Some(default))
  }
  def number: String => Try[Option[Int]] = text => Try(Option(java.lang.Integer.parseInt(text.trim)))
  def names: String => Try[PatientName] = text => {
    if (text.contains(",")) {
      Try(PatientName(text.substring(0, text.indexOf(",")).trim(), text.substring(text.indexOf(",") + 1).trim()))
    } else {
      Try(PatientName(text.trim(), ""))
    }
  }
  
  def DPDTerm: String => Try[String] = text => {Try(text.trim.replace(" - ","_").replace(' ', '_'))}

  case class SnomedFSN(fsn: String, category: String)

  def fsnCategory: String => Try[SnomedFSN] = text => {
    val pattern = "\\((.*?)\\)".r
    pattern.findFirstIn(text) match {
      case Some(r) => {
        val category = r.substring(1, r.length() - 1)
        Try(SnomedFSN(text.substring(0, text.length - category.length - 2).trim.replace(' ', '_'), category))
      }
      case None => Try(SnomedFSN(text.trim(), ""))
    }

  }

  def admFieldConvertors = List(
    FieldConvertor("Account", 11, plain),
    FieldConvertor("UnitNumber", 10, plain),
    FieldConvertor("PatientName", 30, names),
    FieldConvertor("Sex", 1, plain),
    FieldConvertor("DOB", 10, ddmmyyyyToDate),
    FieldConvertor("HCN", 13, plain),
    FieldConvertor("Admit Date", 10, ddmmyyyyToDate),
    FieldConvertor("Location", 10, plain),
    FieldConvertor("Room", 10, plain),
    FieldConvertor("Bed", 3, plain),
    FieldConvertor("Admitting", 10, plain),
    FieldConvertor("Attending", 10, plain),
    FieldConvertor("Family", 10, plain),
    FieldConvertor("Fam Priv?", 1, plain),
    FieldConvertor("Hosp?", 1, plain),
    FieldConvertor("Flag", 1, plain),
    FieldConvertor("Service", 10, plain),
    FieldConvertor("Address1", 30, plain),
    FieldConvertor("Address2", 30, plain),
    FieldConvertor("City", 20, plain),
    FieldConvertor("Province", 2, plain),
    FieldConvertor("Postal", 10, plain),
    FieldConvertor("Home Phone", 18, plain),
    FieldConvertor("Work Phone", 18, plain),
    FieldConvertor("ALC Status", 10, plain),
    FieldConvertor("ALCDate", 8, plain))

    
  
  /**
   * extract down-casted data from FieldConvertor
   */
    
  def extract[A](f: FieldConvertor[_])  : A =  f.get.get.asInstanceOf[A] 
  

}