package forcomp

import forcomp.Anagrams._

object AnagramsWs {

  wordOccurrences("Robert")                       //> res0: forcomp.Anagrams.Occurrences = List((b,1), (e,1), (o,1), (r,2), (t,1))
                                                  //| 
  val a1 = wordOccurrences("asdf")                //> a1  : forcomp.Anagrams.Occurrences = List((a,1), (d,1), (f,1), (s,1))
  val a2 = wordOccurrences("aaasdfawf")           //> a2  : forcomp.Anagrams.Occurrences = List((a,4), (d,1), (f,2), (s,1), (w,1))
                                                  //| 
  val a3 = wordOccurrences("aaAsdfA")             //> a3  : forcomp.Anagrams.Occurrences = List((a,4), (d,1), (f,1), (s,1))
  val a4 = wordOccurrences("aaAsdfA \n .,")       //> a4  : forcomp.Anagrams.Occurrences = List((a,4), (d,1), (f,1), (s,1))
  /** The `dictionaryByOccurrences` is a `Map` from different occurrences to a sequence of all
   *  the words that have that occurrence count.
   *  This map serves as an easy way to obtain all the anagrams of a word given its occurrence list.
   *
   *  For example, the word "eat" has the following character occurrence list:
   *
   *     `List(('a', 1), ('e', 1), ('t', 1))`
   *
   *  Incidentally, so do the words "ate" and "tea".
   *
   *  This means that the `dictionaryByOccurrences` map will contain an entry:
   *
   *    List(('a', 1), ('e', 1), ('t', 1)) -> Seq("ate", "eat", "tea")
   *
   */
  
  def addNewOccurence(pair: (Occurrences, Word), theMap: Map[Occurrences,List[Word]]): Map[Occurrences,List[Word]] = {
      val occurence = pair._1
      val newList = pair._2 :: (theMap get occurence getOrElse(Nil))
      theMap + (occurence -> newList)
  }                                               //> addNewOccurence: (pair: (forcomp.Anagrams.Occurrences, forcomp.Anagrams.Wor
                                                  //| d), theMap: Map[forcomp.Anagrams.Occurrences,List[forcomp.Anagrams.Word]])M
                                                  //| ap[forcomp.Anagrams.Occurrences,List[forcomp.Anagrams.Word]]

  subtractPair(('a', 3), List(('b',4),('a',5)))   //> res1: List[(Char, Int)] = List((b,4), (a,2))
  subtractPair(('a', 5), List(('b',4),('a',5)))   //> res2: List[(Char, Int)] = List((b,4))
  subtractPair(('a', 0), List(('b',4),('a',5)))   //> res3: List[(Char, Int)] = List((b,4), (a,5))
 // subtractPair(('a', 6), List(('b',4),('a',5)))

    val lard = List(('a', 1), ('d', 1), ('l', 1), ('r', 1))
                                                  //> lard  : List[(Char, Int)] = List((a,1), (d,1), (l,1), (r,1))
    val r = List(('r', 1))                        //> r  : List[(Char, Int)] = List((r,1))
    val lad = List(('a', 1), ('d', 1), ('l', 1))  //> lad  : List[(Char, Int)] = List((a,1), (d,1), (l,1))
    subtract(lard, r)                             //> res4: forcomp.Anagrams.Occurrences = List((a,1), (d,1), (l,1))

}