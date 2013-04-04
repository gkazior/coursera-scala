package recfun

import scala.annotation.tailrec

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import RecursiveUtils.isListOrderedStrong
import RecursiveUtils.maxSoFar

@RunWith(classOf[JUnitRunner])
class RecursiveUtilsSuite extends FunSuite {
  test("max: ") {
    assert(maxSoFar(0, List(1, 2, 3)) === 3)
  }

  test("isListOrderedStrong") {
    assert(isListOrderedStrong(0, List()))
    assert(isListOrderedStrong(-10, List()))
    assert(isListOrderedStrong(-10, List(-9)))
    assert(isListOrderedStrong(0, List(1, 2)))

    assert(isListOrderedStrong(-9, List(-9)) === false)
    assert(isListOrderedStrong(0, List(-9)) === false)

    assert(!isListOrderedStrong(0, List(-9, 0, 9)))
    assert(!isListOrderedStrong(-9, List(-9, 0, 9)))

    assert(isListOrderedStrong(-10, List(-9, 0, 9)))
  }

}
