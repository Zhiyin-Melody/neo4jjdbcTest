package Test;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author:
 * @Description:
 * @Date: Created in 2018/9/23 13:24
 * @Pram:
 */
public class Mkuohao {
    public static void main(String[] args) {
        String s="ds(af(323)ld)()sao,(sd)"; //示例文本
        String pattern="(\\([^\\)]+\\))"; //正则表达式，匹配括号内容
        ArrayList list=new ArrayList();
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(s);
        while(m.find()){
            list.add(m.group());
        }
        System.out.println(list.toString());
    }
}
