import java.util
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

import scala.collection.concurrent.TrieMap
import scala.collection.JavaConverters._

/**
  * Created by Manon on 5/25/2018.
  */
trait EventStats {
  def incrementCount(statName : String , statValue : String,  statCount : Int) : Int
  def getSnapshot() : scala.collection.Map[String, scala.collection.Map[String, AtomicInteger]]
}

class ConcurrentEventStats extends  EventStats{

  private val statsByName = new TrieMap[String, TrieMap[String, AtomicInteger]]()

  override def incrementCount(statName : String, statValue: String, statCount: Int) : Int = {
    val stat =  getOrAddAtomic(statsByName, statName ,new TrieMap[String, AtomicInteger]())
    val oldStatCount = getOrAddAtomic(stat, statValue, new AtomicInteger(0))
    oldStatCount.addAndGet(statCount)
  }

  def getOrAddAtomic[T](trieMap: TrieMap[String, T], keyName: String, value : T) : T = {
    var done =false;
    while (!done){
      val maybeNew = trieMap.putIfAbsent(keyName, value)
      done = maybeNew != None
    }
    trieMap(keyName)
  }


  override def getSnapshot() : scala.collection.Map[String, scala.collection.Map[String, AtomicInteger]] = {
    statsByName.readOnlySnapshot()
  }
}
