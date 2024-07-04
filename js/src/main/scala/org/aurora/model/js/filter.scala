package org.aurora.model.js

import org.aurora.model.shared.dto.Patient
import org.aurora.model.patientfilter.*
import org.aurora.model.js.DataModel.patientTableData
import org.aurora.model.shared.dto.Patient
import org.aurora.model.patientfilter.SearchField.FLOORWING
object filter :
  def floorWing(p:Patient):FLOORWING = 
    p.floor match {
      case Some(f) => 
        f match {
          case "T ER OVFLW" => FLOORWING("2",Some("E"))
          case "ED CONSULT" => FLOORWING("2",Some("E"))
          case "T HRM CED"  => FLOORWING("1",Some("H"))
          case "T 2 MH"     => FLOORWING("2",Some("M"))
          case "T 1 FORENS" => FLOORWING("1",Some("F"))
          case f            => FLOORWING(f.substring(2,3),Some(f.substring(3,4)))
        }
      case None => FLOORWING(" ",None)
    }



  given IncludeMethods[Patient](st=null) with
    import SearchField._
    def includeFullName(patientdata:Patient):Boolean = st.fn match {
      case None => true
      case Some(FULLNAME(last,first)) =>  
        val result = (last,first) match {
          case (None,None) => true
          case (Some(x),None) => patientdata.lastName.toUpperCase.startsWith(x.toUpperCase())
          case (None,Some(y)) => patientdata.firstName.toUpperCase.startsWith(y.toUpperCase())
          case (Some(x),Some(y)) => patientdata.lastName.toUpperCase.startsWith(x.toUpperCase()) && patientdata.firstName.toUpperCase.startsWith(y.toUpperCase())
        }
        result
    }
    def includeFloorWing(patientdata:Patient):Boolean = 
      st.fw match {
        case None => true
        case Some(FLOORWING(floor,None)) => 
          floor == floorWing(patientdata).floor

        case _ => st.fw.get == floorWing(patientdata)
      }

    def includeMrp(patientdata:Patient):Boolean = st.mrp match {
      case None => true
      case Some(MRP(text)) => {
        val result = text match {
          case None => true
          case Some(x) => patientdata.mrp.get.startsWith(x)
        }
        result
      }
    }

    def includeRoom(patientdata:Patient):Boolean = st.rm match {
      case None => true
      case Some(ROOMBED(text,None)) => patientdata.room.get.startsWith(text)
      case default => true
    }
      
  
  
  //TODO WORKING HERE, testing in FetchMapParserFilterTest
  
  def pfilter[T](st:Searchterms)(using im: IncludeMethods[T]):(T)=>Boolean = 
    im.st = st
    (p:T) => im.include(p)

  
end filter
