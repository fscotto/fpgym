package org.gym.fp

object TestThread extends App {
  val r = new Runnable {
    def run() {
      for (i <- 1 to 1000)
        println(i)
    }
  }
  val t = new Thread(r)
  t.start()
  for (i <- 5000 to 6000) println(i)
  t.join()
}
