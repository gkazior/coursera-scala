package objsets

object GoogleVsAppleWs {
  val google = GoogleVsApple.google               //> google  : List[String] = List(android, Android, galaxy, Galaxy, nexus, Nexus)
                                                  //| 
  GoogleVsApple.googleTweets                      //> res0: objsets.TweetSet = (User(mashable,78)L=.R=(User(mashable,42)L=.R=(User
                                                  //| (CNET,36)L=.R=(User(engadget,12)L=.R=(User(gadgetlab,22)L=.R=(User(CNET,49)L
                                                  //| =.R=(User(engadget,23)L=(User(engadget,13)L=.R=.)R=(User(engadget,20)L=.R=(U
                                                  //| ser(gadgetlab,79)L=.R=(User(gadgetlab,11)L=.R=(User(gizmodo,33)L=(User(CNET,
                                                  //| 25)L=.R=(User(CNET,39)L=.R=(User(CNET,48)L=.R=(User(CNET,131)L=.R=(User(enga
                                                  //| dget,8)L=.R=(User(CNET,15)L=.R=.))))))R=(User(gizmodo,120)L=.R=(User(TechCru
                                                  //| nch,18)L=.R=(User(engadget,25)L=(User(engadget,12)L=.R=.)R=(User(gizmodo,22)
                                                  //| L=.R=(User(engadget,32)L=.R=(User(CNET,19)L=(User(CNET,12)L=.R=.)R=(User(Tec
                                                  //| hCrunch,24)L=(User(engadget,21)L=(User(engadget,9)L=.R=(User(CNET,14)L=.R=.)
                                                  //| )R=(User(mashable,85)L=.R=.))R=(User(gizmodo,101)L=.R=(User(gizmodo,290)L=(U
                                                  //| ser(TechCrunch,24)L=(User(CNET,25)L=.R=.)R=.)R=(User(mashable,49)L=.R=(User(
                                                  //| CNET,10)L=.R=(User(gizmodo,108)L=.R=.)))))))))))))))))))))))
  val googleTweets = TweetReader.allTweets.filter(x => google.exists(keyword => x.text.contains(keyword)))
                                                  //> googleTweets  : objsets.TweetSet = (User(mashable,78)L=.R=(User(mashable,42)
                                                  //| L=.R=(User(CNET,36)L=.R=(User(engadget,12)L=.R=(User(gadgetlab,22)L=.R=(User
                                                  //| (CNET,49)L=.R=(User(engadget,23)L=(User(engadget,13)L=.R=.)R=(User(engadget,
                                                  //| 20)L=.R=(User(gadgetlab,79)L=.R=(User(gadgetlab,11)L=.R=(User(gizmodo,33)L=(
                                                  //| User(CNET,25)L=.R=(User(CNET,39)L=.R=(User(CNET,48)L=.R=(User(CNET,131)L=.R=
                                                  //| (User(engadget,8)L=.R=(User(CNET,15)L=.R=.))))))R=(User(gizmodo,120)L=.R=(Us
                                                  //| er(TechCrunch,18)L=.R=(User(engadget,25)L=(User(engadget,12)L=.R=.)R=(User(g
                                                  //| izmodo,22)L=.R=(User(engadget,32)L=.R=(User(CNET,19)L=(User(CNET,12)L=.R=.)R
                                                  //| =(User(TechCrunch,24)L=(User(engadget,21)L=(User(engadget,9)L=.R=(User(CNET,
                                                  //| 14)L=.R=.))R=(User(mashable,85)L=.R=.))R=(User(gizmodo,101)L=.R=(User(gizmod
                                                  //| o,290)L=(User(TechCrunch,24)L=(User(CNET,25)L=.R=.)R=.)R=(User(mashable,49)L
                                                  //| =.R=(User(CNET,10)L=.R=(User(gizmodo,108)L=.R=.)))))))))))))))))))))))

  googleTweets foreach (x => println(x.text))     //> 5 Mobile Photographers Capturing the World With #Android http://t.co/786NneB
                                                  //| t
                                                  //| 7 Free #Android Apps for Killing Time in Lines http://t.co/eKu5hhsh
                                                  //| A mathematician accurately predicted when Android's app store would hit 25 b
                                                  //| illion downloads http://t.co/VFLBJ0z3
                                                  //| AT&amp;T 4G LTE adds Galaxy Note 2, Galay Tab 2 10.1, Galaxy Express and Gal
                                                  //| axy Rugby Pro to lineup -  http://t.co/uvBFFMQO
                                                  //| BlueStacks and AMD Bring 500,000 Android Apps to Windows 8: http://t.co/Gsku
                                                  //| XhRo by @alexandra_chang
                                                  //| Camera contest:  Apple iPhone 5 vs. Samsung Galaxy S3 vs. HTC One X http://t
                                                  //| .co/PmbhNgrd
                                                  //| Cubify lets you skin, 3D print your own personal Android -  http://t.co/S6ni
                                                  //| mh5R
                                                  //| Court of Appeals for the Federal Circuit tells Judge Koh to revisit Galaxy T
                                                  //| ab 10.1 injunction -  http://t.co/iIOCcwDW
                                                  //| FAVI's $50 Streaming Stick adds apps, streaming services to any HDTV with An
                                                  //| droid 4.1 Jelly Bean -  http://t.co/dL9geyBU
                                                  //| First iPhone 5 B
                                                  //| Output exceeds cutoff limit.

}