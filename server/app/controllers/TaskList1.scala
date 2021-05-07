package controllers

import play.api.mvc.{BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}

@Singleton
class TaskList1 @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def taskList() = Action {
    val tasks = List("task1", "task2", "task3")
    Ok(views.html.taskList1(tasks))
  }

  def validateLoginGet(username: String, password: String) = Action {
    Ok(s"Login: $username, Password: $password")
  }

  def validateLoginPost = Action { request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      Redirect(routes.TaskList1.taskList())
    }.getOrElse(Redirect(routes.TaskList1.login1))
  }

  def login1 = Action {
    Ok(views.html.login1())
  }

}
