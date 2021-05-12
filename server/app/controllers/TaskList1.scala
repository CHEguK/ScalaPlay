package controllers

import models.TaskListInMemoryModel
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}

@Singleton
class TaskList1 @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def taskList(): Action[AnyContent] = Action { request =>
    val usernameOpt = request.session.get("username")
    usernameOpt.map { username =>
      val tasks = TaskListInMemoryModel.getTasks(username)
      Ok(views.html.taskList1(tasks))
    }.getOrElse(Redirect(routes.TaskList1.login))
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
        Redirect(routes.TaskList1.taskList()).withSession("username" -> username)
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
        Redirect(routes.TaskList1.taskList()).withSession("username" -> username)
      } else {
        Redirect(routes.TaskList1.login)
      }
    }.getOrElse(Redirect(routes.TaskList1.login))
  }

  def login = Action {
    Ok(views.html.login())
  }

  def logout = Action {
    Redirect(routes.TaskList1.login).withSession()
  }

}
