package TransTest;
/**
 *@Author:LIZHIYIN
 *@Desciption:匹配查询语句中FILTER第一次出现的索引位置。
 *@para:
 *@Date:Created in 20180417
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SparqlT2SparqlMatcher {

    public int SparqlT2SparqlMatcher(String string,String str){
    Matcher matcher=Pattern.compile(str).matcher(string);
            if(matcher.find()){
        return matcher.start();
    }else{
        return -1;
    }
}

    public static void main(String[] args) {
        SparqlT2SparqlMatcher sparqlT2SparqlMatcher = new SparqlT2SparqlMatcher();

    }
}
