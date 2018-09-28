package Data;
/**
 *@Author:LIZHIYIN
 *@Desciption:把前缀缩写写进文件中；
 *@para:
 *@Date:Created in 20180311
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class prefixTest {
	public static void main(String[] args){
	FileWriter file;
	
		try {
			file = new FileWriter("D://a//new2.txt");
			@SuppressWarnings("resource")
			BufferedWriter out = new BufferedWriter(file);
			// 先把前缀缩写写进文件中；
			out.write("@base <http://yago-knowledge.org/resource/> .\r\n"
					+ "@prefix dbp: <http://dbpedia.org/ontology/> .\r\n"
					+ "@prefix owl: <http://www.w3.org/2002/07/owl#> .\r\n"
					+ "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\r\n"
					+ "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\r\n"
					+ "@prefix skos: <http://www.w3.org/2004/02/skos/core#> .\r\n"
					+ "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\r\n\r\n");
		    
		     out.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
}
