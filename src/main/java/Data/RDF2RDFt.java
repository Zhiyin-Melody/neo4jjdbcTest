package Data;
/**
 *@Author:LIZHIYIN
 *@Desciption:将RDF数据转换成RDFt数据存储的文件；
 *@para:
 *@Date:Created in 20180311
 */
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class RDF2RDFt {

	public static void main(String args[]) {
		// String inputFileName = "E:\\Pattern Mining\\test.rdf";
		String inputFileName = "D:\\DataSet\\test\\yagoDateTest.ttl";
		// String inputFileName = "E:\\Pattern Mining\\test.ttl";
		FileWriter file;
		try {
			file = new FileWriter("D://a//new4.txt");
			BufferedWriter out = new BufferedWriter(file);
			// 先把前缀缩写写进文件中；
			out.write("@base <http://yago-knowledge.org/resource/> .\r\n"
					+ "@prefix dbp: <http://dbpedia.org/ontology/> .\r\n"
					+ "@prefix owl: <http://www.w3.org/2002/07/owl#> .\r\n"
					+ "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\r\n"
					+ "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\r\n"
					+ "@prefix skos: <http://www.w3.org/2004/02/skos/core#> .\r\n"
					+ "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\r\n\r\n");
			Model model = ModelFactory.createDefaultModel();

			InputStream in = FileManager.get().open(inputFileName);
			if (in == null) {
				throw new IllegalArgumentException("File: " + inputFileName
						+ " not found");
			}

			// model.read(in, "","RDF/XML");//根据文件格式选用参数即可解析不同类型
			// model.read(in, "","N3");
			model.read(in, "", "TTL");

			// list the statements in the graph
			StmtIterator iter = model.listStatements();

			// print out the predicate, subject and object of each statement
			while (iter.hasNext()) {
				Statement stmt = iter.nextStatement(); // get next statement
				// Resource subject = stmt.getSubject(); // get the subject
				// Property predicate = stmt.getPredicate(); // get the
				// predicate
				// RDFNode object = stmt.getObject(); // get the object

				String subject = stmt.getSubject().toString(); // get the subject
				String predicate = stmt.getPredicate().toString(); // get the predicate
				RDFNode object = stmt.getObject(); // get the object

				// 以下是随机生成时间的方式
				RandTime rdtime = new RandTime();
				String resulttime = rdtime.RandTime();

				// 构造开始日期
				// ****************
				int i = new Random().nextInt(10) + 1;
				String stRegx = predicate + "[" + resulttime + "]:"
						+ String.valueOf(i) + "";
				// System.out.print(" 谓语 ");
				// System.out.println(stRegx + "\n");
				try {
					out.write("<");
					subject = subject.substring(subject.lastIndexOf("/") + 1,
							subject.length());
					out.write(subject, 0, subject.length());
					out.write("> <");
					stRegx = stRegx.substring(stRegx.lastIndexOf("/") + 1,
							stRegx.length());

					out.write(stRegx, 0, stRegx.length());
					out.write("> ");
					// 处理宾语;需要判断宾语是实例还是文字类型;
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
					// out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// out.close();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
