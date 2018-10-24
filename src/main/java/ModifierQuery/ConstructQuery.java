package ModifierQuery;

import DB.ConnectNeo4J;
import RDFtPattern.BaseRDFPattern2Cypher;
import RDFtPattern.BaseRDFt2RDFPattern;

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

        String predicate="";
        String t="";
        String ts="";
        String te="";
        if(constructStr.contains("[")){//有时间信息；
            if(constructStr.contains(",")){//表示有ts,te
                predicate=constructStr.substring(constructStr.indexOf(":")+1,constructStr.indexOf("["));
                ts=constructStr.substring(constructStr.indexOf("[")+1,constructStr.indexOf(","));
                te=constructStr.substring(constructStr.indexOf(",")+1,constructStr.indexOf("]"));
            }else{//表示只有t;
                predicate=constructStr.substring(constructStr.indexOf(":")+1,constructStr.indexOf("["));
                t=constructStr.substring(constructStr.indexOf("[")+1,constructStr.indexOf("]"));
            }
        }else{//正常三元组；
            predicate=constructStr.substring(constructStr.indexOf(":")+1,constructStr.substring(0,constructStr.indexOf(":")+1).length()+constructStr.substring(constructStr.indexOf(":")+1).indexOf(" "));
        }
        if(whereStr.contains("FILTER")){//处理约束条件；
            //先由sparql[t]转换到sparql语言；
            whereStrSub = whereStr.substring(0,whereStr.indexOf("FILTER"));//三元组模式；
            filterStr =whereStr.substring(whereStr.indexOf("FILTER"));//FILTER语句中的内容；
            getSparqlWhereStr = getWhereStr(whereStrSub);//处理约束条件中的返回值，两个已经存在的节点；
            String SPARQLStr="CONSTRUCT {"+getSparqlconStr+"}\nWHERE{"+getSparqlWhereStr+"\n"+filterStr+"}";
            System.out.println(SPARQLStr);
            //再由sparql语言转换到cypher语言；
            getCypherWhereStr = "MATCH "+getCypherWhereStr(getSparqlWhereStr)+"\n"+getFilter(filterStr)+"RETURN S1,S2";
            //先执行getCypherWhereStr返回两个节点；作为新的关系的两个节点存在；
            ResultSet resultSetMatch = statement.executeQuery(getCypherWhereStr);
            String resmatch ="";//存放查询的两个节点；
            while(resultSetMatch.next()){//这个查询结果返回有问题；
                resmatch = resultSetMatch.getString("n");
            }

            getCypherConStr = getCypherConStr(resmatch,predicate,ts,te);
            //得到的结果将再使用进行构建节点-关系-节点的图形式；
            ResultSet resultSetCreate = statement.executeQuery(getCypherConStr);
            while(resultSetCreate.next()){//研究一下返回的结果怎么显示好，最好是字符串；
                String resCreate=resultSetCreate.getString("n");
            }

        }else{
            //先由sparql[t]转换到sparql语言；
            getSparqlWhereStr = getWhereStr(whereStr);//处理约束条件中的返回值，两个已经存在的节点；
            String SPARQLStr="CONSTRUCT {"+getSparqlconStr+"}\nWHERE{"+getSparqlWhereStr+"}";
            System.out.println(SPARQLStr);
            //再由sparql语言转换到cypher语言；
            getCypherWhereStr = "MATCH "+getCypherWhereStr(getSparqlWhereStr)+"RETURN S1,S2";//这里再进行处理吧；
            //先执行getCypherWhereStr返回两个节点；作为新的关系的两个节点存在；
            ResultSet resultSetMatch = statement.executeQuery(getCypherWhereStr);
            String resmatch ="";//存放查询的两个节点；
            while(resultSetMatch.next()){
                resmatch = resultSetMatch.getString("n");
            }
            getCypherConStr = getCypherConStr(resmatch,predicate,ts,te);//这里先考虑ts,te的吧；
            //得到的结果将再使用进行构建节点-关系-节点的图形式；
            ResultSet resultSetCreate = statement.executeQuery(getCypherConStr);
            System.out.println(resultSetCreate);
            while(resultSetCreate.next()){
                String resCreate=resultSetCreate.getString("n");
            }
        }

    }
    //处理filter中的语句；这个filter需要自己重新写；
    private String getFilter(String filterStr) {
        String filters="";

        return filters;
    }

    //转换到cypher语言中的时候处理的构造语句；
    private String getCypherConStr(String getSparqlconStr,String predicate,String ts,String te) {
        String cypherconStr="";
        String[] a=getSparqlconStr.split(" ");
        String object1 =a[0];
        String object2 =a[1];
        //CREATE (Kobe_Bean_Bryant:Kobe_Bean_Bryant)-[ Relationship0: isTeammate {type:'property',rdft_hasStartTime:Ts1,rdft_hasEndTime:Te1,rdft_hasNumUpdate:1}]- (Shaquille_Rashaun_ONeal: Shaquille_Rashaun_ONeal)

        cypherconStr="CREATE ("+object1+"[ Relationship:"+predicate+"{type:'property',rdft_hasStartTime:"+ts+",rdft_hasEndTime:"+te+",rdft_hasNumUpdate:1}]- ("+object2+")";
        return cypherconStr;
    }


    //转换到Cypher语言中的约束条件语句；
    private String getCypherWhereStr(String getSparqlWhereStr) {
        String cypherwhereStr="";
        cypherwhereStr= BaseRDFPattern2Cypher.BaseRDFPattern2Cypher(getSparqlWhereStr);
        return cypherwhereStr;
    }

    //转换到sparql语言中的约束条件语句，找到符合的主语和宾语返回；
    private String getWhereStr(String whereStr) {
        String sparqlwhereStr="";
        sparqlwhereStr = BaseRDFt2RDFPattern.BaseRDFt2RDFPattern(whereStr);
        return sparqlwhereStr;
    }

    //转换到sparql语言中构造RDF三元组集合；
    private String getSparqlConStr(String constructStr) {
        String sparqlConStr="";
        sparqlConStr= BaseRDFt2RDFPattern.BaseRDFt2RDFPattern(constructStr);
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
