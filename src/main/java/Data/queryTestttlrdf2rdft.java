package Data;
/**
 *@Author:LIZHIYIN
 *@Desciption:查询的是RDFt转换RDF形式后的主谓宾；
 *@para:
 *@Date:Created in 20180311
 */

	import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;

import java.io.InputStream;

	public class queryTestttlrdf2rdft { 
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
					+ "SELECT ?s ?p ?o "
					+ "WHERE {?s ?p ?o}" + "LIMIT 10";// 这里是model中的前50个三元组；并不是说每个.rdf文件的前50个三元组；
			
	        Query query = QueryFactory.create(queryString_01);
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			
			ResultSet results = qe.execSelect();
		
			System.out.println("查询结果是：");
			ResultSetFormatter.out(System.out,
					(ResultSet) results, query);

	        
	    }
	    public static void main(String[] args){
	        String fileName = "D:\\a\\3.ttl";
	        parsettl(fileName);
	    }
	}

