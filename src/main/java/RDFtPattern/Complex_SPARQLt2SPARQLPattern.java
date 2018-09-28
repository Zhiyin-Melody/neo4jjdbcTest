package RDFtPattern;

/**
 * @Author:
 * @Description:将SPARQL[t]复杂图模式转换成SPARQL模式；
 * @Date: Created in 2018/9/28 10:40
 * @Pram:
 */
public class Complex_SPARQLt2SPARQLPattern {
    public static String Complex_SPARQLt2SPARQLPattern(String graphPattern) {
        String SPARQLgraphStr="";

        return SPARQLgraphStr;
    }

    public static void main(String[] args) {
        System.out.println("输入的组图模式是：");
        String graphPattern = "SELECT ?O1,?O2 \n" +
                "WHERE{\n" +
                "{S1, P1[ts1,te1]-n1,?O1}\n" +
                "{S2, P2[ts2,te2]-n2,?O2}\n" +
                "}\n";
        System.out.println("将SPARQL[t]复杂图模式转换成SPARQL模式");
        System.out.println(Complex_SPARQLt2SPARQLPattern(graphPattern));
    }
}
