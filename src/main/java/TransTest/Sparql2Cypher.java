package TransTest;


import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.Op;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *@Author:LIZHIYIN
 *@Desciption:将sparqlt转换成Cypher算法；这个带深度优先遍历树结构的算法；
 *@para:输入的是sparql查询语句，返回的是cypher查询语句；
 *@Date:Created in 20180417
 */
public class Sparql2Cypher {
    List<TreeNode> ChildTree = new ArrayList<TreeNode>();

    public String Sparql2Cypher(String string){
        System.out.println("传过来的查询语句是："+string);
        String cypherStirng = "";
        HashMap<String,Integer> vetex = new HashMap<String,Integer>();
        Query query = QueryFactory.create(string);
        Op op = Algebra.compile(query);
        //采用JenaARQ解析SPARQL语句；
        String afterParse = new String(op.toString());
        System.out.println("解析后的字符串是："+afterParse);
        String[] brk = afterParse.split("triple");//将三元组们用triple分割开；
        //从 select部分提取出将返回结果值的变量集合;
        Set<String> RetVarSet = new HashSet<String>();
        RetVarSet.add(brk[0]);//将前面的东西放到RetVarSet中；也就是结果集的描述信息；
        List<String> TriplePatternSet = new ArrayList<String>();//用于存放三元组的；
        for (int i = 1; i <brk.length ; i++) {
            Pattern p = Pattern.compile("[?<>()\n]");
            Matcher m = p.matcher(brk[i]);
            String cleaned = m.replaceAll("").trim();
            TriplePatternSet.add(cleaned);
            String[] arr = cleaned.split(" ");
            if(vetex.get(arr[0])==null){
                vetex.put(arr[0], vetex.keySet().size());
                ChildTree.add(new TreeNode(vetex.get(arr[0]), arr[0]));
            }
            if(vetex.get(arr[2])==null) {
                vetex.put(arr[2], vetex.keySet().size());
                ChildTree.add(new TreeNode(vetex.get(arr[2]), arr[2]));
            }
            System.out.println("brk中存放的是："+cleaned);
        }
        System.out.println(vetex.keySet().size());
       //用邻接表来存储三元组；构建图；
        TreeNode treeNode = null;
        TreeNode treeNode1 = null;
        for(int i = 0; i < TriplePatternSet.size(); i++){
            String[] arr = TriplePatternSet.get(i).split(" ");
            if(vetex.containsKey(arr[1])){
                treeNode = ChildTree.get(vetex.get(arr[1]));
                treeNode.isRel = false;
                ChildTreeNode childNode = new ChildTreeNode(treeNode.idx, null);
                treeNode1 = ChildTree.get(vetex.get(arr[0]));
                childNode.pNext = treeNode1.pChildNext;
                treeNode1.pChildNext = childNode;

                treeNode1 = ChildTree.get(vetex.get(arr[2]));
                childNode = new ChildTreeNode(treeNode1.idx, null);
                childNode.pNext = treeNode.pChildNext;
                treeNode.pChildNext = childNode;
            }else{
                treeNode = ChildTree.get(vetex.get(arr[0]));
                treeNode1 = ChildTree.get(vetex.get(arr[2]));
                ChildTreeNode childNode = new ChildTreeNode(treeNode1.idx, arr[1]);
                childNode.pNext = treeNode.pChildNext;
                treeNode.pChildNext = childNode;
            }
        }
        //打印顶点及索引；
        System.out.println("构建图之后的顶点是："+vetex);
        //打印图；
        for(TreeNode tn:ChildTree){
            String ret = tn.idx+"-";
            ChildTreeNode cn = tn.pChildNext;
            while(cn!=null){
                ret += cn.childIndex + "-";
                cn = cn.pNext;
            }
            System.out.println(ret);
        }
        //深度优先遍历邻接表，记录所有的遍历路径；
        List<List<String>> tn = new ArrayList<List<String>>();
        DFSTraverse(ChildTree.get(0), tn, null, "");
        //打印全部路径；
        System.out.println("全部路径："+tn.size());
        for(List<String> treeNodes:tn){
            System.out.println(treeNodes);
        }
        Set<String> pathSet = new HashSet<String>();
        Iterator it = pathSet.iterator();
        while( it.hasNext()){
//            pathSet.add(pathStr);
        }
        //记录非变量的集合，每一条遍历对应路径对应match的一个分句，
        String matchString = "";
        //for (pathStr:pathSet) {
       //     ConditionNodeSet.add(pathStr);
       //     matchString = pathStr;
       // }
       // 从ConditionNodeSet中任选一个顶点，作为start的起始顶点；
    //    String startString = ConditionNodeSet;
      //  String returnString = RetVarSet;
      //  cypherStirng = startString+matchString+returnString;
        return "";
    }


//深度优先搜索函数；
    private void DFSTraverse(TreeNode root, List<List<String>> tn, List<String> treeNodes, String relName) {
        root.isVisited = true;
        ChildTreeNode p = root.pChildNext;
//        r.append(" ");
//        if (p != null) {
//            r.append(SYMBOLS_L);
//        }
//        r.append(root.data);
        if(treeNodes == null){
            treeNodes = new ArrayList();
            treeNodes.add(root.idx+"");
        }else {
            treeNodes.add(root.idx + "_" + relName);
        }
        while (p != null) {
            List<String> new_treeNodes = new ArrayList<String>();
            new_treeNodes.addAll(treeNodes);
            DFSTraverse(ChildTree.get(p.childIndex), tn, new_treeNodes, p.relName);
            p = p.pNext;
        }
        if (root.pChildNext == null) {
            tn.add(treeNodes);
//            treeNodes = null;
        }
    }

    public static void main(String[] args) {
        Sparql2Cypher sparql2Cypher = new Sparql2Cypher();
        String prefixString = "base <http://yago-knowledge.org/resource/> \n" +
                "prefix dbp: <http://dbpedia.org/ontology/> \n" +
                "prefix owl: <http://www.w3.org/2002/07/owl#> \n" +
                "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
                "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "prefix skos: <http://www.w3.org/2004/02/skos/core#> \n" +
                "prefix xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
                "prefix rdft:<http://www.neu.edu/2018/rdft-syntax-ns#> ";
        String sparqlString = prefixString +
                "SELECT ?s ?count  \n" +
                " WHERE { \n" +
                "\t?s ?haspopulation ?count .\n" +
                "\t?haspopulation  rdf:type rdft:property .\n" +
                "\t?haspopulation  rdft:hasStartTime ?ts .\n" +
                "\t?haspopulation  rdft:hasEndTime ?te .\n" +
                "\t?haspopulation  rdft:hasNumUpdate ?n .\n" +
                " FILTER regex(str(?s), 'china')\n" +
                " FILTER (?ts >= '2000-01-01'^^<http://www.w3.org/2001/XMLSchema#date> && ?ts <= '2000-12-30'^^<http://www.w3.org/2001/XMLSchema#date>)\n" +
                "} LIMIT 50";
        //输入的是sparql查询语句，返回的是cypher查询语句；
        String cypherString = sparql2Cypher.Sparql2Cypher(sparqlString);
        System.out.println("主函数的查询语句："+cypherString);
    }
}
