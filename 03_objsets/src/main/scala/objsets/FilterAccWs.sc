package objsets

object FilterAccWs extends TestData4Ws {

  val set5 = new Empty incl e incl c incl d       //> set5  : objsets.TweetSet = (User(e,29)L=(User(c,7)L=.R=(User(d,9)L=.R=.))R=.
                                                  //| )

  set5.filterAcc(_.retweets < 30, new Empty)      //> res0: objsets.TweetSet = (User(e,29)L=(User(c,7)L=.R=(User(d,9)L=.R=.))R=.)
                                                  //| 
  set5.filterAcc(_.retweets < 10, new Empty)      //> res1: objsets.TweetSet = (User(c,7)L=.R=(User(d,9)L=.R=.))

}