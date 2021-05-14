package controllers

import models.TaskListInMemoryModel
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}

@Singleton
class TaskList1 @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def taskList(): Action[AnyContent] = Action { implicit request =>
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
        Redirect(routes.TaskList1.login).flashing("error" -> "Invalid username/password combination")
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
        Redirect(routes.TaskList1.login).flashing("error" -> "User creation failed")
      }
    }.getOrElse(Redirect(routes.TaskList1.login))
  }

  def addTask() = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    val usernameOpt = request.session.get("username")
    usernameOpt.map { username =>
      postVals.map { args =>
        val task = args("newTask").head
        TaskListInMemoryModel.addTask(username, task)
        Redirect(routes.TaskList1.taskList())
      }.getOrElse(Redirect(routes.TaskList1.taskList()))
    }.getOrElse(Redirect(routes.TaskList1.login))
  }

  def login = Action { implicit request =>
    Ok(views.html.login())
  }

  def logout = Action {
    Redirect(routes.TaskList1.login).withSession()
  }

}
