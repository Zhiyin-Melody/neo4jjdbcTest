function show(){
    var selectNode=document.getElementById("select");
    var info1=document.getElementById("info1");
    var info2=document.getElementById("info2");
    var NBA=document.getElementById("NBA");
    var YAGO=document.getElementById("YAGO");
    var optGroup1=document.getElementsByClassName("optgroup1");
    var optGroup2=document.getElementsByClassName("optgroup2");
        info1.style.display="none";
        info2.style.display="none";

    if(selectNode.value=="dataset1"){
        info1.style.display="block";
        NBA.style.display="block";
        YAGO.style.display="none";
    }
    if(selectNode.value=="dataset2"){
        info2.style.display="block";
        YAGO.style.display="block";
        NBA.style.display="none";
    }
}
function showqueryString(){
    var selectNode=document.getElementById("selectStr");
    var queryStringTextarea=document.getElementById("queryStringTextarea");
    var prefix="@base <http://www.neu.edu/2018/NBA_Sportinfo#> .\n" +
        "@prefix dbp: <http://dbpedia.org/ontology/> .\n" +
        "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n" +
        "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n" +
        "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" +
        "@prefix skos: <http://www.w3.org/2004/02/skos/core#> .\n" +
        "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n" +
        "@prefix rdft: <http://www.neu.edu/2018/rdft-syntax-ns#> .\n\n";
    if(selectNode.value=="queryString1"){
        queryStringTextarea.value=prefix+
            "SELECT ?t ?BirthCity \n" +
            "WHERE {\n" +
            "?People info:hasBirthCity[?t]-1 ?BirthCity .\n" +
            "FILTER ?People=“Yao_Ming”}\n";
    }
    if(selectNode.value=="queryString2"){
        queryStringTextarea.value=prefix+
            "SELECT ?ts ?te DISTINCT(?team) \n" +
            "WHERE{\n" +
            "?People info:Plays_For[?ts,?te]-?n ?team .\n" +
            "FILTER ?ts >= “1992-01-01” and ?te <= “2001-01-01”\n" +
            "FILTER ?People=“A.C._Green_Jr.”\n" +
            "}\n";
    }
    if(selectNode.value=="queryString3"){
        queryStringTextarea.value=prefix+
            "SELECT ?s ?p ?o \n" +
            "WHERE{\n" +
            "?s info:Name ?name\n" +
            "?s ?p[?ts,?te]-?n ?o .\n" +
            "FILTER ?name =“Shaquille_Rashaun_O'Neal”\n" +
            "} \n" +
            "LIMIT 20\n";
    }
    if(selectNode.value=="queryString4"){
        queryStringTextarea.value=prefix+
            "SELECT ?Team ?Score\n" +
            "WHERE{\n" +
            "{“Allen_Ezail_Iverson“ info:Plays_For[?ts1,?te1]-?n11 ?Team .\n" +
            "“Allen_Ezail_Iverson“ info:Score1[?ts1,?te1]-?n12 ?Score .\n" +
            "FILTER ?Team =\"Philadelphia_76ers\" \n" +
            "FILTER ?ts1 >= “2006-01-01” and ?te1 <= “2007-01-01”}\n" +
            "{“Allen_Ezail_Iverson“ info:Plays_For[?ts2,?te2]-?n21 ?Team .\n" +
            "“Allen_Ezail_Iverson“ info:Score1[?ts2,?te2]-?n22 ?Score .\n" +
            "FILTER ?Team =\"Denver_Nuggets\"\n" +
            "FILTER ?ts2 >= “2006-01-01” and ?te2 <= “2007-01-01”}\n" +
            "}\n";
    }
    if(selectNode.value=="queryString5"){
        queryStringTextarea.value=prefix+
            "SELECT ?Name ?City\n" +
            "WHERE{\n" +
            "{?People info:Name ?Name .}\n" +
            "OPTIONAL\n" +
            "{?People info:hasBirthCity[?t]-?n ?City .}}\n" +
            "OFFSET 5\n" +
            "LIMIT 15\n";
    }
    if(selectNode.value=="queryString6"){
        queryStringTextarea.value=prefix+
            "SELECT (MAX(?Grade1) AS ?Score) (MAX(?Grade2) AS ?Score) \n" +
            "WHERE{\n" +
            "{“Jeremy_ShuHow_Lin“ info:Plays_For[?ts1,?te1]-?n11> \"Houston_Rockets\" .\n" +
            "“Jeremy_ShuHow_Lin“ info:Score1[?ts1, ?te1]-?n12> ?Grade1 .}\n" +
            "UNION\n" +
            "{“Jeremy_ShuHow_Lin“ info:Plays_For[?ts2,?te2]-?n21> \" Los_Angeles_Lakers \" . “Jeremy_ShuHow_Lin“ info:Score1[?ts2, ?te2]-?n22> ?Grade2 .}}\n";
    }
    if(selectNode.value=="queryString7"){
        queryStringTextarea.value=prefix+
            "ASK {“Kobe_Bean_Bryant” info:Plays_For[?ts,?te]-?n \"Los_Angeles_Lakers\" .\n" +
            "FILTER ?ts >= “1996-01-01“ and ?te <= ”2016-12-30“\n" +
            "}\n";
    }
    if(selectNode.value=="queryString8"){
        queryStringTextarea.value=prefix+
            "CONSTRUCT {“Kobe_Bean_Bryant“ info:isTeammate[?ts1,?te1]-1 ”Shaquille_Rashaun_O'Neal“ .}\n" +
            "WHERE{\n" +
            "“Kobe_Bean_Bryant“ info:Plays_For[?ts1,?te1]-?n1 \"Los_Angeles_Lakers\" .\n" +
            "“Shaquille_Rashaun_O'Neal“ info:Plays_For[?ts2,?te2]-?n2 \"Los_Angeles_Lakers\" .\n" +
            "FILTER (?ts1 >= ?ts2 and ?te1 <= ?te2) or (?ts1 <= ?ts2 and ?te1 >= ?te2)}\n";
    }
    if(selectNode.value=="queryString9"){
        queryStringTextarea.value=prefix+
            "DESCRIBE ?People {?People info:Plays_For[?ts,?te]-?n \"Golden_State_Warriors\" . \n" +
            "FILTER ?ts >= “2017-12-30“ and ?te <= ”2018-12-30“\n" +
            "}\n";
    }
}