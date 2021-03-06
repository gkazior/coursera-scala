package patmat

import common._
import patmat.MapUtils._
import scala.annotation.tailrec

/**
 * Assignment 4: Huffman coding
 *
 */
object Huffman {

  /**
   * A huffman code is represented by a binary tree.
   *
   * Every `Leaf` node of the tree represents one character of the alphabet that the tree can encode.
   * The weight of a `Leaf` is the frequency of appearance of the character.
   *
   * The branches of the huffman tree, the `Fork` nodes, represent a set containing all the characters
   * present in the leaves below it. The weight of a `Fork` node is the sum of the weights of these
   * leaves.
   */
  abstract class CodeTree
  case class Fork(left: CodeTree, right: CodeTree, chars: List[Char], weight: Int) extends CodeTree {
    def this(left: CodeTree, right: CodeTree) = this(left, right, chars(left) ::: chars(right), weight(left) + weight(right))
    override def toString() = {
      "Fork(" + chars.mkString + "," + weight + "," + left + "," + right + ")"
    }
  }
  case class Leaf(char: Char, weight: Int) extends CodeTree

  // Part 1: Basics

  def weight(tree: CodeTree): Int = tree match {
    case fork: Fork => weight(fork.left) + weight(fork.right)
    case leaf: Leaf => leaf.weight
  }

  def chars(tree: CodeTree): List[Char] = tree match {
    case fork: Fork => fork.chars
    case leaf: Leaf => List(leaf.char)
  }

  def makeCodeTree(left: CodeTree, right: CodeTree) =
    Fork(left, right, chars(left) ::: chars(right), weight(left) + weight(right))

  // Part 2: Generating Huffman trees

  /**
   * In this assignment, we are working with lists of characters. This function allows
   * you to easily create a character list from a given string.
   */
  def string2Chars(str: String): List[Char] = str.toList

  /**
   * This function computes for each unique character in the list `chars` the number of
   * times it occurs. For example, the invocation
   *
   *   times(List('a', 'b', 'a'))
   *
   * should return the following (the order of the resulting list is not important):
   *
   *   List(('a', 2), ('b', 1))
   *
   * The type `List[(Char, Int)]` denotes a list of pairs, where each pair consists of a
   * character and an integer. Pairs can be constructed easily using parentheses:
   *
   *   val pair: (Char, Int) = ('c', 1)
   *
   * In order to access the two elements of a pair, you can use the accessors `_1` and `_2`:
   *
   *   val theChar = pair._1
   *   val theInt  = pair._2
   *
   * Another way to deconstruct a pair is using pattern matching:
   *
   *   pair match {
   *     case (theChar, theInt) =>
   *       println("character is: "+ theChar)
   *       println("integer is  : "+ theInt)
   *   }
   */
  def times(chars: List[Char]): List[(Char, Int)] = {
    foreach[Char, List[(Char, Int)]](
      chars // traversing chars
      , List() // accumulator is empty list
      ) {
        (x, acc) => addPair((x, 1), acc) // collecting function adds a pair
      }
  }

  /**
   * Returns a list of `Leaf` nodes for a given frequency table `freqs`.
   *
   * The returned list should be ordered by ascending weights (i.e. the
   * head of the list should have the smallest weight), where the weight
   * of a leaf is the frequency of the character.
   */
  def makeOrderedLeafList(freqs: List[(Char, Int)]): List[Leaf] = {
    val orderedList = foreach[(Char, Int), List[(Char, Int)]](freqs, Nil) {
      (pair, acc) => putInOrder(pair, acc, firstLess)
    }

    orderedList map (x => Leaf(x._1, x._2))
  }

  def firstLess(pairA: (Char, Int), pairB: (Char, Int)): Boolean = pairA._2 < pairB._2
  def firstLessByWeight(a: CodeTree, b: CodeTree) = weight(a) < weight(b)
  def firstGreaterByWeight(a: CodeTree, b: CodeTree) = weight(a) > weight(b)

  /**
   * Puts in the list
   */
  def putInOrder[Type](item: Type, list: List[Type], orderFn: (Type, Type) => Boolean): List[Type] = {
    list match {
      case Nil => List(item)
      case head :: tail =>
        if (orderFn(item, head)) item :: list
        else head :: putInOrder(item, tail, orderFn)
    }
  }

  /**
   * Checks whether the list `trees` contains only one single code tree.
   */
  def singleton(trees: List[CodeTree]): Boolean = {
    trees match {
      // interesting thing - I do not have to guard against empty list:  case Nil => false
      case head :: Nil => true
      case _ => false
    }
  }

  /**
   * The parameter `trees` of this function is a list of code trees ordered
   * by ascending weights.
   *
   * This function takes the first two elements of the list `trees` and combines
   * them into a single `Fork` node. This node is then added back into the
   * remaining elements of `trees` at a position such that the ordering by weights
   * is preserved.
   *
   * If `trees` is a list of less than two elements, that list should be returned
   * unchanged.
   */
  def combine(trees: List[CodeTree]): List[CodeTree] = combineX(trees, false)

  /**
   * Same as combine but recursive
   */
  @tailrec
  def combineX(trees: List[CodeTree], recursive: Boolean = true): List[CodeTree] = {
    trees match {
      case Nil => Nil
      case head :: Nil => trees
      case first :: second :: rest =>
        if (recursive) combineX(putInOrder(new Fork(first, second), rest, firstLessByWeight))
        else putInOrder(new Fork(first, second), rest, firstLessByWeight)
    }
  }

  /**
   * This function will be called in the following way:
   *
   *   until(singleton, combine)(trees)
   *
   * where `trees` is of type `List[CodeTree]`, `singleton` and `combine` refer to
   * the two functions defined above.
   *
   * In such an invocation, `until` should call the two functions until the list of
   * code trees contains only one single tree, and then return that singleton list.
   *
   * Hint: before writing the implementation,
   *  - start by defining the parameter types such that the above example invocation
   *    is valid. The parameter types of `until` should match the argument types of
   *    the example invocation. Also define the return type of the `until` function.
   *  - try to find sensible parameter names for `xxx`, `yyy` and `zzz`.
   */
  @tailrec
  def until[Type](guardPred: List[Type] => Boolean, combineFn: List[Type] => List[Type])(trees: List[Type]): List[Type] = {
    if (guardPred(trees)) trees
    else until(guardPred, combineFn)(combineFn(trees))
  }

  /**
   * This function creates a code tree which is optimal to encode the text `chars`.
   *
   * The parameter `chars` is an arbitrary text. This function extracts the character
   * frequencies from that text and creates a code tree based on them.
   */
  def createCodeTree(chars: List[Char]): CodeTree = until(singleton, combine)(makeOrderedLeafList(times(chars))) head

  // Part 3: Decoding

  type Bit = Int
  val TRIVIAL_TREE_CODE: Bit = 0
  val TRIVIAL_TREE = true // Trivial Huffman tree is when the tree is leaf
  val NORMAL_TREE = !TRIVIAL_TREE  // Normal tree has Fork in root
  /**
   * This function decodes the bit sequence `bits` using the code tree `tree` and returns
   * the resulting list of characters.
   */
  def decode(tree: CodeTree, bits: List[Bit]): List[Char] = {
    def decodeHelper(rootTree: CodeTree, tree: CodeTree, bits: List[Bit], bitsPosition: Int, acc: List[Char]): List[Char] = {
      //println("decodeHelper Tree: " + tree + " bits:" + bits + " acc:" + acc)
      (bits, tree, rootTree == tree) match {
        case (Nil          , fork: Fork, _) => throw new java.util.InputMismatchException("Bit sequence not complete at position: " + bitsPosition)
        case (Nil          , leaf: Leaf, true ) => acc 
        case (Nil          , leaf: Leaf, false) => leaf.char :: acc
        case (bits         , leaf: Leaf, false) => decodeHelper(rootTree, rootTree  , bits     , bitsPosition    , leaf.char :: acc)
        case (bits         , leaf: Leaf, true ) => decodeHelper(rootTree, rootTree  , bits.tail, bitsPosition + 1, leaf.char :: acc)
        case (0 :: bitTail , fork: Fork, _    ) => decodeHelper(rootTree, fork.left , bitTail  , bitsPosition + 1, acc)
        case (1 :: bitTail , fork: Fork, _    ) => decodeHelper(rootTree, fork.right, bitTail  , bitsPosition + 1, acc)
        case (bit :: _, _, _) => throw new java.util.InputMismatchException("Invalid bit (must be one of 0,1) \"" + bit + "\" at position: " + bitsPosition)
        case (_,_,_) => assert(false, "Invalid case"); Nil // If assertion raises then I was wrong  
      }        
    }
    decodeHelper(tree, tree, bits, 0, Nil) reverse // First position is 0 as in good languages like C ;-)
  }
  /**
   * A Huffman coding tree for the French language.
   * Generated from the data given at
   *   http://fr.wikipedia.org/wiki/Fr%C3%A9quence_d%27apparition_des_lettres_en_fran%C3%A7ais
   */
  val frenchCode: CodeTree = Fork(Fork(Fork(Leaf('s', 121895), Fork(Leaf('d', 56269), Fork(Fork(Fork(Leaf('x', 5928), Leaf('j', 8351), List('x', 'j'), 14279), Leaf('f', 16351), List('x', 'j', 'f'), 30630), Fork(Fork(Fork(Fork(Leaf('z', 2093), Fork(Leaf('k', 745), Leaf('w', 1747), List('k', 'w'), 2492), List('z', 'k', 'w'), 4585), Leaf('y', 4725), List('z', 'k', 'w', 'y'), 9310), Leaf('h', 11298), List('z', 'k', 'w', 'y', 'h'), 20608), Leaf('q', 20889), List('z', 'k', 'w', 'y', 'h', 'q'), 41497), List('x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q'), 72127), List('d', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q'), 128396), List('s', 'd', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q'), 250291), Fork(Fork(Leaf('o', 82762), Leaf('l', 83668), List('o', 'l'), 166430), Fork(Fork(Leaf('m', 45521), Leaf('p', 46335), List('m', 'p'), 91856), Leaf('u', 96785), List('m', 'p', 'u'), 188641), List('o', 'l', 'm', 'p', 'u'), 355071), List('s', 'd', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q', 'o', 'l', 'm', 'p', 'u'), 605362), Fork(Fork(Fork(Leaf('r', 100500), Fork(Leaf('c', 50003), Fork(Leaf('v', 24975), Fork(Leaf('g', 13288), Leaf('b', 13822), List('g', 'b'), 27110), List('v', 'g', 'b'), 52085), List('c', 'v', 'g', 'b'), 102088), List('r', 'c', 'v', 'g', 'b'), 202588), Fork(Leaf('n', 108812), Leaf('t', 111103), List('n', 't'), 219915), List('r', 'c', 'v', 'g', 'b', 'n', 't'), 422503), Fork(Leaf('e', 225947), Fork(Leaf('i', 115465), Leaf('a', 117110), List('i', 'a'), 232575), List('e', 'i', 'a'), 458522), List('r', 'c', 'v', 'g', 'b', 'n', 't', 'e', 'i', 'a'), 881025), List('s', 'd', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q', 'o', 'l', 'm', 'p', 'u', 'r', 'c', 'v', 'g', 'b', 'n', 't', 'e', 'i', 'a'), 1486387)

  /**
   * What does the secret message say? Can you decode it?
   * For the decoding use the `frenchCode' Huffman tree defined above.
   */
  val secret: List[Bit] = List(0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1)

  /**
   * Write a function that returns the decoded secret
   */
  def decodedSecret: List[Char] = decode(frenchCode, secret)

  // Part 4a: Encoding using Huffman tree

  /**
   * This function encodes `text` using the code tree `tree`
   * into a sequence of bits.
   */
  def encode(tree: CodeTree)(text: List[Char]): List[Bit] = {
    def collectEncodedChar(char: Char, acc: List[Bit]): List[Bit] = {
      acc ::: encodedChar(tree, tree, Nil)(char)
    }
    foreach[Char, List[Bit]](text, Nil)(collectEncodedChar)
  } 
  
  
  private def encodedChar(rootTree: CodeTree, tree: CodeTree, currentEncoded: List[Bit])(char: Char): List[Bit] = {
    (rootTree == tree, tree, chars(tree).contains(char)) match {
      case (_          , _           , false) => throw new java.util.InputMismatchException("Invalid input char: " + char)
      case (true       , leaf: Leaf  , true ) => currentEncoded ::: List(TRIVIAL_TREE_CODE)
      case (false      , leaf: Leaf  , true ) => currentEncoded
      case (_          , fork: Fork  , true ) =>
        if (chars(fork.left).contains(char)) encodedChar(rootTree, fork.left , currentEncoded ::: List(0))(char)
                                        else encodedChar(rootTree, fork.right, currentEncoded ::: List(1))(char)
      case (_, _, _) => assert(false, "case is not handled"); Nil // The compiler says not exhaustive for (_,_,true) but it is not true!
    }
  } 

  // Part 4b: Encoding using code table

  type CodeTable = List[(Char, List[Bit])]

  /**
   * This function returns the bit sequence that represents the character `char` in
   * the code table `table`.
   */
  @tailrec
  def codeBits(table: CodeTable)(char: Char): List[Bit] = {
    table match {
      case Nil => Nil
      case (char, code) :: tail => code
      case _ :: tail => codeBits(tail) (char)
    }
  }

  /**
   * Given a code tree, create a code table which contains, for every character in the
   * code tree, the sequence of bits representing that character.
   *
   * Hint: think of a recursive solution: every sub-tree of the code tree `tree` is itself
   * a valid code tree that can be represented as a code table. Using the code tables of the
   * sub-trees, think of how to build the code table for the entire tree.
   */
  def convert(tree: CodeTree): CodeTable = {
    def convertHelper(rootTree:CodeTree, tree: CodeTree, codePart: List[Bit], acc: CodeTable): CodeTable = {
      (rootTree == tree, tree) match {
         case (true , leaf: Leaf) => (leaf.char, List(TRIVIAL_TREE_CODE)) :: acc
         case (false, leaf: Leaf) => (leaf.char, codePart) :: acc
         case (_    , fork: Fork) => convertHelper(rootTree, fork.left , codePart ::: List(0), acc) :::
                                     convertHelper(rootTree, fork.right, codePart ::: List(1), Nil)
         case (_,_) => assert(false, "Invalid case"); Nil // I have to study match since this is again when the compiler complains about not exhaustive match
      }
    }
    convertHelper(tree, tree, Nil, Nil)
  }
  /**
   * This function takes two code tables and merges them into one. Depending on how you
   * use it in the `convert` method above, this merge method might also do some transformations
   * on the two parameter code tables.
   */ 
  def mergeCodeTables(a: CodeTable, b: CodeTable): CodeTable = {
    @tailrec
    def mergeCodePair(codePair: (Char, List[Bit]), b: CodeTable, acc: CodeTable): CodeTable =  {
      b match {
        case Nil => acc
        case (codePair._1, codePair._2) :: tail => acc
        case (codePair._1, badCode) :: tail => throw new IllegalArgumentException("The char '" + codePair._1 + "' coded in a different way in the merged lists. Codes: " + badCode + " " + codePair._2)
        case (badChar, codePair._2) :: tail => throw new IllegalArgumentException("Different chars coded by the same sequnce: " + codePair._2 + ". Chars: '" + badChar + "' '" + codePair._1 + "'")
        case head::tail => mergeCodePair(codePair, tail, acc)
      }
    }
    @tailrec
    def mergeTablesHelper(a: CodeTable, b: CodeTable, acc: CodeTable): CodeTable = {
      a match {
        case Nil => acc
        case head :: tail =>  mergeTablesHelper(tail, b, mergeCodePair(head, b, acc))
      } 
    }
    // take each codePair from the a 
    // try to add it to the b
    mergeTablesHelper(a,b,b)
  }

  /**
   * This function encodes `text` according to the code tree `tree`.
   *
   * To speed up the encoding process, it first converts the code tree to a code table
   * and then uses it to perform the actual encoding.
   */
  def quickEncode(tree: CodeTree)(text: List[Char]): List[Bit] = {
    val quickTable = convert(tree)
    @tailrec
    def findCode(charToFind: Char, table: CodeTable): List[Bit] = {
      val TheCharToFind = charToFind // Have to put the value in upper case to make match possible!!!
      table match {
        case Nil => throw new IllegalArgumentException("Cannot find the code for the char '" + charToFind + "'")
        case (TheCharToFind, charCode) :: tableTail => charCode
        case head :: tableTail => findCode(charToFind, tableTail)
      }
    }
    @tailrec
    def quickEncodeHelper(table: CodeTable, acc: List[Bit])(text: List[Char]): List[Bit] = {
      text match {
        case Nil => acc
        case theChar :: textTail => quickEncodeHelper(table, acc ::: findCode(theChar, quickTable))(textTail)
      }
    }
    quickEncodeHelper(quickTable, Nil)(text);
  }
}
