package example

object ListWorksheet {

  /**
   * Exercise 3
   *
   * Computes the number of possible changes
   *
   * @param money money to change
   * @coins coins amounts, the same amount may be chosen several times
   * @return number of possible changes
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def countChangeHead(moneyLeft: Int, coins: List[Int], countSoFar: Int): Int = {
      //println("Called with: " + moneyLeft + " " + coins + " soFar:" + countSoFar);
      val ret = coins match {
        case Nil => countSoFar
        case head :: tail =>
          (moneyLeft - head).signum match {
            case 1 =>
              println("Eat[" + head + "] ");
              countChangeHead(moneyLeft - head, coins, countSoFar) +
              countChangeHead(moneyLeft, tail, 0) // Sth to change
            case 0 =>
              println("Match[" + head + "]");
              countChangeHead(moneyLeft, tail, countSoFar + 1) // Exactly change == to moneyLeft
            case _ => print("Fura! ");
              countChangeHead(moneyLeft, tail, countSoFar) // fura
          }
      }
      
      println("Returning: " + ret)
      ret
    }

    if (money <= 0) 0
    else countChangeHead(money, coins.sortWith(_ > _), 0)
  }                                               //> countChange: (money: Int, coins: List[Int])Int

  //var r1 = countChange(2, List(2)) == 1;
  //var r2 = countChange(8, List(2)) == 1;
  //var r3 = countChange(2, List(1, 2));
  var r3 = countChange(6, List(1, 2));            //> Eat[2] 
                                                  //| Eat[2] 
                                                  //| Match[2]
                                                  //| Eat[1] 
                                                  //| Match[1]
                                                  //| Returning: 2
                                                  //| Returning: 2
                                                  //| Returning: 0
                                                  //| Returning: 2
                                                  //| Returning: 2
                                                  //| Eat[1] 
                                                  //| Eat[1] 
                                                  //| Eat[1] 
                                                  //| Match[1]
                                                  //| Returning: 1
                                                  //| Returning: 1
                                                  //| Returning: 0
                                                  //| Returning: 1
                                                  //| Returning: 0
                                                  //| Returning: 1
                                                  //| Returning: 0
                                                  //| Returning: 1
                                                  //| Returning: 3
                                                  //| Eat[1] 
                                                  //| Eat[1] 
                                                  //| Eat[1] 
                                                  //| Eat[1] 
                                                  //| Eat[1] 
                                                  //| Match[1]
                                                  //| Returning: 1
                                                  //| Returning: 1
                                                  //| Returning: 0
                                                  //| Returning: 1
                                                  //| Returning: 0
                                                  //| Returning: 1
                                                  //| Returning: 0
                                                  //| Returning: 1
                                                  //| Returning: 0
                                                  //| Returning: 1
                                                  //| Returning: 0
                                                  //| Returning: 1
                                                  //| Returning: 4
                                                  //| r3  : Int = 4
}