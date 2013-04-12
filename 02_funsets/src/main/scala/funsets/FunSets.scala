package funsets

import common._
import MapReduce._
/**
 * 2. Purely Functional Sets.
 */
object FunSets {
  /**
   * We represent a set by its characteristic function, i.e.
   * its `contains` predicate.
   */
  type Set = Int => Boolean

  type SetMapper = Int => Int // type for mapping function which is applied on set elements 
  type SetValueReducer = (Set, Int) => Set // type for reduce function which is applied on the sets and a value from the set and gives a reduced set
  type Set2Reducer = (Set, Set) => Set // type for reduce function which is applied on two sets and gives a reduced set

  type SetPredicate = (Int) => Boolean // Predicate applied on the set ex. as a filter

  /**
   * Indicates whether a set contains a given element.
   */
  def contains(s: Set, elem: Int): Boolean = s(elem)

  /**
   * Returns the set of the one given element.
   */
  def singletonSet(elem: Int): Set = {
    x => elem == x
  }

  /**
   * empty set - contains nothing
   */
  def emptySet(): Set = {
    x => false
  }

  /**
   * universum set - contains all values
   */
  def universumSet(): Set = {
    x => true
  }

  /**
   * set of from .. to.
   * from and to both are part of a range
   */
  def closedRange(from: Int, to: Int): Set = {
    x => (x >= from && x <= to)
  }

  /**
   * Returns the union of the two given sets,
   * the sets of all elements that are in either `s` or `t`.
   */
  def union(s: Set, t: Set): Set = {
    x => s(x) || t(x)
  }

  /**
   * Returns the intersection of the two given sets,
   * the set of all elements that are both in `s` and `t`.
   */
  def intersect(s: Set, t: Set): Set = {
    x => s(x) && t(x)
  }

  /**
   * Returns the difference of the two given sets,
   * the set of all elements of `s` that are not in `t`.
   */
  def diff(s: Set, t: Set): Set = {
    x => s(x) && !t(x)
  }

  /**
   * Returns the subset of `s` for which `p` holds.
   */
  def filter(s: Set, p: Int => Boolean): Set = {
    intersect(s, p)
  }

  // Predicates
  def everyElement(e: Int): Boolean = true // every element of the set 
  def noneElement(e: Int): Boolean = false // none element

  /**
   * The bounds for `forall` and `exists` are +/- 1000.
   */
  val bound = 1000

  /**
   * Check the predicate. If fails then it returns false, if for each element is succeeds then returns true
   */
  def checkPredicate(s: Set, p: SetPredicate, initialValue: Boolean): Boolean = {
    def iter(a: Int, acc: Boolean): Boolean = {
      if (a > bound) acc
      else if (contains(s, a) && !p(a)) false
      else iter(a + 1, acc)
    }
    iter(-bound, initialValue)
  }

  // NOTE:
  //
  //   For all and exists have to check all the values so it is impossible to give the pure functional implementation
  // it must be something like this: forall(s,p) = forall(union(universumSet(), s) ,p) - but is is recursive definition
  // Possible we may pass the iterator to the function and define only failure condition 
  // ex forall(predicate)=failWhen(differs(everyElement,predicate))
  //    exists(predicate)=failWhen(differs(noneElement, predicate))
  //    exists(predicate)=!failWhen(differs(!noneElement, predicate))

  // WOW, and this may work that way:
  // - if we provide the range to check - it will check all the values
  // - if we do not provide the value it may generate it starting from 0 .. infinitum and 0 .. -infinitum, then
  // - forall will stop only when the predicate is not everyElement 
  // - exists will stop only when the predicate is not noneElement
  // - Scala is GREAT (as LISP is)
  /**
   * Returns whether all bounded integers within `s` satisfy `p`.
   */
  def forall(s: Set, p: Int => Boolean): Boolean = {
    def iter(a: Int, acc: Boolean): Boolean = {
      if (a > bound) acc
      else if (contains(s, a) && !p(a)) false
      else iter(a + 1, acc)
    }
    iter(-bound, true)
  }

  /**
   * Returns whether there exists a bounded integer within `s`
   * that satisfies `p`.
   */
  def exists(s: Set, p: Int => Boolean): Boolean = {
    !forall(s, x => !p(x))
  }

  /**
   * Returns a set transformed by applying `f` to each element of `s`.
   */
  def map(s: Set, f: Int => Int): Set = {
    def iter(a: Int, acc: Set): Set = {
      if (a > bound) acc
      else if (contains(s, a))
        iter(a + 1, union(singletonSet(f(a)), acc))
      else iter(a + 1, acc)
    }
    iter(-bound, emptySet())
  }
  def map2(s: Set, f: SetMapper): Set = mapreduce(s, f, union)

  /**
   * Displays the contents of a set
   */
  def toString(s: Set): String = {
    val xs = for (i <- -bound to bound if contains(s, i)) yield i
    xs.mkString("{", ",", "}")
  }

  /**
   *  The same as toString but the range is 2 times wider
   */
  def toString2(s: Set): String = {
    val xs = for (i <- -bound * 2 to bound * 2 if contains(s, i)) yield i
    xs.mkString("{", ",", "}")
  }

  def applyPrinter(s: Set, set2String: Set => String, printer: String => Unit) = {
    println(set2String(s))
  }

  /**
   * Prints the contents of a set on the console.
   */
  def printSet(s: Set) {
    applyPrinter(s, toString, println)
  }

  /**
   * The same as printSet but uses toString2
   */
  def printSet2(s: Set) {
    applyPrinter(s, toString2, println)
  }
}
