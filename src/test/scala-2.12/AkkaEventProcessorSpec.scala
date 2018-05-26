import org.scalatest.FunSuite

/**
  * Created by Manon on 5/26/2018.
  */
class AkkaEventProcessorSpec extends FunSuite {
  test("It should process jsons in stream and update the stats"){
    val eventsJsons = Stream("{ \"�sk���!���",
      "{ \"A�у}�\b��#",
      "{ \"event_type\": \"foo\", \"data\": \"lorem\", \"timestamp\": 1527312782 }",
      "{ \"event_type\": \"baz\", \"data\": \"ipsum\", \"timestamp\": 1527312782 }")

    val eventStats = new ConcurrentEventStats()

    val eventProcessor = new AkkaEventProcessor(eventsJsons , new JsonEventParser(), new EventStatsUpdater(eventStats))
    eventProcessor.startEventProcessingPipeline()
    Thread.sleep(500)
    val snapshot = eventStats.getSnapshot()
    val expected = Map("data" -> Map("lorem" -> 1,
                                     "ipsum" -> 1),
                       "types" -> Map("foo" -> 1,
                                      "baz" -> 1))
    assert(expected == snapshot)

  }
}
