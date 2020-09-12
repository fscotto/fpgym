package org.gym.fp

sealed abstract class Furniture

case class Couch() extends Furniture

case class Chair() extends Furniture

object FurnitureTest {
  def main(args: Array[String]): Unit = {
    val a = Couch
    val b = Chair
  }

  def findPlaceToSit(piece: Furniture) = {
    piece match {
      case a: Couch => "Lie on the couch"
      case b: Chair => "Sit on the chair"
    }
  }
}