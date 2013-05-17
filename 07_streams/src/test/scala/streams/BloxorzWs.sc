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
    def findChar(c: Char, levelVector: Vector[Vector[Char]]): Pos = {
      val foundPosition = for {
        xIdx <- (0 to levelVector.size - 1)
        yIdx <- (0 to levelVector(xIdx).size - 1)
        if levelVector(xIdx)(yIdx) == c
      } yield Pos(xIdx, yIdx)
      if (foundPosition.size == 0) throw new java.lang.IllegalStateException("Invalid map? Cannot find char " + c + " on the terrain map");
      foundPosition(0)
    }
  }
  val problem = new Problem                       //> problem  : streams.BloxorzWs.Problem = streams.BloxorzWs$$anonfun$main$1$Pro
                                                  //| blem$1@4df3e7e8

  val v = Vector(0, 1, 2) updated (2, 5)          //> v  : scala.collection.immutable.Vector[Int] = Vector(0, 1, 5)

  //problem.findChar('T', levelVector)
   v.indices                                      //> res0: scala.collection.immutable.Range = Range(0, 1, 2)
   
   for (i <- v.indices) {println (i)}             //> 0
                                                  //| 1
                                                  //| 2
  //levelVector.takeRight(n).indexOf('S')

  //val terrainFunction = problem.terrainFunction(levelVector)

  //terrainFunction(Pos(1,2))
  //terrainFunction(Pos(0,0))

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