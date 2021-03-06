package forcomp

import common._
import scala.annotation.tailrec

object Anagrams {

  /** A word is simply a `String`. */
  type Word = String

  /** A sentence is a `List` of words. */
  type Sentence = List[Word]

  /**
   * `Occurrences` is a `List` of pairs of characters and positive integers saying
   *  how often the character appears.
   *  This list is sorted alphabetically w.r.t. to the character in each pair.
   *  All characters in the occurrence list are lowercase.
   *
   *  Any list of pairs of lowercase characters and their frequency which is not sorted
   *  is **not** an occurrence list.
   *
   *  Note: If the frequency of some character is zero, then that character should not be
   *  in the list.
   */
  type Occurrences = List[(Char, Int)]

  /**
   * The dictionary is simply a sequence of words.
   *  It is predefined and obtained as a sequence using the utility method `loadDictionary`.
   */
  val dictionary: List[Word] = loadDictionary

  def subtractPair(pair: (Char, Int), list: List[(Char, Int)]): List[(Char, Int)] = {
    val TheChar = pair._1
    val TheTimes = pair._2
    list match {
      case Nil                         => Nil
      case (TheChar, TheTimes) :: tail => tail
      case (TheChar, times) :: tail    => { val newTimes = times - TheTimes; if (newTimes <= 0) assert(false); (TheChar, newTimes) :: tail }
      case (char, times) :: tail       => (char, times) :: subtractPair(pair, tail)
    }
  }

  /**
   * Converts the word into its character occurence list.
   *
   *  Note: the uppercase and lowercase version of the character are treated as the
   *  same character, and are represented as a lowercase character in the occurrence list.
   */
  def wordOccurrences(w: Word): Occurrences = {
    def lessCharFirst(pairA: (Char, Int), pairB: (Char, Int)) = pairA._1 < pairB._1
    
    val letterList = w.toLowerCase().toList filter (_.isLetterOrDigit)
    
   (letterList groupBy (x=>x) map (x => (x._1, x._2.length))).toList.sortWith(lessCharFirst)

  }

  /** Converts a sentence into its character occurrence list. */
  def sentenceOccurrences(s: Sentence): Occurrences = {
    //val bigWord: Word = s.foldLeft("") ((acc, word) => acc + word)
    val bigWord: Word = s.foldLeft("")(_ + _)
    wordOccurrences(bigWord)
  }


  /**
   * The `dictionaryByOccurrences` is a `Map` from different occurrences to a sequence of all
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
  lazy val dictionaryByOccurrences: Map[Occurrences, List[Word]] = {
    val acc0 = Map[Occurrences, List[Word]]()
    dictionary map (x => (x, wordOccurrences(x))) groupBy (x => x._2) map (x => (x._1 -> x._2.unzip._1))
  }

  /** Returns all the anagrams of a given word. */
  def wordAnagrams(word: Word): List[Word] = {
    dictionaryByOccurrences.getOrElse(wordOccurrences(word), Nil)
  }

  /**
   * Returns the list of all subsets of the occurrence list.
   *  This includes the occurrence itself, i.e. `List(('k', 1), ('o', 1))`
   *  is a subset of `List(('k', 1), ('o', 1))`.
   *  It also include the empty subset `List()`.
   *
   *  Example: the subsets of the occurrence list `List(('a', 2), ('b', 2))` are:
   *
   *    List(
   *      List(),
   *      List(('a', 1)),
   *      List(('a', 2)),
   *      List(('b', 1)),
   *      List(('a', 1), ('b', 1)),
   *      List(('a', 2), ('b', 1)),
   *      List(('b', 2)),
   *      List(('a', 1), ('b', 2)),
   *      List(('a', 2), ('b', 2))
   *    )
   *
   *  Note that the order of the occurrence list subsets does not matter -- the subsets
   *  in the example above could have been displayed in some other order.
   */
  def combinations(occurrences: Occurrences): List[Occurrences] = {
    def loopOverPair(pair: (Char, Int), theList: Occurrences): Seq[Occurrences] = {
      val (char, times) = pair
      for (i <- 0 to times) yield subtractPair((char, i), theList)

    }
    def combinationsHelper(pair: (Char, Int), leftPairs: Occurrences, acc: Set[Occurrences]): Set[Occurrences] = {
      val currentComb = acc ++ (for (item <- acc) yield loopOverPair(pair, item)).flatten
      leftPairs match {
        case Nil          => currentComb
        case head :: tail => combinationsHelper(head, tail, currentComb)
      }

    }
    occurrences match {
      case Nil          => List(Nil)
      case head :: tail => combinationsHelper(head, tail, Set(occurrences)).toList
    }
  }
  /**
   * Subtracts occurrence list `y` from occurrence list `x`.
   *
   *  The precondition is that the occurrence list `y` is a subset of
   *  the occurrence list `x` -- any character appearing in `y` must
   *  appear in `x`, and its frequency in `y` must be smaller or equal
   *  than its frequency in `x`.
   *
   *  Note: the resulting value is an occurrence - meaning it is sorted
   *  and has no zero-entries.
   */
  def subtract(x: Occurrences, y: Occurrences): Occurrences = {
    y.foldLeft(x)((acc, pair) => subtractPair(pair, acc))
  }

  /**
   * Returns a list of all anagram sentences of the given sentence.
   *
   *  An anagram of a sentence is formed by taking the occurrences of all the characters of
   *  all the words in the sentence, and producing all possible combinations of words with those characters,
   *  such that the words have to be from the dictionary.
   *
   *  The number of words in the sentence and its anagrams does not have to correspond.
   *  For example, the sentence `List("I", "love", "you")` is an anagram of the sentence `List("You", "olive")`.
   *
   *  Also, two sentences with the same words but in a different order are considered two different anagrams.
   *  For example, sentences `List("You", "olive")` and `List("olive", "you")` are different anagrams of
   *  `List("I", "love", "you")`.
   *
   *  Here is a full example of a sentence `List("Yes", "man")` and its anagrams for our dictionary:
   *
   *    List(
   *      List(en, as, my),
   *      List(en, my, as),
   *      List(man, yes),
   *      List(men, say),
   *      List(as, en, my),
   *      List(as, my, en),
   *      List(sane, my),
   *      List(Sean, my),
   *      List(my, en, as),
   *      List(my, as, en),
   *      List(my, sane),
   *      List(my, Sean),
   *      List(say, men),
   *      List(yes, man)
   *    )
   *
   *  The different sentences do not have to be output in the order shown above - any order is fine as long as
   *  all the anagrams are there. Every returned word has to exist in the dictionary.
   *
   *  Note: in case that the words of the sentence are in the dictionary, then the sentence is the anagram of itself,
   *  so it has to be returned in this list.
   *
   *  Note: There is only one anagram of an empty sentence.
   */
  def sentenceAnagrams(sentence: Sentence): List[Sentence] = {
    val dictOcr = dictionaryByOccurrences

    /*
     returns accumulated sentences for the sentence given by occurrences in ocrSentence
     tries the ocrToCheck and if not possible the rest - ocrToCheckRest
     @param ocrPossibleWords - ocrs to check
     @param ocrSentence - ocr of the sentence to check
     @param currentSentence - accumulator for the checked sentence
     @param sentencesAcc - accumulator for the sentences list
    */
    def getSentences(ocrPossibleWords: List[Occurrences], ocrSentence: Occurrences, currentSentence: Sentence, sentencesAcc: List[Sentence], calls: Int): List[Sentence] = {
      ocrPossibleWords match {
        case Nil => sentencesAcc
        case ocrWord :: ocrTail => {
          ocrSentence match {
            case Nil => currentSentence :: sentencesAcc
            case _ => {
              val acc0 = getSentences(ocrTail, ocrSentence, currentSentence, sentencesAcc, calls + 1)
              if (dictOcr.isDefinedAt(ocrWord)) {
                val restSentence = subtract(ocrSentence, ocrWord)
                dictOcr(ocrWord).foldLeft(acc0)(
                  (acc, word) => acc ++ getSentences(combinations(restSentence), restSentence, word :: currentSentence, sentencesAcc, calls + 1))
              } else
                acc0
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
  }

}
