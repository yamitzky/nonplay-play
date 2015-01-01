package controllers

import models.Article
import play.api._
import play.api.mvc._

/**
 * Created by xd on 2015/01/01.
 */

object Articles extends Controller {

  def index = Action {
    Ok(views.html.articles.index(Article.findAll()))
  }

  def show(article_id: Int) = Action {
    Article.includes(Article.userOpt).findById(article_id) match {
      case Some(a) => Ok(views.html.articles.show(a))
      case None    => NotFound("Not found article")
    }
  }

}

