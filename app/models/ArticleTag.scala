package models

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

// If your model has +23 fields, switch this to normal class and mixin scalikejdbc.EntityEquality.
case class ArticleTag(
  id: Int,
  articleId: Int,
  tagId: Int
)

object ArticleTag extends SkinnyCRUDMapperWithId[Int, ArticleTag] {
  override lazy val tableName = "article_tags"
  override lazy val defaultAlias = createAlias("at")
  override def idToRawValue(id: Int): Any = id
  override def rawValueToId(value: Any): Int = value.toString.toInt

  override def extract(rs: WrappedResultSet, rn: ResultName[ArticleTag]): ArticleTag = {
    autoConstruct(rs, rn)
  }
}
