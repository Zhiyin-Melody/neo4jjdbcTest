package ModifierQuery;

import DB.ConnectNeo4J;
import RDFtPattern.BaseRDFPattern2Cypher;
import RDFtPattern.BaseRDFt2RDFPattern;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author:
 * @Description:Describe查询；
 * @Date: Created in 2018/10/2 16:12
 * @Pram:
 */
public class DescribeQuery {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //连接数据库；
        Statement stm= new ConnectNeo4J().ConnectNeo4J();
        //Describe查询函数；
        String SPARQLTString="DESCRIBE ?People {?People info:Plays_For[?ts,?te]-?n \"Golden_State_Warriors\" . \n" +
                "FILTER ?ts >= '2017-12-30' and ?te <= '2018-12-30'\n" +
                "}\n";
         String resultStr=describeQuery(SPARQLTString,stm);
        System.out.println(resultStr);
    }
//处理Describe函数；
    private static String describeQuery(String sparqltString, Statement stm) throws SQLException {
        String resStr="";
        String desWhere1 = "";
        String desWhere2 = "";
        String desFilter="";
        String getsparqldes="";

        String getcyphermatch="";
        String getcypherfilter="";

        String SPARQLdes="";//存放转换到sparql的语言结果；
        String Cypherdes="";//存放转换到cypher的语言结果；

        if(sparqltString.contains("FILTER")){//处理包含约束条件的情况；
            //先由sparql[t]转换到sparql语言；
            desWhere1 =sparqltString.substring(sparqltString.indexOf("{")+1,sparqltString.indexOf("FILTER"));
            desFilter = sparqltString.substring(sparqltString.indexOf("FILTER")+6,sparqltString.indexOf("}"));
            getsparqldes = BaseRDFt2RDFPattern.BaseRDFt2RDFPattern(desWhere1);
            SPARQLdes = "DESCRIBE ?People {"+getsparqldes+desFilter+"}";
            System.out.println(SPARQLdes);
            //再由sparql语言转换到cypher语言；
            getcyphermatch= getCypherMatch(getsparqldes);
            getcypherfilter= getcypherFilter(desFilter);
            Cypherdes="MATCH "+getcyphermatch+"\n WHERE "+getcypherfilter+"\n RETURN (n)-[0...5]->(m)";
        }else{//处理正常的情况；
            //先由sparql[t]转换到sparql语言；
            desWhere2=sparqltString.substring(sparqltString.indexOf("{")+1,sparqltString.indexOf("}"));
            getsparqldes = BaseRDFt2RDFPattern.BaseRDFt2RDFPattern(desWhere2);
            SPARQLdes =  "DESCRIBE ?People {"+getsparqldes+"}";
            System.out.println(SPARQLdes);
            //再由sparql语言转换到cypher语言；
            getcyphermatch= getCypherMatch(getsparqldes);
            Cypherdes="MATCH "+getcyphermatch+"\n RETURN (n)-[0...5]->(m)";
        }
        System.out.println(Cypherdes);

        //查询数据库中的结果；这个查询的结果怎么展示还是个问题，有待研究一下；
        ResultSet resultSetMatch = stm.executeQuery(Cypherdes);
        while(resultSetMatch.next()){
            resStr=resultSetMatch.getString("n");
        }

        return resStr;
    }
    //转换成cypher语言中的match语句；
    private static String getCypherMatch(String desWhere1) {
        String cyphermatch="";
        cyphermatch= BaseRDFPattern2Cypher.BaseRDFPattern2Cypher(desWhere1);
        return cyphermatch;
    }

    //转化成cypher语言中的filter语句；
    private static String getcypherFilter(String desFilter) {
        String cypherfilter="";
        cypherfilter = ASKQuery.getFilter(desFilter);
        return cypherfilter;
    }
}
