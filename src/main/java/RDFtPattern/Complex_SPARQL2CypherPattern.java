package RDFtPattern;

/**
 * @Author:
 * @Description:将SPARQL复杂图模式转换成Cypher查询语言；
 * @Date: Created in 2018/9/28 10:42
 * @Pram:
 */
public class Complex_SPARQL2CypherPattern {
    public static StringBuffer Complex_SPARQL2CypherPattern(String SPARQL_Pattern1) {
        StringBuffer cypher_graphStr=new StringBuffer();
        if(SPARQL_Pattern1.contains("OPTIONAL")){
            String [] rdfgrapgStr = SPARQL_Pattern1.split("OPTIONAL");
            for (int i = 0; i <rdfgrapgStr.length ; i++) {
                String s=rdfgrapgStr[i].substring(rdfgrapgStr[i].indexOf("{")+1,rdfgrapgStr[i].lastIndexOf("}"));
                if(i==rdfgrapgStr.length-1){
                    cypher_graphStr.append(BaseRDFPattern2Cypher.BaseRDFPattern2Cypher(s));

                }else {
                    cypher_graphStr.append(BaseRDFPattern2Cypher.BaseRDFPattern2Cypher(s));
                    cypher_graphStr.append(" OPTIONAL ");
                }
            }
        }
        else if(SPARQL_Pattern1.contains("UNION")){
            String [] rdfgrapgStr = SPARQL_Pattern1.split("UNION");
            for (int i = 0; i <rdfgrapgStr.length;i++){
                String s=rdfgrapgStr[i].substring(rdfgrapgStr[i].indexOf("{")+1,rdfgrapgStr[i].lastIndexOf("}"));
                if(i == rdfgrapgStr.length-1){
                    cypher_graphStr.append(BaseRDFPattern2Cypher.BaseRDFPattern2Cypher(s));
                }else {
                    cypher_graphStr.append(BaseRDFPattern2Cypher.BaseRDFPattern2Cypher(s));
                    cypher_graphStr.append(" UNION ");
                }
            }
        }
        else if(Complex_SPARQLt2SPARQLPattern.isMuiltyGrapgh(SPARQL_Pattern1)){
            SPARQL_Pattern1 = SPARQL_Pattern1.replaceAll("\n","");
            String [] rdfgrapgStr = SPARQL_Pattern1.split("\\}\\{");
            for (int i = 0; i <rdfgrapgStr.length; i++) {
                String str="";
                if(rdfgrapgStr[i].contains("{")){
                    str=rdfgrapgStr[i].substring(rdfgrapgStr[i].indexOf("{")+1);
                }else if(rdfgrapgStr[i].contains("}")){
                    str=rdfgrapgStr[i].substring(0,rdfgrapgStr[i].indexOf("}"));
                }else{
                    str=rdfgrapgStr[i];
                }
                if(i==rdfgrapgStr.length-1){
                    cypher_graphStr.append(BaseRDFPattern2Cypher.BaseRDFPattern2Cypher(str));
                }else {
                    cypher_graphStr.append(BaseRDFPattern2Cypher.BaseRDFPattern2Cypher(str));
                }
            }
        }
        return cypher_graphStr;
    }

    public static void main(String[] args) {
        System.out.println("将SPARQL复杂图模式转换成Cypher查询语言；");
        //可选图模式；
        String SPARQL_Pattern1="{ S1 ?p ?O1 . ?p rdf:type rdft:property . ?p rdft:hasStartTime ts1^^xsd:date . ?p rdft:hasEndTime te1^^xsd:date . ?p rdft:hasNumUpdate n .}\n" +
                " OPTIONAL { S2 ?p ?O2 . ?p rdf:type rdft:property . ?p rdft:hasStartTime ts2^^xsd:date . ?p rdft:hasEndTime te2^^xsd:date . ?p rdft:hasNumUpdate n .}\n";
        //多图模式；
        String SPARQL_Pattern2="{ S1 ?p ?O1 . ?p rdf:type rdft:property . ?p rdft:hasStartTime ts1^^xsd:date . ?p rdft:hasEndTime te1^^xsd:date . ?p rdft:hasNumUpdate n .}\n" +
                " UNION { S2 ?p ?O2 . ?p rdf:type rdft:property . ?p rdft:hasStartTime ts2^^xsd:date . ?p rdft:hasEndTime te2^^xsd:date . ?p rdft:hasNumUpdate n .}\n" +
                " UNION { S3 ?p ?O3 . ?p rdf:type rdft:property . ?p rdft:hasStartTime ts3^^xsd:date . ?p rdft:hasEndTime te3^^xsd:date . ?p rdft:hasNumUpdate n .}\n";
        //组图模式；
        String SPARQL_Pattern3="{ S1 ?p ?O1 . ?p rdf:type rdft:property . ?p rdft:hasStartTime ts1^^xsd:date . ?p rdft:hasEndTime te1^^xsd:date . ?p rdft:hasNumUpdate n .}\n" +
                "{ S2 ?p ?O2 . ?p rdf:type rdft:property . ?p rdft:hasStartTime ts2^^xsd:date . ?p rdft:hasEndTime te2^^xsd:date . ?p rdft:hasNumUpdate n .}\n" +
                "{ S3 ?p ?O3 . ?p rdf:type rdft:property . ?p rdft:hasStartTime ts3^^xsd:date . ?p rdft:hasEndTime te3^^xsd:date . ?p rdft:hasNumUpdate n .}\n";
        System.out.println("输入的组图模式是：" + SPARQL_Pattern1);
        System.out.println("将SPARQL复杂图模式转换成Cypher模式：");
        System.out.println(Complex_SPARQL2CypherPattern(SPARQL_Pattern1));

    }
}
