# bencodetest
web crawler test

This project is using Spring boot, MongoDB, Java 7+, crawler4j, AngularJS, Jasmine, Spring webservice, Maven, JUnist, EasyMock.

Please import as a Maven project from Eclipse.

Please make sure have MongoDB installed in your env.

Please change the MongoDB in application.properties under resources folder.

MyCrawler is the crawler class. 

CRAWLSTORAGEFOLDER in application.properties should be configured as well and the path should be created in your env.

After run the maven job, you can simply run the App.java as java application, and launch a browser by url http://localhost:8080/ you will see the application home page.

On the top there are 2 buttons Crawling and Searching which are the 2 APIs I implemented.

http://localhost:8080/test/test.html is the unit test page for AngularJS
