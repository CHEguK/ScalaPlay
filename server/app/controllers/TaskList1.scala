package controllers

import play.api.mvc.{BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}

@Singleton
class TaskList1 @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def taskList() = Action {
    Ok("Results")
  }
}
