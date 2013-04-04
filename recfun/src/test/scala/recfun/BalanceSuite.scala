package recfun

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import Main.balance
import Main.balance2
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BalanceSuite extends FunSuite {
  def testBalanceFn(testName: String, balanceFn: (List[Char]) => Boolean, arg: String, expectedResult: Boolean): Unit = {
    test(testName) {
      assert(balanceFn(arg.toList) === expectedResult)
    }
  }
  def testHelper(expectedResult: Boolean, arg: String): Unit = {
    // Very funny way to make the unique name for testcase
    // Seems that scala is perfectly fine but JUnitRunner/eclipse do not like strange names
    // Names proposed as embedded names are not nice too ex. first name is cut just after first space
    testBalanceFn("balance : [" + arg.hashCode + "] " + arg, balance, arg, expectedResult)
    testBalanceFn("balance2: [" + arg.hashCode + "] " + arg, balance2, arg, expectedResult)
  }
  // I do not change embedded tests - in fact I am curious of the mechanics behind assignment
  test("balance: '(if (zero? x) max (/ 1 x))' is balanced") {
    assert(balance("(if (zero? x) max (/ 1 x))".toList))
  }

  test("balance: 'I told him ...' is balanced") {
    assert(balance("I told him (that it's not (yet) done).\n(But he wasn't listening)".toList))
  }

  test("balance: ':-)' is unbalanced") {
    assert(!balance(":-)".toList))
  }

  test("balance: counting is not enough") {
    assert(!balance("())(".toList))
  }

  // Bad idea. When test fails the stack does not show the problem. Better to use arg.hashCode ;-)
  test("True values") {
    for (
      arg <- List( //
        "(if (zero? x) max (/ 1 x))' is balanced" //
        , "I told him (that it's not (yet) done).\n(But he wasn't listening)" //
        , "()" //
        , "" //
        , "This works as well" //
        , "\n\n", "(\n\n)", "(\n(\n))" //
        , "()()()()", "(((((((())))))))" //
        )
    ) {
      assert(balance(arg.toList))
      assert(balance2(arg.toList))
    }
  }

  testHelper(false, ")")
  testHelper(false, "(")
  testHelper(false, "))")
  testHelper(false, "((")
  testHelper(false, ")(")
  testHelper(false, "(()")
  testHelper(false, "(()))")
  testHelper(false, ":-)")
  testHelper(false, ":-(")
  testHelper(false, "())(")

  // As "True values". That code does not lead to good problem diagnosis
  test("False values") {
    for (
      arg <- List( //
        ")", "(", "))", "((", ")(" //
        , "(()", "())" //
        , "())(" //
        , ":-)", ":-(" // Secret weapon
        )
    ) {
      assert(!balance(arg.toList))
      assert(!balance2(arg.toList))
    }
  }

}
