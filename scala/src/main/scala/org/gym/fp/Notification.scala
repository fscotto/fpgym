package org.gym.fp

abstract class Notification

case class Email(sender: String, title: String, body: String) extends Notification

case class SMS(caller: String, message: String) extends Notification

case class VoiceRecording(contactName: String, link: String) extends Notification

class NotificationTest() {
  def showNotification(notification: Notification): String = {
    notification match {
      case Email(email, title, _) =>
        s"You got an email from $email with title: $title"
      case SMS(number, message) =>
        s"You got an SMS from $number! Message: $message"
      case VoiceRecording(name, link) =>
        s"You received a Voice Recording from $name! Click the link to hear it: $link"
    }
  }

  def showImportantNotification(notification: Notification, importantPeopleInfo: Seq[String]): String = {
    notification match {
      case Email(email, _, _) if importantPeopleInfo.contains(email) =>
        "You got an email from special someone!"
      case SMS(number, _) if importantPeopleInfo.contains(number) =>
        "You got an SMS from special someone!"
      case other =>
        showNotification(other) // nothing special, delegate to our original showNotification function
    }
  }
}

object NotificationTest {

  private def newLine = println()

  def main(args: Array[String]): Unit = {
    val t = new NotificationTest

    println("TEST 1")
    test1(t)
    newLine

    println("TEST 2")
    test2(t)
    newLine
  }

  def test1(t: NotificationTest) = {
    val someSms = SMS("12345", "Are you there?")
    val someVoiceRecording = VoiceRecording("Tom", "voicerecording.org/id/123")

    println(t.showNotification(someSms)) // prints You got an SMS from 12345! Message: Are you there?
    println(t.showNotification(someVoiceRecording)) // you received a Voice Recording from Tom! Click the link to hear it: voicerecording.org/id/123
  }

  def test2(t: NotificationTest) = {
    val importantPeopleInfo = Seq("867-5309", "jenny@gmail.com")

    val someSms = SMS("867-5309", "Are you there?")
    val someVoiceRecording = VoiceRecording("Tom", "voicerecording.org/id/123")
    val importantEmail = Email("jenny@gmail.com", "Drinks tonight?", "I'm free after 5!")
    val importantSms = SMS("867-5309", "I'm here! Where are you?")

    println(t.showImportantNotification(someSms, importantPeopleInfo))
    println(t.showImportantNotification(someVoiceRecording, importantPeopleInfo))
    println(t.showImportantNotification(importantEmail, importantPeopleInfo))
    println(t.showImportantNotification(importantSms, importantPeopleInfo))
  }

}
