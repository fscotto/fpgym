package org.gym.fp

object App {

  def main(args: Array[String]): Unit = {
    doOperationsOnTuples()
    doHighOrderFunctions()
    doPatternMatching()
  }

  def doOperationsOnTuples(): Unit = {
    // definire una tupla
    val tupla = (1, "Hello", true) // può essere di vari tipi
    println(tupla)

    // Destrutturare una tupla
    val (number, greeting, isValid) = tupla
    println(s"$number, $greeting, $isValid")

    // Funzione che prende come argomento una tupla di (Int, String, Boolean)
    def printTuple(t: (Int, String, Boolean)): Unit = println(s"Da funzione printTuple: $t")

    printTuple(tupla)

    // Pattern matching su lista di tuple
    val planets = List(
      ("Mercury", 57.9), ("Venus", 108.2), ("Earth", 149.2),
      ("Mars", 227.9), ("Jupiter", 778.3)
    )

    planets.foreach {
      case ("Earth", distance) =>
        println(s"Our planet is $distance million kilometers from the sun")
      case _ =>
    }

    // Enumerazione
    object Planet extends Enumeration {
      type Planet = Value
      val Mercury, Venus, Earth, Mars, Saturn, Jupiter, Uranus, Neptune = Value
    }

    import Planet._
    val planets2 = Set(
      (Mercury, 57.9), (Venus, 108.2), (Earth, 149.2),
      (Mars, 227.9), (Jupiter, 778.3)
    )

    planets2.foreach {
      case (Jupiter, distance) =>
        println(s"Our planet is $distance million kilometers from the sun")
      case _ =>
    }

  }

  def doHighOrderFunctions(): Unit = {

    object SalaryRaiser {
      private def promotion(salaries: List[Double], promotionsFunction: Double => Double): List[Double] =
        salaries.map(promotionsFunction)

      def smallPromotion(salaries: List[Double]): List[Double] =
        promotion(salaries, salary => salary * 1.1)

      def greatPromotion(salaries: List[Double]): List[Double] =
        promotion(salaries, salary => salary * Math.log(salary))

      def hugePromotion(salaries: List[Double]): List[Double] =
        promotion(salaries, salary => salary * salary)
    }

    case class Person(val name: String, val salary: Double)

    val promotions = List(Person("Giacomo", 20000), Person("Mario", 30000)).map(_.salary)
    SalaryRaiser.smallPromotion(promotions).foreach(println)

    def urlBuilder(ssl: Boolean, domainName: String): (String, String) => String = {
      val schema = if (ssl) "https://" else "http://"
      (endpoint: String, query: String) => s"$schema$domainName/$endpoint?$query"
    }

    val domainName = "www.example.com"
    def getURL = urlBuilder(ssl = true, domainName)
    val endpoint = "users"
    val query = "id=1"
    val url = getURL(endpoint, query)
    println(url)
  }

  def doPatternMatching(): Unit = {
    // Pattern Matching con le case class
    abstract class Notification
    case class Email(sender: String, title: String, body: String) extends Notification
    case class SMS(caller: String, message: String) extends Notification
    case class VoiceRecording(contactName: String, link: String) extends Notification

    def showNotification(notification: Notification): String = {
      notification match {
        case Email(sender, title, _) =>
          s"You got an email from $sender with title: $title"
        case SMS(number, message) =>
          s"You got an SMS from $number! Message: $message"
        case VoiceRecording(name, link) =>
          s"You received a Voice Recording from $name! Click the link to hear it: $link"
      }
    }

    val someSMS = SMS("12345", "Are you there?")
    val someVoiceRecording = VoiceRecording("Tom", "voicerecording.org/id/123")

    println(showNotification(someSMS))
    println(showNotification(someVoiceRecording))

    // Aggiungo un Pattern Guard, cioè una condizione addizionale per cui vale quel matching
    def showImportantNotification(notification: Notification, importantPeopleInfo: Seq[String]): String = {
      notification match {
        case Email(sender, _, _) if importantPeopleInfo.contains(sender) =>
          "You got an email from special someone!"
        case SMS(number, _) if importantPeopleInfo.contains(number) =>
          "You got an SMS from special someone!"
        case other =>
          showNotification(other)
      }
    }

    val importantPeopleInfo = Seq("867-5309", "jenny@gmail.com")

    val importantEmail = Email("jenny@gmail.com", "Drinks tonight?", "I'm free after 5!")
    val importantSms = SMS("867-5309", "I'm here! Where are you?")

    println(showImportantNotification(someSMS, importantPeopleInfo))
    println(showImportantNotification(someVoiceRecording, importantPeopleInfo))
    println(showImportantNotification(importantEmail, importantPeopleInfo))
    println(showImportantNotification(importantSms, importantPeopleInfo))

  }

}
