import play.api.libs.json.{JsValue, Json}

/**
  * Created by Manon on 5/25/2018.
  */
trait IEventParser {
  def parse(json : String) : Event
}

class EventParser extends IEventParser{
  override def parse(json: String): Event = {
    val eventJsValue  = Json.parse(json)
    new Event(getStringValueFromJson(eventJsValue, "event_type"), getStringValueFromJson(eventJsValue, "data"))
  }

  private def getStringValueFromJson(jsValue : JsValue, attributeName : String) : String = {
    (jsValue \ attributeName).as[String]
  }
}
