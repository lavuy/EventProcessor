
import akka.http.scaladsl.model.{HttpEntity, HttpResponse}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import play.api.libs.json.Json

/**
  * Created by Manon on 5/25/2018.
  */
trait EventStatsServer extends Runnable {
  def terminate()
}

class AkkaEventStatsServer(eventStats : EventStats, port: Int) extends EventStatsServer{
  def run() = {
    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher
    println(s"Server listning on port $port")
    println(s"try me out on: http://127.0.0.1:$port/stats")
    val route =
      path("stats") {
        get {
          complete(HttpResponse(entity = Json.stringify(Json.toJson(eventStats.getSnapshot())).toString()))
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  }

  override def terminate(): Unit = {

  }
}

