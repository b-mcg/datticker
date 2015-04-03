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
