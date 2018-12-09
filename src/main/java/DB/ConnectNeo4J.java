package DB;

/**
 *@Author:LIZHIYIN
 *@Desciption:以jbdc的形式连接Neo4j数据库；目前没有用到，
 *@para:
 *@Date:Created in 20180311
 */
//cd /usr/local/app/neo4j-community-3.4.0-beta02/bin
//sudo ./neo4j console
//sudo ./neo4j start
//直接neo4j console 或 neo4j stop
import java.sql.*;

import static java.lang.Class.forName;


public class ConnectNeo4J {

    public Statement ConnectNeo4J() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        forName("org.neo4j.jdbc.Driver").newInstance();
        //Connection con = DriverManager.getConnection("jdbc:neo4j://219.216.65.123:7474/", "neo4j", "123456");  //这里是实验室服务器的数据库,原来的；

       Connection con = DriverManager.getConnection("jdbc:neo4j://202.199.6.215:7474/", "neo4j", "123456");  //这里是实验室服务器的数据库,
        Statement statement = con.createStatement();
        return statement;
    }
        public static void main (String[]args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        /*ConnectNeo4J connectNeo4J = new ConnectNeo4J();
        Statement stm= connectNeo4J.ConnectNeo4J();

        String queryString_04 = "match (n) return (n)";

            ResultSet resultSet = stm.executeQuery(queryString_04);
            while(resultSet.next()){
                System.out.print(resultSet.getString("n"));}*/
        }
    }


