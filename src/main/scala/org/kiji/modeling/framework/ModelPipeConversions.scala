package org.kiji.modeling.framework

import cascading.pipe.Pipe
import cascading.flow.FlowDef
import com.twitter.scalding.Mode

import org.kiji.annotations.ApiAudience
import org.kiji.annotations.ApiStability
import org.kiji.annotations.Inheritance
import org.kiji.express.flow.KijiSource
import org.kiji.express.modeling.lib.RecommendationPipe

/**
 * ModelPipeConversions contains implicit conversions necessary for KijiModeling that are not
 * included in KijiExpress's KijiJob.
 */
@ApiAudience.Private
@ApiStability.Experimental
@Inheritance.Sealed
trait ModelPipeConversions {

  /**
   * Converts a Cascading Pipe to an Express Recommendation Pipe. This method permits implicit
   * conversions from Pipe to RecommendationPipe.
   *
   * @param pipe to convert to a RecommendationPipe.
   * @return a RecommendationPipe wrapping the specified Pipe.
   */
  implicit def pipe2RecommendationPipe(pipe: Pipe): RecommendationPipe =
    new RecommendationPipe(pipe)

  /**
   * Converts a KijiSource to a KijiExpress Recommendation Pipe. This method permits implicit
   * conversions from Source to RecommendationPipe.
   *
   * We expect flowDef and mode implicits to be in scope.  This should be true in the context of a
   * Job, KijiJob, or inside the ShellRunner.
   *
   * @param source to convert to a KijiPipe.
   * @return a RecommendationPipe read from the specified source.
   */
  implicit def source2RecommendationPipe(
      source: KijiSource)(
      implicit flowDef: FlowDef,
      mode: Mode): RecommendationPipe = new RecommendationPipe(source.read(flowDef, mode))
}
