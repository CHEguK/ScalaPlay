package controllers

import models.{TaskListInMemoryModel, UserData}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.{Inject, Singleton}

@Singleton
class TaskList3 @Inject()(сс: ControllerComponents) extends AbstractController(сс) {
  def load = Action { implicit request =>
    Ok(views.html.version3Main())
  }

  implicit val userDataReads = Json.reads[UserData]

  def validate = Action { implicit request =>
    request.body.asJson.map { body =>
      Json.fromJson[UserData](body) match {
        case JsSuccess(ud, path) =>
          if(TaskListInMemoryModel.validate(ud.username, ud.password)) {
            Ok(Json.toJson(true)).withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
          } else {
            Ok(views.html.login2())
          }
        case e @ JsError(_) => Redirect(routes.TaskList3.load)
      }
      Ok("")
    }.getOrElse(Redirect(routes.TaskList3.load))
  }

  def data = Action {
    Ok(Json.toJson(Seq("A", "B", "C")))
  }
}
