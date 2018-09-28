package DB;
/*
* String prefixString = "base <http://yago-knowledge.org/resource/> \n" +
                "prefix dbp: <http://dbpedia.org/ontology/> \n" +
                "prefix owl: <http://www.w3.org/2002/07/owl#> \n" +
                "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
                "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "prefix skos: <http://www.w3.org/2004/02/skos/core#> \n" +
                "prefix xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
                "prefix rdft:<http://www.neu.edu/2018/rdft-syntax-ns#> ";

                CREATE INDEX ON :Resource(uri)
                CREATE INDEX ON :URI(uri)
                CREATE INDEX ON :BNode(uri)
                CREATE INDEX ON :Class(uri)

* */
/**
 * @Author:
 * @Description:将用到的前缀写入进Neo4j数据库中；
 * @Date: Created in 2018/9/28 9:05
 * @Pram:
 */

public class WritePrefix {
    public WritePrefix() {
        String PrefixString = "CREATE (:NamespacePrefixDefinition{`http://www.w3.org/1999/02/22-rdf-syntax-ns#`:'rdf',`http://www.w3.org/2000/01/rdf-schema#`:'rdfs',`http://www.w3.org/2002/07/owl#`:'owl',`http://www.neu.edu/2018/rdft-syntax-ns#`:'rdft',`http://www.w3.org/2001/XMLSchema#`:'xsd',`http://dbpedia.org/ontology/`:'dbp',`http://yago-knowledge.org/resource/`:'@base'})";
        String resourceStr = "CREATE INDEX ON :Resource(uri)";
        String uriStr = "CREATE INDEX ON :URI(uri)";
        String bNodeStr = "CREATE INDEX ON :BNode(uri)";
        String classStr = "CREATE INDEX ON :Class(uri)";

    }

    public static void main(String[] args) {
        WritePrefix writePrefix=new WritePrefix();

    }
}
