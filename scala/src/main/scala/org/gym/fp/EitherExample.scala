package org.gym.fp

case class Failed(msg: String)

object EitherExample extends App {

  println(giveMeAnAnswer(divideXByY(1, 0)))
  println(giveMeAnAnswer(divideXByY(1, 1)))

  def divideXByY(x: Int, y: Int): Either[String, Int] = {
    if (y == 0) Left("Dude, can't divide by 0") else Right(x / y)
  }

  private def giveMeAnAnswer(question: Either[String, Int]): String = {
    question match {
      case Left(value) => "Answer: " + value
      case Right(value) => "Answer: " + value
      case _ => "I don't known"
    }
  }

}
