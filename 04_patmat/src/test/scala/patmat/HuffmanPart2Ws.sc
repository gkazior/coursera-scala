package patmat

import patmat.Huffman._

object HuffmanPart2Ws {
  def NEVER[Type](arg: Type): Boolean = false     //> NEVER: [Type](arg: Type)Boolean
  def ALWAYS[Type](arg: Type): Boolean = true     //> ALWAYS: [Type](arg: Type)Boolean

  def NEVER2[Type, AccType](arg1: Type, arg2: AccType): Boolean = false
                                                  //> NEVER2: [Type, AccType](arg1: Type, arg2: AccType)Boolean
  def ALWAYS2[Type, AccType](arg1: Type, arg2: AccType): Boolean = true
                                                  //> ALWAYS2: [Type, AccType](arg1: Type, arg2: AccType)Boolean

  def foreach[Type, AccType](
    list: List[Type] // foreach traverses for each element of the list
    , acc: => AccType = Nil // Starts with the acc
    , collectPred: => Type => Boolean = ALWAYS[Type]_ // foreach collects the element when collectPred is true
    , stopPred: (Type, AccType) => Boolean = NEVER2[Type, AccType]_ // stop traversing when stopPred returns true
    )(collectFn: => (Type, AccType) => AccType // then it accumulates the result using the collectFn
    ): AccType = {

    list match {
      case Nil => acc
      case head :: tail =>
        if (stopPred(head, acc)) acc
        else if (collectPred(head))
          foreach(tail, collectFn(head, acc), collectPred, stopPred)(collectFn)
        else foreach(tail, acc, collectPred, stopPred)(collectFn)
    }

  }                                               //> foreach: [Type, AccType](list: List[Type], acc: => AccType, collectPred: =>
                                                  //|  Type => Boolean, stopPred: (Type, AccType) => Boolean)(collectFn: => (Type
                                                  //| , AccType) => AccType)AccType
  val concat: List[(Char, Int)] = ('a', 1) :: List(('b', 2))
                                                  //> concat  : List[(Char, Int)] = List((a,1), (b,2))


  times(string2Chars("helpma"))                   //> res0: List[(Char, Int)] = List((h,1), (e,1), (l,1), (p,1), (m,1), (a,1))
  times(string2Chars("aheala"))                   //> res1: List[(Char, Int)] = List((a,3), (h,1), (e,1), (l,1))
  val eha = times(string2Chars("eehahealaa"))     //> eha  : List[(Char, Int)] = List((e,3), (h,2), (a,4), (l,1))
  
  putInOrder(('a', 10), eha, firstLess)           //> res2: List[(Char, Int)] = List((e,3), (h,2), (a,4), (l,1), (a,10))
  
  makeOrderedLeafList(eha)                        //> List((l,1), (h,2), (e,3), (a,4))
                                                  //| res3: List[patmat.Huffman.Leaf] = List(Leaf(l,1), Leaf(h,2), Leaf(e,3), Lea
                                                  //| f(a,4))

  val l1 = string2Chars("aaabbcc")                //> l1  : List[Char] = List(a, a, a, b, b, c, c)
  val l2 = List(1, 2, 3, 4, 1)                    //> l2  : List[Int] = List(1, 2, 3, 4, 1)
  def plus(x: Int, acc: Int = 1) = x + acc        //> plus: (x: Int, acc: Int)Int
  def plus2(x: Int, fn: Int => Int = x => x + 1) = fn(x)
                                                  //> plus2: (x: Int, fn: Int => Int)Int
  def plus3(x: Int, fn: Int => Int = x => x + 1, fnPred: Int => Boolean = ALWAYS[Int]) = if (fnPred(x)) fn(x) else x
                                                  //> plus3: (x: Int, fn: Int => Int, fnPred: Int => Boolean)Int

  l2.reduce(plus)                                 //> res4: Int = 11
  l2 reduce plus                                  //> res5: Int = 11
  l2 reduce (_ + _)                               //> res6: Int = 11
  
  l2 map (_ + 1)                                  //> res7: List[Int] = List(2, 3, 4, 5, 2)
  // computes the sum
  foreach[Int, Int](l2, 0)((x, acc) => x + acc)   //> res8: Int = 11
 
  // computes the sum, stops then sum is greater than 5
  foreach[Int, Int](l2, 0 //
  , collectPred = ALWAYS //
  , stopPred = (x, acc) => acc > 5) ((x, acc) => x + acc)
                                                  //> res9: Int = 6
  
  foreach[Int, Int](l2, 0, stopPred = (x, acc) => acc > 5) // Here the collectPred is ommitted
  {
    (x, acc) => x + acc // Thanks to partial function we may write also
  }                                               //> res10: Int = 6

  l2.foldLeft(0) {
    (x, y) => x + y
  }                                               //> res11: Int = 11

  val defaultArg = plus(3)                        //> defaultArg  : Int = 4
  val defaultArg2 = plus2(3, 1 + _)               //> defaultArg2  : Int = 4
  val defaultArg3 = plus2(3)                      //> defaultArg3  : Int = 4
  val defaultArg40 = plus3(3, 1 + _, ALWAYS[Int]) //> defaultArg40  : Int = 4
  val defaultArg41 = plus3(3, 1 + _)              //> defaultArg41  : Int = 4

}