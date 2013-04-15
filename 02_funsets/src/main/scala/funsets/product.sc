package funsets

object product {
  def product(f: Int => Int)(a: Int, b: Int): Int = {
    if (a > b) 1
    else f(a) * product(f)(a + 1, b)
  }                                               //> product: (f: Int => Int)(a: Int, b: Int)Int

  product(x => x)(1, 5)                           //> res0: Int = 120

  def factorial(n: Int): Int = product(x => x)(1, n)
                                                  //> factorial: (n: Int)Int

  factorial(5)                                    //> res1: Int = 120

  def mapReduce(mapFn: Int => Int, reduceFn: (Int, Int) => Int, zero: Int)(from: Int, to: Int): Int = {
    if (from > to) zero
    else reduceFn(mapFn(from), mapReduce(mapFn, reduceFn, zero)(from + 1, to))
  }                                               //> mapReduce: (mapFn: Int => Int, reduceFn: (Int, Int) => Int, zero: Int)(from:
                                                  //|  Int, to: Int)Int

  def factorial2(n: Int) = mapReduce(x => x, (x, y) => x * y, 1)(1,n)
                                                  //> factorial2: (n: Int)Int
  
  factorial2(5)                                   //> res2: Int = 120
}