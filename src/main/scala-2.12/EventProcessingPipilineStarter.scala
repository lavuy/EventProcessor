import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ActorMaterializerSettings, Supervision}
import akka.stream.Supervision._
import akka.stream.scaladsl.{Sink, Source}

/**
  * Created by Manon on 5/26/2018.
  */
trait EvenrProcessor {
  def startEventProcessingPipeline() : Unit
}

class AkkaEventProcessor(lineStream: Stream[String], eventParser: EventParser,
                         eventStatsUpdater: EventStatsUpdater){
  def startEventProcessingPipeline(): Unit = {
    implicit val materializer = createBadEventDroppingMaterializer

    Source(lineStream)
      .map(l => eventParser.parse(l))
      .runWith(Sink.foreach(l => eventStatsUpdater.UpdateStats(l)))
  }

  def createBadEventDroppingMaterializer: ActorMaterializer = {
    implicit val system = ActorSystem("event-stream")
    val decider: Decider = {
      case _ => Supervision.Resume
    }

    ActorMaterializer(ActorMaterializerSettings(system).withSupervisionStrategy(decider))
  }
}
