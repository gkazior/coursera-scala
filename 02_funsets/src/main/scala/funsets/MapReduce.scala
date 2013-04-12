package funsets

import funsets.FunSets._

object MapReduce {

    
  def mapreduce(s: Set // inputSet
  , mapperFn: SetMapper // mapper function
  , reducerFn: Set2Reducer // reducer function
  ): Set = {
    // applies reduceFn of Set2Reducer type
    def applyReducer(s: Set, value: Int, reduceFn: Set2Reducer): Set = {
      reduceFn(s, singletonSet(value))
    }
    // iterates through the range from a to bound
    def iter(a: Int, acc: Set): Set = {
      if (a > bound) acc
      else if (contains(s, a))
        iter(a + 1, applyReducer(acc, mapperFn(a), reducerFn))
      else iter(a + 1, acc)
    }
    iter(-bound, emptySet())
  }

}