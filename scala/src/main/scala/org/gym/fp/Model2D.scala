package org.gym.fp

import org.gym.fp.model.{Frame2D, Line}

object Model2D {

  def getGrid(n: Int) = {

    // completare costruzione di un modello 2D di una griglia con n linee verticali ed n linee orizzontali
    List(new Line(0.0, 0.0, 1.0, 1.0)) // esempio di linea
  }

  def main(args: Array[String]) {
    println("Displaying 20x20 grid...")
    Frame2D.display(Model2D.getGrid(20), 500, 500)
  }

}
