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
                                                  //| 1$InfiniteLevel$2$@5a62a404

  val startB = new l.Block(l.Pos(0, 0), l.Pos(0, 0))
                                                  //> startB  : streams.BloxorzWs.l.Block =  B(0:0:0:0)
  val b = new l.Block(l.Pos(1, 1), l.Pos(1, 2))   //> b  : streams.BloxorzWs.l.Block =  B(1:1:1:2)
  //l.from((startB, Nil) #:: Stream.empty, Set()).take(100).toList

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

    /*  val level =
      """ooo-------
      |oSoooo----
      |ooooooooo-
      |-ooooooooo
      |-----ooToo
      |------ooo-""".stripMargin
*/

    val level =
      """ooo-------
      |oSoooo----
      |ooooooooo-
      |-ooooooooo
      |-----ToToo
      |------ooo-""".stripMargin

    val optsolution = List(Right, Right, Down, Right, Right, Right, Down)
    val sol1 = List(Right, Down, Right)
  }

  val level1 = new Level1 {}                      //> level1  : streams.BloxorzWs.Level1 = streams.BloxorzWs$$anonfun$main$1$$ano
                                                  //| n$1@4a8f5f75
  val theTestBlock = level1.Block(level1.Pos(1, 1), level1.Pos(1, 1))
                                                  //> theTestBlock  : streams.BloxorzWs.level1.Block =  B(1:1:1:1)
  theTestBlock.legalNeighbors                     //> res0: List[(streams.BloxorzWs.level1.Block, streams.BloxorzWs.level1.Move)]
                                                  //|  = List(( B(1:2:1:3),Right), ( B(2:1:3:1),Down))
  //level1.pathsToGoal.take(1).toList
  level1.solution                                 //> FROM for: Stream(( B(1:1:1:1),List()), ?) more: List(List(Right), List(Down
                                                  //| ))
                                                  //| FROM for: Stream(( B(1:2:1:3),List(Right)), ( B(2:1:3:1),List(Down))) more:
                                                  //|  List(List(Right, Right), List(Down, Right), List(Right, Down))
                                                  //| FROM for: Stream(( B(1:4:1:4),List(Right, Right)), ( B(2:2:2:3),List(Down, 
                                                  //| Right)), ( B(2:2:3:2),List(Right, Down))) more: List(List(Down, Right, Righ
                                                  //| t), List(Left, Down, Right), List(Right, Down, Right), List(Down, Down, Rig
                                                  //| ht), List(Right, Right, Down), List(Up, Right, Down))
                                                  //| FROM for: Stream(( B(2:4:3:4),List(Down, Right, Right)), ( B(2:1:2:1),List(
                                                  //| Left, Down, Right)), ( B(2:4:2:4),List(Right, Down, Right)), ( B(3:2:3:3),L
                                                  //| ist(Down, Down, Right)), ( B(2:3:3:3),List(Right, Right, Down)), ( B(1:2:1:
                                                  //| 2),List(Up, Right, Down))) more: List(List(Right, Down, Right, Right), List
                                                  //| (Up, Left, Down, Right), List(Right, Right, Down, Right), List(Left, Down, 
                                                  //| Down, Right), List(Right, Down, Down, Right), List(Up, Right, Right, Down),
                                                  //|  List(Left, Up, Right, Down), List(Right, Up, Right, Down))
                                                  //| FROM for: Stream(( B(2:5:3:5),List(Right, Down, Right, Right)), ( B(0:1:1:1
                                                  //| ),List(Up, Left, Down, Right)), ( B(2:5:2:6),List(Right, Right, Down, Right
                                                  //| )), ( B(3:1:3:1),List(Left, Down, Down, Right)), ( B(3:4:3:4),List(Right, D
                                                  //| own, Down, Right)), ( B(1:3:1:3),List(Up, Right, Right, Down)), ( B(1:0:1:1
                                                  //| ),List(Left, Up, Right, Down)), ( B(1:3:1:4),List(Right, Up, Right, Down)))
                                                  //|  more: List(List(Right, Right, Down, Right, Right), List(Up, Right, Down, R
                                                  //| ight, Right), List(Down, Right, Down, Right, Right), List(Left, Up, Left, D
                                                  //| own, Right), List(Right, Up, Left, Down, Right), List(Right, Right, Right, 
                                                  //| Down, Right), List(Down, Right, Right, Down, Right), List(Up, Left, Down, D
                                                  //| own, Right), List(Right, Right, Down, Down, Right), List(Up, Right, Down, D
                                                  //| own, Right), List(Left, Up, Right, Right, Down), List(Right, Up, Right, Rig
                                                  //| ht, Down), List(Up, Left, Up, Right, Down), List(Down, Left, Up, Right, Dow
                                                  //| n), List(Right, Right, Up, Right, Down), List(Down, Right, Up, Right, Down)
                                                  //| )
                                                  //| FROM for: Stream(( B(2:6:3:6),List(Right, Right, Down, Right, Right)), ( B(
                                                  //| 1:5:1:5),List(Up, Right, Down, Right, Right)), ( B(4:5:4:5),List(Down, Righ
                                                  //| t, Down, Right, Right)), ( B(0:0:1:0),List(Left, Up, Left, Down, Right)), (
                                                  //|  B(0:2:1:2),List(Right, Up, Left, Down, Right)), ( B(2:7:2:7),List(Right, R
                                                  //| ight, Right, Down, Right)), ( B(3:5:3:6),List(Down, Right, Right, Down, Rig
                                                  //| ht)), ( B(1:1:2:1),List(Up, Left, Down, Down, Right)), ( B(3:5:3:6),List(Ri
                                                  //| ght, Right, Down, Down, Right)), ( B(1:4:2:4),List(Up, Right, Down, Down, R
                                                  //| ight)), ( B(1:1:1:2),List(Left, Up, Right, Right, Down)), ( B(1:4:1:5),List
                                                  //| (Right, Up, Right, Right, Down)), ( B(0:0:0:1),List(Up, Left, Up, Right, Do
                                                  //| wn)), ( B(2:0:2:1),List(Down, Left, Up, Right, Down)), ( B(1:5:1:5),List(Ri
                                                  //| ght, Right, Up, Right, Down)), ( B(2:3:2:4),List(Down, Right, Up, Right, Do
                                                  //| wn))) more: List(List(Right, Right, Right, Down, Right, Right), List(Down, 
                                                  //| Right, Right, Down, Right, Right), List(Right, Down, Right, Down, Right, Ri
                                                  //| ght), List(Down, Left, Up, Left, Down, Right), List(Down, Right, Up, Left, 
                                                  //| Down, Right), List(Down, Right, Right, Right, Down, Right), List(Right, Dow
                                                  //| n, Right, Right, Down, Right), List(Down, Down, Right, Right, Down, Right),
                                                  //|  List(Left, Up, Left, Down, Down, Right), List(Right, Up, Left, Down, Down,
                                                  //|  Right), List(Up, Up, Left, Down, Down, Right), List(Right, Right, Right, D
                                                  //| own, Down, Right), List(Down, Right, Right, Down, Down, Right), List(Left, 
                                                  //| Up, Right, Down, Down, Right), List(Right, Up, Right, Down, Down, Right), L
                                                  //| ist(Left, Left, Up, Right, Right, Down), List(Up, Left, Up, Right, Right, D
                                                  //| own), List(Down, Left, Up, Right, Right, Down), List(Down, Right, Up, Right
                                                  //| , Right, Down), List(Right, Up, Left, Up, Right, Down), List(Right, Down, L
                                                  //| eft, Up, Right, Down), List(Left, Down, Right, Up, Right, Down), List(Right
                                                  //| , Down, Right, Up, Right, Down), List(Down, Down, Right, Up, Right, Down))
                                                  //| 
                                                  //| FROM for: Stream(( B(2:7:3:7),List(Right, Right, Right, Down, Right, Right)
                                                  //| ), ( B(4:6:4:6),List(Down, Right, Right, Down, Right, Right)), ( B(4:6:4:7)
                                                  //| ,List(Right, Down, Right, Down, Right, Right)), ( B(2:0:2:0),List(Down, Lef
                                                  //| t, Up, Left, Down, Right)), ( B(2:2:2:2),List(Down, Right, Up, Left, Down, 
                                                  //| Right)), ( B(3:7:4:7),List(Down, Right, Right, Right, Down, Right)), ( B(3:
                                                  //| 7:3:7),List(Right, Down, Right, Right, Down, Right)), ( B(4:5:4:6),List(Dow
                                                  //| n, Down, Right, Right, Down, Right)), ( B(1:0:2:0),List(Left, Up, Left, Dow
                                                  //| n, Down, Right)), ( B(1:2:2:2),List(Right, Up, Left, Down, Down, Right)), (
                                                  //|  B(0:1:0:1),List(Up, Up, Left, Down, Down, Right)), ( B(3:7:3:7),List(Right
                                                  //| , Right, Right, Down, Down, Right)), ( B(4:5:4:6),List(Down, Right, Right, 
                                                  //| Down, Down, Right)), ( B(1:3:2:3),List(Left, Up, Right, Down, Down, Right))
                                                  //| , ( B(1:5:2:5),List(Right, Up, Right, Down, Down, Right)), ( B(1:0:1:0),Lis
                                                  //| t(Left, Left, Up, Right, Right, Down)), ( B(0:1:0:2),List(Up, Left, Up, Rig
                                                  //| ht, Right, Down)), ( B(2:1:2:2),List(Down, Left, Up, Right, Right, Down)), 
                                                  //| ( B(2:4:2:5),List(Down, Right, Up, Right, Right, Down)), ( B(0:2:0:2),List(
                                                  //| Right, Up, Left, Up, Right, Down)), ( B(2:2:2:2),List(Right, Down, Left, Up
                                                  //| , Right, Down)), ( B(2:2:2:2),List(Left, Down, Right, Up, Right, Down)), ( 
                                                  //| B(2:5:2:5),List(Right, Down, Right, Up, Right, Down)), ( B(3:3:3:4),List(Do
                                                  //| wn, Down, Right, Up, Right, Down))) more: List(List(Right, Right, Right, Ri
                                                  //| ght, Down, Right, Right), List(Down, Right, Right, Right, Down, Right, Righ
                                                  //| t), List(Right, Down, Right, Right, Down, Right, Right), List(Right, Right,
                                                  //|  Down, Right, Down, Right, Right), List(Up, Right, Down, Right, Down, Right
                                                  //| , Right), List(Down, Right, Down, Right, Down, Right, Right), List(Left, Do
                                                  //| wn, Right, Right, Right, Down, Right), List(Right, Down, Right, Right, Righ
                                                  //| t, Down, Right), List(Down, Down, Right, Right, Right, Down, Right), List(R
                                                  //| ight, Right, Down, Right, Right, Down, Right), List(Down, Right, Down, Righ
                                                  //| t, Right, Down, Right), List(Right, Down, Down, Right, Right, Down, Right),
                                                  //|  List(Up, Left, Up, Left, Down, Down, Right), List(Down, Right, Up, Left, D
                                                  //| own, Down, Right), List(Right, Right, Right, Right, Down, Down, Right), Lis
                                                  //| t(Down, Right, Right, Right, Down, Down, Right), List(Right, Down, Right, R
                                                  //| ight, Down, Down, Right), List(Down, Left, Up, Right, Down, Down, Right), L
                                                  //| ist(Down, Right, Up, Right, Down, Down, Right), List(Left, Up, Left, Up, Ri
                                                  //| ght, Right, Down), List(Right, Down, Left, Up, Right, Right, Down), List(Do
                                                  //| wn, Down, Left, Up, Right, Right, Down), List(Left, Down, Right, Up, Right,
                                                  //|  Right, Down), List(Right, Down, Right, Up, Right, Right, Down), List(Down,
                                                  //|  Down, Right, Up, Right, Right, Down), List(Right, Right, Down, Right, Up, 
                                                  //| Right, Down), List(Down, Right, Down, Right, Up, Right, Down), List(Left, D
                                                  //| own, Down, Right, Up, Right, Down), List(Right, Down, Down, Right, Up, Righ
                                                  //| t, Down))
                                                  //| FROM for: Stream(( B(2:8:3:8),List(Right, Right, Right, Right, Down, Right,
                                                  //|  Right)), ( B(4:7:4:7),List(Down, Right, Right, Right, Down, Right, Right))
                                                  //| , ( B(4:7:4:8),List(Right, Down, Right, Right, Down, Right, Right)), ( B(4:
                                                  //| 8:4:8),List(Right, Right, Down, Right, Down, Right, Right)), ( B(3:6:3:7),L
                                                  //| ist(Up, Right, Down, Right, Down, Right, Right)), ( B(5:6:5:7),List(Down, R
                                                  //| ight, Down, Right, Down, Right, Right)), ( B(3:6:4:6),List(Left, Down, Righ
                                                  //| t, Right, Right, Down, Right)), ( B(3:8:4:8),List(Right, Down, Right, Right
                                                  //| , Right, Down, Right)), ( B(5:7:5:7),List(Down, Down, Right, Right, Right, 
                                                  //| Down, Right)), ( B(3:8:3:9),List(Right, Right, Down, Right, Right, Down, Ri
                                                  //| ght)), ( B(4:7:5:7),List(Down, Right, Down, Right, Right, Down, Right)), ( 
                                                  //| B(4:7:4:7),List(Right, Down, Down, Right, Right, Down, Right)), ( B(0:0:0:0
                                                  //| ),List(Up, Left, Up, Left, Down, Down, Right)), ( B(3:2:3:2),List(Down, Rig
                                                  //| ht, Up, Left, Down, Down, Right)), ( B(3:8:3:9),List(Right, Right, Right, R
                                                  //| ight, Down, Down, Right)), ( B(4:7:5:7),List(Down, Right, Right, Right, Dow
                                                  //| n, Down, Right)), ( B(4:7:4:7),List(Right, Down, Right, Right, Down, Down, 
                                                  //| Right)), ( B(3:3:3:3),List(Down, Left, Up, Right, Down, Down, Right)), ( B(
                                                  //| 3:5:3:5),List(Down, Right, Up, Right, Down, Down, Right)), ( B(0:0:0:0),Lis
                                                  //| t(Left, Up, Left, Up, Right, Right, Down)), ( B(2:3:2:3),List(Right, Down, 
                                                  //| Left, Up, Right, Right, Down)), ( B(3:1:3:2),List(Down, Down, Left, Up, Rig
                                                  //| ht, Right, Down)), ( B(2:3:2:3),List(Left, Down, Right, Up, Right, Right, D
                                                  //| own)), ( B(2:6:2:6),List(Right, Down, Right, Up, Right, Right, Down)), ( B(
                                                  //| 3:4:3:5),List(Down, Down, Right, Up, Right, Right, Down)), ( B(2:6:2:7),Lis
                                                  //| t(Right, Right, Down, Right, Up, Right, Down)), ( B(3:5:4:5),List(Down, Rig
                                                  //| ht, Down, Right, Up, Right, Down)), ( B(3:2:3:2),List(Left, Down, Down, Rig
                                                  //| ht, Up, Right, Down)), ( B(3:5:3:5),List(Right, Down, Down, Right, Up, Righ
                                                  //| t, Down))) more: List(List(Right, Down, Right, Right, Right, Down, Right, R
                                                  //| ight), List(Right, Right, Down, Right, Right, Down, Right, Right), List(Up,
                                                  //|  Right, Down, Right, Right, Down, Right, Right), List(Down, Right, Down, Ri
                                                  //| ght, Right, Down, Right, Right), List(Right, Up, Right, Down, Right, Down, 
                                                  //| Right, Right), List(Right, Down, Right, Down, Right, Down, Right, Right), L
                                                  //| ist(Down, Left, Down, Right, Right, Right, Down, Right), List(Right, Right,
                                                  //|  Down, Right, Right, Right, Down, Right), List(Up, Right, Down, Right, Righ
                                                  //| t, Right, Down, Right), List(Down, Right, Down, Right, Right, Right, Down, 
                                                  //| Right), List(Down, Right, Right, Down, Right, Right, Down, Right), List(Lef
                                                  //| t, Down, Right, Down, Right, Right, Down, Right), List(Right, Down, Right, 
                                                  //| Down, Right, Right, Down, Right), List(Right, Right, Down, Down, Right, Rig
                                                  //| ht, Down, Right), List(Down, Right, Right, Right, Right, Down, Down, Right)
                                                  //| , List(Left, Down, Right, Right, Right, Down, Down, Right), List(Right, Dow
                                                  //| n, Right, Right, Right, Down, Down, Right), List(Right, Right, Down, Right,
                                                  //|  Right, Down, Down, Right), List(Right, Right, Down, Right, Up, Right, Righ
                                                  //| t, Down), List(Right, Down, Down, Right, Up, Right, Right, Down), List(Righ
                                                  //| t, Right, Right, Down, Right, Up, Right, Down))
                                                  //| FROM for: Stream(( B(4:8:4:9),List(Right, Down, Right, Right, Right, Down, 
                                                  //| Right, Right)), ( B(4:9:4:9),List(Right, Right, Down, Right, Right, Down, R
                                                  //| ight, Right)), ( B(3:7:3:8),List(Up, Right, Down, Right, Right, Down, Right
                                                  //| , Right)), ( B(5:7:5:8),List(Down, Right, Down, Right, Right, Down, Right, 
                                                  //| Right)), ( B(3:8:3:8),List(Right, Up, Right, Down, Right, Down, Right, Righ
                                                  //| t)), ( B(5:8:5:8),List(Right, Down, Right, Down, Right, Down, Right, Right)
                                                  //| ), ( B(5:6:5:6),List(Down, Left, Down, Right, Right, Right, Down, Right)), 
                                                  //| ( B(3:9:4:9),List(Right, Right, Down, Right, Right, Right, Down, Right)), (
                                                  //|  B(2:8:2:8),List(Up, Right, Down, Right, Right, Right, Down, Right)), ( B(5
                                                  //| :8:5:8),List(Down, Right, Down, Right, Right, Right, Down, Right)), ( B(4:8
                                                  //| :4:9),List(Down, Right, Right, Down, Right, Right, Down, Right)), ( B(4:6:5
                                                  //| :6),List(Left, Down, Right, Down, Right, Right, Down, Right)), ( B(4:8:5:8)
                                                  //| ,List(Right, Down, Right, Down, Right, Right, Down, Right)), ( B(4:8:4:9),L
                                                  //| ist(Right, Right, Down, Down, Right, Right, Down, Right)), ( B(4:8:4:9),Lis
                                                  //| t(Down, Right, Right, Right, Right, Down, Down, Right)), ( B(4:6:5:6),List(
                                                  //| Left, Down, Right, Right, Right, Down, Down, Right)), ( B(4:8:5:8),List(Rig
                                                  //| ht, Down, Right, Right, Right, Down, Down, Right)), ( B(4:8:4:9),List(Right
                                                  //| , Right, Down, Right, Right, Down, Down, Right)), ( B(2:7:2:8),List(Right, 
                                                  //| Right, Down, Right, Up, Right, Right, Down)), ( B(3:6:3:6),List(Right, Down
                                                  //| , Down, Right, Up, Right, Right, Down)), ( B(2:8:2:8),List(Right, Right, Ri
                                                  //| ght, Down, Right, Up, Right, Down))) more: List(List(Right, Up, Right, Down
                                                  //| , Right, Right, Down, Right, Right))
                                                  //| FROM for: Stream(( B(3:9:3:9),List(Right, Up, Right, Down, Right, Right, Do
                                                  //| wn, Right, Right))) more: List()
                                                  //| res1: List[streams.BloxorzWs.level1.Move] = List(Right, Right, Down, Right,
                                                  //|  Down)

  /*
  val more = for {
          path <- paths
          next <- moves map path.extend
          if !(explored contains next.endState)
        } yield next
        paths #:: from(more, explored ++ (more map (_.endState)))
*/


  level1.from((theTestBlock, Nil) #:: Stream.empty, Set(theTestBlock)).take(12).toList
                                                  //> FROM for: Stream(( B(1:1:1:1),List()), ?) more: List(List(Right), List(Down
                                                  //| ))
                                                  //| FROM for: Stream(( B(1:2:1:3),List(Right)), ( B(2:1:3:1),List(Down))) more:
                                                  //|  List(List(Right, Right), List(Down, Right), List(Right, Down))
                                                  //| FROM for: Stream(( B(1:4:1:4),List(Right, Right)), ( B(2:2:2:3),List(Down, 
                                                  //| Right)), ( B(2:2:3:2),List(Right, Down))) more: List(List(Down, Right, Righ
                                                  //| t), List(Left, Down, Right), List(Right, Down, Right), List(Down, Down, Rig
                                                  //| ht), List(Right, Right, Down), List(Up, Right, Down))
                                                  //| FROM for: Stream(( B(2:4:3:4),List(Down, Right, Right)), ( B(2:1:2:1),List(
                                                  //| Left, Down, Right)), ( B(2:4:2:4),List(Right, Down, Right)), ( B(3:2:3:3),L
                                                  //| ist(Down, Down, Right)), ( B(2:3:3:3),List(Right, Right, Down)), ( B(1:2:1:
                                                  //| 2),List(Up, Right, Down))) more: List(List(Right, Down, Right, Right), List
                                                  //| (Up, Left, Down, Right), List(Right, Right, Down, Right), List(Left, Down, 
                                                  //| Down, Right), List(Right, Down, Down, Right), List(Up, Right, Right, Down),
                                                  //|  List(Left, Up, Right, Down), List(Right, Up, Right, Down))
                                                  //| res2: List[(streams.BloxorzWs.level1.Block, List[streams.BloxorzWs.level1.M
                                                  //| ove])] = List(( B(1:1:1:1),List()), ( B(1:2:1:3),List(Right)), ( B(2:1:3:1)
                                                  //| ,List(Down)), ( B(1:4:1:4),List(Right, Right)), ( B(2:2:2:3),List(Down, Rig
                                                  //| ht)), ( B(2:2:3:2),List(Right, Down)), ( B(2:4:3:4),List(Down, Right, Right
                                                  //| )), ( B(2:1:2:1),List(Left, Down, Right)), ( B(2:4:2:4),List(Right, Down, R
                                                  //| ight)), ( B(3:2:3:3),List(Down, Down, Right)), ( B(2:3:3:3),List(Right, Rig
                                                  //| ht, Down)), ( B(1:2:1:2),List(Up, Right, Down)))

  level1.displaySolution(level1.sol1)             //> Visualizing solution found for game streams.BloxorzWs$$anonfun$main$1$$anon
                                                  //| $1
                                                  //| Status with block at  B(1:1:1:1)
                                                  //| ooo-------
                                                  //| o#oooo----
                                                  //| ooooooooo-
                                                  //| -ooooooooo
                                                  //| -----ooooo
                                                  //| ------ooo-
                                                  //| Performing move Right
                                                  //| Status with block at  B(1:2:1:3)
                                                  //| ooo-------
                                                  //| oo##oo----
                                                  //| ooooooooo-
                                                  //| -ooooooooo
                                                  //| -----ooooo
                                                  //| ------ooo-
                                                  //| Performing move Down
                                                  //| Status with block at  B(2:2:2:3)
                                                  //| ooo-------
                                                  //| oooooo----
                                                  //| oo##ooooo-
                                                  //| -ooooooooo
                                                  //| -----ooooo
                                                  //| ------ooo-
                                                  //| Performing move Right
                                                  //| Status with block at  B(2:4:2:4)
                                                  //| ooo-------
                                                  //| oooooo----
                                                  //| oooo#oooo-
                                                  //| -ooooooooo
                                                  //| -----ooooo
                                                  //| ------ooo-
                                                  //| Game Over

  level1.displaySolution                          //> Visualizing solution found for game streams.BloxorzWs$$anonfun$main$1$$anon
                                                  //| $1
                                                  //| Status with block at  B(1:1:1:1)
                                                  //| ooo-------
                                                  //| o#oooo----
                                                  //| ooooooooo-
                                                  //| -ooooooooo
                                                  //| -----ooooo
                                                  //| ------ooo-
                                                  //| Performing move Right
                                                  //| Status with block at  B(1:2:1:3)
                                                  //| ooo-------
                                                  //| oo##oo----
                                                  //| ooooooooo-
                                                  //| -ooooooooo
                                                  //| -----ooooo
                                                  //| ------ooo-
                                                  //| Performing move Right
                                                  //| Status with block at  B(1:4:1:4)
                                                  //| ooo-------
                                                  //| oooo#o----
                                                  //| ooooooooo-
                                                  //| -ooooooooo
                                                  //| -----ooooo
                                                  //| ------ooo-
                                                  //| Performing move Down
                                                  //| Status with block at  B(2:4:3:4)
                                                  //| ooo-------
                                                  //| oooooo----
                                                  //| oooo#oooo-
                                                  //| -ooo#ooooo
                                                  //| -----ooooo
                                                  //| ------ooo-
                                                  //| Performing move Right
                                                  //| Status with block at  B(2:5:3:5)
                                                  //| ooo-------
                                                  //| oooooo----
                                                  //| ooooo#ooo-
                                                  //| -oooo#oooo
                                                  //| -----ooooo
                                                  //| ------ooo-
                                                  //| Performing move Down
                                                  //| Status with block at  B(4:5:4:5)
                                                  //| ooo-------
                                                  //| oooooo----
                                                  //| ooooooooo-
                                                  //| -ooooooooo
                                                  //| -----#oooo
                                                  //| ------ooo-
                                                  //| Game Over
  //l.pathsToGoal.take(2).toList

  /*
  l.pathsToGoal
  l.done(l.Block(l.goal, l.goal))
  l.pathsFromStart.find(pair => l.done(pair._1) )
  println(l.solution)
  */
}