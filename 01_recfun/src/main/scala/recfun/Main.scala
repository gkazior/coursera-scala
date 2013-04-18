package recfun

import scala.annotation.tailrec
import RecursiveUtils.isListOrderedStrong

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
   * Ex. pascal(1,2) gets the item from the first column and second row (counted from 0). The item value is 2.
   *
   * Function is not optimized. Stack usage is not optimized.
   *
   * @param c column
   * @param r row
   *
   * @return the value from the pascal triangle from row r and column c
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
  @tailrec // redundant function
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
   * Check if the list contains balanced parenthesis.
   * The order is important - first `open` than matching `close`
   * Never `close` when all opened are already balanced
   *
   * See the test suite for the specification
   * @param chars list of chars to check the balance
   * @return true when the chars has balanced parenthesis and false otherwise
   *
   */
  def balance(chars: List[Char]): Boolean = {
    balanceHelper(0, chars);
  }

  /* 
   * Should be the same as balance2. I only use pattern matching which is great in scala.
   * 
   * */
  // redundant function
  def balance2(chars: List[Char]): Boolean = {
    balance2Helper(0, chars);
  }

  /**
   * Exercise 3
   *
   * Computes the number of possible changes
   *
   * @param money money to change
   * @coins coins amounts, the same amount may be chosen several times
   * @return number of possible changes
   *
   * @throws java.lang.IllegalArgumentException for invalid input
   */
  def countChange(money: Int, coins: List[Int]): Int = {

    // redundant function
    def coinsAreValid(coins: List[Int]): Boolean = {
      // false when the list is not distinct - leads to not intuitive behavior
      // maybe false when the list is not sorted. Sometimes when the list is sorted we sort, but do not need to
      isListOrderedStrong(0, coins) // 0 cannot be in the list!
    } // coinsAreValid

    //@tailrec //- cannot easily make it tail recursive because of case 1  
    def countChangeHelper(moneyLeft: Int, coins: List[Int], countSoFar: Int): Int = {
      coins match {
        case Nil => countSoFar
        case head :: tail =>
          countChangeHelper(moneyLeft //
          , tail //
          , (moneyLeft - head).signum match {
            case 1 => countChangeHelper(moneyLeft - head, coins, countSoFar) // Sth to change
            case 0 => countSoFar + 1 // nothing left to change
            case _ => countSoFar // tried to much
          })
      }
    } // countChangeHelper

    // Some simple profiling
    //countChangeHead(money, coins, 0)                                        // 8.374 sec
    //countChangeHead(money, coins.sortWith(_ > _), 0)                        // 3.832 sec
    //if (money <= 0) 0 else countChangeHead(money, coins.sortWith(_ > _), 0) // 3.790 sec
    // 2.834 sec after removing println
    // 2.665 sec after removing ret   
    // 0.054 sec removing all prints

    val sortedDescendingCoins = coins.distinct.sortWith(_ > _)

    if (!coinsAreValid(sortedDescendingCoins.reverse)) throw new java.lang.IllegalArgumentException

    if (money <= 0) 0
    else countChangeHelper(money, sortedDescendingCoins, 0) // 3.832 sec
  }
}
