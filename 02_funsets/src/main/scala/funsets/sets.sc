package funsets

object sets {
  
  import FunSets._
  contains(singletonSet(1), 1)                    //> res0: Boolean = true

  contains(singletonSet(2), 1)                    //> res1: Boolean = false

  contains(union(singletonSet(2), singletonSet(1)) , 3)
                                                  //> res2: Boolean = false
  val s1 = singletonSet(1)                        //> s1  : Int => Boolean = <function1>
  val s2 = singletonSet(2)                        //> s2  : Int => Boolean = <function1>
  val s3 = singletonSet(3)                        //> s3  : Int => Boolean = <function1>
  
  val s123 = union(s1, union(s2, s3))             //> s123  : Int => Boolean = <function1>
  val range10 = closedRange(-10, 10)              //> range10  : Int => Boolean = <function1>
  
  printSet(s123)                                  //> {1,2,3}


  val bound = 1000                                //> bound  : Int = 1000

  forall(s123, x => x < 10)                       //> res3: Boolean = true
  forall(s123, x => x < 2)                        //> res4: Boolean = false
  forall(s123, x => x > 0 )                       //> res5: Boolean = true

  
  printSet(range10)                               //> {-10,-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9,10}
  forall(range10, x => x >= 0 )                   //> res6: Boolean = false
  forall(range10, x => x > -20 && x < 30)         //> res7: Boolean = true

  exists(s123, x => x > 2)                        //> res8: Boolean = true
  exists(s123, x => x > 5)                        //> res9: Boolean = false
  exists(range10, x => x > 5)                     //> res10: Boolean = true
  exists(range10, x => x > 15)                    //> res11: Boolean = false
  exists(range10, x => x < -5)                    //> res12: Boolean = true
  
}