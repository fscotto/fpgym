package org.gym.fp

abstract class Device

case class Phone(model: String) extends Device {
  def screenOff = "Turning screen off"
}

case class Computer(model: String) extends Device {
  def screenSaverOn = "Turning screen saver on..."
}

object DeviceTest {

  private def newLine = println()

  def main(args: Array[String]): Unit = {
    println("TEST 1")
    val p = Phone("Samsung Galaxy S8 Edge")
    val c = Computer("Alienware")
    println(goIdle(p))
    println(goIdle(c))
    newLine
  }

  def goIdle(device: Device) = {
    device match {
      case p: Phone => p.screenOff
      case c: Computer => c.screenSaverOn
    }
  }
}
