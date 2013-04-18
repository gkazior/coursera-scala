package objsets


object TweetSetWs {

  val set1 = new Empty                            //> set1  : objsets.Empty = .
  val set2 = set1.incl(new Tweet("a", "a body", 20))
                                                  //> set2  : objsets.TweetSet = (User(a,20)L=.R=.)
  val set3 = set2.incl(new Tweet("b", "b body", 20))
                                                  //> set3  : objsets.TweetSet = (User(a,20)L=.R=(User(b,20)L=.R=.))

  val c = new Tweet("c", "c body", 7)             //> c  : objsets.Tweet = User(c,7)
  val d = new Tweet("d", "d body", 9)             //> d  : objsets.Tweet = User(d,9)
  val e = new Tweet("e", "d body", 29)            //> e  : objsets.Tweet = User(e,29)
  val x = new Tweet("x", "x body", 7)             //> x  : objsets.Tweet = User(x,7)
  val z = new Tweet("z", "z body", 13)            //> z  : objsets.Tweet = User(z,13)

  val set4c = set3.incl(c)                        //> set4c  : objsets.TweetSet = (User(a,20)L=.R=(User(b,20)L=.R=(User(c,7)L=.R=.
                                                  //| )))
  val set4d = set3.incl(d)                        //> set4d  : objsets.TweetSet = (User(a,20)L=.R=(User(b,20)L=.R=(User(d,9)L=.R=.
                                                  //| )))
  val set5 = set4c.incl(d)                        //> set5  : objsets.TweetSet = (User(a,20)L=.R=(User(b,20)L=.R=(User(c,7)L=.R=(U
                                                  //| ser(d,9)L=.R=.))))
  val sete = set1.incl(e)                         //> sete  : objsets.TweetSet = (User(e,29)L=.R=.)
 
  set5.filter(x => x.retweets > 10)               //> res0: objsets.TweetSet = (User(a,20)L=.R=(User(b,20)L=.R=.))
  val set_e = set1.incl(e)                        //> set_e  : objsets.TweetSet = (User(e,29)L=.R=.)
  val set_x = set1.incl(x)                        //> set_x  : objsets.TweetSet = (User(x,7)L=.R=.)
  val set_z = set1.incl(z)                        //> set_z  : objsets.TweetSet = (User(z,13)L=.R=.)
  
  val set_xe = set_x.incl(e)                      //> set_xe  : objsets.TweetSet = (User(x,7)L=(User(e,29)L=.R=.)R=.)
  val set_ex = set_e.incl(x)                      //> set_ex  : objsets.TweetSet = (User(e,29)L=.R=(User(x,7)L=.R=.))

  val set_xez = set_xe.incl(z)                    //> set_xez  : objsets.TweetSet = (User(x,7)L=(User(e,29)L=.R=.)R=(User(z,13)L=.
                                                  //| R=.))
  
  val set_exz = set_ex.incl(z)                    //> set_exz  : objsets.TweetSet = (User(e,29)L=.R=(User(x,7)L=.R=(User(z,13)L=.R
                                                  //| =.)))
  
 

  
  set_xez.descendingByRetweet                     //> res1: objsets.TweetList = (User(e,29) (User(z,13) (User(x,7) <end>)))
  set_exz.descendingByRetweet                     //> res2: objsets.TweetList = (User(e,29) (User(z,13) (User(x,7) <end>)))

  set5.descendingByRetweet                        //> res3: objsets.TweetList = (User(a,20) (User(b,20) (User(d,9) (User(c,7) <end
                                                  //| >))))

}