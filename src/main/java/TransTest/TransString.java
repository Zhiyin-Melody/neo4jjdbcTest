package TransTest;

/**
 * @Author:LIZHIYIN
 * @Description:将转换函数都连接起来，从SPARQL[t]到SPARQL到Cypher，最后输出的是Cypher语句；
 * @Date: Created in 2018/11/17 21:30
 * @Pram:
 */
public class TransString {
    public String trans(String preTransString){
        String ans="";
        StringBuilder SPARQLTString = new StringBuilder();
        SPARQLTString = new SparqlT2SparqlQuery().SparqlT2SparqlMethod(preTransString);
        System.out.println(SPARQLTString);
        String cypherString = new Sparql2CypherQuery().Sparql2Cypher(SPARQLTString.toString());
        ans=cypherString;
        return ans;
    }
    public static void main(String[] args) {
        System.out.println("开始转换：");
        TransString transString = new TransString();
        String string=/*"prefix owl: <http://www.w3.org/2002/07/owl#> \r\n"
                + "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \r\n"
                + "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> \r\n"+*/
                "SELECT ?t ?BirthCity \n" +
                "WHERE {\n" +
                "?People info:hasBirthCity[?t]-1 ?BirthCity .\n" +
                "FILTER ?People=\"Yao_Ming\"}\n";
        transString.trans(string);
    }
}
