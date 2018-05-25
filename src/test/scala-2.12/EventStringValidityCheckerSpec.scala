import org.scalatest.FunSuite

/**
  * Created by Manon on 5/25/2018.
  */
class EventStringValidityCheckerSpec extends FunSuite {
  test("it should return false for an invalid JSON string") {
    val checker = new EventStringValidityChecker
    val malformedJson = "{ \"����,����";
    assert(checker.isValidEventString(malformedJson) == false)
  }

  test("it should return true for a valid JSON string") {
    val checker = new EventStringValidityChecker
    val malformedJson = "{ \"event_type\": \"bar\", \"data\": \"sit\", \"timestamp\": 1527227668 }";
    assert(checker.isValidEventString(malformedJson) == true)
  }
}
