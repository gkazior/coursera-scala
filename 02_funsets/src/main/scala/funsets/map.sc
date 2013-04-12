package funsets

object map {

  import FunSets._
  import MapReduce._

  val r123 = closedRange(1, 3)                    //> r123  : Int => Boolean = <function1>
  val range10 = closedRange(1, 10)                //> range10  : Int => Boolean = <function1>
  val lBound = closedRange(-bound - 1, -bound + 2)//> lBound  : Int => Boolean = <function1>
  val rBound = closedRange(bound - 2, bound + 2)  //> rBound  : Int => Boolean = <function1>
  val rWithZero = closedRange(-1, 1)              //> rWithZero  : Int => Boolean = <function1>

  def map(s: Set, f: Int => Int): Set = {
    def iter(a: Int, acc: Set): Set = {
      if (a > bound) acc
      else if (contains(s, a))
        iter(a + 1, union(singletonSet(f(a)), acc))
      else iter(a + 1, acc)
    }
    iter(-bound, emptySet())
  }                                               //> map: (s: Int => Boolean, f: Int => Int)Int => Boolean

  def map2(s: Set, f: SetMapper): Set = mapreduce(s, f, union)
                                                  //> map2: (s: Int => Boolean, f: Int => Int)Int => Boolean

  checkPredicate(closedRange(2, 5), x => x < 6, true)
                                                  //> res0: Boolean = true

  printSet(r123)                                  //> {1,2,3}
  printSet(map(r123, x => x * 2))                 //> {2,4,6}
  printSet(map2(r123, x => x * 2))                //> {2,4,6}

  printSet(range10)                               //> {1,2,3,4,5,6,7,8,9,10}
  printSet(map(range10, x => x * 2))              //> {2,4,6,8,10,12,14,16,18,20}
  printSet(map2(range10, x => x * 2))             //> {2,4,6,8,10,12,14,16,18,20}

  printSet(rWithZero)                             //> {-1,0,1}
  printSet(map(rWithZero, x => x * 2))            //> {-2,0,2}
  printSet(map2(rWithZero, x => x * 2))           //> {-2,0,2}

  printSet2(lBound)                               //> {-1001,-1000,-999,-998}
  printSet2(map(lBound, x => x * 2))              //> {-2000,-1998,-1996}
  printSet2(map2(lBound, x => x * 2))             //> {-2000,-1998,-1996}

  printSet2(rBound)                               //> {998,999,1000,1001,1002}
  printSet2(map(rBound, x => x * 2))              //> {1996,1998,2000}
  printSet2(map2(rBound, x => x * 2))             //> {1996,1998,2000}

}