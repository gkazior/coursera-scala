package patmat

import patmat.Huffman._

object HuffmanPart4Ws {
  val huf1 = Leaf('a', 10)                        //> huf1  : patmat.Huffman.Leaf = Leaf(a,10)
  val huf2 = new Fork(Leaf('a', 10), Leaf('b', 5))//> huf2  : patmat.Huffman.Fork = Fork(ab,15,Leaf(a,10),Leaf(b,5))
  val huf3 = new Fork(new Fork(Leaf('a', 10), Leaf('c', 1)), Leaf('b', 5))
                                                  //> huf3  : patmat.Huffman.Fork = Fork(acb,16,Fork(ac,11,Leaf(a,10),Leaf(c,1)),L
                                                  //| eaf(b,5))

  encode(huf1)(string2Chars("aaa"))               //> res0: List[patmat.Huffman.Bit] = List(0, 0, 0)
  encode(huf2)(string2Chars("aab"))               //> res1: List[patmat.Huffman.Bit] = List(0, 0, 1)


  val TheChar = 'a'                               //> TheChar  : Char = a
  val list: List[(Char, Int)] = List(('a', 1), ('b', 1))
                                                  //> list  : List[(Char, Int)] = List((a,1), (b,1))
  
  list match {
    case (TheChar, _) :: tail => println("Found")
  }                                               //> Found

  quickEncode(huf1)(string2Chars("aaa"))          //> findCode: a table: List((a,List(0)))
                                                  //| Result code List(0)
                                                  //| findCode: a table: List((a,List(0)))
                                                  //| Result code List(0)
                                                  //| findCode: a table: List((a,List(0)))
                                                  //| Result code List(0)
                                                  //| res2: List[patmat.Huffman.Bit] = List(0, 0, 0)
  quickEncode(huf2)(string2Chars("aab"))          //> findCode: a table: List((a,List(0)), (b,List(1)))
                                                  //| Result code List(0)
                                                  //| findCode: a table: List((a,List(0)), (b,List(1)))
                                                  //| Result code List(0)
                                                  //| findCode: b table: List((a,List(0)), (b,List(1)))
                                                  //| findCode: b table: List((b,List(1)))
                                                  //| Result code List(1)
                                                  //| Result code List(1)
                                                  //| res3: List[patmat.Huffman.Bit] = List(0, 0, 1)

  decode(frenchCode, encode(frenchCode)(string2Chars("huffmanestcool")))
                                                  //> res4: List[Char] = List(h, u, f, f, m, a, n, e, s, t, c, o, o, l)

  convert(huf1)                                   //> res5: patmat.Huffman.CodeTable = List((a,List(0)))
  convert(huf2)                                   //> res6: patmat.Huffman.CodeTable = List((a,List(0)), (b,List(1)))
  convert(huf3)                                   //> res7: patmat.Huffman.CodeTable = List((a,List(0, 0)), (c,List(0, 1)), (b,Lis
                                                  //| t(1)))

  mergeCodeTables(convert(huf1), convert(huf2))   //> res8: patmat.Huffman.CodeTable = List((a,List(0)), (b,List(1)))
  //mergeCodeTables(convert(huf1), convert(frenchCode)) //  The char a coded in a different way in the merged lists. Codes: List(1, 1, 1, 1) List(0)
  convert(frenchCode)                             //> res9: patmat.Huffman.CodeTable = List((s,List(0, 0, 0)), (d,List(0, 0, 1, 0)
                                                  //| ), (x,List(0, 0, 1, 1, 0, 0, 0)), (j,List(0, 0, 1, 1, 0, 0, 1)), (f,List(0, 
                                                  //| 0, 1, 1, 0, 1)), (z,List(0, 0, 1, 1, 1, 0, 0, 0, 0)), (k,List(0, 0, 1, 1, 1,
                                                  //|  0, 0, 0, 1, 0)), (w,List(0, 0, 1, 1, 1, 0, 0, 0, 1, 1)), (y,List(0, 0, 1, 1
                                                  //| , 1, 0, 0, 1)), (h,List(0, 0, 1, 1, 1, 0, 1)), (q,List(0, 0, 1, 1, 1, 1)), (
                                                  //| o,List(0, 1, 0, 0)), (l,List(0, 1, 0, 1)), (m,List(0, 1, 1, 0, 0)), (p,List(
                                                  //| 0, 1, 1, 0, 1)), (u,List(0, 1, 1, 1)), (r,List(1, 0, 0, 0)), (c,List(1, 0, 0
                                                  //| , 1, 0)), (v,List(1, 0, 0, 1, 1, 0)), (g,List(1, 0, 0, 1, 1, 1, 0)), (b,List
                                                  //| (1, 0, 0, 1, 1, 1, 1)), (n,List(1, 0, 1, 0)), (t,List(1, 0, 1, 1)), (e,List(
                                                  //| 1, 1, 0)), (i,List(1, 1, 1, 0)), (a,List(1, 1, 1, 1)))
}