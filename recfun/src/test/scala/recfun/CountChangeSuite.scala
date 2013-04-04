package recfun

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CountChangeSuite extends FunSuite {
  import Main.countChange
  def assertCountChange(ways: Int, givenChange: Int, coins: List[Int]) {
    assert(countChange(givenChange, coins) === ways)
  }
  test("countChange: borders for change") {
    assertCountChange(0, 0, List(1, 2))
    assertCountChange(0, 0, List(1, 2, 5))
  }
  test("countChange: borders for coins") {
    assertCountChange(0, 0, List(1, 2))
    assertCountChange(0, 0, List(1, 2, 5))
  }

  test("countChange: invalid args for change") {
    // change negative
  }
  test("countChange: invalid args for coins") {
    // cannot contain 0 
    // change contain negatives
    // cannot contain duplicates
  }
  test("countChange: normal args") {
    assertCountChange(1, 1, List(1))
    assertCountChange(1, 5, List(5))
    assertCountChange(1, 501, List(501))

    assertCountChange(2, 2, List(1,2))
    assertCountChange(2, 2, List(1,2))
    assertCountChange(3, 4, List(1,2))
    assertCountChange(3, 5, List(1,2))
  }
  test("countChange: strange") {
    // change negative
  }

  test("countChange: example given in instructions") {
    assert(countChange(4, List(1, 2)) === 3)
  }

  test("countChange: sorted CHF") {
    assert(countChange(300, List(5, 10, 20, 50, 100, 200, 500)) === 1022)
  }

  test("countChange: no pennies") {
    assert(countChange(301, List(5, 10, 20, 50, 100, 200, 500)) === 0)
  }

  test("countChange: unsorted CHF") {
    assert(countChange(300, List(500, 5, 50, 100, 20, 200, 10)) === 1022)
  }
  test("countChange: check the borders ") {
    assert(countChange(0, List(1, 2)) === 0)
  }

}
