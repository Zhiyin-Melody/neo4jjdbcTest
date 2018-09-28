package Data;

import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Class.forName;

/**
 * @Author:lizhiyin
 * @Description:将RDFt数据存储在Neo4j数据库中；
 * @Date: Created in 2018/8/26 11:31
 * @Pram:
 */
public class RDFtSaveNeo4j {

    public static void readintoRDFt() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //连接Neo4j数据库;
        forName("org.neo4j.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:neo4j://202.199.6.64:7474/", "neo4j", "123456");  //这里是实验室服务器的数据库,
        java.sql.Statement statement = con.createStatement();

        String CypherString =" ";
        Model model = ModelFactory.createDefaultModel();

        String inputFileName = "D:\\a\\c.ttl";//这个是RDFt数据形式的数据集；
        InputStream in = FileManager.get().open(inputFileName);
        if (in == null) {
            throw new IllegalArgumentException("File: " + inputFileName
                    + " not found");
        }
        model.read(in, "", "TTL");
        StmtIterator iter = model.listStatements();
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement(); //得到三元组声明；
            String subject = stmt.getSubject().toString(); // get the subject
            String subsubject = subject.substring(subject.lastIndexOf("/")+1,subject.length());
            System.out.println("主语是："+subsubject);
            String predicate = stmt.getPredicate().toString(); // get the predicate

            RDFNode object = stmt.getObject(); // get the object

            System.out.println("三元组是"+stmt);
            //将谓语部分分离出时间属性和更新次数属性；

            String subPredicate="" ;
            if(predicate.contains("/") && predicate.contains("[")){
                subPredicate= predicate.substring(predicate.lastIndexOf("/")+1, predicate.indexOf('['));//子谓语部分；
                System.out.println("谓语部分是："+subPredicate);
            }
            String hasTime = predicate.substring(predicate.indexOf('['),
                    predicate.indexOf(']') + 1);
            String hasStartTime = " ";
            String hasEndTime = " ";
            if (hasTime.contains(",")) {
                hasStartTime = hasTime.substring(hasTime.indexOf('[') + 1,
                        hasTime.indexOf(','));
                hasEndTime = hasTime.substring(hasTime.indexOf(',') + 1,
                        hasTime.indexOf(']'));
            }

            String hasNumUpdate = predicate.substring(
                    predicate.indexOf(']') + 2, predicate.length());//更新次数；

            CypherString = "CREATE (subject:"+subsubject+")-[subPredicate:"+subPredicate+"{rdft_hasSrartTime:'"+hasStartTime
                    +"',rdft_hasEndTime:'"+hasEndTime+"',rdft_hasNumUpdate:"+hasNumUpdate+"}"+"]->(object:`"+object+"`)";

            System.out.println("返回的查询语句是"+CypherString);

            //使用查询语言进行数据库查询操作；
            ResultSet resultSet = statement.executeQuery(CypherString);
            while(resultSet.next()){
                System.out.print(resultSet.getString("subject"));}
        }
        }
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
          readintoRDFt(); //读取RDFt数据集,并且转换成Cypher查询语言；
 }
}
