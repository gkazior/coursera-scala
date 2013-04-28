package patmat

import patmat.Huffman._

object HuffmanPart2Ws {

  times(string2Chars("helpma"))                   //> res0: List[(Char, Int)] = List((h,1), (e,1), (l,1), (p,1), (m,1), (a,1))
  times(string2Chars("aheala"))                   //> res1: List[(Char, Int)] = List((a,3), (h,1), (e,1), (l,1))
  val eha = times(string2Chars("eehahealaa"))     //> eha  : List[(Char, Int)] = List((e,3), (h,2), (a,4), (l,1))

  putInOrder(('a', 10), eha, firstLess)           //> res2: List[(Char, Int)] = List((e,3), (h,2), (a,4), (l,1), (a,10))

  makeOrderedLeafList(eha)                        //> res3: List[patmat.Huffman.Leaf] = List(Leaf(l,1), Leaf(h,2), Leaf(e,3), Leaf
                                                  //| (a,4))

  val l1 = string2Chars("aaabbcc")                //> l1  : List[Char] = List(a, a, a, b, b, c, c)
  val l2 = List(1, 2, 3, 4, 1)                    //> l2  : List[Int] = List(1, 2, 3, 4, 1)

  val huf1 = Leaf('a', 10)                        //> huf1  : patmat.Huffman.Leaf = Leaf(a,10)
  val huf2 = new Fork(Leaf('a', 10), Leaf('b', 5))//> huf2  : patmat.Huffman.Fork = Fork(ab,15,Leaf(a,10),Leaf(b,5))
  val huf3 = new Fork(new Fork(Leaf('a', 10), Leaf('c', 1)), Leaf('b', 5))
                                                  //> huf3  : patmat.Huffman.Fork = Fork(acb,16,Fork(ac,11,Leaf(a,10),Leaf(c,1)),L
                                                  //| eaf(b,5))

  val t1 = Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5)
                                                  //> t1  : patmat.Huffman.Fork = Fork(ab,5,Leaf(a,2),Leaf(b,3))
  val t2 = Fork(Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5), Leaf('d', 4), List('a', 'b', 'd'), 9)
                                                  //> t2  : patmat.Huffman.Fork = Fork(abd,9,Fork(ab,5,Leaf(a,2),Leaf(b,3)),Leaf(d
                                                  //| ,4))

  decode(huf1, Nil)                               //> res4: List[Char] = List()
  decode(huf1, List(1))                           //> res5: List[Char] = List(a)
  decode(huf1, List(0))                           //> res6: List[Char] = List(a)
  decode(huf1, List(1, 0, 1, 0))                  //> res7: List[Char] = List(a, a, a, a)

  decode(huf2, List(1))                           //> res8: List[Char] = List(b)
  decode(huf2, List(0))                           //> res9: List[Char] = List(a)
  decode(huf2, List(1, 0, 1, 0))                  //> res10: List[Char] = List(b, a, b, a)

  //decode(huf3, List(0)) //Bit sequence not complete at position: 1

  decode(huf3, List(1, 0, 1, 0, 0, 1))            //> res11: List[Char] = List(b, c, a, b)
  decode(frenchCode, secret)                      //> res12: List[Char] = List(h, u, f, f, m, a, n, e, s, t, c, o, o, l)

}