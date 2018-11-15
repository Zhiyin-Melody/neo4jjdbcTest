package Display;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Path;

import java.util.List;

/**
 * @Author:lizhiyin；
 * @Description:将Neo4J数据库中的数据用可视化展现在页面上的代码；
 * @Date: Created in 2018/11/14 22:13
 * @Pram:
 */
public class Match2D3 {
    Driver driver;

    public Match2D3(String uri, String user, String password){
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user,password));
    }

    //界面传回的操作请求，拼成Match语句查库，查库结果拼成json格式写jason文件；
    public void gernerateJsonFiles(){
        Session session = driver.session();
        StatementResult result = session.run(
                "MATCH p=(n)-[r]->(o) RETURN p"
        );

        StringBuffer nodes = new StringBuffer();
        StringBuffer linkes = new StringBuffer();

        while (result.hasNext()){
            Record record = result.next();
            //打印出记录的路径；
            System.out.println(record);
            List<Value> list = record.values();
            for (Value v: list
                 ) {
                Path p = v.asPath();
                System.out.println(v.asPath());
            }
        }
    }

    public static void main(String[] args) {

       Match2D3 example = new Match2D3("bolt:neo4j://202.199.6.64:7474/", "neo4j", "123456");
       example.gernerateJsonFiles();
    }
}
