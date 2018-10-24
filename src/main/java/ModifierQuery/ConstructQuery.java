package ModifierQuery;

import DB.ConnectNeo4J;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author:
 * @Description:Construct查询；
 * @Date: Created in 2018/10/2 16:10
 * @Pram:
 */
public class ConstructQuery {
    //构造方法；
    public ConstructQuery(String sparqltString,Statement statement) throws SQLException {
        String constructStr = sparqltString.substring(sparqltString.indexOf("{")+1,sparqltString.indexOf("}"));
        String whereStr = sparqltString.substring(sparqltString.indexOf("WHERE{")+6,sparqltString.lastIndexOf("}"));
        String whereStrSub ="";
        String filterStr="";
        String getSparqlWhereStr ="";

        String getCypherWhereStr="";
        String getCypherConStr ="";

        String getSparqlconStr = getSparqlConStr(constructStr);//处理构造新的三元组集合；

        if(whereStr.contains("FILTER")){//处理约束条件；
            //先由sparql[t]转换到sparql语言；
            whereStrSub = whereStr.substring(0,whereStr.indexOf("FILTER"));//三元组模式；
            filterStr =whereStr.substring(whereStr.indexOf("FILTER"));//FILTER语句中的内容；
            getSparqlWhereStr = getWhereStr(whereStrSub);//处理约束条件中的返回值，两个已经存在的节点；

            //再由sparql语言转换到cypher语言；
            getCypherWhereStr = getCypherWhereStr(getSparqlWhereStr)+getFilter(filterStr);
            getCypherConStr = getCypherConStr(getSparqlconStr);

        }else{
            //先由sparql[t]转换到sparql语言；
            getSparqlWhereStr = getWhereStr(whereStr);//处理约束条件中的返回值，两个已经存在的节点；
            //再由sparql语言转换到cypher语言；
            getCypherWhereStr = getCypherWhereStr(getSparqlWhereStr);
            getCypherConStr = getCypherConStr(getSparqlconStr);
        }


        String SPARQLStr=getSparqlconStr+getSparqlWhereStr+filterStr;
        System.out.println("转换后的语句是："+SPARQLStr);


        //先执行getCypherWhereStr返回两个节点；作为新的关系的两个节点存在；
        ResultSet resultSetMatch = statement.executeQuery(getCypherWhereStr);
        while(resultSetMatch.next()){
            resultSetMatch.getRow();
        }
        //得到的结果将再使用进行构建节点-关系-节点的图形式；
        ResultSet resultSetCreate = statement.executeQuery(getCypherConStr);
        while(resultSetCreate.next()){
            resultSetCreate.getRow();
        }
    }
//处理filter中的语句；
    private String getFilter(String filterStr) {
        String filters="";
        return filters;
    }

    //转换到cypher语言中的时候处理的构造语句；
    private String getCypherConStr(String getSparqlconStr) {
        String cypherconStr="";
        return cypherconStr;
    }


    //转换到Cypher语言中的约束条件语句；
    private String getCypherWhereStr(String getSparqlWhereStr) {
        String cypherwhereStr="";
        return cypherwhereStr;
    }

    //转换到sparql语言中的约束条件语句，找到符合的主语和宾语返回；
    private String getWhereStr(String whereStr) {
        String sparqlwhereStr="";

        return sparqlwhereStr;
    }

    //转换到sparql语言中构造RDF三元组集合；
    private String getSparqlConStr(String constructStr) {
        String sparqlConStr="";

        return sparqlConStr;
    }


    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //连接数据库；
        Statement stm= new ConnectNeo4J().ConnectNeo4J();
        //Construct查询函数；
        //构建构造一个新的关系两个运动员是队友info:isTeammate
        String SPARQLTString="CONSTRUCT {\"Kobe_Bean_Bryant\" info:isTeammate[?ts1,?te1]-1 \"Shaquille_Rashaun_ONeal\" .}\n" +
                "WHERE{\n" +
                "\"Kobe_Bean_Bryant\" info:Plays_For[?ts1,?te1]-?n1 \"Los_Angeles_Lakers\" .\n" +
                "\"Shaquille_Rashaun_ONeal\" info:Plays_For[?ts2,?te2]-?n2 \"Los_Angeles_Lakers\" .\n" +
                "FILTER (?ts1 >= ?ts2 and ?te1 <= ?te2) or (?ts1 <= ?ts2 and ?te1 >= ?te2)}\n";
        new ConstructQuery(SPARQLTString,stm);
    }
}
