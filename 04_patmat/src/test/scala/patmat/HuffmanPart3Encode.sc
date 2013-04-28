package patmat

import patmat.Huffman._
import patmat.MapUtils._
import scala.annotation.tailrec
object HuffmanPart3Encode {
  val TRIVIAL = true // trivial Huffman tree has only a root leaf
                                                  //> TRIVIAL  : Boolean = true

  def encodedChar(rootTree: CodeTree, tree: CodeTree, currentEncoded: List[Bit])(char: Char): List[Bit] = {
    (rootTree == tree, tree, chars(tree).contains(char)) match {
      case (_, _, false)               => throw new RuntimeException("Change the exception class. Leaf does not contain a char! Invalid input? char: " + char);
      case (true, (leaf: Leaf), true)  => currentEncoded ::: List(0)
      case (false, (leaf: Leaf), true) => currentEncoded
      case (_, (fork: Fork), true) =>
        if (chars(fork.left).contains(char)) encodedChar(rootTree, fork.left, currentEncoded ::: List(0))(char)
        else encodedChar(rootTree, fork.right, currentEncoded ::: List(1))(char)
    }
  }                                               //> encodedChar: (rootTree: patmat.Huffman.CodeTree, tree: patmat.Huffman.CodeTr
                                                  //| ee, currentEncoded: List[patmat.Huffman.Bit])(char: Char)List[patmat.Huffman
                                                  //| .Bit]

  def encode(tree: CodeTree)(text: List[Char]): List[Bit] = {
    def collectEncodedChar(char: Char, acc: List[Bit]): List[Bit] = {
      acc ::: encodedChar(tree, tree, Nil)(char)
    }
    foreach[Char, List[Bit]](text, Nil)(collectEncodedChar)
  }                                               //> encode: (tree: patmat.Huffman.CodeTree)(text: List[Char])List[patmat.Huffma
                                                  //| n.Bit]

  //encodeHelper(tree, tree, Nil, Nil)(text)

  val huf1 = Leaf('a', 10)                        //> huf1  : patmat.Huffman.Leaf = Leaf(a,10)
  val huf2 = new Fork(Leaf('a', 10), Leaf('b', 5))//> huf2  : patmat.Huffman.Fork = Fork(ab,15,Leaf(a,10),Leaf(b,5))
  val huf3 = new Fork(new Fork(Leaf('a', 10), Leaf('c', 1)), Leaf('b', 5))
                                                  //> huf3  : patmat.Huffman.Fork = Fork(acb,16,Fork(ac,11,Leaf(a,10),Leaf(c,1)),
                                                  //| Leaf(b,5))

  encode(huf1)(string2Chars("aaa"))               //> res0: List[patmat.Huffman.Bit] = List(0, 0, 0)
  encode(huf2)(string2Chars("aab"))               //> res1: List[patmat.Huffman.Bit] = List(0, 0, 1)
  decode(frenchCode, encode(frenchCode)(string2Chars("huffmanestcool")))
                                                  //> res2: List[Char] = List(h, u, f, f, m, a, n, e, s, t, c, o, o, l)

}