package com.example.datticker

// Android Imports
import android.os.Bundle
import android.widget.{LinearLayout, RelativeLayout, TextView, Button}
import android.view.ViewGroup.LayoutParams._
import android.view.{Gravity, View}
import android.app.Activity
import android.graphics.Color

// Macroid Imports
import macroid._
import macroid.FullDsl._
import macroid.contrib._
import macroid.contrib.Layouts.RuleRelativeLayout
import macroid.contrib.Layouts.RuleRelativeLayout.Rule
import macroid.Tweak

// Scala Imports
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

// Local Imports
import jsonExtraction.CryptoCoin.fBTCData
import Tweaks.DTT._

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


/**
  * MainActivity for a bitcoin ticker which gathers
  * its data from bitcoinaverage.
  *
  */
class MainActivity extends Activity with Contexts[Activity] with IdGeneration {

  // Slots for BTC data to be held in
  var capLast   = slot[TextView]
  var capAsk    = slot[TextView]
  var cap24h    = slot[TextView]

  // Predefined font sizes
  lazy val fat  = size(22)
  lazy val med  = size(15)
  lazy val fmed = size(18)

  /** Short hands for arranging the ask and 24_h textviews below the last one
    * so it looks something like this:
    *                        Last
    *                     Ask   24_h
    */
  def bLS()  = lp[RRL](WRAP_CONTENT, WRAP_CONTENT, Rule(belowOf, Id.btcLast), Rule(leftOf, Id.btcLast), Rule(startOf, Id.btcLast))
  def bRE()  = lp[RRL](WRAP_CONTENT, WRAP_CONTENT, Rule(belowOf, Id.btcLast), Rule(rightOf, Id.btcLast), Rule(endOf, Id.btcLast))

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    // Initial BTC ticker data
    val btcData = fBTCData()

    // Setup the main view
    lazy val view = l[RuleRelativeLayout](

      // Title TextView
      w[TextView] <~
        tvC(0, 0, 0) <~ // Set text color to black
        text("BTC|USD") <~
        fmed <~
        tCH(),

      // TextView for holding last ticker field
      w[TextView] <~
        tvC(0, 0, 0) <~ // Set text color to black
        btcData.map(p => text(p._1)) <~ // Get the last field
        fat <~
        tvG(Gravity.CENTER_HORIZONTAL) <~
        id(Id.btcLast) <~ // Give the main textview an id for future textview formatting
        wire(capLast) <~
        cHCV(),

      // TextView for holding ask ticker field
      w[TextView] <~
        tvC(0, 0, 0) <~ // Set text color to black
        btcData.map(p => text(p._2)) <~ // Get the ask field
        med <~ // Set medium text size
        wire(capAsk) <~
        bLS(),

      // TextView for holding 24_h ticker field
      w[TextView] <~
        tvC(0, 0, 0) <~ // Set text color to black
        btcData.map(p => text(p._3)) <~ // Get the 24_h field
        med <~ // Set medium text size
        wire(cap24h) <~
        bRE(),

      // Refresh button
      w[Button] <~
        text("Refresh") <~
        bgC(255, 215, 0) <~ // Set button background color to gold
        // Update with new ticker info on click
        On.click {

          // Fetch current ticker data and reset each textview
          val newBTCData = fBTCData()
          (capLast <~ newBTCData.map(p => text(p._1)) <~ fat) ~
          (capAsk  <~ newBTCData.map(p => text(p._2)) <~ med) ~
          (cap24h  <~ newBTCData.map(p => text(p._3)) <~ med)
        } <~ bCH()
    ) <~ bgC(224, 255, 255) <~ // Set activity background color to light cyan
      mP()

    setContentView(getUi(view))
  }
}
