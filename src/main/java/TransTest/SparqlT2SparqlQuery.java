package TransTest;
/**
 *@Author:LIZHIYIN
 *@Desciption:将sparqlt转换成sparql;
 *@para:
 *@Date:Created in 20180311
 */
import java.util.ArrayList;

public class SparqlT2SparqlQuery {
    public StringBuilder SparqlT2SparqlMethod(){
        String SPARQLTString = "prefix owl: <http://www.w3.org/2002/07/owl#> \r\n"
                            + "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \r\n"
                            + "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> \r\n"
                            +"SELECT ?count \r\n"
                            + "WHERE {\r\n"
                            +"        <http://yago-knowledge.org/resource/china> <http://yago-knowledge.org/resource/haspopulation[?ts,?te]-?n> ?count .\r\n"
                            +"        <http://yago-knowledge.org/resource/America> <http://yago-knowledge.org/resource/haspopulation[?ts0,?te0]-?n0> ?count0 .\r\n"
                            +"        <http://yago-knowledge.org/resource/uk> <http://yago-knowledge.org/resource/haspopulation-?n1> ?count1 .\r\n"
                            +"        <http://yago-knowledge.org/resource/England> <http://yago-knowledge.org/resource/haspopulation> ?count2 .\r\n"
                            +"FILTER regex(?country,\"china\") . "
                            +"FILTER (?ts >= \"2000-01-01\"^^xsd:data && ?ts <= \"2000-12-30\"^^xsd:data) ."
                            +"}\r\n"
                            +"OFFSET 2\n"
                            +"Order by DESC\n"
                            +"LIMIT 10 ";

        StringBuilder SPARQLString = new StringBuilder();//保存转换后的查询语言字符串形式；
        //不涉及多线程的情况下，用StringBuilder来存储字符串；比较好；单线程用比较快；
        //先把SPARQLTString字符串分成三部分；再分别处理每部分的内容；
        String prefixString = SPARQLTString.substring(0, SPARQLTString.indexOf("{"));
        //从开始到where{ 之间的字符串；
        String whereString = SPARQLTString.substring(SPARQLTString.indexOf("{")+1, SPARQLTString.indexOf("}"));
        //where语句中包含的RDF graph 三元组图。其中还有过滤filter的语句，这个不能够省略；whereFilterString分开来写就是；whereString = whereRDFtString + whereFilterString
        String whereRDFtStringHasPre = whereString.substring(0,new SparqlT2SparqlMatcher().SparqlT2SparqlMatcher(whereString,"FILTER"));//WHERE语句中的rdf graph中的部分；
        String whereFilterString = whereString.substring(new SparqlT2SparqlMatcher().SparqlT2SparqlMatcher(whereString,"FILTER"),whereString.length());//WHERE中的FILTER部分；这个函数先不管，以后细分的时候再写；
       // System.out.println(new SparqlT2SparqlMatcher().SparqlT2SparqlMatcher(whereString,"FILTER"));
        System.out.print("whereRDFtStringHasPre语句是:"+whereRDFtStringHasPre);
        System.out.print("whereFilterString语句是:"+whereFilterString);
        String whereRDFtString=whereRDFtStringHasPre.replace("\r\n"," ");//去掉换行符；
        String [] statementRDFt = whereRDFtString.trim().split(" \\.");
        //将RDFt graph中的每个三元组分割出来；因为每个三元组是用.分隔开的；每个三元组放在数组中的一个元素中表示；

        //将RDFt三元组转换成RDF三元组；
        ArrayList<ArrayList<String>> lsls = RDFt2RDFTriple(statementRDFt);

        //处理修饰符部分；
        String conditionString = SPARQLTString.substring(SPARQLTString.indexOf("}")+1);

        //对结果的条件约束语句；
        StringBuilder whereStringSparql =new StringBuilder();

        //将lsls中转换成字符串输出；
        for (int i = 0; i <lsls.size() ; i++) {//3
            ArrayList<String> ls = new ArrayList<String>();
            for (int j = 0; j <lsls.get(i).size() ; j++) {//5
                whereStringSparql.append(lsls.get(i).get(j).toString());
            }
        }
        SPARQLString .append(prefixString).append("{"+whereStringSparql+" "+whereFilterString+"}").append(conditionString);
        return SPARQLString;//返回的是一个转换后的标准的sparql语言;
    }

    //将RDFt三元组转换成RDF三元组的形式；
    public ArrayList<ArrayList<String>> RDFt2RDFTriple(String[] statementRDFt) {
        ArrayList<ArrayList<String>> lsls = new ArrayList<ArrayList<String>>();

        for(int i = 0; i < statementRDFt.length; i++){
             if(statementRDFt[i].contains("[")&&statementRDFt[i].contains("]")){
                 System.out.println("包含时间信息或者包含更新次数信息:");
                 System.out.println(statementRDFt[i]);//这个包含了主谓宾的;这个块儿得考虑是否有时间信息或者只有n的情况；
                  lsls.add(transPredicate(statementRDFt[i].trim()));//转换谓语部分,放到list中;
             }
             if(!(statementRDFt[i].contains("[")&&statementRDFt[i].contains("]"))&&statementRDFt[i].contains("-")){
                 System.out.println("不包含时间信息");
                 lsls.add(transPredicateOnlyn(statementRDFt[i].trim()));//转换只包含n的，没有时间信息的谓语部分；
             }
             if(!(statementRDFt[i].contains("[")&&statementRDFt[i].contains("]"))||!(statementRDFt[i].contains("-"))){
                 System.out.println("是正常的三元组的时候");
                 lsls.add(transPredicateRDF(statementRDFt[i]));
             }

            //System.out.println("打印每个三元组" + statementRDFt[i].toString());//每个三元组中不包含 .了；
        }
        return lsls;
    }

    //谓语部分转换成SPARQL分解的形式,并返回;(s,p[ts,te]-n,o)的形式;
    private ArrayList<String> transPredicate(String s) {
        ArrayList<String>  predicateString = new ArrayList<String>();

        String [] a = s.split(" ");

        String p = a[1].substring(0,a[1].indexOf("["))+">";
        String ts = a[1].substring(a[1].indexOf("[")+1,a[1].indexOf(","));
        String te = a[1].substring(a[1].indexOf(",")+1,a[1].indexOf("]"));
        String n = a[1].substring(a[1].lastIndexOf("-")+1);
        String predicate= p.substring(p.indexOf(":")+1,p.indexOf(">"));
        predicateString.add(" "+a[0]+" ?"+predicate+" "+a[2]+" .");
        predicateString.add(" ?"+predicate+" rdf:type rdft:property .");
        if(ts.equals(te)){  //当ts=te时，
            predicateString.add(" ?"+predicate+" rdft:hasTime" + ts + "^^xsd:date .");
            predicateString.add(" ?"+predicate+" rdft:hasNumUpdate" + n + " .");
        }else {  //当ts!=te时，
            predicateString.add(" ?"+predicate+" rdft:hasStartTime " + ts + "^^xsd:date .");
            predicateString.add(" ?"+predicate+" rdft:hasEndTime " + te + "^^xsd:date .");
            predicateString.add(" ?"+predicate+" rdft:hasNumUpdate " + n + " .");
        }
        return predicateString;
    }
    //不带时间信息，只有n的谓语部分的转换函数；(s,p-n,o)的形式;
    private ArrayList<String> transPredicateOnlyn(String s) {
        ArrayList<String>  predicateStringOnlyn = new ArrayList<String>();
        String [] a = s.split(" ");


        String p = a[1].substring(0,a[1].lastIndexOf("-"))+">";
        String n = a[1].substring(a[1].lastIndexOf("-")+1,a[1].length()-1);

        predicateStringOnlyn.add(""+a[0]+" ?p "+a[2]+" .");
        predicateStringOnlyn.add(" ?p rdf:type rdft:property .");
        predicateStringOnlyn.add(" ?p rdft:hasNumUpdate " + n + " .");
        return predicateStringOnlyn;
    }
    //正常的RDF的三元组不需要改变的;(S,P,O)形式;
    private ArrayList<String> transPredicateRDF(String s) {
        ArrayList<String> lsRDF = new ArrayList<String>();
        lsRDF.add(" "+s.trim()+" .");
        return lsRDF;
    }

    //主函数；
    public static void main(String[] args) {
        SparqlT2SparqlQuery sparqlT2SparqlQuery = new SparqlT2SparqlQuery();
        StringBuilder S = sparqlT2SparqlQuery.SparqlT2SparqlMethod();
        System.out.println("主函数执行结果SPARQL语句\n"+S);//return 回来得接收返回值才行。
    }
}
