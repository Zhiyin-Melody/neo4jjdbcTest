package Test;

/**
 * @Author:
 * @Description:
 * @Date: Created in 2018/10/23 10:34
 * @Pram:
 */
public class split {
    public static void main(String[] args) {
        String SPARQL_Pattern1="{S1 ?p ?O1 . ?p rdf:type rdft:property . ?p rdft:hasStartTime ts1^^xsd:date . ?p rdft:hasEndTime te1^^xsd:date . ?p rdft:hasNumUpdate 4 .";
        String A[] =SPARQL_Pattern1.split(" \\. ");//.要转义；

        System.out.println("zhzhi ");
    }
}
