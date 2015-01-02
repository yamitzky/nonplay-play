package models

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

// If your model has +23 fields, switch this to normal class and mixin scalikejdbc.EntityEquality.
case class Tag(
  id: Int,
  name: String
)

object Tag extends SkinnyCRUDMapperWithId[Int, Tag] {
  override lazy val tableName = "tags"
  override lazy val defaultAlias = createAlias("t")
  override def idToRawValue(id: Int): Any = id
  override def rawValueToId(value: Any): Int = value.toString.toInt

  override def extract(rs: WrappedResultSet, rn: ResultName[Tag]): Tag = {
    autoConstruct(rs, rn)
  }

  def findByName(name: String): Option[Tag] = {
    Tag.where('name -> name).limit(1).apply().headOption
  }
}
