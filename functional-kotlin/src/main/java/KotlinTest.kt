import java.time.LocalDate


data class Person(val name: String,
                  val lastname: String,
                  val birthday: LocalDate)

typealias Function<T, R> = (T) -> R

typealias BiFunction<T, U, R> = (T, U) -> R

inline class Amount(val quantity: Int)

inline class Weight(val quantity: Int)

inline class Password(val value: String)

class UInt(val value: Int) {
  init {
    require(value >= 0)
  }
}

fun add(a: Int, b: Int): Int = a + b

fun main() {
  val (a, _) = Pair(1, 2)
  println(a)

  val fabio = Person("Fabio", "Scotto", LocalDate.of(1988, 2, 17))
  val (name, lastname, _) = fabio
  println("${name}, ${lastname}")

  when(fabio.name) {
    "Fabio" -> println(fabio.name)
  }

  each(UInt(5)) {
    println("Hello")
  }

}

operator fun Int.compareTo(other: UInt): Int {
  return this.compareTo(other.value)
}

fun each(times: UInt, block: () -> Unit) {
  var i = 0
  while(i <= times) {
    block()
    i++
  }
}