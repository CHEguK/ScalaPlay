import org.scalatestplus.play._
import models._

class TaskListInMemorySpec extends PlaySpec {
  "TaskListInMemoryModel" must {
    "do valid login fir default user" in {
      TaskListInMemoryModel.validate("Andrew", "pass") mustBe (true)
    }

    "reject login with wrong username" in {
      TaskListInMemoryModel.validate("asda", "awdad ") mustBe (false)
    }

    "get correct default tasks" in {
      TaskListInMemoryModel.getTasks("Andrew") mustBe (List("Eat", "Work", "Sing"))
    }

    "create new user" in {
      TaskListInMemoryModel.createUser("Test", "test") mustBe (true)
      TaskListInMemoryModel.getTasks("Test") mustBe (Nil)
    }

    "create new user with existing name" in {
      TaskListInMemoryModel.createUser("Andrew", "pass") mustBe (false)
    }

    "add new task for default user" in {
      TaskListInMemoryModel.addTask("Andrew", "play")
      TaskListInMemoryModel.getTasks("Andrew") must contain ("play")
    }

    "add new task for new user" in {
      TaskListInMemoryModel.addTask("Mark", "testing")
      TaskListInMemoryModel.getTasks("Mark") must contain ("testing")
    }
    "remove task from default user" in {
      TaskListInMemoryModel.removeTask("Andrew", TaskListInMemoryModel.getTasks("Andrew").indexOf("Work"))
      TaskListInMemoryModel.getTasks("Andrew") must not contain ("Work")
    }
  }
}