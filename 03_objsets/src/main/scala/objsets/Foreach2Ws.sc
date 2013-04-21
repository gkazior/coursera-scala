package objsets

object Foreach2Ws extends TestData4Ws {

  val set5 = new Empty incl e incl c incl d       //> set5  : objsets.TweetSet = (User(e,29)L=(User(c,7)L=.R=(User(d,9)L=.R=.))R=.
                                                  //| )

  set5.foreach2[Option[Tweet]](x => true // which one to collect
  , (x, maxSoFar) => {
    println(x);
    if (maxSoFar.isEmpty) Some(x)
    else if (x.retweets < maxSoFar.get.retweets) Some(x)
    else maxSoFar
  } //
  , None)                                         //> User(e,29)
                                                  //| elemAcc:Some(User(e,29))
                                                  //| User(c,7)
                                                  //| elemAcc:Some(User(c,7))
                                                  //| User(d,9)
                                                  //| elemAcc:Some(User(c,7))
                                                  //| res0: Option[objsets.Tweet] = Some(User(c,7))

  set5.foreach2[TweetList](x => x.retweets < 20 // which one to collect
  , (x, currentList) => {
    println(x);
    currentList.putInOrder(x, _.retweets > _.retweets)
  } //
  , Nil)                                          //> elemAcc:<end>
                                                  //| User(c,7)
                                                  //| elemAcc:(User(c,7) <end>)
                                                  //| User(d,9)
                                                  //| elemAcc:(User(d,9) (User(c,7) <end>))
                                                  //| res1: objsets.TweetList = (User(d,9) (User(c,7) <end>))

}