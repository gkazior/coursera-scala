package objsets

import common._
import TweetReader._
import scala.language.postfixOps

/**
 * A class to represent tweets.
 */
class Tweet(val user: String, val text: String, val retweets: Int) {
  //  override def toString: String =
  //    "User: " + user + "\n" +
  //      "Text: " + text + " [" + retweets + "]"
  override def toString: String =
    "User(" + user + "," + retweets + ")"
}

/**
 * This represents a set of objects of type `Tweet` in the form of a binary search
 * tree. Every branch in the tree has two children (two `TweetSet`s). There is an
 * invariant which always holds: for every branch `b`, all elements in the left
 * subtree are smaller than the tweet at `b`. The elements in the right subtree are
 * larger.
 *
 * Note that the above structure requires us to be able to compare two tweets (we
 * need to be able to say which of two tweets is larger, or if they are equal). In
 * this implementation, the equality / order of tweets is based on the tweet's text
 * (see `def incl`). Hence, a `TweetSet` could not contain two tweets with the same
 * text from different users.
 *
 *
 * The advantage of representing sets as binary search trees is that the elements
 * of the set can be found quickly. If you want to learn more you can take a look
 * at the Wikipedia page [1], but this is not necessary in order to solve this
 * assignment.
 *
 * [1] http://en.wikipedia.org/wiki/Binary_search_tree
 */
abstract class TweetSet {

  /**
   * This method takes a predicate and returns a subset of all the elements
   * in the original set for which the predicate is true.
   *
   * Question: Can we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def filter(p: Tweet => Boolean): TweetSet = filterAcc(p, new Empty)

  /**
   * This is a helper method for `filter` that propagates the accumulated tweets.
   */
  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet

  /**
   * Returns a new `TweetSet` that is the union of `TweetSet`s `this` and `that`.
   *
   * Question: Should we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def union(that: TweetSet): TweetSet = {
    filterAcc(!that.contains(_), that)
  }

  /**
   * Returns true when the TweetSet is empty.
   * We have the Empty case class but:
   *  - do not want to depend here on implementation classes
   *  - may in fact implement it using theTweetSet.union(Empty) is the same as Empty or foreach() get no call
   *  - in case of different implementation we have to change the code
   *  In any case seems reasonable to introduce the method here
   */
  def isEmpty(): Boolean

  /**
   * Returns the tweet from this set which has the greatest retweet count.
   *
   * Calling `mostRetweeted` on an empty set should throw an exception of
   * type `java.util.NoSuchElementException`.
   *
   * Question: Should we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def mostRetweeted: Tweet = {
    if (isEmpty) throw new java.util.NoSuchElementException

    foreach2[Option[Tweet]](
      x => true // pick everyone
      , (x, maxSoFar) => {
        if (maxSoFar.isEmpty) Some(x)
        else if (x.retweets < maxSoFar.get.retweets) Some(x)
        else maxSoFar
      } //
      , None)
      .get
  }

  /**
   * Returns a list containing all tweets of this set, sorted by retweet count
   * in descending order. In other words, the head of the resulting list should
   * have the highest retweet count.
   *
   * Hint: the method `remove` on TweetSet will be very useful.
   * Question: Should we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def descendingByRetweet: TweetList;

  /**
   * The following methods are already implemented
   */

  /**
   * Returns a new `TweetSet` which contains all elements of this set, and the
   * the new element `tweet` in case it does not already exist in this set.
   *
   * If `this.contains(tweet)`, the current set is returned.
   */
  def incl(tweet: Tweet): TweetSet

  /**
   * Returns a new `TweetSet` which excludes `tweet`.
   */
  def remove(tweet: Tweet): TweetSet

  /**
   * Tests if `tweet` exists in this `TweetSet`.
   */
  def contains(tweet: Tweet): Boolean

  /**
   * This method takes a function and applies it to every element in the set.
   */
  def foreach(f: Tweet => Unit): Unit

  /**
   * like for each but uses
   * @param collectPred to decide if the elem of the set should be collected
   * @param collectFn which collects elements using acc
   * @param acc accumulator for collectFn
   * @returns collected value
   */
  def foreach2[T](collectPred: Tweet => Boolean, collectFn: (Tweet, T) => T, acc: T): T

}

class Empty extends TweetSet {

  /**
   * No matter what the function p is, filtering will always return the Empty set which is this
   */
  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet = acc

  /**
   * The following methods are already implemented
   */

  def contains(tweet: Tweet): Boolean = false

  def incl(tweet: Tweet): TweetSet = new NonEmpty(tweet, new Empty, new Empty)

  def remove(tweet: Tweet): TweetSet = this

  def isEmpty() = true

  def foreach(f: Tweet => Unit): Unit = ()

  def foreach2[T](collectPred: Tweet => Boolean, collectFn: (Tweet, T) => T, acc: T): T = acc

  def descendingByRetweet: TweetList = Nil

  override def toString() = "."
}

class NonEmpty(elem: Tweet, left: TweetSet, right: TweetSet) extends TweetSet {

  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet = {
    val elemAcc = if (p(elem)) acc.incl(elem) else acc
    right.filterAcc(p, left.filterAcc(p, elemAcc))
  }

  /**
   * The following methods are already implemented
   */

  def contains(x: Tweet): Boolean =
    if (x.text < elem.text) left.contains(x)
    else if (elem.text < x.text) right.contains(x)
    else true

  def incl(x: Tweet): TweetSet = {
    if (x.text < elem.text) new NonEmpty(elem, left.incl(x), right)
    else if (x.text > elem.text) new NonEmpty(elem, left, right.incl(x))
    else this
  }

  def remove(tw: Tweet): TweetSet =
    if (tw.text < elem.text) new NonEmpty(elem, left.remove(tw), right)
    else if (elem.text < tw.text) new NonEmpty(elem, left, right.remove(tw))
    else left.union(right)

  def foreach(f: Tweet => Unit): Unit = {
    f(elem)
    left.foreach(f)
    right.foreach(f)
  }

  def isEmpty() = false

  def foreach2[T](collectPred: Tweet => Boolean, collectFn: (Tweet, T) => T, acc: T): T = {
    val elemAcc = if (collectPred(elem)) collectFn(elem, acc) else acc
    right.foreach2(collectPred, collectFn, left.foreach2(collectPred, collectFn, elemAcc))
  }

  private def greaterByRetweet(x: Tweet, y: Tweet): Boolean = x.retweets > y.retweets

  def descendingByRetweet: TweetList = {
    foreach2[TweetList]( // result will be TweetList
      x => true // pick every item
      , (x, acc) => { acc.putInOrder(x, greaterByRetweet) } // Accumulate means putInOder
      , Nil) // Start with empty list
  }

  override def toString() = "(" + elem.toString + "L=" + left.toString() + "R=" + right.toString() + ")"
}

trait TweetList {
  def head: Tweet
  def tail: TweetList
  def isEmpty: Boolean
  def foreach(f: Tweet => Unit): Unit =
    if (!isEmpty) {
      f(head)
      tail.foreach(f)
    }
  def push(tweet: Tweet): TweetList = {
    new Cons(tweet, this)
  }

  /**
   * Puts the tween on the list just after the tweet which is smaller according to orderFn.
   * When all the element are put using this method then the list is ordered.
   * orderFn returns true when the first tweet is smaller then the second
   */
  def putInOrder(tweet: Tweet, orderFn: (Tweet, Tweet) => Boolean): TweetList = {
    if (isEmpty) new Cons(tweet, Nil)
    else if (orderFn(tweet, head)) new Cons(tweet, this)
    else new Cons(head, tail.putInOrder(tweet, orderFn))
  }
}

object Nil extends TweetList {
  def head = throw new java.util.NoSuchElementException("head of EmptyList")
  def tail = throw new java.util.NoSuchElementException("tail of EmptyList")
  def isEmpty = true
  override def toString() = "<end>"
}

class Cons(val head: Tweet, val tail: TweetList) extends TweetList {
  def isEmpty = false
  override def toString() = "(" + head.toString + " " + tail.toString() + ")"
}

object GoogleVsApple {
  val google = List("android", "Android", "galaxy", "Galaxy", "nexus", "Nexus")
  val apple = List("ios", "iOS", "iphone", "iPhone", "ipad", "iPad")

  TweetReader.allTweets.filter(x => google.exists(keyword => x.text.contains(keyword)))
  // everything from TweetData which contains keywords from google and apple?
  lazy val googleTweets: TweetSet = allTweets filter (x => google exists (keyword => x.text.contains(keyword)))
  lazy val appleTweets: TweetSet = allTweets filter (x => apple exists (keyword => x.text.contains(keyword)))

  /**
   * A list of all tweets mentioning a keyword from either apple or google,
   * sorted by the number of retweets.
   */
  lazy val trending: TweetList = googleTweets union appleTweets descendingByRetweet
}

object Main extends App {
  // Print the trending tweets
  GoogleVsApple.trending foreach println
}
