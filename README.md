hsqldb_manage_demo
==================


执行：mvn test -Dtest=HSQLDBTestcase
会插入一万条数据，并Block住。


---------------------------------------------------
启动hsqlDB客户端， java -jar C:\Users\<username>\.m2\repository\org\hsqldb\hsqldb\2.3.1\hsqldb-2.3.1.jar

在GUI里按下面参数连接
0, Type: HSQL Database Engine Server
1, URL: jdbc:hsqldb:hsql://localhost/xdb
2, User: SA


执行SQL： 
SELECT * FROM "PUBLIC"."TESTTABLE"
可查看到结果。
--------------------------------------
数据结果是在hsqldbData/testdb目录下， 不过，还没看到真实的数据。只看到testdb.log文件， 里面记录着执行的SQL。

这样，最大的问题是HSQLDB关掉后， 不能在GUI上正常地看到Table里的数据。 想能只能肉眼去解析testdb.log文件。
