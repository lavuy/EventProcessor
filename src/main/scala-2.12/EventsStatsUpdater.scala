
class EventStatsUpdater(eventStats : EventStats) {
  def UpdateStats(event : Event) = {
    val dataCount = eventStats.incrementCount("data" ,event.data, 1)
    val typeCount =  eventStats.incrementCount("types", event.eventType, 1)
  }
}



