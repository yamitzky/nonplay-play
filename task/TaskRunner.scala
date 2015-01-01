/**
 * Created by xd on 2015/01/01.
 */

import skinny.task._, generator._

object TaskRunner extends SkinnyTaskLauncher {
  register("model", (params) => PlayModelGenerator.run(params))
  register("reverse-model", (params) => PlayReverseModelGenerator.run(params))
  register("reverse-model-all", (params) => PlayReverseModelAllGenerator.run(params))
}
