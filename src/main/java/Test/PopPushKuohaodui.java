package Test;

import java.util.Stack;

/**
 * @Author:
 * @Description:
 * @Date: Created in 2018/9/23 1:03
 * @Pram:
 */
public class PopPushKuohaodui {


        public static void main(String[] args){
            String s="(hdv)(dv)(dvgvv)(ssd)";
            System.out.println(isComplete(s));
        }

        public static boolean isComplete(String s){
            Stack<String> left=new Stack<String>();
            while (s.length()!=0){
                //取字符串首字母
                String character=s.substring(0,1);
                //剩余的字符串
                s=s.substring(1);
                if(character.equals("(")){
                    //如果是左括号，则压入栈
                    left.push(character);
                }else if(character.equals(")")||character.equals("]")||character.equals("}")){
                    //首先检查栈是否为空
                    if(left.isEmpty())
                        return false;
                    //弹出最后的左括号
                    String leftChar=left.pop();
                    //检查左右括号是否匹配
                    if(character.equals(")")){
                        if(!leftChar.equals("("))
                            return false;
                    }else if(character.equals("]")){
                        if(!leftChar.equals("["))
                            return false;
                    }else if(character.equals("}")){
                        if(!leftChar.equals("{"))
                            return false;
                    }
                }
            }
            //此时栈中不应该再有左括号
            return left.isEmpty();
        }
    }


