package Display;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author:lizhiyin
 * @Description:（自己写的代码）将关系和节点转换成json的形式；
 * @Date: Created in 2018/11/16 9:57
 * @Pram:
 */
public class ToJson {

    private String json = "";

    //将格式化节点数据和关系数据拼凑成完整的json数据

    public ToJson(StringBuffer relationNodesBuffer, StringBuffer relationBuffer){

        StringBuffer result = new StringBuffer("");

        result.append("{");

        result.append(relationNodesBuffer);

        result.append(",");

        result.append("\n");//换行

        result.append(relationBuffer);

        result.append("}");



        json = result.toString();



    }



    //将json数据写入文件

    public void writeJson(){

        try {

            FileOutputStream fileOutputStream = new FileOutputStream("D:\\IDEA Workspace\\neo4jjdbcTest\\web\\Data\\NBAdata.json");

            fileOutputStream.write(json.getBytes());

            fileOutputStream.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }



    //返回完整的json数据

    public String getJson(){

        return json;

    }

}
