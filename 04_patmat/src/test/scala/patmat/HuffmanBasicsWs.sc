package patmat

import patmat.Huffman._

trait TestTrees {
  val t1 = Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5)
  val t2 = Fork(Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5), Leaf('d', 4), List('a', 'b', 'd'), 9)
}

object HuffmanBasicsWs extends TestTrees {
  List(1) ::: List(2, 3)                          //> res0: List[Int] = List(1, 2, 3)
  1 :: List(2, 3)                                 //> res1: List[Int] = List(1, 2, 3)

  weight(t1)                                      //> res2: Int = 5
  weight(t1.left)                                 //> res3: Int = 2
  weight(t1.right)                                //> res4: Int = 3

  string2Chars("helpma")                          //> res5: List[Char] = List(h, e, l, p, m, a)

}