package controllers

import helpers.RichModel.RichModel
import play.api.data._
import play.api.data.Forms._
import skinny.validator.{NewValidation, MapValidator}

/**
 * Created by xd on 2015/01/02.
 */

trait SkinnyController {

  /**
   * Form which has not bound request data from request yet.
   * @param validations skinny-style validations
   */
  case class UnboundForm(validations: Seq[NewValidation]) {

    /**
     * Binds request data from request.
     * @param request
     * @return validated form
     */
    def bindFromRequest(implicit request: play.api.mvc.Request[_]): MapValidator = {
      MapValidator(params)(validations:_*)
    }

    /**
     * Binds data.
     * @param params data
     * @return validated form
     */
    def bind(params: Map[String, String]): MapValidator = {
      MapValidator(params)(validations:_*)
    }

    /**
     * Converts to Play's Form.
     * e.g. used to GET form that is not POSTed yet.
     */
    implicit def toForm: play.api.data.Form[_] = {
      play.api.data.Form(ignored(null), Map(), Nil, None)
    }
  }

  implicit class RichMapValidator(form: MapValidator) {

    /**
     * Converts from Skinny's MapValidator(i.e. bound form) to Play's Form.
     * e.g. used to GET form that is already POSTed and invalid.
     */
    def toForm: play.api.data.Form[_] = {
      val errors = form.errors.errors.flatMap { case(key, errs) =>
        errs.map { e =>
          FormError(key, "error." + e.name, e.messageParams)
        }
      }.toSeq
      play.api.data.Form(ignored(null), form.paramMap.filter(_._2 != null).mapValues(_.toString), errors, None)
    }
  }

  /**
   * Factory method to create unbound form which has validations.
   * @param validations skinny-style validations
   * @return
   */
  def validation(validations: NewValidation*) = UnboundForm(validations)

  def params(implicit request: play.api.mvc.Request[_]) = {
    ((request.body match {
      case body: play.api.mvc.AnyContent if body.asFormUrlEncoded.isDefined => body.asFormUrlEncoded.get
      case body: play.api.mvc.AnyContent if body.asMultipartFormData.isDefined => body.asMultipartFormData.get.asFormUrlEncoded
      case body: Map[_, _] => body.asInstanceOf[Map[String, Seq[String]]]
      case body: play.api.mvc.MultipartFormData[_] => body.asFormUrlEncoded
      case _ => Map.empty[String, Seq[String]]
    }) ++ request.queryString).mapValues(_.last)
  }

  implicit class MappableModel[T <% RichModel[_]](model: T) {
    /**
     * Converts case class to form.
     * e.g. used to GET form to edit that is not POSTed yet.
     */
    def toForm = {
      play.api.data.Form(ignored(null), model.toMap.mapValues(_.toString), Nil, None)
    }
  }
}
