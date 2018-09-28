package TransTest;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import java.util.Scanner;
public class SPARQLT2sparqlPY {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//      System.out.println("http://www.example.org/terms/Lake_Esrum/".split("/"));
		 PythonInterpreter interpreter = new PythonInterpreter();
	     interpreter.execfile("D:\\IDEA Workspace\\neo4jjdbcTest20180311\\LzhiyinS2S.py");
	     System.out.println("=======请选择：");
	        System.out.println("选择1,请输入这样的参数起始时间ts,结束时间te,次数");
	        System.out.println("选择2,请输入这样的参数,国家country,起始时间ts,结束时间te");
	        System.out.println("选择3,退出");
	        while (true) {
	            Scanner input = new Scanner(System.in);
	            String result = input.next();
	            int number = Integer.valueOf(result);
	            switch (number) {         
	            case 1:
	 	            String ts = String.valueOf(input.next());
	 	            String te = String.valueOf(input.next());
	 	            Integer n = Integer.valueOf(input.next());
	            	PyFunction pyFunction = interpreter.get("trans1", PyFunction.class); // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型 
	      	        PyObject pyObject = pyFunction.__call__(new PyString(ts),new PyString(te), new PyInteger(n)); // 调用函数,传参数
	      	        System.out.println(pyObject);
	      	        break;
	            case 2:
	            	    String ts_1 = String.valueOf(input.next());
		 	            String te_1 = String.valueOf(input.next());
		 	            String tc = String.valueOf(input.next());
		            	PyFunction pyFunction_1= interpreter.get("trans2", PyFunction.class); // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型 
		      	        PyObject pyObject_1 = pyFunction_1.__call__(new PyString(ts_1),new PyString(te_1), new PyString(tc)); // 调用函数，传参数
		      	        System.out.println(pyObject_1);
		      	        break;
	            case 3:
	                System.out.println("======退了=====");
	                break;
	            default:
	                break;
	            }
	            
	        }          
            	
            	   	       
      	        
	
	      
      }
	}

