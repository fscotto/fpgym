package org.gym.fp

import scala.annotation.tailrec

object Operation {

  def abs(x: Double) = if (x < 0) -x else x

  def add(a: Double)(b: Double) = a + b

  def sub(a: Double)(b: Double) = a - b

  def prod(a: Double)(b: Double) = a * b

  def div(a: Double)(b: Double) = if (b != 0) a / b else 0

  def inc(x: Double) = add(x)(1)

  def dec(x: Double) = sub(x)(1)

  def mcd(x: Int, y: Int): Int = if (y == 0) x else mcd(y, x % y)

  def sqrt(x: Double) = {
    val epsilon = 0.000000000001

    def firstPrev(y: Double, x: Double) = abs(y * y - x) < epsilon

    def secondPrev(y: Double, x: Double) = (y + x / y) / 2.0

    def bestSqrt(y: Double, x: Double): Double =
      if (firstPrev(y, x)) y else bestSqrt(secondPrev(y, x), x)

    bestSqrt(1.0, x)
  }

  def pow(n: Double)(x: Double) = {
    @tailrec
    def p(n: Double, x: Double, a: Double): Double =
      if (x == 0) a else p(n, x - 1, n * a)

    p(n, x, 1)
  }

  def square(n: Double) = pow(n)(2)

  def cube(n: Double) = pow(n)(3)

  def percent(n: Double, p: Double) = if (p > 0 && n > 0) div(prod(n)(p))(100.0) else 0

}