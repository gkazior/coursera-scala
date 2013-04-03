package recfun

import scala.annotation.tailrec

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   * Sample pascal triangle
   *       1      // r = 0 c = 0
   *      1 1     // r = 1 c = 0,1
   *     1 2 1    // r = 2 c = 0,1,2
   *    1 3 3 1   // r = 3 c = 0,1,2,3
   *   1 4 6 4 1  // r = 4 c = 0,1,2,3,4
   *
   * Columns and rows indexed from 0
   *
   * Function is not optimized. Stack usage is not optimized.
   */
  def pascal(c: Int, r: Int): Int = {
    if (c < 0 || r < 0) throw new java.util.NoSuchElementException

    if (c == 0 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2
   *
   * See the test suite for
   */
  def balance(chars: List[Char]): Boolean = {
    @tailrec
    def balanceHelper(currentlyOpenNo: Int, chars: List[Char]): Boolean = {
      if (currentlyOpenNo < 0) false
      else if (chars.isEmpty) currentlyOpenNo == 0
      else chars.head match {
        case '(' => balanceHelper(currentlyOpenNo + 1, chars.tail)
        case ')' => balanceHelper(currentlyOpenNo - 1, chars.tail)
        case _ => balanceHelper(currentlyOpenNo, chars.tail)
      }

    }
    balanceHelper(0, chars);
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    0
  }
}
