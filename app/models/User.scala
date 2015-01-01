package models

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

// If your model has +23 fields, switch this to normal class and mixin scalikejdbc.EntityEquality.
case class User(
  id: Int,
  name: String,
  gender: Option[Int] = None,
  attr1: Option[Int] = None,
  attr2: Option[Int] = None,
  attr3: Option[Int] = None,
  attr4: Option[Int] = None,
  attr5: Option[Int] = None,
  attr6: Option[Int] = None,
  attr7: Option[Int] = None,
  attr8: Option[Int] = None,
  attr9: Option[Int] = None,
  attr10: Option[Int] = None,
  attr11: Option[Int] = None,
  attr12: Option[Int] = None,
  attr13: Option[Int] = None,
  attr14: Option[Int] = None,
  attr15: Option[Int] = None,
  attr16: Option[Int] = None,
  attr17: Option[Int] = None,
  attr18: Option[Int] = None,
  attr19: Option[Int] = None,
  attr20: Option[Int] = None,
  attr21: Option[Int] = None,
  attr22: Option[Int] = None,
  attr23: Option[Int] = None
)

object User extends SkinnyCRUDMapperWithId[Int, User] {
  override lazy val tableName = "users"
  override lazy val defaultAlias = createAlias("u")
  override def idToRawValue(id: Int): Any = id
  override def rawValueToId(value: Any): Int = value.toString.toInt

  override def extract(rs: WrappedResultSet, rn: ResultName[User]): User = {
    autoConstruct(rs, rn)
  }
}
