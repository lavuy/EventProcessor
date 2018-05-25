import play.api.libs.json.Json

import scala.util.parsing.json._

/**
  * Created by Manon on 5/25/2018.
  */

trait IEventStringValidityChecker {
  def isValidEventString(s : String) : Boolean
}

class EventStringValidityChecker extends IEventStringValidityChecker {
  override def isValidEventString(s: String): Boolean = {
    var result : Boolean  = false
    try{
      val a  = Json.parse(s)
      result = true
    }
    catch {
      case _: Throwable =>
    }
    result
  }
}
