package patmat

object MapUtils {
  def NEVER[Type](arg: Type): Boolean = false
  def ALWAYS[Type](arg: Type): Boolean = true

  def NEVER2[Type, AccType](arg1: Type, arg2: AccType): Boolean = false

  def ALWAYS2[Type, AccType](arg1: Type, arg2: AccType): Boolean = true

  /**
   * foreach traverses for each element of the list
   *
   * <code>
   * // computes the sum
   * val list = List(1, 2, 3, 4, 1)
   * foreach[Int, Int](list, 0)((x, acc) => x + acc)   //> res5: Int = 11
   *
   * //computes the sum, stops then sum is greater than 5
   * foreach[Int, Int](l2, 0 //
   * , collectPred = ALWAYS //
   * , stopPred = (x, acc) => acc > 5) ((x, acc) => x + acc)
   * //> res6: Int = 6
   *
   *
   * foreach[Int, Int](l2, 0, stopPred = (x, acc) => acc > 5) // Here the collectPred is ommitted
   * {
   * (x, acc) => x + acc // Thanks to partial function we may write also
   * }
   * <code>
   *
   */
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
  }

  /**
   *  Adds a pair to the list when the first element from the pair is not present on the list.
   *  When present it returns the copy of the list with replaced matched pair
   */
  def addPair(pair: (Char, Int), list: List[(Char, Int)]): List[(Char, Int)] = {
    list match {
      case Nil => List(pair)
      case (char, times) :: tail =>
        if (char == pair._1) (char, pair._2 + times) :: tail
        else (char, times) :: addPair(pair, tail)
    }
  }
}