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
object EventProcessor {
  def main(args: Array[String]): Unit = {
    println("Hello, world!");
    val eventStats = new ConcurrentEventStats
    val eventParser = new EventParser
    val eventStatsUpdater = new EventStatsUpdater(eventStats)

    val lineStream = Process("\"D:\\Downloads\\generator-windows-amd64.exe\"").lineStream

    BuildEventProcessingPipeline(lineStream, eventParser, eventStatsUpdater)
    val server = new AkkaEventStatsServer(eventStats, 8080)
    val thread = new Thread(server)
    thread.start()
  }

  def BuildEventProcessingPipeline(lineStream: Stream[String], eventParser : IEventParser, eventStatsUpdater: EventStatsUpdater): Unit = {
    implicit val system = ActorSystem("reactive-tweets")
    val decider: Decider = {
      case _ => Supervision.Resume
    }

    implicit val materializer = ActorMaterializer(ActorMaterializerSettings(system).withSupervisionStrategy(decider))

    Source(lineStream)
        .map(l => eventParser.parse(l))
        .runWith(Sink.foreach(l => eventStatsUpdater.UpdateStats(l)))

  }
}
