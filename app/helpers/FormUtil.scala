package helpers

import play.api.data.FormError
import play.api.data.Forms._
import skinny.validator.MapValidator

/**
 * Created by xd on 2015/01/02.
 */
object FormUtil {
  implicit class RichParamMap(paramMap: Map[String, String]) {
    def getParamSeq(key: String): Seq[Map[String, String]] = {
      val param = paramMap.filterKeys(_.startsWith(s"$key["))
      val indexes = param.keySet.flatMap { k =>
        val sub = k.substring(key.length + 1) // "key[0].field" to "0].field"
        val e = sub.indexOf(']')
        if (e > -1) Some(sub.substring(0, e))
        else None
      }.toSeq

      indexes.map { i =>
        val k = s"$key[$i]"
        param.filterKeys(_.startsWith(k))
          .map(kv => kv._1.substring(k.length + 1) -> kv._2) // "key[0].field" to "field"
      }
    }
  }

  implicit class RichMapValidatorSeq(forms: Seq[MapValidator]) {
    def toForm(prefix: String): play.api.data.Form[_] = {
      val errors = for ((form, i) <- forms.zipWithIndex) yield {
        form.errors.errors.flatMap { case(key, errs) =>
          errs.map { e =>
            FormError(s"$prefix[$i].$key", "error." + e.name, e.messageParams)
          }
        }.toSeq
      }
      val data = forms.zipWithIndex flatMap { case (form, i) =>
        form.paramMap.filter(_._2 != null).map(kv => s"$prefix[$i].${kv._1}" -> kv._2.toString)
      }
      play.api.data.Form(ignored(null), data.toMap, errors.flatten, None)
    }
  }
}
