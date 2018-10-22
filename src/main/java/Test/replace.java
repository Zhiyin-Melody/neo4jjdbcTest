package Test;

/**
 * @Author:
 * @Description:
 * @Date: Created in 2018/9/20 0:37
 * @Pram:
 */
public class replace {
    public static void main(String[] args) {
        String s ="\r\n   s..p-n...o.\r\n";
        String s0 ="p-n";
        String s1=s.replaceAll("\r\n"," ");
        System.out.println(s1);
        System.out.println(s0.indexOf("-"));
        System.out.println(s0.length());
        String p = s0.substring(0,s0.indexOf("-"));
        System.out.println(p);
       String n = s0.substring(s0.indexOf("-")+1,s0.length());
        System.out.println(n);

    }
}
