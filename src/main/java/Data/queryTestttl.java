package Data;
/**
 *@Author:LIZHIYIN
 *@Desciption:查询.ttl文件中的数据；
 *@para:
 *@Date:Created in 20180311
 */
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;

import java.io.InputStream;

	public class queryTestttl { 
	    public static void parsettl(String fileName){
	        Model model = ModelFactory.createDefaultModel();  
	        InputStream in = FileManager.get().open(fileName);
	        
	        if (in == null)   
	        {  
	            throw new IllegalArgumentException("File: " + fileName + " not found");  
	        }  
	  
	        model.read(in, "","TTL");  
	       
	        StmtIterator iter = model.listStatements();  
	  
//	        while (iter.hasNext())   
//	        {  
//	            Statement stmt = iter.nextStatement();  
//	            String subject = stmt.getSubject().toString(); 
//	            String predicate = stmt.getPredicate().toString();
//	            RDFNode object = stmt.getObject();  
//	            System.out.print(" 主语 " + subject);
//	            System.out.print(" 谓语 " + predicate);
//	            
//	            if (object instanceof Resource)   //宾语为新的实体
//	            {  
//	                System.out.print(" 宾语 " + object+" . \n");  
//	            }  
//	            else {// 宾语为字面量  
//	                System.out.print(" 宾语 \"" + object.toString() + "\" . \n");  
//	            }   
//	        }
	        //------------以下执行查询语句；
	        String queryString_01 = "prefix owl: <http://www.w3.org/2002/07/owl#> "
					+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
					+ "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
					+"prefix rdft:<http://www.neu.edu/2018/rdft-syntax-ns#> "
					+ "SELECT ?s ?count ?ts "
					+ "WHERE { "
					+ " ?s ?haspopulation ?count ."
//					<http://yago-knowledge.org/resource/china>
					+"?haspopulation  rdf:type rdft:property ."
//
					+"?haspopulation  rdft:hasStartTime ?ts ."
					+"?haspopulation  rdft:hasEndTime ?te ."
					+"?haspopulation  rdft:hasNumUpdate ?n ."

					+"FILTER regex(str(?s), 'china')"
			        +"FILTER (?ts >= '1993-01-01'^^<http://www.w3.org/2001/XMLSchema#date> && ?ts <= '2000-12-30'^^<http://www.w3.org/2001/XMLSchema#date>)"
					+ "}"
					+ "LIMIT 50";// 这里是model中的前50个三元组；并不是说每个.rdf文件的前50个三元组；
			
	        Query query = QueryFactory.create(queryString_01);
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			
			ResultSet results = qe.execSelect();
//			String s = results.next().toString();//结果变字符串输出了；
//			System.out.println(results.getResultVars().toString());
//			System.out.println("s是"+s);
			System.out.println("查询结果是：");
			ResultSetFormatter.out(System.out,
					(ResultSet) results, query);

	        
	    }
	    public static void main(String[] args){
	        String fileName = "D:\\a\\d.ttl";
	        parsettl(fileName);
	    }
	}

