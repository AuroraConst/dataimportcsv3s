package org.aurora.js.model

import org.aurora.model.shared.dto.Patient
import org.aurora.model.patientfilter.*
import org.aurora.js.model.DataModel.patientTableData
object filter :
  
  
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
      import fastparse._
      import SingleLineWhitespace._  //for skipping whiteespaces and tabs
      import org.aurora.model.parserdsl.floorwing

      def filterMatchesFloor(filterFloor:String) =
        patientdata.floor.getOrElse("") match {
          case "T ER OVFLW" => filterFloor == "2"
          case "ED CONSULT" => filterFloor == "2"
          case "T HRM CED"  => filterFloor =="1"
          case "T 2 MH"     => filterFloor =="2"
          case "T 1 FORENS" => filterFloor =="1"
          case f            => filterFloor == f.substring(2,3)
        }


      st.fw match {
        case None => true
        case Some(FLOORWING(floor,None)) => 
          filterMatchesFloor(floor)

        case Some(FLOORWING(floor,someWing)) => 

          val wingMatch = someWing match {
            case None => true
            case Some(wing) => 
              val patientFloor = patientdata.floor.getOrElse("     ").charAt(0)
              patientFloor.isLetter match {
                case true => wing.charAt(0) == patientFloor
                case false => false //TODO finish: deal with floor exceptions like hrm, emerg, amh etc
              }
          }
          
          wingMatch && filterMatchesFloor(floor)
      }

    def includeMrp(patientdata:Patient):Boolean = st.mrp match {
      case None => true
      case Some(MRP(text)) => {
        val result = text match {
          case None => true
          case Some(x) => patientdata.mrp.contains(x)
        }
        result
      }
    }
      
  
  
  //TODO WORKING HERE, testing in FetchMapParserFilterTest
  
  def pfilter[T](st:Searchterms)(using im: IncludeMethods[T]):(T)=>Boolean = 
    im.st = st
    (p:T) => im.include(p)

  
end filter
