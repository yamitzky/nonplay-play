package helpers

import javassist.Modifier

/**
 * Created by xd on 2015/01/03.
 */
object RichModel {
  implicit class RichModel[T <: Product](model: T) {
    def toMap: Map[String, Any] = {
      model.getClass.getDeclaredFields.map(_.getName).zip(model.productIterator.toList).toMap
    }
  }
}
