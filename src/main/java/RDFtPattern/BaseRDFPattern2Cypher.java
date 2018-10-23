package RDFtPattern;

import TransTest.Sparql2CypherQuery;
/*这个代码需要再继续改改；
* */
/**
 * @Author:
 * @Description:将SPARQLtPattern转换成cypher查询形式；
 * @Date: Created in 2018/9/28 10:37
 * @Pram:
 */
public class BaseRDFPattern2Cypher {
    public static String BaseRDFPattern2Cypher(String RDFGraphPattern) {
        RDFGraphPattern="0 . "+RDFGraphPattern;//将s[0]占据位置，避免后面转换出现冲突；

        String[] s = RDFGraphPattern.split(" \\. ");
        for (int i = 0; i <s.length ; i++) {
            if (s[i].contains("rdft:")){
                s[i]=s[i].replaceAll("rdft:","");
            }
            if(s[i].contains("rdf:")){
                s[i]=s[i].replaceAll("rdf:","");
            }
        }
        StringBuffer C1=new StringBuffer();
        StringBuffer C2=new StringBuffer();

        StringBuffer CypherStr =new Sparql2CypherQuery().MatchStringConstruct(C1,C2,s);
        return CypherStr.toString();
    }

    public static void main(String[] args) {
        System.out.println("输入的RDFPattern形式：");
        String RDFGraphPattern= "<http://yago-knowledge.org/resource/china> ?p ?count . ?p rdf:type rdft:property . ?p rdft:hasStartTime ?ts^^xsd:date . ?p rdft:hasEndTime ?te^^xsd:date . ?p rdft:hasNumUpdate ?n . <http://yago-knowledge.org/resource/America> ?p ?count0 . ?p rdf:type rdft:property . ?p rdft:hasStartTime ?ts0^^xsd:date . ?p rdft:hasEndTime ?te0^^xsd:date . ?p rdft:hasNumUpdate ?n0 .\n";
        System.out.println("将SPARQLtPattern转换成cypher查询形式:");
        String CypherStr=BaseRDFPattern2Cypher(RDFGraphPattern);
        System.out.println(CypherStr);
    }
}
