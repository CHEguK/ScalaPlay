package models

import collection.mutable

object TaskListInMemoryModel {
  private val users = mutable.Map[String, String]("Andrew" -> "pass")
  private val tasks = mutable.Map[String, List[String]]("Andrew" -> List("Eat", "Work", "Sing"))

  def validate(username: String, password: String): Boolean = {
    users.get(username).contains(password)
  }

  def createUser(username: String, password: String): Boolean = {
    if (users.contains(username)) false
    else {
      users(username) = password
      true
    }
  }

  def getTasks(username: String): Seq[String] = {
    tasks.get(username).getOrElse(Nil)
  }

  def addTask(username: String, task: String): Unit = ???

  def removeTask(username: String, index: Int): Boolean = ???


}
