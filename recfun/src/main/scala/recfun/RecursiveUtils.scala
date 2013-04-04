package recfun

import scala.annotation.tailrec
import RecursiveUtils._

// redundant (for assignment) object
object RecursiveUtils {
  // I know there is scala fun for it but for practicing ...
  @tailrec
  def isListOrderedStrong(orderedFromValue: Int, list: List[Int]): Boolean = {
    list match {
      case Nil =>
        true
      case head :: tail =>
        if (orderedFromValue >= head) false
        else isListOrderedStrong(head, tail)
    }
  } // isListOrderedStrong
  // I know there is scala fun for it but for practicing ...
  @tailrec
  def maxSoFar(maxSoFarValue: Int, list: List[Int]): Int = {
    list match {
      case Nil => maxSoFarValue
      case head :: tail => maxSoFar(
        if (head > maxSoFarValue) head
        else maxSoFarValue, tail)
    }
  } // maxSoFar

}