package com.example.datticker.jsonExtraction

// LiftJson Imports
import net.liftweb.json._

// Scala Imports
import scala.io.Source.fromURL
import scala.util.{Try, Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object CryptoCoin {

  type STriple = (String, String, String)

  /**
    * Returns a triple of formatted strings
    * containing the last, ask, and 24h_avg
    * JSON fields.
    *
    */
  def getBitcoinData() = {

    // Trap any exceptions that might occur
    // from connecting to the server or from parsing the json
    val result = Try {
      val res = fromURL("https://api.bitcoinaverage.com/ticker/USD")
      val json = parse(res.mkString)

      res.close()
      json
    }
    
    result match {
    
      case Success(json) => {
        val tickerData: Map[String, Double] = (for {
                                               JObject(child) <- json
                                               JField(key, JDouble(data)) <- child
                                             } yield (key, data)).toMap

        ("%.2f" format (tickerData("last")), "Ask: %.2f" format (tickerData("ask")), "24h_avg: %.2f" format (tickerData("24h_avg")))
      }

      case Failure(err) => ("Error:", "Could not connect to", "server or parse json")
    }
  }

  /** Returns a Future triple of BTC
    * ticker data.
    *
    */
  def fBTCData(): Future[STriple] = Future {
    getBitcoinData()
  }
}
