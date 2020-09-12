package org.gym.fp

import org.gym.fp.model.Par.par

object TestThread2 extends App {
  par {
    for (i <- 0 to 1000) println(i)
  } {
    for (i <- 5000 to 6000) println(i)
  }
}
