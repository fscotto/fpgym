package org.gym.fp

import org.gym.fp.model.Rational

import scala.annotation.tailrec

class Exercise {

  private def newLine = println()

  //es. 1
  def mcd(x: Int, y: Int): Int = if (y == 0) x else mcd(y, x % y)

  //es. 2
  def sommeQuad(x: Int, y: Int): Int = if (x > y) 0 else x * x + sommeQuad(x + 1, y)

  //es. 3
  def sqrt(x: Double) = {
    val epsilon = 0.000000000001

    def abs(x: Double) = if (x < 0) -x else x

    def firstPrev(y: Double, x: Double) = abs(y * y - x) < epsilon

    def secondPrev(y: Double, x: Double) = (y + x / y) / 2.0

    def bestSqrt(y: Double, x: Double): Double =
      if (firstPrev(y, x)) y else bestSqrt(secondPrev(y, x), x)

    bestSqrt(1.0, x)
  }

  // f(g(x)) es. 4
  def componi(f: Int => Int, g: Int => Int) = g.andThen(f)

  //es. 5
  def somma(f: Int => Int): (Int, Int) => Int = {
    def g(a: Int, b: Int): Int = if (a - b > 0) 0 else f(a) + g(a + 1, b)

    g
  }

  // es. 6
  def applicaDueVolte(f: Int => Int, x: Int) = componi(f, f)(x)

  // es. 9
  def primo(n: Int): Boolean = {
    val sqrt = Operation.sqrt(n.doubleValue()).intValue();
    var result = true

    if (n == 2) {
      result = false
    }

    for (i <- 2 to sqrt) {
      if (n % i == 0) {
        result = false
      }
    }
    result
  }

  //es. 10
  def fib(n: Int): Int =
    if (n == 0) 0 else if (n == 1) 1 else fib(n - 1) + fib(n - 2)

  //es. 11
  def sommaQuadRic(a: Int, b: Int): Int = {
    @tailrec
    def somma(a: Int, b: Int, acc: Int): Int =
      if (a - b > 0) acc else somma(a + 1, b, (a * a) + acc)

    somma(a, b, 0)
  }

  def sommaQuadList(a: Int, b: Int) = List.range(a, b + 1).map(x => x * x).reduce(_ + _)

  //es. 12
  def funcEqual(f1: Int => Int, f2: Int => Int, n: Int): Boolean =
    if (n >= 0) f1(n) == f2(n) else f1(n) == f2(n) && funcEqual(f1, f2, n - 1)

  //es. 13 TODO NON SI CAPISCE IL RISULTATO DEL ESERCIZIO
  def concatena(f1: Double => Double, f2: Double => Double, f3: Double => Double, a: Double, b: Double) = ???

  //es. 14
  def maxLength(strings: List[String]) =
    strings.map(s => s.length()).reduce((s1, s2) => if (s1 > s2) s1 else s2)

  //es. 15
  def equal(l1: List[Int], l2: List[Int]): Boolean =
    if (l1.isEmpty && !l2.isEmpty || !l1.isEmpty && l2.isEmpty)
      false
    else if (l1.isEmpty && l2.isEmpty)
      true
    else
      l1.head == l2.head && equal(l1.tail, l2.tail)

  //es. 16
  def filter(l: List[Int], predicate: Int => Boolean): List[Int] = {
    @tailrec
    def innerFilter(l: List[Int], predicate: Int => Boolean, acc: List[Int]): List[Int] =
      if (l.isEmpty) acc else innerFilter(l.tail, predicate, if (predicate(l.head)) l.head :: acc else acc)

    innerFilter(l, predicate, List()).sorted
  }

  //es. 17
  def minMaxRic(l: List[Int]): (Int, Int) = {
    @tailrec
    def min(l: List[Int], m: Int): Int = if (l.isEmpty) m else min(l.tail, if (l.head < m) l.head else m)

    @tailrec
    def max(l: List[Int], m: Int): Int = if (l.isEmpty) m else max(l.tail, if (l.head > m) l.head else m)

    (min(l, Int.MaxValue), max(l, Int.MinValue))
  }

  def minMaxList(l: List[Int]): (Int, Int) = {
    val min = l.reduce((a, b) => if (a < b) a else b)
    val max = l.reduce((a, b) => if (a > b) a else b)
    (min, max)
  }

  def minMaxList2(l: List[Int]): (Int, Int) = (l.min, l.max)

  //es. 18
  def mediaVeicoli(l: List[Int]): Double =
    l.span(v => v > 0)._1.foldLeft(0)(_ + _).toDouble / l.span(v => v > 0)._1.foldLeft(0.0)((r, c) => r + 1)

  //es. 19
  def allDistinct[T <% Ordered[T]](l: List[T]): Boolean = l.size == l.distinct.size

  //es. 20 TODO NON SI CAPISCE IL RISULTATO DEL ESERCIZIO
  def inferiori(l: List[Int]): List[(Int, Int)] = ???

  //es. 21
  def find[T](x: T, l: List[T]): Boolean = l.find(x == _).getOrElse(None) != None

  //es. 22
  def removeDuplicates[T](l: List[T]): List[T] = l.distinct

  //es. 23
  def union[T](l1: List[T], l2: List[T]): List[T] = l1.union(l2).distinct

  //es. 24
  def intersection[T](l1: List[T], l2: List[T]): List[T] = l1.intersect(l2).distinct

  //es. 31
  def forComprehension() = {
    case class Studente(val nome: String, val eta: Int, val esami: List[String])

    val q = List(
      Studente("Marco", 23, List("PFP", "SC")),
      Studente("Laura", 19, List("SC", "FI1", "PFP")),
      Studente("Stefano", 23, List("SC", "FI1")),
      Studente("Marco", 25, List("SC", "FI1", "FI2")),
      Studente("Paola", 22, List("SC", "PFP"))
    )

    // estrarre tutti gli studenti con eta compresa tra 20 e 24 anni (inclusi) che hanno sostenuto l'esame di "PFP"
    val query1 = for {
      s <- q
      if (s.eta >= 20 && s.eta <= 24 && s.esami.contains("PFP"))
    } yield s;
    // stampare i dati degli studenti in query1
    println("Studenti dai 20 ai 24 anni con esame PFP: ")
    query1 foreach println
    newLine

    // estrarre tutti gli studenti con eta strettamente inferiore a 24 anni che hanno sostenuto almeno due esami
    val query2 = for {
      s <- q
      if (s.eta < 24 && s.esami.size >= 2)
    } yield s;
    // stampare i dati degli studenti in query2
    println("Studenti minori di 24 anni con almeno 2 esami: ")
    query2 foreach println
  }

  //es. 33
  def repeat(times: Int)(statement: => Unit) = for (i <- 1 to times) statement

  //es. 34

  // Versione imperativa di prova
  //    def vecprod(v1: Array[Double], v2: Array[Double]) = {
  //        require(v1.size == v2.size)
  //        var sp = 0.0
  //        var i = 0
  //        while (i < v1.size) {
  //            sp += v1(i) * v2(i)
  //            i += 1
  //        }
  //        sp
  //    }

  def vecprod(v1: Array[Double], v2: Array[Double]) = {
    require(v1.size == v2.size)
    v1.zip(v2).map(t => t._1 * t._2).reduce(_ + _)
  }

  def executeAllTests() = {

    //es. 1
    println("Esercizio 1")
    println("Minimo comune denominatore tra 5 e 15 = " + mcd(5, 15))
    newLine

    //es. 2
    println("Esercizio 2")
    println("sommatoria dei quadrati da 1 a 3 = " + sommeQuad(1, 3)) // stampa 14 (somma dei quadrati di 1, 2 e 3)
    newLine

    //es. 3
    println("Esercizio 3")
    println("Radice quadrata di 2 ~ " + sqrt(2))
    newLine

    // f(g(x)) es.4
    println("Esercizio 4")
    val f = componi(x => x * x, x => x + 1)
    println("f(g(x) / f(x) = x * x e g(x) = x + 1 := " + f(9)) // stampa 100
    newLine

    //es.5
    println("Esercizio 5")
    val sommaQuadrati = somma(x => x * x)
    println("sommatoria dei quadrati da 1 a 3 = " + sommaQuadrati(1, 3)) // stampa 14 (somma dei quadrati di 1, 2 e 3)
    newLine

    //es.6
    println("Esercizio 6")
    println("10 + 1 + 1 = " + applicaDueVolte(x => x + 1, 10)) // stampa 12
    newLine

    //es.9
    println("Esercizio 9")
    println("primo(3) = " + primo(3)) // true
    println("primo(2) = " + primo(2)) // false
    newLine

    //es.10
    println("Esercizio 10")
    println("fib(20) = " + fib(20))
    newLine

    //es.11
    println("Esercizio 11")
    println("sommatoria dei quadrati da 1 a 3 = " + sommaQuadRic(1, 3)) //14
    println("sommatoria dei quadrati da 1 a 3 = " + sommaQuadList(1, 3)) //14
    newLine

    //es.12
    println("Esercizio 12")
    println(funcEqual(x => x * x, x => x * x, 4)) // true
    println(funcEqual(x => x * x, x => x + 1, 4)) // false
    newLine

    //es. 14
    println("Esercizio 14")
    println(maxLength(List("ciao", "famiglia", "supercalifragilistichespiralidoso"))) // 33
    newLine

    //es. 15
    println("Esercizio 15")
    println(equal(List(1, 2, 3), List(1, 2, 3))) // true
    println(equal(List(1, 2, 3), List(1, 2, 3, 4))) // false
    println(equal(List(1, 2, 3, 4), List(1, 2, 3))) // false
    newLine

    //es. 16
    println("Esercizio 16")
    val l = List(3, 1, 2, 4, 5)
    println(filter(l, _ > 2)) // stampa 3,4,5
    newLine

    //es. 17
    println("Esercizio 17")
    println("Versione ricorsiva: " + minMaxRic(List(10, 2, 3, 6)))
    println("Versione con metodi standard: " + minMaxList(List(10, 2, 3, 6)))
    newLine

    //es. 18
    println("Esercizio 18")
    println("La media e' = " + mediaVeicoli(List(4, 8, 4, 8, 4, 4, 4, 4, -1, 77, 88, 99))) // 5
    newLine

    //es. 19
    println("Esercizio 19")
    println(allDistinct(List(1, 2, 3, 4, 5))) // true
    println(allDistinct(List(4, 8, 4, 8, 4, 4, 4, 4, -1, 77, 88, 99))) // false
    newLine

    //es. 20
    println("Esercizio 20")
    //println(inferiori(List(1,3,1,2,2,1))) // deve restituire List((1,0), (2,3), (3,5))
    newLine

    //es. 21
    println("Esercizio 21")
    println(find(2, List(3, 1, 2, 4, 5))) // true
    println(find(20, List(3, 1, 2, 4, 5))) // false
    newLine

    //es. 22
    println("Esercizio 22")
    println(removeDuplicates(List(1, 2, 3, 4, 4, 5))) // List(1, 2, 3, 4, 5)
    println(removeDuplicates(List(4, 8, 4, 8, 4, 4, 4, 4, -1, 77, 88, 99))) // List(4, 8, -1, 77, 88, 99)
    newLine

    //es. 23
    println("Esercizio 23")
    println(union(List(1, 2, 3, 4, 4, 5), List(4, 8, 4, 8, 4, 4, 4, 4, -1, 77, 88, 99))) // List(1, 2, 3, 4, 5, 8, -1, 77, 88, 99)
    newLine

    //es. 24
    println("Esercizio 24")
    println(intersection(List(1, 2, 3, 4, 4, 5), List(4, 8, 4, 8, 4, 4, 4, 4, 5, -1, 77, 88, 99))) // List(4, 5)
    newLine

    //es. 25
    println("Esercizio 25")
    val r1 = new Rational(2, 7)
    val r2 = new Rational(8, 6)
    val r3 = new Rational(4, 14)
    val r4: Rational = 1.0 / 2.0
    val r5: Rational = 5
    println(r1 + r2) // stampa 34/21
    println(r1 - r2) // stampa -22/21
    println(r1 * r2) // stampa 8/21
    println(r1 / r2) // stampa 3/14
    println(r1 == r3) // stampa true
    println(r1 != r2) // stampa true
    println(r1 < r2) // stampa true
    println(r2 < r1) // stampa false
    println(r4)
    println(r5)
    newLine

    //es. 31
    println("Esercizio 31")
    forComprehension()
    newLine

    //es. 33
    println("Esercizio 33")
    repeat(3) {
      println("ciao")
    }
    newLine

    //es. 34
    println("Esercizio 34")
    println(vecprod(Array(1.0, 2.0, 3.0, 4.0, 5.0), Array(6.0, 7.0, 8.0, 9.0, 10.0))) // 130.0
    newLine
  }

}
