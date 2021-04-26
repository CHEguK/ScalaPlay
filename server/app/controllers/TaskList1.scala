package controllers

import play.api.mvc.{BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}

@Singleton
class TaskList1 @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def taskList() = Action {
    val tasks = List("task1", "task2", "task3")
    Ok(views.html.taskList1(tasks))
  }

  def product(productType: String, prodNum: Int) = Action {
    Ok(s"Product type: $productType, product Number: $prodNum")
  }

}
