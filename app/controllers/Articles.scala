package controllers

import models.{ArticleTag, Tag, User, Article}
import helpers.FormUtil._
import org.joda.time.DateTime
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import skinny.controller.Params
import skinny.validator._
import skinny._
import helpers.RichModel._

/**
 * Created by xd on 2015/01/01.
 */

object Articles extends Controller with SkinnyController {
  def createForm: UnboundForm = validation(
    paramKey("title") is required & maxLength(30),
    paramKey("body") is required & maxLength(1024)
  )

  def createFormStrongParameters: Seq[(String, ParamType)] = Seq(
    "title" -> ParamType.String,
    "body" -> ParamType.String,
    "user_id" -> ParamType.Int
  )

  def updateFormStrongParameters: Seq[(String, ParamType)] = Seq(
    "title" -> ParamType.String,
    "body" -> ParamType.String
  )

  def index = Action {
    Ok(views.html.articles.index(Article.findAll()))
  }

  def show(article_id: Int) = Action {
    Article.joins(Article.userOpt).findById(article_id) match {
      case Some(a) => Ok(views.html.articles.show(a))
      case None    => NotFound("Not found article")
    }
  }

  def newForm = Action { implicit request =>
    Ok(views.html.articles.newForm(createForm.toForm))
  }

  def create() = Action { implicit request =>
    val form = createForm.bindFromRequest
    if (form.validate()) {
      val p = form.paramMap ++ Map("user_id" -> 1) // must be authorized user's id
      val id = Article.createWithPermittedAttributes(Params(p).permit(createFormStrongParameters: _*))
      Redirect(routes.Articles.show(id))
    } else {
      BadRequest(views.html.articles.newForm(form.toForm))
    }
  }

  def edit(article_id: Int) = Action {
    Article.findById(article_id) match {
      case Some(a) => Ok(views.html.articles.editForm(article_id, a.toForm))
      case None    => NotFound("Not found article")
    }
  }

  def update(article_id: Int) = Action { implicit request =>
    Article.findById(article_id) match {
      case Some(a) =>
        val form = createForm.bindFromRequest
        if (form.validate()) {
          Article.updateById(article_id).withPermittedAttributes(Params(params).permit(updateFormStrongParameters: _*))
          Redirect(routes.Articles.show(article_id))
        } else {
          BadRequest(views.html.articles.editForm(article_id, form.toForm))
        }
      case None => NotFound("Not found article")
    }
  }

  /* example for nested values */

  def createTagStrongParameters: Seq[(String, ParamType)] = Seq(
    "name" -> ParamType.String
  )

  def tagForm: UnboundForm = validation(
    paramKey("name") is required & maxLength(16)
  )

  def createWithTag() = Action { implicit request =>
    val form = createForm.bindFromRequest
    val tag_forms = params.getParamSeq("tags").map(p => tagForm.bind(p))

    if (form.validate() && tag_forms.forall(_.validate())) {
      val p = form.paramMap ++ Map("user_id" -> 1) // must be authorized user's id
      val id = Article.createWithPermittedAttributes(Params(p).permit(createFormStrongParameters: _*))

      // tag creation
      for(f <- tag_forms) {
        val tag = f.params.get("name").get
        val tid = Tag.findByName(tag) match {
          case Some(t) => t.id
          case None    => Tag.createWithAttributes('name -> tag)
        }
        ArticleTag.createWithAttributes('article_id -> id, 'tag_id -> tid)
      }

      Redirect(routes.Articles.show(id))
    } else {
      BadRequest(views.html.articles.newFormWithTag(form.toForm, tag_forms.toForm("tags")))
    }
  }
}

