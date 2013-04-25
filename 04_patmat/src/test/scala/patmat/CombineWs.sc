package patmat

import patmat.Huffman._

object CombineWs {
  val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
                                                  //> leaflist  : List[patmat.Huffman.Leaf] = List(Leaf(e,1), Leaf(t,2), Leaf(x,4)
                                                  //| )
  val l2 = List(Leaf('e', 1), Leaf('t', 2))       //> l2  : List[patmat.Huffman.Leaf] = List(Leaf(e,1), Leaf(t,2))
  val l1 = List(Leaf('e', 1))                     //> l1  : List[patmat.Huffman.Leaf] = List(Leaf(e,1))

  new Fork(Leaf('e', 1),Leaf('t', 2))             //> res0: patmat.Huffman.Fork = Fork(et,3,Leaf(e,1),Leaf(t,2))
  //assert(combine(leaflist) === List(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4)))

  def combine(trees: List[CodeTree]): List[CodeTree] = {
    trees match {
      case Nil => Nil
      case head :: Nil => trees
      case first :: second :: rest => {
        println("first: " + first + " second:" + second + " rest: " + rest)
        putInOrder(new Fork(first, second), rest, firstLessByWeight)
      }
    }
  }                                               //> combine: (trees: List[patmat.Huffman.CodeTree])List[patmat.Huffman.CodeTree]
                                                  //| 

  combine(Nil)                                    //> res1: List[patmat.Huffman.CodeTree] = List()
  combine(l1)                                     //> res2: List[patmat.Huffman.CodeTree] = List(Leaf(e,1))
  combine(l2)                                     //> first: Leaf(e,1) second:Leaf(t,2) rest: List()
                                                  //| res3: List[patmat.Huffman.CodeTree] = List(Fork(et,3,Leaf(e,1),Leaf(t,2)))
  combine(leaflist)                               //> first: Leaf(e,1) second:Leaf(t,2) rest: List(Leaf(x,4))
                                                  //| res4: List[patmat.Huffman.CodeTree] = List(Fork(et,3,Leaf(e,1),Leaf(t,2)), L
                                                  //| eaf(x,4))

 // def createCodeTree(chars: List[Char]): CodeTree = until(singleton, combine) ( makeOrderedLeafList(times(chars)))  head

  val chars = string2Chars("ananas")              //> chars  : List[Char] = List(a, n, a, n, a, s)
  val timesChars = times(chars)                   //> timesChars  : List[(Char, Int)] = List((a,3), (n,2), (s,1))
  val orderedChars = makeOrderedLeafList(timesChars)
                                                  //> orderedChars  : List[patmat.Huffman.Leaf] = List(Leaf(s,1), Leaf(n,2), Leaf(
                                                  //| a,3))
  val comb = combineX(orderedChars, true)         //> comb  : List[patmat.Huffman.CodeTree] = List(Fork(asn,6,Leaf(a,3),Fork(sn,3
                                                  //| ,Leaf(s,1),Leaf(n,2))))
  //singleton(comb)
  val comb2 = until(singleton, combine) (orderedChars)
                                                  //> first: Leaf(s,1) second:Leaf(n,2) rest: List(Leaf(a,3))
                                                  //| first: Leaf(a,3) second:Fork(sn,3,Leaf(s,1),Leaf(n,2)) rest: List()
                                                  //| comb2  : List[patmat.Huffman.CodeTree] = List(Fork(asn,6,Leaf(a,3),Fork(sn,
                                                  //| 3,Leaf(s,1),Leaf(n,2))))

  createCodeTree(string2Chars("ananas"))          //> res5: patmat.Huffman.CodeTree = Fork(asn,6,Leaf(a,3),Fork(sn,3,Leaf(s,1),Le
                                                  //| af(n,2)))
}