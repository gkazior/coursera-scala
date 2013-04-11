package recfun

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PascalSuite extends FunSuite {
  import Main.pascal
  test("pascal: invalid c -1") {
    intercept[NoSuchElementException] {
      pascal(-1, 2)
    }
  }
  test("pascal: invalid c -5") {
    intercept[NoSuchElementException] {
      pascal(-5, 2)
    }
  }
  test("pascal: invalid r -1") {
    intercept[NoSuchElementException] {
      pascal(0, -1)
    }
  }
  test("pascal: invalid r -5") {
    intercept[NoSuchElementException] {
      pascal(10, -5)
    }
  }
  test("pascal: invalid c and r") {
    intercept[NoSuchElementException] {
      pascal(-1, -1)
    }
  }
  test("pascal: col=0,row=2") {
    assert(pascal(0, 2) === 1)
  }
  test("pascal: col=1,row=2") {
    assert(pascal(1, 2) === 2)
  }
  test("pascal: col=1,row=3") {
    assert(pascal(1, 3) === 3)
  }
  test("pascal: tree") {
    val expectedResults = List(List(1) //
    , List(1, 1) //
    , List(1, 2, 1) //
    , List(1, 3, 3, 1) //
    , List(1, 4, 6, 4, 1) //
    , List(1, 5, 10, 10, 5, 1) //
    )

    var idxRow = 0
    for (rowList <- expectedResults) {
      var idxColumn = 0
      for (value <- rowList) {
        assert(pascal(idxColumn, idxRow) === value)
        idxColumn = idxColumn + 1
      }
      idxRow = idxRow + 1
    }

  }

}
