import org.scalatest.FunSuite

/**
  * Created by Manon on 5/26/2018.
  */
class JsonEventParserSpec extends FunSuite{
  test("It shuold extract the data and event type from the json event"){
    val json = "{ \"event_type\": \"foo\", \"data\": \"lorem\", \"timestamp\": 1527312782 }"
    val parser = new JsonEventParser
    val event = parser.parse(json)
    assert(event.eventType == "foo" )
    assert(event.data == "lorem" )
  }
}
