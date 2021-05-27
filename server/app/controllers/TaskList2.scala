package controllers

import javax.inject._

import models.TaskListInMemoryModel
import play.api.mvc.{AbstractController, Action, AnyContent, BaseController, ControllerComponents, MessagesActionBuilder}
import play.api.data._
import play.api.data.Forms._
import play.api.i18n._

@Singleton
class TaskList2 @Inject()(сс: ControllerComponents) extends AbstractController(сс) {
  def load = Action { implicit request =>
    Ok(views.html.version2Main())
  }

  def login = Action {
    Ok(views.html.login2())
  }

  def validate(username: String, password: String) = Action {
    if (TaskListInMemoryModel.validate(username, password)) {
      Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username))).withSession("username" -> username)
    } else {
      Ok(views.html.login2())
    }
  }

  def createUser(username: String, password: String) = Action {
    if (TaskListInMemoryModel.createUser(username, password)) {
      Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username))).withSession("username" -> username)
    } else {
      Ok(views.html.login2())
    }
  }

  def addTask(task: String) = Action { implicit request =>
    val usernameOpt = request.session.get("username")
    usernameOpt.map { username =>
      TaskListInMemoryModel.addTask(username, task)
      Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username)))
    }.getOrElse(Ok(views.html.login2()))
  }

  def delete(index: Int) = Action { implicit request =>
    val usernameOpt = request.session.get("username")
    usernameOpt.map { username =>
      TaskListInMemoryModel.removeTask(username, index)
      Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username)))
    }.getOrElse(Ok(views.html.login2()))
  }

  def logout = Action {
    Redirect(routes.TaskList2.login).withNewSession
  }
}
