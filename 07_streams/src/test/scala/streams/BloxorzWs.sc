package streams

import streams.BloxorzWs._

object BloxorzWs {
  case class Pos(x: Int, y: Int) {
    /** The position obtained by changing the `x` coordinate by `d` */
    def dx(d: Int) = copy(x = x + d)

    /** The position obtained by changing the `y` coordinate by `d` */
    def dy(d: Int) = copy(y = y + d)
  }
  val level: String =
        """ST
          |oo
          |oo""".stripMargin                      //> level  : String = ST
                                                  //| oo
                                                  //| oo

  val levelVector = Vector(level.split("\n").map(str => Vector(str: _*)): _*)
                                                  //> levelVector  : scala.collection.immutable.Vector[scala.collection.immutable.
                                                  //| Vector[Char]] = Vector(Vector(S, T), Vector(o, o), Vector(o, o))
  
  class Problem {

    def terrainFunction(levelVector: Vector[Vector[Char]]): Pos => Boolean = {
      def helperFun(pos: Pos): Boolean = {
        // TODO: with default value will be fine here for nonexising indexes. Maybe override the ()
        if (levelVector.isDefinedAt(pos.x) && levelVector(pos.x).isDefinedAt(pos.y))
          levelVector(pos.x)(pos.y) match {
          case 'o' => true
          case 'T' => true
          case 'S' => true
          case _ => false
          }
        else false
      }
      helperFun
    }
  }
  val problem = new Problem                       //> problem  : streams.BloxorzWs.Problem = streams.BloxorzWs$$anonfun$main$1$Pr
                                                  //| oblem$1@754e3d8f
  
  val v = Vector(0,1,2) updated (2,5)             //> v  : scala.collection.immutable.Vector[Int] = Vector(0, 1, 5)
  val levelVectorWithDefault = levelVector        //> levelVectorWithDefault  : scala.collection.immutable.Vector[scala.collectio
                                                  //| n.immutable.Vector[Char]] = Vector(Vector(S, T), Vector(o, o), Vector(o, o)
                                                  //| )
  
  val terrainFunction = problem.terrainFunction(levelVector)
                                                  //> terrainFunction  : streams.BloxorzWs.Pos => Boolean = <function1>
  
  terrainFunction(Pos(1,2))                       //> res0: Boolean = false
  terrainFunction(Pos(0,0))                       //> res1: Boolean = true

  
  /**
   * This method returns terrain function that represents the terrain
   * in `levelVector`. The vector contains parsed version of the `level`
   * string. For example, the following level
   *
   *   val level =
   *     """ST
   *       |oo
   *       |oo""".stripMargin
   *
   * is represented as
   *
   *   Vector(Vector('S', 'T'), Vector('o', 'o'), Vector('o', 'o'))
   *
   * The resulting function should return `true` if the position `pos` is
   * a valid position (not a '-' character) inside the terrain described
   * by `levelVector`.
   */

}