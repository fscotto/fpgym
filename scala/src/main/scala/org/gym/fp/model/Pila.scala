package org.gym.fp.model

import scala.collection.mutable.ArrayBuffer

class Pila[T] {
  private var l: ArrayBuffer[T] = ArrayBuffer[T]()

  def isEmpty(): Boolean = l.isEmpty

  def push(elem: T): Unit = l.append(elem)

  def pop(): T = {
    if (isEmpty()) throw new IllegalArgumentException()
    l.remove(0)
  }

  def top(): T = {
    if (isEmpty()) throw new IllegalArgumentException()
    l.last
  }
}

object TestPila {
  def main(args: Array[String]): Unit = {
    var p: Pila[Int] = new Pila[Int]
    for (i <- 1 to 10) p.push(i)
    while (!p.isEmpty()) println(p.pop())
  }
}
