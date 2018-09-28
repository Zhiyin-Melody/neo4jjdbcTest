package DB;

/**
 *@Author:LIZHIYIN
 *@Desciption:以jbdc的形式连接Neo4j数据库；
 *@para:
 *@Date:Created in 20180311
 */
//cd /usr/local/app/neo4j-community-3.4.0-beta02/bin
//sudo ./neo4j console
//sudo ./neo4j start
//直接neo4j console 或 neo4j stop
import java.sql.*;

import static java.lang.Class.forName;


public class ConnectDB {

        public static void main(String[] args) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
            // TODO Auto-generated method stub
            forName("org.neo4j.jdbc.Driver").newInstance();
            //Connection con = DriverManager.getConnection("jdbc:neo4j://192.168.75.128:7474/","neo4j","123456");  //这里是虚拟机上的数据库，可以使用localhost,最好还是使用ip地址,
            Connection con = DriverManager.getConnection("jdbc:neo4j://219.216.65.123:7474/","neo4j","123456");  //这里是实验室服务器的数据库,

            //Querying
            //查询语句也就是写的cypher语言
            String queryString_00 = "create (n0:employ { name: 'S' }),(ma:manage { name: 'A1' })";
            String queryString_01 = "match (n) return (n)";
            String queryString_02 = "match (n0:employ { name: 'S' }) delete (n0)";
            String queryString_03 = "create (n0:employ { name: 'S' })";
            String queryString_04 = "match (n:Dirty_Hearts{__wasCreatedOnDate:2011-10-13}) delete (n)";

            //执行语句的部分；
//            try(Statement stmt = con.createStatement())
//            {
//                ResultSet rs = stmt.executeQuery(queryString_01);
//                while(rs.next())
//                {
//                    System.out.println(rs.getString("n"));
//                }
//            }
//            -------以下是自己编写的程序----
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(queryString_01);
            while(resultSet.next()){
            System.out.print(resultSet.getString("n"));}
        }

//        // Connecting以下这是官方代码；
//try (Connection con = DriverManager.getConnection("jdbc:neo4j:bolt://localhost", 'neo4j', password)) {
//
//            // Querying
//            String query = "MATCH (u:User)-[:FRIEND]-(f:User) WHERE u.name = {1} RETURN f.name, f.age";
//            try (PreparedStatement stmt = con.prepareStatement(query)) {
//                stmt.setString(1,"John");
//
//                try (ResultSet rs = stmt.execute()) {
//                    while (rs.next()) {
//                        System.out.println("Friend: "+rs.getString("f.name")+" is "+rs.getInt("f.age"));
//                    }
//                }
//            }
//        }
    }


