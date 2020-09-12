package org.gym.fp.model

import org.gym.fp.Operation.{mcd, pow}


case class Rational(num: Int, den: Int) {

  def +(other: Rational) =
    Rational.minimize(Rational((this.num * other.den) + (this.den * other.num), this.den * other.den))

  def -(other: Rational) =
    Rational.minimize(Rational((this.num * other.den) - (this.den * other.num), this.den * other.den))

  def *(other: Rational) =
    Rational.minimize(Rational(this.num * other.num, this.den * other.den))

  def /(other: Rational) =
    Rational.minimize(Rational(this.num * other.den, this.den * other.num))

  def >(other: Rational) = (this.num / this.den) > (other.num / other.den)

  def <(other: Rational) = (this.num / this.den) < (other.num / other.den)

  def >=(other: Rational) = (this.num / this.den) >= (other.num / other.den)

  def <=(other: Rational) = (this.num / this.den) <= (other.num / other.den)

  def ==(other: Rational) = (this.num / this.den) == (other.num / other.den)

  def !=(other: Rational) = (this.num / this.den) != (other.num / other.den)

  override def toString() = s"$num/$den"

}

object Rational {

  private def minimize(r: Rational) =
    if (r.num == 0) Rational(r.num, 1) else Rational(r.num / mcd(r.num, r.den), r.den / mcd(r.num, r.den))

  implicit def doubleToRational(d: Double) = {
    val len = String.valueOf(d).split("\\.")(1).length
    val den = pow(10)(len).toInt
    val num = (d * den).toInt
    minimize(new Rational(num, den))
  }

  implicit def intToRational(i: Int) = {
    minimize(new Rational(i, 1))
  }

}