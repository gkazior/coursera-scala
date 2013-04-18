package objsets

object TweetSetWs {
  val set1 = new Empty                            //> set1  : objsets.Empty = .
  val set2 = set1.incl(new Tweet("a", "a body", 20))
                                                  //> set2  : objsets.TweetSet = (.User(a,20).)
  val set3 = set2.incl(new Tweet("b", "b body", 20))
                                                  //> set3  : objsets.TweetSet = (.User(a,20)(.User(b,20).))

  val c = new Tweet("c", "c body", 7)             //> c  : objsets.Tweet = User(c,7)
  val d = new Tweet("d", "d body", 9)             //> d  : objsets.Tweet = User(d,9)
  val e = new Tweet("e", "d body", 29)            //> e  : objsets.Tweet = User(e,29)
  val x = new Tweet("x", "x body", 7)             //> x  : objsets.Tweet = User(x,7)
  val z = new Tweet("z", "z body", 13)            //> z  : objsets.Tweet = User(z,13)

  val set4c = set3.incl(c)                        //> set4c  : objsets.TweetSet = (.User(a,20)(.User(b,20)(.User(c,7).)))
  val set4d = set3.incl(d)                        //> set4d  : objsets.TweetSet = (.User(a,20)(.User(b,20)(.User(d,9).)))
  val set5 = set4c.incl(d)                        //> set5  : objsets.TweetSet = (.User(a,20)(.User(b,20)(.User(c,7)(.User(d,9).))
                                                  //| ))
  val sete = set1.incl(e)                         //> sete  : objsets.TweetSet = (.User(e,29).)
 
  set5.filter(x => x.retweets > 10)               //> res0: objsets.TweetSet = (.User(a,20)(.User(b,20).))

  val set_e = set1.incl(e)                        //> set_e  : objsets.TweetSet = (.User(e,29).)
  val set_x = set1.incl(x)                        //> set_x  : objsets.TweetSet = (.User(x,7).)
  val set_z = set1.incl(z)                        //> set_z  : objsets.TweetSet = (.User(z,13).)
  
  val set_xe = set_x.incl(e)                      //> set_xe  : objsets.TweetSet = ((.User(e,29).)User(x,7).)
  val set_ex = set_e.incl(x)                      //> set_ex  : objsets.TweetSet = (.User(e,29)(.User(x,7).))

  val set_xez = set_xe.incl(z)                    //> set_xez  : objsets.TweetSet = ((.User(e,29).)User(x,7)(.User(z,13).))
  
  val set_exz = set_ex.incl(z)                    //> set_exz  : objsets.TweetSet = (.User(e,29)(.User(x,7)(.User(z,13).)))
  
  
  
  set_xez.descendingByRetweet                     //> Visit:User(e,29)
                                                  //| Visit:User(x,7)
                                                  //| Visit:User(z,13)
                                                  //| res1: objsets.TweetList = (User(z,13) (User(x,7) (User(e,29) <end>)))
  set_exz.descendingByRetweet                     //> Visit:User(e,29)
                                                  //| Visit:User(x,7)
                                                  //| Visit:User(z,13)
                                                  //| res2: objsets.TweetList = (User(z,13) (User(x,7) (User(e,29) <end>)))

//  set5.descendingByRetweet

}