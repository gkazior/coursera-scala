package funsets

object MapReduceWorksheet {

  type Set = Int => Boolean
  type SetMapper = Int => Int // type for mapping function which is applied on set elements
  type SetValueReducer = (Set, Int) => Set // type for reduce function which is applied on the sets and a value from the set and gives a reduced set
  type Set2Reducer = (Set, Set) => Set // type for reduce function which is applied on two sets and gives a reduced set

  type SetPredicate = (Int) => Boolean // Predicate applied on the set ex. as a filter

  def singletonSet(elem: Int): Set = {
    x => elem == x
  }                                               //> singletonSet: (elem: Int)Int => Boolean

  def contains(s: Set, elem: Int): Boolean = s(elem)
                                                  //> contains: (s: Int => Boolean, elem: Int)Boolean

  def emptySet(): Set = {
    x => false
  }                                               //> emptySet: ()Int => Boolean

  def closedRange(from: Int, to: Int): Set = {
    x => (x >= from && x <= to)
  }                                               //> closedRange: (from: Int, to: Int)Int => Boolean

  def union(s: Set, t: Set): Set = {
    x => s(x) || t(x)
  }                                               //> union: (s: Int => Boolean, t: Int => Boolean)Int => Boolean

  val bound = 1000                                //> bound  : Int = 1000

  def toString(s: Set): String = {
    val xs = for (i <- -bound to bound if contains(s, i)) yield i
    xs.mkString("{", ",", "}")
  }                                               //> toString: (s: Int => Boolean)String

  def applyPrinter(s: Set, set2String: Set => String, printer: String => Unit) = {
    println(set2String(s))
  }                                               //> applyPrinter: (s: Int => Boolean, set2String: (Int => Boolean) => String, p
                                                  //| rinter: String => Unit)Unit

  /**
   * Prints the contents of a set on the console.
   */
  def printSet(s: Set) {
    applyPrinter(s, toString, println)
  }                                               //> printSet: (s: Int => Boolean)Unit
  def mapreduce(s: Set // inputSet
  , mapperFn: SetMapper // mapper function
  , reducerFn: Set2Reducer // reducer function
  )(leftBound: Int, rightBound: Int): Set = {
    // applies reduceFn of Set2Reducer type
    def applyReducer(s: Set, value: Int, reduceFn: Set2Reducer): Set = {
      reduceFn(s, singletonSet(value))
    }
    // iterates through the range from a to bound
    def iter(a: Int, acc: Set): Set = {
      if (a > rightBound) acc
      else if (contains(s, a))
        iter(a + 1, applyReducer(acc, mapperFn(a), reducerFn))
      else iter(a + 1, acc)
    }
    iter(leftBound, emptySet())
  }                                               //> mapreduce: (s: Int => Boolean, mapperFn: Int => Int, reducerFn: (Int => Boo
                                                  //| lean, Int => Boolean) => Int => Boolean)(leftBound: Int, rightBound: Int)In
                                                  //| t => Boolean

  def map(s: Set, fn: SetMapper): Set = mapreduce(s, fn, union)(-bound, bound)
                                                  //> map: (s: Int => Boolean, fn: Int => Int)Int => Boolean

  val r1 = closedRange(-1, 5)                     //> r1  : Int => Boolean = <function1>
  val fn: SetMapper = x => 2 * x                  //> fn  : Int => Int = <function1>
  printSet(mapreduce(r1, fn, union)(-5, 5))       //> {-2,0,2,4,6,8,10}
  printSet(map(r1, fn))                           //> {-2,0,2,4,6,8,10}
}