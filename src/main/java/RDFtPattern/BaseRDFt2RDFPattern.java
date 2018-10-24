package RDFtPattern;

import TransTest.SparqlT2SparqlQuery;

import java.util.ArrayList;

/**
 * @Author:
 * @Description:将RDFt基本图模式转换成RDFt基本图模式；
 * @Date: Created in 2018/9/28 10:34
 * @Pram:
 */
public class BaseRDFt2RDFPattern {
    public static String BaseRDFt2RDFPattern(String RDFtGraphPatternStr) {

    String RDFtString = RDFtGraphPatternStr.replace("\r\n"," ");//去掉换行符；
    String [] statementRDFt = RDFtString.trim().split(" \\.");
        ArrayList<ArrayList<String>> lsls = new SparqlT2SparqlQuery().RDFt2RDFTriple(statementRDFt);

        StringBuilder sparqlPatternSTR =new StringBuilder();
        //将lsls中转换成字符串输出；
        for (int i = 0; i <lsls.size() ; i++) {//3
            ArrayList<String> ls = new ArrayList<String>();
            for (int j = 0; j <lsls.get(i).size() ; j++) {//5
                sparqlPatternSTR.append(lsls.get(i).get(j));
            }
        }
     return sparqlPatternSTR.toString();
    }

    public static void main(String[] args) {
        System.out.println("输入的RDFt基本图模式是：" );
        String s = "<http://yago-knowledge.org/resource/china> <http://yago-knowledge.org/resource/haspopulation[?ts,?te]-?n> ?count . \n\r"+
                "<http://yago-knowledge.org/resource/America> <http://yago-knowledge.org/resource/haspopulation[?ts0,?te0]-?n0> ?count0 .";
        String ansStr = BaseRDFt2RDFPattern(s);
        System.out.println("将RDFt基本图模式转换成RDF基本图模式是：");
        System.out.println(ansStr);
    }
}
