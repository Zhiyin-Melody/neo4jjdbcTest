package Test;

import java.io.*;

/**
 * @Author:
 * @Description:
 * @Date: Created in 2018/10/25 9:51
 * @Pram:
 */
public class readTTlFile {
    public static void readTxtFile(String filePath){
        try {
            String encoding="UTF-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader( new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;//<id_y8wzee_1ul_t19226>	<extractionSource>	<http://en.wikipedia.org/wiki/Juan_Mauricio_Soler>-.
                String finds="Chukwuma_Akabueze";
                        //"<http://en.wikipedia.org/wiki/Juan_Mauricio_Soler>";//"<id_y8wzee_1ul_t19226>"
                while((lineTxt = bufferedReader.readLine()) != null){
                    if(lineTxt.contains(finds)){
                        System.out.println("true");
                        System.out.println(lineTxt);
                    }

                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        }
        catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }
    public static void main(String[] args) throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader("D:\\DataSet\\yago_no01\\yagoGeonamesOnlyData.ttl"));
        String str = null;
        String filePath ="D:\\DataSet\\yago_no\\yagoConteXtFacts_en.ttl";//<Ian_Rodgerson>occursSince<id_y8wzee_1ul_t19226>
        int lineNumber = 0;
       /* while ((str = bfr.readLine()) != null) {
            lineNumber++;
            System.out.println(lineNumber + " " + str);
        }*/
        /*str = bfr.readLine();
        for (int i = 0; i < 500 ; i++) {
            lineNumber++;
            System.out.println(lineNumber + " " + str);
        }
        bfr.close();*/
        readTxtFile(filePath);
        System.out.println("完毕");
    }
}
/*
* true
<Juan_Mauricio_Soler>	<hasWikipediaAnchorText>	"Mauricio Soler"@eng .
true
<Juan_Mauricio_Soler_Hernández>	<hasWikipediaAnchorText>	"Mauricio Soler"@eng .
true
<Juan_Mauricio_Soler_Hernandez>	<hasWikipediaAnchorText>	"Mauricio Soler"@eng .
true
<Juan_Mauricio_Soler_Rodriguez>	<hasWikipediaAnchorText>	"Mauricio Soler"@eng .
完毕
* */