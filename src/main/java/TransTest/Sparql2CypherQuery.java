package TransTest;


import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.Op;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *@Author:LIZHIYIN
 *@Desciption:将sparqlt转换成Cypher算法；
 *@para:输入的是sparql查询语句，返回的是cypher查询语句；
 *@Date:Created in 20180920;
 */
public class Sparql2CypherQuery {

    public String Sparql2Cypher(String string){
        System.out.println("传过来的查询语句是："+string);
        StringBuffer cypherString = new StringBuffer();//最终结果；
        StringBuffer startString  = new StringBuffer();//开始节点；
        StringBuffer matchString  = new StringBuffer();
        StringBuffer matchStringp = new StringBuffer();
        StringBuffer matchStringpro = new StringBuffer();
        StringBuffer whereString = new StringBuffer();//过滤器；
        StringBuffer returnString = new StringBuffer();//结果集；
        StringBuffer modifyString = new StringBuffer();//修饰符；
        //先定义用来存放Cypher中每个部分的字符串；
        Query query = QueryFactory.create(string);
        Op op = Algebra.compile(query);

        //采用JenaARQ解析SPARQL语句；
        String afterParse = new String(op.toString());
       // System.out.println("解析后的字符串是："+afterParse);
        String[] brk = afterParse.split("triple");//将三元组们用triple分割开；
        //从 select部分提取出将返回结果值的变量集合;
        Set<String> RetVarSet = new HashSet<String>();
        //处理startString；这里先不做，要是在大量数据中查询的话，指定开始节点会有帮助；先放着；

        //处理matchString；
       // List<String> TriplePatternSet = new ArrayList<String>();//用于存放三元组的；
        matchString.append(MatchStringConstruct(matchStringp,matchStringpro, brk));

        //处理returnString;
        String[] returnS = brk[0].split("filter");
        returnString.append(getReturnString(returnS ));

        //处理whereString的函数;
        if(returnS.length > 1){
        String wheres = returnS[1].substring(returnS[1].indexOf("("),returnS[1].lastIndexOf(")")+1);
        String wherepre = wheres.substring(0,wheres.indexOf("str")+3);
        String wheresub = "("+wheres.substring(wheres.indexOf("str")+3,wheres.indexOf(") (")).trim();
        String wherelas = ")"+wheres.substring(wheres.indexOf(") ("));
        wheres = wherepre + wheresub + wherelas;
        //除去?变量；
        Pattern comp=Pattern.compile("[?]");
        Matcher m1=comp.matcher(wheres);
        whereString.append(getWhereString(m1.replaceAll("").trim()));
        }else {
            whereString.delete(0,whereString.length());//没有约束部分时；
        }
        //处理修饰符部分；
        modifyString=modifyPart(string);
        //System.out.println("修饰符部分是："+modifyString.toString());
        //结合上述所有部分作为总的部分;

        cypherString.append(startString).append("MATCH "+matchString);
                if(whereString.length()>0){
                    cypherString.append("\nWHERE "+whereString);
                }
                cypherString.append("\nRETURN "+returnString);
                if(modifyString!=null){
                    cypherString.append("\n"+modifyString.toString().trim());
                }
        return cypherString.toString();
    }

    public StringBuffer MatchStringConstruct(StringBuffer matchStringp, StringBuffer matchStringpro, String[] brk) {
        int k = 1;
        for (int i = 1; i <brk.length ; i+=k) {
            Pattern compileP = Pattern.compile("[?<>()\n]");
            Matcher m = compileP.matcher(brk[i]);
            String cleaned = m.replaceAll("").trim();
            //TriplePatternSet.add(cleaned);//这个集合中也先不用做，因为还不需要;留着以后做；
            String[] arr = cleaned.split(" ");//存放的是干净的每个部分;
            if(i>1) matchStringp.append(",");

            //这里随机给谓语关系增加标签；
            String RelationshipLable = getRelLable();

            matchStringp.append("("+ arr[0]+")-[");
            for (int j = i; j <i+5 ; j++) {
                if(brk[j].contains(arr[1])){
                    String [] pb =Pattern.compile("[?<>()\n]").matcher(brk[j]).replaceAll("").trim().split(" ");
                    if(pb[0].equals(arr[1])){
                        if(pb[1].contains("hasTime")||pb[1].contains("hasStartTime")||pb[1].contains("hasEndTime")){//如果是hasTime,hasStartTime，hasEndTime时加上rdft_和双引号；
                            matchStringpro.append("rdft_"+pb[1].substring(pb[1].lastIndexOf("#")+1)+":'"+pb[2].substring(pb[2].lastIndexOf("#") + 1)+"'");
                        }
                        else if(pb[1].contains("type")){
                            matchStringpro.append(pb[1].substring(pb[1].lastIndexOf("#") + 1) + ":'" + pb[2].substring(pb[2].lastIndexOf("#") + 1)+"'");
                        }
                        else if(pb[1].contains("hasNumUpdate")){
                            matchStringpro.append(pb[1].substring(pb[1].lastIndexOf("#") + 1) + ":" + pb[2].substring(pb[2].lastIndexOf("#") + 1));
                        }else{
                            matchStringpro.append(pb[1].substring(pb[1].lastIndexOf("#") + 1) + ":`" + pb[2].substring(pb[2].lastIndexOf("#") + 1)+"`");
                        }
                        k++;
                        if(k<5)
                            matchStringpro.append(",");
                    }
                    else{
                        k=1;
                        matchStringpro.delete(0,matchStringpro.length());
                        matchStringpro.append(RelationshipLable+":"+arr[1]+"{");
                    }
                }else
                    { k=1;
                    break;}
            }
            matchStringp.append(matchStringpro).append("}]->("+arr[2]+")");
        }
        return matchStringp;
    }

    //得到标签的随机表示形式;先就用relationship这个标签；
    private String getRelLable() {
        String sLale="Relationship";
        return sLale;
    }

    //处理return部分;
    private String getReturnString(String[] returnS) {
        String ReturnString = "";
        String returnSet = returnS[0].substring(returnS[0].lastIndexOf("project")+9,returnS[0].lastIndexOf(")"));
        Pattern compileP = Pattern.compile("[?<>()\n]");
        Matcher m = compileP.matcher(returnSet);
        ReturnString = m.replaceAll("").trim();
        ReturnString = ReturnString.replaceAll(" ",",");
        return ReturnString;
    }

    //处理修饰符的部分；偏移量；
    private StringBuffer modifyPart(String string) {
        StringBuffer modifyString = new StringBuffer();
        String modifyStringsub=(string.substring(string.lastIndexOf("}")+1));
        if(modifyStringsub.contains("OFFSET")||modifyStringsub.contains("offset")){
           modifyStringsub=modifyStringsub.replaceAll("OFFSET","SKIP");
        }
        modifyString.append(modifyStringsub);
        return modifyString;
    }

    //处理过滤器中的条件;WHERE语句后面的内容；
    private String getWhereString(String whereS) {
        StringBuffer sb = new StringBuffer();
        Stack<String> stack = new Stack<String>();
        ArrayList<String> ls = new ArrayList<String>(); //存放每个filter句子;
        int k = 1;
        int l=0,r=0;
        String s ="";
        for (int i = 0; i < whereS.length(); i+=k) {
            char character = whereS.charAt(i);//存放的是当前的字符；
            if(character == '('){
                stack.push("(");
                l++;
            }else if(character == ')'){
                Stack<String> st=new Stack<String>();
                stack.push(")");
                r++;
                if(!stack.isEmpty()&&l==r+1){//栈不为空的时候，就弹栈到一个字符串中；拼接成一个完整的filter语句，放到ls中；
                    stack.pop();
                    int stacklen =stack.size();
                    for (int j = 0; j <stacklen-3 ; j++) {
                        st.push(stack.pop());
                    }
                StringBuffer stringBuffer = new StringBuffer();
                    int stlen = st.size();
                for (int j = 0; j <stlen ; j++) {
                    stringBuffer.append(st.pop());
                }
                ls.add(stringBuffer.toString());
                }
            }
            String whereSS = whereS.substring(i+1);
            s=whereS.substring(i+1,isIndex(whereSS)+i+1);
            if(s.length()!=0||s==" ")
            {stack.push(s);}
            k=s.length()+1;
        }
        //调整每条约束语句中的内容；
        wheresubSeq(sb, ls);
        return sb.toString();
    }

    //将得到的每个约束中的内容进行调整；将准确的Cypher语言形式返回；
    private void wheresubSeq(StringBuffer sb, ArrayList<String> ls) {
        for (int i = 0; i <ls.size() ; i++) {
            List<String> list=new ArrayList<String>();
            String sls = ls.get(i);
            //这是包含两项内容时的转换;
            if(sls.contains("&&")||sls.contains("||")){
                Pattern p = Pattern.compile("(\\([^\\)]+\\))");//提取括号中的内容；
                Matcher m = p.matcher(sls.substring(2));
                while(m.find()){
                    list.add( m.group().substring(1, m.group().length()-1));
                }

              for (int j = 0; j <list.size() ; j++) {
                StringBuffer sf= new StringBuffer();
                String[] sarr = list.get(j).split(" ");
                sf.append("Relationship"+"."+sarr[1]).append(sarr[0]).append("'"+sarr[2].substring(1,sarr[2].indexOf("^")-1)+"'");
                if(j<list.size()-1){
                    sb.append(sf).append(" ").append(ls.get(i).substring(2,5));
                }else {
                    sb.append(sf).append(" ");
                }
              }
            }
           else //包含一项内容的转换；
            {
                /*带有约束条件的时候的cypher查询;
                * MATCH (n)
                WHERE n.name CONTAINS '三'
                RETURN n*/
                String lsi = ls.get(i);//第i个元素；
                if(lsi.contains("regex")){//假如包含正则表达式的时候；
                    String Str= lsi.substring(lsi.indexOf("(")+5,lsi.indexOf(")"));
                    String filterStr = lsi.substring(lsi.lastIndexOf(" ")+2,lsi.lastIndexOf(")")-1);
                    sb.append("Relationship"+"."+ Str +" CONTAINS '"+filterStr+"' ");

                }
                else{
                    sb.append(lsi+" ");
                }
                sb.append("\nWHERE ");
            }

        }
    }

    //找到（或）当前的索引；
    private int isIndex(String where) {
        int ans=0;
        for (int i = 0; i <where.length() ; i++) {
            if(where.charAt(i)=='('){
                ans = i;
                break;
            }else if(where.charAt(i)==')')
            {
                ans = i;
                break;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Sparql2CypherQuery sparql2Cypher = new Sparql2CypherQuery();
        String prefixString = "base <http://yago-knowledge.org/resource/> \n" +
                "prefix dbp: <http://dbpedia.org/ontology/> \n" +
                "prefix owl: <http://www.w3.org/2002/07/owl#> \n" +
                "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
                "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "prefix skos: <http://www.w3.org/2004/02/skos/core#> \n" +
                "prefix xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
                "prefix rdft:<http://www.neu.edu/2018/rdft-syntax-ns#> ";
        String sparqlString = prefixString +
                "SELECT ?s1 ?count1  \n" +
                " WHERE { \n" +
                "\t?s1 ?haspopulation1 ?count1 .\n" +
                "\t?haspopulation1  rdf:type rdft:property .\n" +
                "\t?haspopulation1  rdft:hasStartTime ?ts1 .\n" +
                "\t?haspopulation1  rdft:hasEndTime ?te1 .\n" +
                "\t?haspopulation1  rdft:hasNumUpdate ?n1 .\n" +
                "\t?s2 ?haspopulation2 ?count2 .\n" +
                "\t?haspopulation2  rdf:type rdft:property .\n" +
                "\t?haspopulation2  rdft:hasStartTime ?ts2 .\n" +
                "\t?haspopulation2  rdft:hasEndTime ?te2 .\n" +
                "\t?haspopulation2  rdft:hasNumUpdate ?n2 .\n" +
                " FILTER regex(str(?s), 'china')\n" +
                " FILTER (?ts >= '2000-01-01'^^<http://www.w3.org/2001/XMLSchema#date> && ?te <= '2000-12-30'^^<http://www.w3.org/2001/XMLSchema#date>)\n" +
                "} OFFSET 2 \n" +
                //"ORDER BY?n \n"+//order by这个解析不了;
                "LIMIT 50 \n";
       //转换之后的语句应该是这样子的;
      /*这个可以查出来；
      MATCH (s1)-[haspopulation{rdft_hasEndTime:'2005-03-01',rdft_hasNumUpdate:3,rdft_hasSrartTime:'2005-03-01'}]->(count1)
RETURN s1, count1
LIMIT 50
-----------------------或者这样的；
MATCH (s1)-[haspopulation{rdft_hasNumUpdate:3,rdft_hasSrartTime:'2005-03-01'}]->(count1)
WHERE haspopulation.rdft_hasEndTime='2005-03-01'
RETURN s1, count1
LIMIT 50
---------或者这样的创建和查询均成功了；
MATCH (s1)-[:haspopulation1{type:'property',rdft_hasStartTime:'ts1',rdft_hasEndTime:'te1',rdft_hasNumUpdate:'n1'}]->(count1),(s2)-[:haspopulation2{type:'property',rdft_hasStartTime:'ts2',rdft_hasEndTime:'te2',rdft_hasNumUpdate:'n2'}]->(count2)
RETURN s1,count1
SKIP 2
LIMIT 50
--------------------带有完整约束条件的查询语言；
MATCH (CHINA)-[RELLABLE:haspopulation1{type:'property',rdft_hasStartTime:'2018-0-1',rdft_hasEndTime:'2018-09-24',rdft_hasNumUpdate:3}]->(`100000`)
WHERE  RELLABLE.rdft_hasNumUpdate=3 and RELLABLE.rdft_hasEndTime='2018-09-24'
RETURN CHINA,`100000`
-----------------------where中部分的语句应该写成这个样子的；

  MATCH (n)
  WHERE n.name CONTAINS '三'
  RETURN n
  */
        //输入的是sparql查询语句，返回的是cypher查询语句；
        String cypherString = sparql2Cypher.Sparql2Cypher(sparqlString);
        System.out.println("主函数的查询语句：\n"+cypherString);
    }
}
