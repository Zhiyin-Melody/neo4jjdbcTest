package ModifierQuery;

import DB.ConnectNeo4J;
import TransTest.Sparql2CypherQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static RDFtPattern.BaseRDFPattern2Cypher.BaseRDFPattern2Cypher;
import static RDFtPattern.BaseRDFt2RDFPattern.BaseRDFt2RDFPattern;
import static RDFtPattern.Complex_SPARQL2CypherPattern.Complex_SPARQL2CypherPattern;
import static RDFtPattern.Complex_SPARQLt2SPARQLPattern.Complex_SPARQLt2SPARQLPattern;

/**
 * @Author:
 * @Description:ASK查询方式；
 * @Date: Created in 2018/10/2 16:09
 * @Pram:
 */
public class ASKQuery {
    //ASK查询函数；
    public static boolean ASKQuery(String sparqltString,Statement statement) throws SQLException {
        String sparqlStr = transToSparql(sparqltString);
        String CypherStr = transToCypher(sparqlStr);
        ResultSet resultSet = statement.executeQuery(CypherStr);
        while(resultSet.next()){
            resultSet.getRow();
        }
        if(resultSet.getRow()!=-1){
            return true;
        }
     return false;
    }
//SPARQLT语言转换成SPARQL;
    private static String transToSparql(String sparqltString) {
        String sparqlS="";
        String sparqltStringSub1 = sparqltString.substring(sparqltString.indexOf("{")+1,sparqltString.indexOf("FILTER"));
        String sparqltStringSub2 = sparqltString.substring(sparqltString.indexOf("FILTER"));

        String sparqlStringSub1="";
        if(sparqltString.contains("OPTIONAL")||sparqltString.contains("UNION")||sparqltString.contains("\\}\\{")){//复杂图模式处理；
            sparqlStringSub1 = Complex_SPARQLt2SPARQLPattern(sparqltStringSub1).toString();
        }else{//基本图模式处理；
            sparqlStringSub1 = BaseRDFt2RDFPattern(sparqltStringSub1);
        }

        sparqlS="ASK {"+sparqlStringSub1+"\n"+sparqltStringSub2;
        return sparqlS;
    }
//SPARQL语言转换成Cypher;
    private static String transToCypher(String sparqlStr) {
        String CypherS="";
        String sparqlStrSub1=sparqlStr.substring(sparqlStr.indexOf("{")+1,sparqlStr.indexOf("FILTER"));
        Pattern compileP = Pattern.compile("[\"]");
        Matcher m = compileP.matcher(sparqlStrSub1);
        String sparqlStrsub1 = m.replaceAll("").trim();
        String sparqlStrSub2=sparqlStr.substring(sparqlStr.indexOf("FILTER")+6);
        String cypherStringSub1="";//处理模式的函数；
        if(sparqlStr.contains("OPTIONAL")||sparqlStr.contains("UNION")||sparqlStr.contains("\\}\\{")){//复杂图模式处理；
            cypherStringSub1 = Complex_SPARQL2CypherPattern(sparqlStrsub1).toString();
        }else{//基本图模式处理；
            cypherStringSub1 = BaseRDFPattern2Cypher(sparqlStrsub1);
        }

        String sparqlStrSub3=getFilter(sparqlStrSub2);//处理过滤条件的函数；
        CypherS ="MATCH "+cypherStringSub1+"\nWHERE "+sparqlStrSub3+"\nRETURN [Relationship]";
        return CypherS;
    }
    //处理过滤条件；等会儿再处理；
    private static String getFilter(String sparqlStrSub2) {
        Pattern compileP = Pattern.compile("[\\{\\}\n]");
        Matcher m = compileP.matcher(sparqlStrSub2);
        String sparqlStr = m.replaceAll("").trim();//处理一下字符串；
        String whereStr=new Sparql2CypherQuery().getWhereString(sparqlStr);
        return whereStr;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //连接数据库；
        Statement stm= new ConnectNeo4J().ConnectNeo4J();
        //ASK查询函数；
        String SPARQLTString="ASK {\"Kobe_Bean_Bryant\" info:Plays_For[?ts,?te]-?n \"Los_Angeles_Lakers\" .\n" +
                "FILTER ?ts >= '1996-01-01' and ?te <= '2016-12-30'\n" +
                "}\n";
        boolean value=ASKQuery(SPARQLTString,stm);
        System.out.println(value);
    }
}
