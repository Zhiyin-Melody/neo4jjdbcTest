package RDFtPattern;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author:
 * @Description:将SPARQL[t]复杂图模式转换成SPARQL模式；
 * @Date: Created in 2018/9/28 10:40
 * @Pram:
 */
public class Complex_SPARQLt2SPARQLPattern{
    public static StringBuffer Complex_SPARQLt2SPARQLPattern(String SPARQLTString) {
        StringBuffer SPARQLString = new StringBuffer();
        ArrayList<String> list= new ArrayList<String>();

        if(SPARQLTString.contains("OPTIONAL")){
            String [] rdfgrapgStr = SPARQLTString.split("OPTIONAL");
            for (int i = 0; i <rdfgrapgStr.length ; i++) {
                String s=rdfgrapgStr[i].substring(rdfgrapgStr[i].indexOf("{")+1,rdfgrapgStr[i].lastIndexOf("}"));
                if(i==rdfgrapgStr.length-1){
                    SPARQLString.append("{"+BaseRDFt2RDFPattern.BaseRDFt2RDFPattern(s)+"}");

                }else {
                    SPARQLString.append("{"+BaseRDFt2RDFPattern.BaseRDFt2RDFPattern(s)+"}");
                    SPARQLString.append(" OPTIONAL ");
                }
            }
        }
        else if(SPARQLTString.contains("UNION")){
            String [] rdfgrapgStr = SPARQLTString.split("UNION");
            for (int i = 0; i <rdfgrapgStr.length;i++){
                String s=rdfgrapgStr[i].substring(rdfgrapgStr[i].indexOf("{")+1,rdfgrapgStr[i].lastIndexOf("}"));
                if(i == rdfgrapgStr.length-1){
                    SPARQLString.append("{"+BaseRDFt2RDFPattern.BaseRDFt2RDFPattern(s)+"}");
                }else {
                    SPARQLString.append("{"+BaseRDFt2RDFPattern.BaseRDFt2RDFPattern(s)+"}");
                    SPARQLString.append(" UNION ");
                }
            }
        }
        else if(isMuiltyGrapgh(SPARQLTString)){
            SPARQLTString = SPARQLTString.replaceAll("\n","");
            String [] rdfgrapgStr = SPARQLTString.split("\\}\\{");
            for (int i = 0; i <rdfgrapgStr.length; i++) {
                String s="";
                if(rdfgrapgStr[i].contains("{")){
                      s=rdfgrapgStr[i].substring(rdfgrapgStr[i].indexOf("{")+1);
                }else if(rdfgrapgStr[i].contains("}")){
                      s=rdfgrapgStr[i].substring(0,rdfgrapgStr[i].indexOf("}"));
                }else{
                      s=rdfgrapgStr[i];
                }
                if(i==rdfgrapgStr.length-1){
                    SPARQLString.append("{" + BaseRDFt2RDFPattern.BaseRDFt2RDFPattern(s)+"}");
                }else {
                    SPARQLString.append("{" + BaseRDFt2RDFPattern.BaseRDFt2RDFPattern(s) + "}\n");
                }
            }
        }
        return SPARQLString;
    }

    //判断是否是多图；
    private static boolean isMuiltyGrapgh(String sparqltString) {
        sparqltString=sparqltString.replaceAll("\n","");
        Pattern pattern = Pattern.compile(".*\\{.*\\}\\{.*\\}+.*");
        Matcher matcher = pattern.matcher(sparqltString);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {

        //OPTIONAL模式;
        String graphPattern1 =
                "{S1 P1[ts1,te1]-n1 ?O1 .}\nOPTIONAL" +
                "{S2 P2[ts2,te2]-n2 ?O2 .}\n" ;
        //UNION模式;
        String graphPattern2 =
                "{S1 P1[ts1,te1]-n1 ?O1 .}\nUNION" +
                "{S2 P2[ts2,te2]-n2 ?O2 .}\nUNION"+
                "{S3 P3[ts3,te3]-n3 ?O3 .}\n";
        //多图模式；
        String graphPattern3 =
                "{S1 P1[ts1,te1]-n1 ?O1 .}\n" +
                "{S2 P2[ts2,te2]-n2 ?O2 .}\n" +
                "{S3 P3[ts3,te3]-n3 ?O3 .}\n";

        System.out.println("输入的组图模式是：" + graphPattern1);
        System.out.println("将SPARQL[t]复杂图模式转换成SPARQL模式：");
        System.out.println(Complex_SPARQLt2SPARQLPattern(graphPattern3));
    }
}
