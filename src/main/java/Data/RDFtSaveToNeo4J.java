package Data;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;

import java.io.InputStream;
import java.sql.*;

import static java.lang.Class.forName;

/**
 * @Author:
 * @Description:将RDFt数据存储在Neo4j数据库中；
 * @Date: Created in 2018/10/22 14:20
 * @Pram:
 */
public class RDFtSaveToNeo4J {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Statement statement = connectNeo4j();
        ReadIntoRDFt(statement);
    }

    //读取RDFt数据集；
    private static void ReadIntoRDFt(Statement statement) throws SQLException {
        String CypherString = "";
        Model model = ModelFactory.createDefaultModel();
        String inputFileName = "D:\\a\\AC.ttl";//这个是RDFt数据形式的数据集；yagoEX.ttl 和 NBAEXTest.ttl
        InputStream in = FileManager.get().open(inputFileName);
        if (in == null) {
            throw new IllegalArgumentException("File: " + inputFileName
                    + " not found");
        }
        model.read(in, "", "TTL");
        StmtIterator iter = model.listStatements();
        while (iter.hasNext()) {
            org.apache.jena.rdf.model.Statement stmt = iter.nextStatement(); //得到三元组声明；
            String subject = stmt.getSubject().toString(); // get the subject
            String subsubject = subject.substring(subject.lastIndexOf("/") + 1);
            System.out.println("主语是：" + subsubject);

            String predicate = stmt.getPredicate().toString(); // get the predicate

            RDFNode object = stmt.getObject(); // get the object
            String objectStr = object.toString();//宾语；

            System.out.println("三元组是" + stmt);
            //将谓语部分分离出时间属性和更新次数属性；


            String predicateStr=getPredicate(predicate);
            CypherString = getCypherStr(statement, subsubject, objectStr, predicateStr);
                System.out.println("返回的查询语句是" + CypherString);

                ResultSet resultSet = statement.executeQuery(CypherString);
                while (resultSet.next()) {
                    resultSet.getRow();
                }
            }

        }
    //生成Cypher查询语句；
    private static String getCypherStr(Statement statement, String subsubject, String objectStr, String predicateStr) throws SQLException {
        String CypherString;
        if (isExistSubject(subsubject, statement)) {//匹配到该节点作为节点增加新的关系；
            if (isExistObject(objectStr, statement)) { //判断object 节点在数据库中是否存在；
                CypherString = "MATCH ("+subsubject+":" + subsubject + "{name:'"+subsubject+"'}),(objects:`" + objectStr + "`{name:'"+objectStr+"'})\n" +"WITH ("+subsubject+"),(objects)\n"+
                        " CREATE ("+subsubject+")"+predicateStr+"(objects)";
            }else{ //执行创建语句建立新的节点和关系；
                CypherString = "MATCH ("+subsubject+":" + subsubject + "{name:'"+subsubject+"'})\n" +"WITH ("+subsubject+")\n"+
                        " CREATE ("+subsubject+")"+predicateStr+"(objects:`" + objectStr + "`{name:'"+objectStr+"'})";
            }
        }else{//匹配到该节点增加新的属性关系；
        if (isExistObject(objectStr, statement)) { //判断object 节点在数据库中是否存在；
            CypherString = "MATCH (objects:`" + objectStr + "`{name:'"+objectStr+"'})\n" +"WITH (objects)\n"+
                    " CREATE ("+subsubject+":" + subsubject + "{name:'"+subsubject+"'})"+predicateStr+"(objects)";
            //匹配到该节点作为节点增加新的关系；
        }else{ //执行创建语句建立新的节点和关系；
            CypherString = " CREATE ("+subsubject+":" + subsubject + "{name:'"+subsubject+"'})"+predicateStr+"(objects:`" + objectStr + "`{name:'"+objectStr+"'})";
        }
        }
        return CypherString;
    }

    //连接Neo4J数据库；
        private static Statement connectNeo4j () throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
            forName("org.neo4j.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:neo4j://202.199.6.64:7474/", "neo4j", "123456");  //这里是实验室服务器的数据库,
            java.sql.Statement statement = con.createStatement();
            return statement;
        }

        //判断是否存在主语；
        private static boolean isExistSubject (String subsubject, Statement statement) throws SQLException {
            String findsubject = "MATCH ("+subsubject+":" + subsubject + "{name:'"+subsubject+"'})\n RETURN ("+subsubject+")";
            ResultSet resultSet = statement.executeQuery(findsubject);
            while (resultSet.next()) {
               resultSet.getRow();
            }
            if(resultSet.getRow()==-1){
                return false;
            }
            return true;
        }

        //判断是否存在宾语
        private static boolean isExistObject (String objectStr, Statement statement) throws SQLException {
            String findobject = "MATCH (objects:`" + objectStr + "`{name:'"+objectStr+"'})\n RETURN (objects)";
            ResultSet resultSet = statement.executeQuery(findobject);
            while (resultSet.next()) {
                System.out.print(resultSet.getString("objects"));
            }
            if(resultSet.getRow()==-1){
                return false;
            }
            return true;
        }

    //处理谓语部分；
    private static String getPredicate(String predicate) {
        String predicateStr = "";
        String subPredicate = "";
        String hasNumUpdate = predicate.substring(predicate.indexOf(']') + 2);//更新次数；
        if (predicate.contains("/") && predicate.contains("[")) {
            subPredicate = predicate.substring(predicate.lastIndexOf("/") + 1, predicate.indexOf('['));//子谓语部分；
            System.out.println("谓语部分是：" + subPredicate);
            String hasTime = predicate.substring(predicate.indexOf('['), predicate.indexOf(']') + 1);
            String hasStartTime = " ";
            String hasEndTime = " ";
            if (hasTime.contains(",")) {
                hasStartTime = hasTime.substring(hasTime.indexOf('[') + 1, hasTime.indexOf(','));
                hasEndTime = hasTime.substring(hasTime.indexOf(',') + 1, hasTime.indexOf(']'));
                predicateStr = "-[Relationship:" + subPredicate + "{rdf_type:'rdft_property',rdft_hasStartTime:'" + hasStartTime
                        + "',rdft_hasEndTime:'" + hasEndTime + "',rdft_hasNumUpdate:" + hasNumUpdate + "}" + "]->";
            } else {
                predicateStr = "-[Relationship:" + subPredicate + "{rdf_type:'rdft_property',rdft_hasTime:'" + hasTime
                        + "',rdft_hasNumUpdate:" + hasNumUpdate + "}" + "]->";
            }
        }else{
            subPredicate = predicate.substring(predicate.lastIndexOf("/") + 1);
            predicateStr = "-[Relationship:" + subPredicate +"]->";
        }
        return predicateStr;
    }
    }
