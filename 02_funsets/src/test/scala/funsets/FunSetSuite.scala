package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    var range10 = closedRange(-10, 10)
    val lBound = closedRange(-bound - 1, -bound + 2) //> lBound  : Int => Boolean = <function1>
    val rBound = closedRange(bound - 2, bound + 2) //> rBound  : Int => Boolean = <function1>
    val rWithZero = closedRange(-1, 1) //> rWithZero  : Int => Boolean = <function1>
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("s123 union") {
    new TestSets {
      val s123 = union(s1, union(s2, s3))
      assert(contains(s123, 1), "s123(1)")
      assert(contains(s123, 2), "s123(2)")
      assert(contains(s123, 3), "s123(3)")
      assert(!contains(s123, 4), "s123(4)")
    }
  }

  test("s123&s12") {
    new TestSets {
      val s123 = union(s1, union(s2, s3))
      val s12 = union(s1, s2)
      val sx = intersect(s123, s12)

      assert(contains(sx, 1), "s123(1)")
      assert(contains(sx, 2), "s123(2)")
      assert(!contains(sx, 3), "s123(3)")
      assert(!contains(sx, 4), "s123(4)")
    }
  }
  test("s12&s123") {
    new TestSets {
      val s123 = union(s1, union(s2, s3))
      val s12 = union(s1, s2)
      val sx = intersect(s12, s123)

      assert(contains(sx, 1), "s123(1)")
      assert(contains(sx, 2), "s123(2)")
      assert(!contains(sx, 3), "s123(3)")
      assert(!contains(sx, 4), "s123(4)")
    }
  }
  test("s123-s12") {
    new TestSets {
      val s123 = union(s1, union(s2, s3))
      val s12 = union(s1, s2)
      val sx = diff(s123, s12)

      assert(!contains(sx, 1), "s123(1)")
      assert(!contains(sx, 2), "s123(2)")
      assert(contains(sx, 3), "s123(3)")
      assert(!contains(sx, 4), "s123(4)")
    }
  }
  test("s12-s123") {
    new TestSets {
      val s123 = union(s1, union(s2, s3))
      val s12 = union(s1, s2)
      val sx = diff(s12, s123)

      assert(!contains(sx, 1), "s123(1)")
      assert(!contains(sx, 2), "s123(2)")
      assert(!contains(sx, 3), "s123(3)")
      assert(!contains(sx, 4), "s123(4)")
    }
  }
  test("exists tests") {
    new TestSets {
      assert(exists(range10, x => x > 2), "1")
      assert(exists(range10, x => x < 0), "2")
      assert(exists(range10, x => x > -5), "3")

      assert(exists(lBound, x => x > -bound), "4")
      assert(exists(lBound, x => x == -bound), "5") // Assure the lower bound is checked
      assert(!exists(lBound, x => x < -bound), "6") // Carefully - do not compute outside the (-bound,bound)

      assert(!exists(lBound, x => x > bound), "14")
      assert(!exists(lBound, x => x == bound), "15") // Assure the upper bound is checked
      assert(exists(lBound, x => x < bound), "16") // Carefully - do not compute outside the (-bound,bound)

    }
  }
  test("forall tests") {
    new TestSets {
      assert(!forall(range10, x => (x > 2) && (x < 4)), "1")
      assert(!forall(range10, x => (x < 0) && (x > -4)), "2")
      assert(forall(range10, x => (x >= -10) && (x <= 10)), "3")

      assert(forall(lBound, x => x >= -bound), "4")
      assert(forall(lBound, x => x <= bound), "5") // Assure the lower bound is checked
      assert(!forall(lBound, x => x < -bound), "6") // Carefully - do not compute outside the (-bound,bound)
      assert(!forall(lBound, x => x > bound), "6a") // Carefully - do not compute outside the (-bound,bound)

      assert(!forall(lBound, x => x > bound), "14")
      assert(!forall(lBound, x => x == bound), "15") // Assure the upper bound is checked
      assert(forall(lBound, x => x < bound), "16") // Carefully - do not compute outside the (-bound,bound)
    }
  }
  test("emptySet") {
    new TestSets {
      assert(!exists(emptySet(), everyElement), "1")
      assert(!exists(emptySet(), noneElement), "2")

      assert(forall(emptySet(), everyElement), "3") // For the empty set every predicate is true 
      assert(forall(emptySet(), noneElement), "3a") // Does for the empty set falsePredicate is true?
    }
  }
  test("universumSet") {
    new TestSets {
      assert(!exists(universumSet(), noneElement), "1")
      assert(forall(universumSet(), everyElement), "2")

      assert(forall(union(universumSet(), emptySet()), everyElement), "forall(union(U,0))")
      assert(forall(union(emptySet(), universumSet()), everyElement), "forall(union(0,U))")
    }
  }
  test("filter <= 2") {
    new TestSets {
      val s123 = union(s1, union(s2, s3))
      val sx = filter(s123, x => x <= 2)

      assert(contains(sx, 1), "s123(1)")
      assert(contains(sx, 2), "s123(2)")
      assert(!contains(sx, 3), "s123(3)")
      assert(!contains(sx, 4), "s123(4)")
    }
  }

  test("map") {
    new TestSets {
      val s123 = union(s1, union(s2, s3))
      val sx = filter(s123, x => x <= 2)

      assert(contains(sx, 1), "s123(1)")
      assert(contains(sx, 2), "s123(2)")
      assert(!contains(sx, 3), "s123(3)")
      assert(!contains(sx, 4), "s123(4)")
    }
  }

}
