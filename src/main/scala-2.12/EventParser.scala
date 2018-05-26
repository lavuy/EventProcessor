import play.api.libs.json.{JsValue, Json}

/**
  * Created by Manon on 5/25/2018.
  */
trait EventParser {
  def parse(json : String) : Event
}

class JsonEventParser extends EventParser{
  override def parse(json: String): Event = {
    println (s"Parsing $json")
    val eventJsValue  = Json.parse(json)
    new Event(getStringValueFromJson(eventJsValue, "event_type"),
              getStringValueFromJson(eventJsValue, "data"))
  }

  private def getStringValueFromJson(jsValue : JsValue, attributeName : String) : String = {
    (jsValue \ attributeName).as[String]
  }
}
