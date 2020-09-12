package org.gym.fp.model

abstract class Shape

class Circle(val x: Double, val y: Double, val r: Double) extends Shape

class Line(val x1: Double, val y1: Double, val x2: Double, val y2: Double) extends Shape