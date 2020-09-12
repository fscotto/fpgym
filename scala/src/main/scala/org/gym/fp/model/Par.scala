package org.gym.fp.model

object Par {
  def par(statement1: => Unit)(statement2: => Unit) = {
    val r = new Runnable {
      override def run(): Unit = statement1
    }
    val t = new Thread(r)
    t.start()
    statement2
    t.join()
  }
}
