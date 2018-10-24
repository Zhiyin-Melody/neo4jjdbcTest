package ModifierQuery;

import DB.ConnectNeo4J;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
       // String s="MATCH (Kobe_Bean_Bryant)-[Relationship:Plays_For]->(Los_Angeles_Lakers) WHERE Relationship.rdft_hasSrartTime>='1996-01-01' and Relationship.rdft_hasEndTime<='2016-12-30' RETURN [Relationship]";
         String s1="MATCH (Kobe_Bean_Bryant)-[Relationship:Plays_For]->(Los_Angeles_Lakers) WHERE Relationship.rdft_hasStartTime>='1996-01-01' and Relationship.rdft_hasEndTime<='2016-12-30' RETURN [Relationship]";
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
        String CypherS1="";
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
        CypherS1 ="MATCH "+cypherStringSub1+"\nWHERE "+sparqlStrSub3+"\nRETURN [Relationship]";
        if(CypherS1.contains("{")){
            CypherS=CypherS1.substring(0,CypherS1.indexOf("{"))+CypherS1.substring(CypherS1.indexOf("}")+1);
        }else{
            CypherS=CypherS1;
        }
        return CypherS;
    }
    //处理过滤条件；
    private static String getFilter(String sparqlStrSub2) {
        Pattern compileP = Pattern.compile("[\\{\\}\n]");
        Matcher m = compileP.matcher(sparqlStrSub2);
        String sparqlStr = m.replaceAll("").trim();//处理一下字符串；
        String whereStr=getWhereString(sparqlStr);
        return whereStr;
    }
//处理约束条件函数；
    private static String getWhereString(String sparqlStr) {
        String whereS="";
        ArrayList<String> ls=new ArrayList<String>();
        ls.add(sparqlStr);//这里就仅限只有一个约束条件的情况；后续再加；
        whereS=wheresubSeqmodify(ls).toString();
        return whereS;
    }
//调整约束语句中的顺序；
    private static StringBuffer wheresubSeqmodify(ArrayList<String> list) {
        StringBuffer sb = new StringBuffer();

        for (int j = 0; j < list.size() ; j++) {
            StringBuffer sf = new StringBuffer();
            if(list.get(j).contains("and")){//表示时间段的约束条件，或者有其他的约束条件；
                String[] a =list.get(j).split("and");
                ArrayList<String> ls=new ArrayList<String>();
                for (int i = 0; i <a.length ; i++) {
                    ls.add(a[i].trim().replace("?",""));
                    for (int k = i; k <ls.size(); k++) {
                        String [] s=ls.get(k).split(" ");
                        if(s[0].equals("ts")){
                            sf.append("Relationship.rdft_hasStartTime").append(s[1]).append("'"+s[2].substring(1));
                        }if(s[0].equals("te")){
                        sf.append("Relationship.rdft_hasEndTime").append(s[1]).append("'"+s[2].substring(1));
                        }
                        if (i!=a.length-1) {
                            sf.append(" and ");
                        }
                    }

                }
            }else{//只有一个约束条件；
                String[] sarr = list.get(0).split(" ");
                sf.append("Relationship.rdft_hasTime").append(sarr[1]).append("'"+sarr[2].substring(1));
            }
sb.append(sf);
        }
        return sb;

    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //连接数据库；
        Statement stm= new ConnectNeo4J().ConnectNeo4J();
        //ASK查询函数；
        String SPARQLTString="ASK {\"Kobe_Bean_Bryant\" info:Plays_For[?ts,?te]-1 \"Los_Angeles_Lakers\" .\n" +
                "FILTER ?ts >= '1996-01-01' and ?te <= '2016-12-30'\n" +
                "}\n";
        /*MATCH (Kobe_Bean_Bryant)-[Relationship:Plays_For]->(Los_Angeles_Lakers) WHERE Relationship.rdft_hasSrartTime>='1996-01-01' and Relationship.rdft_hasEndTime>='2016-12-30' RETURN [Relationship]
        这句话能够查出来的；返回true;
        * */
        boolean value=ASKQuery(SPARQLTString,stm);
        System.out.println(value);
    }
}
