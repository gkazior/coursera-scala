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
   * Returns the value from the pascal triangle indexed by (c,r).
   * Ex. pascal(1,2) gets the item from the first column and second row (counted from 0)
   *
   * Function is not optimized. Stack usage is not optimized.
   */
  def pascal(c: Int, r: Int): Int = {
    if (c < 0 || r < 0) throw new java.util.NoSuchElementException

    if (c == 0 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  @tailrec
  private def balanceHelper(currentlyOpenNo: Int, chars: List[Char]): Boolean = {
    if (currentlyOpenNo < 0) false
    else if (chars.isEmpty) currentlyOpenNo == 0
    else chars.head match {
      case '(' => balanceHelper(currentlyOpenNo + 1, chars.tail)
      case ')' => balanceHelper(currentlyOpenNo - 1, chars.tail)
      case _ => balanceHelper(currentlyOpenNo, chars.tail)
    }
  }
  @tailrec
  private def balance2Helper(currentlyOpenNo: Int, chars: List[Char]): Boolean = {
    if (currentlyOpenNo < 0) false
    else chars match {
      case Nil => currentlyOpenNo == 0
      case '(' :: tail => balance2Helper(currentlyOpenNo + 1, tail)
      case ')' :: tail => balance2Helper(currentlyOpenNo - 1, tail)
      case _ :: tail => balance2Helper(currentlyOpenNo, tail)
    }
  }

  /**
   * Exercise 2
   *
   * See the test suite for
   *
   */
  def balance(chars: List[Char]): Boolean = {
    balanceHelper(0, chars);
  }

  /* 
   * Should be the same as balance2. I only use pattern matching which is great in scala.
   * */
  def balance2(chars: List[Char]): Boolean = {
    balance2Helper(0, chars);
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    0
  }
}
