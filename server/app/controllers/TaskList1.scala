package controllers

import models.TaskListInMemoryModel
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}

@Singleton
class TaskList1 @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def taskList(): Action[AnyContent] = Action {
    val username = "Andrew"

    val tasks = TaskListInMemoryModel.getTasks(username)
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
      if (TaskListInMemoryModel.validate(username, password)) {
        Redirect(routes.TaskList1.taskList())
      } else {
        Redirect(routes.TaskList1.login)
      }
    }.getOrElse(Redirect(routes.TaskList1.login))
  }

  def createUser = Action { request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.createUser(username, password)) {
        Redirect(routes.TaskList1.taskList())
      } else {
        Redirect(routes.TaskList1.login)
      }
    }.getOrElse(Redirect(routes.TaskList1.login))
  }

  def login = Action {
    Ok(views.html.login())
  }

}
