import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.hsqldb.Server;

/**
 * Created by wangxc on 2014/5/12.
 */
public class HSQLDBTestcase {
    Server hsqlServer = null;
    Connection connection = null;

    @Before
    public void init() throws ClassNotFoundException, SQLException {
        hsqlServer = new org.hsqldb.Server();

        // HSQLDB prints out a lot of informations when
        // starting and closing, which we don't need now.
        // Normally you should point the setLogWriter
        // to some Writer object that could store the logs.
        hsqlServer.setLogWriter(null);
        hsqlServer.setSilent(true);

        // The actual database will be named 'xdb' and its
        // settings and data will be stored in files
        // testdb.properties and testdb.script
        hsqlServer.setDatabaseName(0, "xdb");
        hsqlServer.setDatabasePath(0, "file:hsqldbData/testdb");

        // Start the database!
        hsqlServer.start();


        // We have here two 'try' blocks and two 'finally'
        // blocks because we have two things to close
        // after all - HSQLDB server and connection
        // Getting a connection to the newly started database
        Class.forName("org.hsqldb.jdbcDriver");
        // Default user of the HSQLDB is 'sa'
        // with an empty password
        connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
    }


    @Test
    public void runJDBC() throws ClassNotFoundException, SQLException, InterruptedException {
        System.out.println("runJDBC in Parent class");

        // Here we run a few SQL statements to see if
        // everything is working.
        // We first drop an existing 'testtable' (supposing
        // it was there from the previous run), create it
        // once again, insert some data and then read it
        // with SELECT query.
        connection.prepareStatement("drop table testtable  IF EXISTS ;").execute();
        connection.prepareStatement("create table testtable ( id INTEGER, " + "name VARCHAR(20) );").execute();
        for (int i=0;i<10000;i++) {
            connection.prepareStatement(String.format("insert into testtable(id, name) values (%s, 'testvalue');", i) ).execute();
        }

        ResultSet rs = connection.prepareStatement("select * from testtable;").executeQuery();

        // Checking if the data is correct
        rs.next();
        System.out.println("Id: " + rs.getInt(1) + " Name: " + rs.getString(2));


        for (int i=0;i<1000;i++) {
            TimeUnit.MINUTES.sleep(1);
        }
    }


    @After
    public void clean() {
        if (hsqlServer != null) {
            hsqlServer.stop();
        }
    }
}


/*
0, Type: HSQL Database Engine Server
1, URL: jdbc:hsqldb:hsql://localhost/xdb
2, User: SA
 */