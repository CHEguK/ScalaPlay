import org.scalatestplus.play.{HtmlUnitFactory, OneBrowserPerSuite, PlaySpec}
import org.scalatestplus.play.guice.GuiceOneServerPerSuite

class TaskListSpec extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
  "Task list 1" must {
    "login and access functions" in {
      go to s"http://localhost:$port/login"
      eventually {
        find("h2").foreach(e => e.text mustBe "Login")
        click on "username-login"
        textField("username-login").value = "Andrew"
        click on "password-login"
        pwdField("password-login").value = "pass"
        submit()
      }
    }
  }
}
