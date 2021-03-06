package controllers

import models.TaskListInMemoryModel
import play.api.mvc.{AbstractController, Action, AnyContent, BaseController, ControllerComponents, MessagesActionBuilder}
import play.api.data._
import play.api.data.Forms._
import javax.inject.{Inject, Singleton}


case class LoginData(username: String, password: String)

@Singleton
class TaskList1 @Inject()(сс: ControllerComponents, messagesAction: MessagesActionBuilder) extends AbstractController(сс) with play.api.i18n.I18nSupport {

  val loginForm = Form(mapping(
    "Username" -> text(3, 10),
    "password" -> text(8, 30)
  )(LoginData.apply)(LoginData.unapply))

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

  def validateLoginForm = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.login(formWithErrors)),
      ld =>
        if (TaskListInMemoryModel.validate(ld.username, ld.password)) {
          Redirect(routes.TaskList1.taskList()).withSession("username" -> ld.username)
        } else {
          Redirect(routes.TaskList1.login).flashing("error" -> "Invalid username/password combination")
        }
    )
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

  def deleteTask = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    val usernameOpt = request.session.get("username")
    usernameOpt.map { username =>
      postVals.map { args =>
        val index = args("index").head.toInt
        TaskListInMemoryModel.removeTask(username, index)
        Redirect(routes.TaskList1.taskList())
      }.getOrElse(Redirect(routes.TaskList1.taskList()))
    }.getOrElse(Redirect(routes.TaskList1.login))
  }



  def login = Action { implicit request =>
    Ok(views.html.login(loginForm))
  }

  def logout = Action {
    Redirect(routes.TaskList1.login).withSession()
  }

}
