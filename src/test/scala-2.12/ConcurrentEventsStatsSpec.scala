import org.scalatest.FunSuite

/**
  * Created by Manon on 5/25/2018.
  */
class ConcurrentEventsStatsSpec extends FunSuite{
  test("it should create a new stat name and value with the given count when non exist") {
    val stats = new ConcurrentEventStats()
    stats.incrementCount("data", "cat", 3)

    assert(stats.getSnapshot()("data")("cat") == 3)
  }

  test("it should increment count of given stat value by given amount") {
    val stats = new ConcurrentEventStats()
    stats.incrementCount("data", "cat", 3)
    stats.incrementCount("data", "cat", 2)
    val snapshot = stats.getSnapshot()
    assert(snapshot("data")("cat") == 5)
  }
}
