package Test;
/**
 *@Author:LIZHIYIN
 *@Desciption:测试数据。返回字符串中子字符串第一次出现的位置索引，
 *@para:
 *@Date:Created in 20180417
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherTest {

        public static void main(String[] args) {
            String str = "yulv # 123456 # yulv@21cn.com";
            Matcher matcher=Pattern.compile("#").matcher(str);
            if(matcher.find()){
                System.out.println(matcher.start());
            }else{
                System.out.println("null");
            }
            //String receiverName=str.substring(0,matcher.start()).trim();
            //System.out.println(receiverName);
        }

}
