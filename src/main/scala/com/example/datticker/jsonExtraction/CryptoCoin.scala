package com.example.datticker.jsonExtraction

// LiftJson Imports
import net.liftweb.json._

// Scala Imports
import scala.io.Source.fromURL
import scala.util.{Try, Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * __Author__  = bmcg
  * __Email__   = bmcg0890@gmail.com
  * __VERSION__ = 0.1 
  *
  * __License__ = 
  * Copyright (C) 2015-2018 b-mcg <bmcg0890@gmail.com>
  * This program is free software: you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published by
  * the Free Software Foundation, either version 3 of the License, or
  * (at your option) any later version.
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
  * GNU General Public License for more details.
  * You should have received a copy of the GNU General Public License
  * along with this program. If not, see <http://www.gnu.org/licenses/>.
  *
  */


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
