package org.gym.fp

// data type
sealed trait BooleanResult[+A] {

  def map[B](f: A => B): BooleanResult[B]

  def flatMap[B](f: A => BooleanResult[B]): BooleanResult[B]

}

// data constructor
case class TrueResult[A](value: A) extends BooleanResult[A] {

  override def map[B](f: A => B): BooleanResult[B] = TrueResult(f(value))

  override def flatMap[B](f: A => BooleanResult[B]): BooleanResult[B] = f(value)

}

case class FalseResult[A]() extends BooleanResult[A] {

  override def map[B](f: A => B): BooleanResult[B] = FalseResult()

  override def flatMap[B](f: A => BooleanResult[B]): BooleanResult[B] = FalseResult()

}
