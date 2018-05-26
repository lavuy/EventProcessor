import java.io.{ByteArrayOutputStream, PrintWriter}
import java.util.concurrent.atomic
import java.util.concurrent.atomic.AtomicInteger

import akka.NotUsed
import akka.actor.ActorSystem
import akka.io.Tcp.Message
import akka.stream.Supervision.Decider
import akka.stream.{ActorMaterializer, ActorMaterializerSettings, Supervision}
import akka.stream.javadsl.Flow
import akka.stream.scaladsl.{Sink, Source}

import scala.collection.concurrent.TrieMap
import sys.process._
import scala.sys.process.ProcessLogger

/**
  * Created by Manon on 5/25/2018.
  */
object EventProcessorApp {
  def main(args: Array[String]): Unit = {
    if (args.size == 0){
      println("Missing generator file path")
      return
    }

    val generatorPath = args(0)

    val eventStats = new ConcurrentEventStats
    val eventParser = new JsonEventParser
    val eventStatsUpdater = new EventStatsUpdater(eventStats)

    val lineStream = Process(generatorPath).lineStream

    var eventProcessingStarter = new AkkaEventProcessor(lineStream, eventParser, eventStatsUpdater)
    eventProcessingStarter.startEventProcessingPipeline()

    val server = new AkkaEventStatsServer(eventStats, 8080)
    val thread = new Thread(server)
    thread.start()
  }

}
