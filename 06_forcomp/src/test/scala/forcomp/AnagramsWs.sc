package forcomp

import forcomp.Anagrams._

object AnagramsWs {
    val w = "Robert"                              //> w  : String = Robert
  val letterList = w.toList collect { case x => x.toLower } filter ((x) => x.isLetterOrDigit)
                                                  //> letterList  : List[Char] = List(r, o, b, e, r, t)
  
  (letterList groupBy (x=>x) map (x => (x._1, x._2.length))).toList
                                                  //> res0: List[(Char, Int)] = List((e,1), (t,1), (b,1), (r,2), (o,1))
  
  wordOccurrences("Robert")                       //> res1: forcomp.Anagrams.Occurrences = List((b,1), (e,1), (o,1), (r,2), (t,1))
                                                  //| 
  val a0 = wordOccurrences("wwa")                 //> a0  : forcomp.Anagrams.Occurrences = List((a,1), (w,2))
  val a1 = wordOccurrences("asdf")                //> a1  : forcomp.Anagrams.Occurrences = List((a,1), (d,1), (f,1), (s,1))
  val a2 = wordOccurrences("aaasdfawf")           //> a2  : forcomp.Anagrams.Occurrences = List((a,4), (d,1), (f,2), (s,1), (w,1))
                                                  //| 
  val a3 = wordOccurrences("aaAsdfA")             //> a3  : forcomp.Anagrams.Occurrences = List((a,4), (d,1), (f,1), (s,1))
  val a4 = wordOccurrences("aaAsdfA \n .,")       //> a4  : forcomp.Anagrams.Occurrences = List((a,4), (d,1), (f,1), (s,1))
  val a10 = wordOccurrences("wwwwaa")             //> a10  : forcomp.Anagrams.Occurrences = List((a,2), (w,4))

  subtractPair(('a', 3), List(('b', 4), ('a', 5)))//> res2: List[(Char, Int)] = List((b,4), (a,2))
  subtractPair(('a', 5), List(('b', 4), ('a', 5)))//> res3: List[(Char, Int)] = List((b,4))
  subtractPair(('a', 0), List(('b', 4), ('a', 5)))//> res4: List[(Char, Int)] = List((b,4), (a,5))
  // subtractPair(('a', 6), List(('b',4),('a',5)))

  subtract(a10, a0)                               //> res5: forcomp.Anagrams.Occurrences = List((a,1), (w,2))

  a0                                              //> res6: forcomp.Anagrams.Occurrences = List((a,1), (w,2))
  combinations(a0)                                //> res7: List[forcomp.Anagrams.Occurrences] = List(List((a,1)), List((a,1), (w,
                                                  //| 1)), List((w,2)), List(), List((w,1)), List((a,1), (w,2)))

  def sentenceAnagrams2(sentence: Sentence): List[Sentence] = {
    val dictOcr = dictionaryByOccurrences

    /*
     returns accumulated sentences for the sentence given by occurrences in ocrSentence
     tries the ocrToCheck and if not possible the rest - ocrToCheckRest
     @param ocrToCheck - ocr to check
     @param ocrToCheckRest - rest ocrs to check
     @param ocrSentence - ocr of the sentence to check
     @param currentSentence - accumulator for the checked sentence
     @param sentencesAcc - accumulator for the sentences list
    */
    def getSentences(ocrPossibleWords: List[Occurrences], ocrSentence: Occurrences, currentSentence: Sentence, sentencesAcc: List[Sentence], calls: Int): List[Sentence] = {
      ocrPossibleWords match {
        case Nil => sentencesAcc
        case ocrWord :: ocrTail => {

          //println("getSentence: ocrSentence:" + ocrSentence + " sentence:" + currentSentence + " possWrds:" + ocrPossibleWords)
          if (calls > 20) Nil else
            (ocrSentence, dictOcr.isDefinedAt(ocrWord)) match {
              case (Nil, _)   => { /*println("acc+=:" + currentSentence);*/ currentSentence :: sentencesAcc }
              case (_, false) => getSentences(ocrTail, ocrSentence, currentSentence, sentencesAcc, calls + 1)
              case (_, true) => {
                //println("Found: " + dictOcr(ocrWord))
                val restSentence = subtract(ocrSentence, ocrWord)
                val acc0 = getSentences(ocrTail, ocrSentence, currentSentence, sentencesAcc, calls + 1)
                dictOcr(ocrWord).foldLeft(acc0)(
                  (acc, word) => acc ++ getSentences(combinations(restSentence), restSentence, word :: currentSentence, sentencesAcc, calls + 1))

              }
            }
        }
      }
    }

    sentence match {
      case Nil => List(Nil)
      case _ => {
        val bigWord: Word = sentence.reduce(_ + _)
        val ocrSentence = wordOccurrences(bigWord)

        getSentences(combinations(ocrSentence), ocrSentence, Nil, Nil, 0)
      }
    }
  }                                               //> sentenceAnagrams2: (sentence: forcomp.Anagrams.Sentence)List[forcomp.Anagra
                                                  //| ms.Sentence]
  val dictionary = List("mama", "tata", "a")      //> dictionary  : List[String] = List(mama, tata, a)
  dictionary map ((x) => (x, wordOccurrences(x))) groupBy ((x) => x._2)
                                                  //> res8: scala.collection.immutable.Map[forcomp.Anagrams.Occurrences,List[(Str
                                                  //| ing, forcomp.Anagrams.Occurrences)]] = Map(List((a,1)) -> List((a,List((a,1
                                                  //| )))), List((a,2), (t,2)) -> List((tata,List((a,2), (t,2)))), List((a,2), (m
                                                  //| ,2)) -> List((mama,List((a,2), (m,2)))))
  
  

  //val wan = wordAnagrams("eat")
  val ym = sentenceAnagrams2(List("Yes", "man"))  //> ym  : List[forcomp.Anagrams.Sentence] = List()
  val an = sentenceAnagrams2(List("eat"))         //> an  : List[forcomp.Anagrams.Sentence] = List(List(ate), List(eat), List(tea
                                                  //| ))

}