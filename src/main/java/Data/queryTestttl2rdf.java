package Data;
/**
 *@Author:LIZHIYIN
 *@Desciption:将RDFt2RDF数据存储的文件；以RDF形式存入文件中；
 *@para:
 *@Date:Created in 20180311
 */
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class queryTestttl2rdf {
	public static void parsettl(String fileName) throws IOException {
		FileWriter file;
		file = new FileWriter("D://a//new2.txt");// 将RDFt2RDF数据存储的文件；
		BufferedWriter out = new BufferedWriter(file);
		out.write("@base <http://yago-knowledge.org/resource/> .\r\n"
				+ "@prefix dbp: <http://dbpedia.org/ontology/> .\r\n"
				+ "@prefix owl: <http://www.w3.org/2002/07/owl#> .\r\n"
				+ "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\r\n"
				+ "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\r\n"
				+ "@prefix skos: <http://www.w3.org/2004/02/skos/core#> .\r\n"
				+ "@prefix rdft: <http://www.w3.org/2018/03/01-rdft-syntax-ns#> .\r\n"
				+ "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\r\n\r\n");
		
		out.flush();

		// 以下这一部分就是将RDFT数据2RDF数据的方法；
		Model model = ModelFactory.createDefaultModel();
		InputStream in = FileManager.get().open(fileName);

		if (in == null) {
			throw new IllegalArgumentException("File: " + fileName
					+ " not found");
		}

		model.read(in, "", "TTL");

		StmtIterator iter = model.listStatements();

		while (iter.hasNext()) {
			Statement stmt = iter.nextStatement();
			String subject = stmt.getSubject().toString();
			String predicate = stmt.getPredicate().toString();
			RDFNode object = stmt.getObject();

			System.out.println(" 主语 " + subject);
			System.out.println(" RDFt谓语 " + predicate);
			String subPredicate = predicate
					.substring(0, predicate.indexOf('['));
			String hasTime = predicate.substring(predicate.indexOf('['),
					predicate.indexOf(']') + 1);
			String hasStartTime = " ";
			String hasEndTime = " ";
			if (hasTime.contains(",")) {
				hasStartTime = hasTime.substring(hasTime.indexOf('[') + 1,
						hasTime.indexOf(','));
				hasEndTime = hasTime.substring(hasTime.indexOf(',') + 1,
						hasTime.indexOf(']'));
			}

			String subpredicatehasUpdate = predicate.substring(
					predicate.indexOf(']') + 2, predicate.length());
			System.out.println(" 谓语 部分" + subPredicate + "-----表示时间" + hasTime
					+ "----表示ts" + hasStartTime + "---表示te" + hasEndTime
					+ "表示更新次数" + subpredicatehasUpdate);
			if (object instanceof Resource) // 宾语为新的实体
			{
				System.out.print(" 宾语 " + object + " . \n");
			} else {// 宾语为字面量
				System.out.print("宾语 \"" + object.toString() + "\" . \n");
			}

			// 以下是将RDFt数据转换为RDF数据模型;具体化表示算法;syntactic Translation

			// 先把前缀缩写写进文件中；

			out.append("<" + subject+ ">" + " ");// 写入主语；

			out.append("<" + subPredicate+ ">" + " ");// 写入谓语；
            //-----------------
			if (object instanceof Resource) {
				String objectString = object.toString();
				if (objectString.contains("^^")) {
					int index = objectString.indexOf("^^");
					System.out.print("输出的" + index);
					out.write("\"");
					out.write(objectString, 0, index);
					out.write("\"^^");
					//
					// 把字符串分开来输出
					String s = objectString.substring(index + 2,
							objectString.length());

					String subobjectString1 = s;
					out.write("<");
					System.out.print("输出的" + subobjectString1);
					subobjectString1 = subobjectString1.substring(
							subobjectString1.lastIndexOf("/") + 1,
							subobjectString1.length());

					out.write(subobjectString1, 0,
							subobjectString1.length());
					System.out.print("输出的" + objectString);
					out.write("> . \r\n");
				} else {
					out.write("<");
					objectString = objectString.substring(
							objectString.lastIndexOf("/") + 1,
							objectString.length());

					out.write(objectString, 0, objectString.length());
					out.write("> . \r\n");
				}
			}
			// 以下这段意味着宾语是文字类型；
			else {
				String objectString = object.toString();
				if (objectString.contains("^^")) {
					int index = objectString.indexOf("^^");
					System.out.print("输出的" + index);
					out.write("\"");
					out.write(objectString, 0, index);
					out.write("\"^^");
					//
					// 把字符串分开来输出
					String s = objectString.substring(index + 2,
							objectString.length());

					String subobjectString1 = s;
					out.write("<");
					System.out.print("输出的" + subobjectString1);
					subobjectString1 = subobjectString1.substring(
							subobjectString1.lastIndexOf("/") + 1,
							subobjectString1.length());
					out.write(subobjectString1, 0,
							subobjectString1.length());
					System.out.print("输出的" + objectString);
					out.write("> . \r\n");
				} else {
					out.write("\"");
					objectString = objectString.substring(
							objectString.lastIndexOf("/") + 1,
							objectString.length());
					out.write(objectString, 0, objectString.length());
					out.write("\" . \r\n");
				}
			}
		
			//----------------
//			out.append(object.toString() + " . \r\n");// 写入宾语；不是固定的需要instanceOf一下；

			out.append("<" + subPredicate + ">" + " a rdf:type . \r\n");
			if (predicate.contains(",")) {
				out.append("<" + subPredicate + ">" + " rdft:hasStartTime \""
						+ hasStartTime + "\"^^xsd:date . \r\n");
				out.append("<" + subPredicate + ">" + " rdft:hasEndTime \""
						+ hasEndTime + "\"^^xsd:date . \r\n");

			} else {
				out.append("<" + subPredicate + ">" + " rdft:hasTime \""
						+ hasTime + "\"^^xsd:date . \r\n");

			}
			out.append("<" + subPredicate + ">" + " rdft:hasNumUpdate \""
					+ subpredicatehasUpdate + "\"^^xsd:integer . \r\n");
			// out.append("一个RDF GRAPH \r\n");s
			out.flush();

			// 将RDFt2RDF数据存储的文件；
	
		}
	}

	public static void main(String[] args) {
		String fileNamein = "D:\\a\\a.ttl";

		try {
			parsettl(fileNamein);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 之后再将数据写入进文件中，然后可以供SPARQL查询；

	}

}
