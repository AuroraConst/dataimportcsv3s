package org.aurora.model.js
import org.aurora.model.js.filter.{*,given}
import org.aurora.model.shared.dto.*
import org.aurora.model.queryparser.*


import com.raquo.airstream.ownership.OneTimeOwner
import com.raquo.laminar.api.L.{*, given}


object DataModel :

  private given Owner = new OneTimeOwner(()=>())

  val patientTableData = Var[List[Patient]](List[Patient]())
  val filterText = Var[String]("")
  
  val filteredTableData = filterText.signal
    .map( s => parseFilterTerms(s) )
    .map( ft => patientTableData.now().filter(filterPredicate[Patient](ft)) )
  
  

  //periodic fetch of elements  every 30 seconds
  //need to think about other streams that would trigger
  //"So, in short, whenever parentStream emits a new ev event, flatStream switches from mirroring the previous 
    // innerStream to mirroring the next innerStream. It forgets about the previous innerStream from then on."
    // (laminar documentation)
  val periodicFetch = EventStream.periodic(30000)
    .flatMapSwitch { e => 
      //this is an inner event stream that gets flattened and  the previous one that
      //was created gets forgotten and the new one that gets created
      //becomes active
      Fetch.patients.map{_.getOrElse(List())}
    }

  




  
  
  



end DataModel
  
