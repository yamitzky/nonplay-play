package models

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

// If your model has +23 fields, switch this to normal class and mixin scalikejdbc.EntityEquality.
case class Article(
  id: Int,
  title: String,
  body: String,
  createdAt: DateTime,
  userId: Int,
  user: Option[User] = None
)

object Article extends SkinnyCRUDMapperWithId[Int, Article] {
  override lazy val tableName = "articles"
  override lazy val defaultAlias = createAlias("a")
  override def idToRawValue(id: Int): Any = id
  override def rawValueToId(value: Any): Int = value.toString.toInt

  override def extract(rs: WrappedResultSet, rn: ResultName[Article]): Article = {
    autoConstruct(rs, rn, "user")
  }

  lazy val userOpt = {
    belongsTo[User](
      right = User,
      merge = (a, u) => a.copy(user = u)
    ).includes[User](
        merge = (as, us) => as map { a =>
          us.find(u => a.user.exists(_.id == u.id))
            .map(u => a.copy(user = Some(u)))
            .getOrElse(a)
        })
  }
}
