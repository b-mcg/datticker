package com.example.datticker.Tweaks

// Imports
import android.widget.{RelativeLayout, LinearLayout, TextView, Button}
import android.view.ViewGroup.LayoutParams._
import android.view.{Gravity, View}
import android.graphics.Color

import macroid._
import macroid.FullDsl._
import macroid.contrib._
import macroid.contrib.Layouts.RuleRelativeLayout
import macroid.contrib.Layouts.RuleRelativeLayout.Rule
import macroid.Tweak

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


/** Contains various tweaks and
  * short hands.
  */
object DTT {

  type TV  = TextView
  type B   = Button
  type LL  = LinearLayout
  type RRL = RuleRelativeLayout

  // RelativeLayout constants
  def parentTop    = RelativeLayout.ALIGN_PARENT_TOP
  def parentBottom = RelativeLayout.ALIGN_PARENT_BOTTOM
  def centerHor    = RelativeLayout.CENTER_HORIZONTAL
  def centerVer    = RelativeLayout.CENTER_VERTICAL
  def belowOf      = RelativeLayout.BELOW
  def leftOf       = RelativeLayout.LEFT_OF
  def rightOf      = RelativeLayout.RIGHT_OF
  def startOf      = RelativeLayout.START_OF
  def endOf        = RelativeLayout.END_OF

  // Gravity, width, and weight sum tweaks for buttons and textviews
  def tvG(g: Int)           = Tweak[TV](_.setGravity(g))
  def llG(g: Int)           = Tweak[LL](_.setGravity(g))
  def llWS(weightSum: Int)  = Tweak[LL](_.setWeightSum(weightSum))
  def btW(width: Int)       = Tweak[B](_.setWidth(width))
  def btG(g: Int)           = Tweak[B](_.setGravity(g))
  def btG(g1: Int, g2: Int) = Tweak[B](_.setGravity(g1 | g2))
  def size(s: Int)          = TextTweaks.size(s)

  // Some common RelativeLayout parameter settings
  def tCH()  = lp[RRL](WRAP_CONTENT, WRAP_CONTENT, Rule(parentTop), Rule(centerHor))
  def cHCV() = lp[RRL](WRAP_CONTENT, WRAP_CONTENT, Rule(centerHor), Rule(centerVer))
  def bCH()  = lp[RRL](WRAP_CONTENT, WRAP_CONTENT, Rule(parentBottom), Rule(centerHor))
  def mP()   = lp[RRL](MATCH_PARENT, MATCH_PARENT)

  // Text and background color tweak short hands
  def tvC(r: Int, g: Int, b: Int) = TextTweaks.color(Color.rgb(r, g, b))
  def bgC(r: Int, g: Int, b: Int) = BgTweaks.color(Color.rgb(r, g, b))
}
