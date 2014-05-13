import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by wangxc on 2014/5/12.
 */
public class ChildTestCase extends HSQLDBTestcase {

    @Test
    public void runJDBC() throws SQLException {
        System.out.println("runJDBC in child class");
        connection.prepareStatement("drop table testtable_child  IF EXISTS ;").execute();
    }
}
