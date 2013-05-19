package streams

import streams.Bloxorz._

object BloxorzWs {

  val level: String =
    """ST
          |oo
          |oo""".stripMargin                      //> level  : String = ST
                                                  //| oo
                                                  //| oo

  val levelVector = Vector(level.split("\n").map(str => Vector(str: _*)): _*)
                                                  //> levelVector  : scala.collection.immutable.Vector[scala.collection.immutable.
                                                  //| Vector[Char]] = Vector(Vector(S, T), Vector(o, o), Vector(o, o))

  object InfiniteLevel extends Solver with InfiniteTerrain {
    val startPos = Pos(1, 3)
    //val goal = Pos(5,8)
    val goal = Pos(5, 8)
  }

  // val ngb = InfiniteLevel.neighborsWithHistory(InfiniteLevel.startBlock, List()) take(3) toList

  val l = InfiniteLevel                           //> l  : streams.BloxorzWs.InfiniteLevel.type = streams.BloxorzWs$$anonfun$main$
                                                  //| 1$InfiniteLevel$2$@45a5049a

  val startB = new l.Block(l.Pos(0, 0), l.Pos(0, 0))
                                                  //> startB  : streams.BloxorzWs.l.Block = B:0:0:0:0
                                                  //| 
  val b = new l.Block(l.Pos(1, 1), l.Pos(1, 2))   //> b  : streams.BloxorzWs.l.Block = B:1:1:1:2
                                                  //| 
  l.from((startB, Nil) #:: Stream.empty, Set()).take(100).toList
                                                  //> res0: List[(streams.BloxorzWs.l.Block, List[streams.BloxorzWs.l.Move])] = Li
                                                  //| st((B:0:-2:0:-1
                                                  //| ,List(Left)), (B:0:1:0:2
                                                  //| ,List(Right)), (B:-2:0:-1:0
                                                  //| ,List(Up)), (B:1:0:2:0
                                                  //| ,List(Down)), (B:0:-3:0:-3
                                                  //| ,List(Left, Left)), (B:0:0:0:0
                                                  //| ,List(Right, Left)), (B:-1:-2:-1:-1
                                                  //| ,List(Up, Left)), (B:1:-2:1:-1
                                                  //| ,List(Down, Left)), (B:0:3:0:3
                                                  //| ,List(Right, Right)), (B:-1:1:-1:2
                                                  //| ,List(Up, Right)), (B:1:1:1:2
                                                  //| ,List(Down, Right)), (B:-2:-1:-1:-1
                                                  //| ,List(Left, Up)), (B:-2:1:-1:1
                                                  //| ,List(Right, Up)), (B:-3:0:-3:0
                                                  //| ,List(Up, Up)), (B:1:-1:2:-1
                                                  //| ,List(Left, Down)), (B:1:1:2:1
                                                  //| ,List(Right, Down)), (B:3:0:3:0
                                                  //| ,List(Down, Down)), (B:1:-2:2:-2
                                                  //| ,List(Left, Left, Down)), (B:0:-1:0:-1
                                                  //| ,List(Up, Left, Down)), (B:3:-1:3:-1
                                                  //| ,List(Down, Left, Down)), (B:1:2:2:2
                                                  //| ,List(Right, Right, Down)), (B:0:1:0:1
                                                  //| ,List(Up, Right, Down)), (B:3:1:3:1
                                                  //| ,List(Down, Right, Down)), (B:3:-2:3:-1
                                                  //| ,List(Left, Down, Down)), (B:3:1:3:2
                                                  //| ,List(Right, Down, Down)), (B:4:0:5:0
                                                  //| ,List(Down, Down, Down)), (B:3:-3:3:-3
                                                  //| ,List(Left, Left, Down, Down)), (B:2:-2:2:-1
                                                  //| ,List(Up, Left, Down, Down)), (B:4:-2:4:-1
                                                  //| ,List(Down, Left, Down, Down)), (B:3:3:3:3
                                                  //| ,List(Right, Right, Down, Down)), (B:2:1:2:2
                                                  //| ,List(Up, Right, Down, Down)), (B:4:1:4:2
                                                  //| ,List(Down, Right, Down, Down)), (B:4:-1:5:-1
                                                  //| ,List(Left, Down, Down, Down)), (B:4:1:5:1
                                                  //| ,List(Right, Down, Down, Down)), (B:6:0:6:0
                                                  //| ,List(Down, Down, Down, Down)), (B:4:-2:5:-2
                                                  //| ,List(Left, Left, Down, Down, Down)), (B:6:-1:6:-1
                                                  //| ,List(Down, Left, Down, Down, Down)), (B:4:2:5:2
                                                  //| ,List(Right, Right, Down, Down, Down)), (B:6:1:6:1
                                                  //| ,List(Down, Right, Down, Down, Down)), (B:6:-2:6:-1
                                                  //| ,List(Left, Down, Down, Down, Down)), (B:6:1:6:2
                                                  //| ,List(Right, Down, Down, Down, Down)), (B:7:0:8:0
                                                  //| ,List(Down, Down, Down, Down, Down)), (B:6:-3:6:-3
                                                  //| ,List(Left, Left, Down, Down, Down, Down)), (B:5:-2:5:-1
                                                  //| ,List(Up, Left, Down, Down, Down, Down)), (B:7:-2:7:-1
                                                  //| ,List(Down, Left, Down, Down, Down, Down)), (B:6:3:6:3
                                                  //| ,List(Right, Right, Down, Down, Down, Down)), (B:5:1:5:2
                                                  //| ,List(Up, Right, Down, Down, Down, Down)), (B:7:1:7:2
                                                  //| ,List(Down, Right, Down, Down, Down, Down)), (B:7:-1:8:-1
                                                  //| ,List(Left, Down, Down, Down, Down, Down)), (B:7:1:8:1
                                                  //| ,List(Right, Down, Down, Down, Down, Down)), (B:9:0:9:0
                                                  //| ,List(Down, Down, Down, Down, Down, Down)), (B:7:-2:8:-2
                                                  //| ,List(Left, Left, Down, Down, Down, Down, Down)), (B:9:-1:9:-1
                                                  //| ,List(Down, Left, Down, Down, Down, Down, Down)), (B:7:2:8:2
                                                  //| ,List(Right, Right, Down, Down, Down, Down, Down)), (B:9:1:9:1
                                                  //| ,List(Down, Right, Down, Down, Down, Down, Down)), (B:9:-2:9:-1
                                                  //| ,List(Left, Down, Down, Down, Down, Down, Down)), (B:9:1:9:2
                                                  //| ,List(Right, Down, Down, Down, Down, Down, Down)), (B:10:0:11:0
                                                  //| ,List(Down, Down, Down, Down, Down, Down, Down)), (B:9:-3:9:-3
                                                  //| ,List(Left, Left, Down, Down, Down, Down, Down, Down)), (B:8:-2:8:-1
                                                  //| ,List(Up, Left, Down, Down, Down, Down, Down, Down)), (B:10:-2:10:-1
                                                  //| ,List(Down, Left, Down, Down, Down, Down, Down, Down)), (B:9:3:9:3
                                                  //| ,List(Right, Right, Down, Down, Down, Down, Down, Down)), (B:8:1:8:2
                                                  //| ,List(Up, Right, Down, Down, Down, Down, Down, Down)), (B:10:1:10:2
                                                  //| ,List(Down, Right, Down, Down, Down, Down, Down, Down)), (B:10:-1:11:-1
                                                  //| ,List(Left, Down, Down, Down, Down, Down, Down, Down)), (B:10:1:11:1
                                                  //| ,List(Right, Down, Down, Down, Down, Down, Down, Down)), (B:12:0:12:0
                                                  //| ,List(Down, Down, Down, Down, Down, Down, Down, Down)), (B:10:-2:11:-2
                                                  //| ,List(Left, Left, Down, Down, Down, Down, Down, Down, Down)), (B:12:-1:12:-1
                                                  //| 
                                                  //| ,List(Down, Left, Down, Down, Down, Down, Down, Down, Down)), (B:10:2:11:2
                                                  //| ,List(Right, Right, Down, Down, Down, Down, Down, Down, Down)), (B:12:1:12:1
                                                  //| 
                                                  //| ,List(Down, Right, Down, Down, Down, Down, Down, Down, Down)), (B:12:-2:12:-
                                                  //| 1
                                                  //| ,List(Left, Down, Down, Down, Down, Down, Down, Down, Down)), (B:12:1:12:2
                                                  //| ,List(Right, Down, Down, Down, Down, Down, Down, Down, Down)), (B:13:0:14:0
                                                  //| ,List(Down, Down, Down, Down, Down, Down, Down, Down, Down)), (B:12:-3:12:-3
                                                  //| 
                                                  //| ,List(Left, Left, Down, Down, Down, Down, Down, Down, Down, Down)), (B:11:-2
                                                  //| :11:-1
                                                  //| ,List(Up, Left, Down, Down, Down, Down, Down, Down, Down, Down)), (B:13:-2:1
                                                  //| 3:-1
                                                  //| ,List(Down, Left, Down, Down, Down, Down, Down, Down, Down, Down)), (B:12:3:
                                                  //| 12:3
                                                  //| ,List(Right, Right, Down, Down, Down, Down, Down, Down, Down, Down)), (B:11:
                                                  //| 1:11:2
                                                  //| ,List(Up, Right, Down, Down, Down, Down, Down, Down, Down, Down)), (B:13:1:1
                                                  //| 3:2
                                                  //| ,List(Down, Right, Down, Down, Down, Down, Down, Down, Down, Down)), (B:13:-
                                                  //| 1:14:-1
                                                  //| ,List(Left, Down, Down, Down, Down, Down, Down, Down, Down, Down)), (B:13:1:
                                                  //| 14:1
                                                  //| ,List(Right, Down, Down, Down, Down, Down, Down, Down, Down, Down)), (B:15:0
                                                  //| :15:0
                                                  //| ,List(Down, Down, Down, Down, Down, Down, Down, Down, Down, Down)), (B:13:-2
                                                  //| :14:-2
                                                  //| ,List(Left, Left, Down, Down, Down, Down, Down, Down, Down, Down, Down)), (B
                                                  //| :15:-1:15:-1
                                                  //| ,List(Down, Left, Down, Down, Down, Down, Down, Down, Down, Down, Down)), (B
                                                  //| :13:2:14:2
                                                  //| ,List(Right, Right, Down, Down, Down, Down, Down, Down, Down, Down, Down)), 
                                                  //| (B:15:1:15:1
                                                  //| ,List(Down, Right, Down, Down, Down, Down, Down, Down, Down, Down, Down)), (
                                                  //| B:15:-2:15:-1
                                                  //| ,List(Left, Down, Down, Down, Down, Down, Down, Down, Down, Down, Down)), (B
                                                  //| :15:1:15:2
                                                  //| ,List(Right, Down, Down, Down, Down, Down, Down, Down, Down, Down, Down)), (
                                                  //| B:16:0:17:0
                                                  //| ,List(Down, Down, Down, Down, Down, Down, Down, Down, Down, Down, Down)), (B
                                                  //| :15:-3:15:-3
                                                  //| ,List(Left, Left, Down, Down, Down, Down, Down, Down, Down, Down, Down, Down
                                                  //| )), (B:14:-2:14:-1
                                                  //| ,List(Up, Left, Down, Down, Down, Down, Down, Down, Down, Down, Down, Down))
                                                  //| , (B:16:-2:16:-1
                                                  //| ,List(Down, Left, Down, Down, Down, Down, Down, Down, Down, Down, Down, Down
                                                  //| )), (B:15:3:15:3
                                                  //| ,List(Right, Right, Down, Down, Down, Down, Down, Down, Down, Down, Down, Do
                                                  //| wn)), (B:14:1:14:2
                                                  //| ,List(Up, Right, Down, Down, Down, Down, Down, Down, Down, Down, Down, Down)
                                                  //| ), (B:16:1:16:2
                                                  //| ,List(Down, Right, Down, Down, Down, Down, Down, Down, Down, Down, Down, Dow
                                                  //| n)), (B:16:-1:17:-1
                                                  //| ,List(Left, Down, Down, Down, Down, Down, Down, Down, Down, Down, Down, Down
                                                  //| )), (B:16:1:17:1
                                                  //| ,List(Right, Down, Down, Down, Down, Down, Down, Down, Down, Down, Down, Dow
                                                  //| n)), (B:18:0:18:0
                                                  //| ,List(Down, Down, Down, Down, Down, Down, Down, Down, Down, Down, Down, Down
                                                  //| )), (B:16:-2:17:-2
                                                  //| ,List(Left, Left, Down, Down, Down, Down, Down, Down, Down, Down, Down, Down
                                                  //| , Down)))

  trait SolutionChecker extends GameDef with Solver with StringParserTerrain {
    /**
     * This method applies a list of moves `ls` to the block at position
     * `startPos`. This can be used to verify if a certain list of moves
     * is a valid solution, i.e. leads to the goal.
     */
    def solve(ls: List[Move]): Block =
      ls.foldLeft(startBlock) {
        case (block, move) => move match {
          case Left  => block.left
          case Right => block.right
          case Up    => block.up
          case Down  => block.down
        }
      }
  }

  trait Level1 extends SolutionVisualizer {
    /* terrain for level 1*/

    val level =
      """ooo-------
      |oSoooo----
      |ooooooooo-
      |-ooooooooo
      |-----ooToo
      |------ooo-""".stripMargin

    val optsolution = List(Right, Right, Down, Right, Right, Right, Down)
  }

  val level1 = new Level1 {}                      //> level1  : streams.BloxorzWs.Level1 = streams.BloxorzWs$$anonfun$main$1$$ano
                                                  //| n$1@34fc265a
  
  level1.solution                                 //> res1: List[streams.BloxorzWs.level1.Move] = List(Right, Down, Right, Down, 
                                                  //| Left, Up, Right, Down, Down, Right, Up, Right, Down)
  level1.displaySolution                          //> Visualizing solution found for game streams.BloxorzWs$$anonfun$main$1$$anon
                                                  //| $1
                                                  //| Status with block at B:1:1:1:1
                                                  //| 
                                                  //| ooo-------
                                                  //| o#oooo----
                                                  //| ooooooooo-
                                                  //| -ooooooooo
                                                  //| -----ooooo
                                                  //| ------ooo-
                                                  //| Performing move Right
                                                  //| Status with block at B:1:2:1:3
                                                  //| 
                                                  //| ooo-------
                                                  //| oo##oo----
                                                  //| ooooooooo-
                                                  //| -ooooooooo
                                                  //| -----ooooo
                                                  //| ------ooo-
                                                  //| Performing move Down
                                                  //| Status with block at B:2:2:2:3
                                                  //| 
                                                  //| ooo-------
                                                  //| oooooo----
                                                  //| oo##ooooo-
                                                  //| -ooooooooo
                                                  //| -----ooooo
                                                  //| ------ooo-
                                                  //| Performing move Right
                                                  //| Status with block at B:2:4:2:4
                                                  //| 
                                                  //| ooo-------
                                                  //| oooooo----
                                                  //| oooo#oooo-
                                                  //| -ooooooooo
                                                  //| -----ooooo
                                                  //| ------ooo-
                                                  //| Performing move Down
                                                  //| Status with block at B:3:4:4:4
                                                  //| 
                                                  //| ooo-------
                                                  //| oooooo----
                                                  //| ooooooooo-
                                                  //| -ooo#ooooo
                                                  //| ----#ooooo
                                                  //| ------ooo-
                                                  //| Performing move Left
                                                  //| Status with block at B:3:3:4:3
                                                  //| 
                                                  //| ooo-------
                                                  //| oooooo----
                                                  //| ooooooooo-
                                                  //| -oo#oooooo
                                                  //| ---#-ooooo
                                                  //| ------ooo-
                                                  //| Performing move Up
                                                  //| Status with block at B:2:3:2:3
                                                  //| 
                                                  //| ooo-------
                                                  //| oooooo----
                                                  //| ooo#ooooo-
                                                  //| -ooooooooo
                                                  //| -----ooooo
                                                  //| ------ooo-
                                                  //| Performing move Right
                                                  //| Status with block at B:2:4:2:5
                                                  //| 
                                                  //| ooo-------
                                                  //| oooooo----
                                                  //| oooo##ooo-
                                                  //| -ooooooooo
                                                  //| -----ooooo
                                                  //| ------ooo-
                                                  //| Performing move Down
                                                  //| Status with block at B:3:4:3:5
                                                  //| 
                                                  //| ooo-------
                                                  //| oooooo----
                                                  //| ooooooooo-
                                                  //| -ooo##oooo
                                                  //| -----ooooo
                                                  //| ------ooo-
                                                  //| Performing move Down
                                                  //| Status with block at B:4:4:4:5
                                                  //| 
                                                  //| ooo-------
                                                  //| oooooo----
                                                  //| ooooooooo-
                                                  //| -ooooooooo
                                                  //| ----##oooo
                                                  //| ------ooo-
                                                  //| Performing move Right
                                                  //| Status with block at B:4:6:4:6
                                                  //| 
                                                  //| ooo-------
                                                  //| oooooo----
                                                  //| ooooooooo-
                                                  //| -ooooooooo
                                                  //| -----o#ooo
                                                  //| ------ooo-
                                                  //| Performing move Up
                                                  //| Status with block at B:2:6:3:6
                                                  //| 
                                                  //| ooo-------
                                                  //| oooooo----
                                                  //| oooooo#oo-
                                                  //| -ooooo#ooo
                                                  //| -----ooooo
                                                  //| ------ooo-
                                                  //| Performing move Right
                                                  //| Status with block at B:2:7:3:7
                                                  //| 
                                                  //| ooo-------
                                                  //| oooooo----
                                                  //| ooooooo#o-
                                                  //| -oooooo#oo
                                                  //| -----ooooo
                                                  //| ------ooo-
                                                  //| Performing move Down
                                                  //| Status with block at B:4:7:4:7
                                                  //| 
                                                  //| ooo-------
                                                  //| oooooo----
                                                  //| ooooooooo-
                                                  //| -ooooooooo
                                                  //| -----oo#oo
                                                  //| ------ooo-
                                                  //| Game Over
  /*
  l.pathsToGoal
  l.done(l.Block(l.goal, l.goal))
  l.pathsFromStart.find(pair => l.done(pair._1) )
  println(l.solution)
  */
}