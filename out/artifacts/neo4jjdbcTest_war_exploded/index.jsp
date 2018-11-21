<%--
  Created by IntelliJ IDEA.
  User: lizhiyin
  Date: 2018/10/22
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/codemirror.css">
    <script type="text/javascript" src="js/DatasetIntroduction.js"></script>

    <script type="text/javascript" src="js/jquery-2.1.4.js"></script><%--记住最先引用--%>
    <script type="text/javascript" src="js/ResultMuiltyShow.js"></script>
    <script type="text/javascript" src="zoomcharts/zoomcharts.js"></script>
    <script type="text/javascript" src="js/dispalyByZoomCharts.js"></script>
    <script type="text/javascript" src="js/dispalyByZoomCharts1.js"></script>

    <script type="text/javascript" src="js/jQuery.js"></script>
    <script type="text/javascript" src="js/d3.js"></script>
    <script type="text/javascript" src="js/d3.min.js"></script>
    <script type="text/javascript" src="js/SPARQLTQueryRunning.js"></script>

    <script type="text/javascript" src="js/ResultMuiltyShow.js"></script>
    <script type="text/javascript" src="js/echartResultShow.js"></script>
    <link href="css/tableorchartshow.css" rel="stylesheet" />


    <title>RDFt时序数据查询原型系统</title>
</head>
<body >
<H3 id="title">RDFt时序数据查询原型系统</H3>

<div class="datasets">


    <div class="IntroductionDataset">

        <div class="information" >
            <div class="endpointWrapper">
                <p id="head">数据集介绍</p>
                <select name="select" id="select" class="endpointText form-control selectized" onchange="show(this.value)">
                    <option value="datasets" selected="selected">-请选择-</option>
                    <div class="selectize-control endpointText form-control single plugin-allowRegularTextInput">
                        <div class="optgroup" >
                            <option value="dataset1">
                                <div class="endpointOptionRow selected" data-selectable="" data-value="dataset1">
                                    <div class="endpointUrl">dataset1：</div>
                                    <div class="endpointTitle">NBA篮球运动员个人职业生涯信息</div>
                                </div>
                            </option>
                            <option value="dataset2">
                                <div class="endpointOptionRow selected" data-selectable="" data-value="dataset2">
                                    <div class="endpointUrl">dataset2：</div>
                                    <div class="endpointTitle">YAGO时序部分数据集</div>
                                </div>
                            </option>
                        </div>
                    </div>
                </select>
            </div>
            <p id="info1" style="display: none">    dataset1数据集，主要介绍了NBA篮球运动员个人信息以及历届比赛（常规赛）成绩信息。
                从官方网站上爬取了约4,000个NBA球星的基本信息，构造了125,793条RDFt数据模型的三元组。
                主要包括10种谓语关系，其中，包括不带有时间信息的基本的三元组，带有时间点的RDFt三元组以及带有时间间隔的RDFt三元组。
                具体如下表：
            <table border="1" cellspacing="0" cellpadding="0">
                <tr >
                    <td>关系</td><td>数量</td>
                </tr>
                <tr>
                    <td>hasBirthCity</td><td>3,519</td>
                </tr>
                <tr>
                    <td>Stature</td><td>4,579</td>
                </tr>
                <tr>
                    <td>Weight</td><td>4,573</td>
                </tr>
                <tr>
                    <td>High_School</td><td>3,855</td>
                </tr>
                <tr>
                    <td>Position</td><td>5,658</td>
                </tr>
                <tr>
                    <td>Plays_For</td><td>24,761</td>
                </tr>
                <tr>
                    <td>Rebound</td><td>24,761</td>
                </tr>
                <tr>
                    <td>Assist</td><td>24,761</td>
                </tr>
                <tr>
                    <td>Score1</td><td>24,761</td>
                </tr>
            </table>
            </p>
            <p id="info2" style="display: none">
                dataset2数据集，是YAGO数据集。主要是集成了Wikipedia，WordNet和GeoNames三个来源的数据集，
                大约1.2亿条三元组知识。还包括了表示时间和空间的数据集，从而完成了yago2的构建，
                又利用相同的方法对10种语言对维基百科的进行抽取整理，构建了yago3，大约有459万个实体，
                2400万个Facts。对于表示时空关系的数据集有约200万条三元组。具体如下表：
            <table border="1" cellspacing="0" cellpadding="0">
                <tr >
                    <td>关系</td><td>数量</td>
                </tr>
                <tr>
                    <td>DiedIn</td><td>22,274</td>
                </tr>
                <tr>
                    <td>diedOnDate</td><td>315,528</td>
                </tr>
                <tr>
                    <td>happenedIn</td><td>5,192</td>
                </tr>
                <tr>
                    <td>happenedOnDate</td><td>22,039</td>
                </tr>
                <tr>
                    <td>occursSince/Until2</td><td>9,840</td>
                </tr>
                <tr>
                    <td>wasBornIn</td><td>56,415</td>
                </tr>
                <tr>
                    <td>wasBornOnDate</td><td>685,746</td>
                </tr>
                <tr>
                    <td>wasCreatedOnDate</td><td>467,194</td>
                </tr>
                <tr>
                    <td>wasDestoryedOnDate</td><td>24,218</td>
                </tr>
                <tr>
                    <td>playsFor</td><td>525,374</td>
                </tr>
                <tr>
                    <td>isAffiliatedTo</td><td>579,397</td>
                </tr>
            </table>
            </p>
        </div>
    </div>

    <div class="viewData">
        <p>Neo4J存储形式</p>
        <%--<img id="NBA" src="img/运动员数据集2.png" style="display: none">
        <img id="YAGO" src="img/运动员数据集图片.png" style="display: none">--%>

        <%--Neo4J数据库展示部分；style="background: #e8eeed"--%>
        <div id="echartDemo" >

        </div>
        <div id="echartDemo1" >

        </div>

    </div>


</div>

<div class="query">
    <p>SPARQL[t]查询语言示例</p>

    <select id="selectStr" class="endpointText form-control selectized" onchange="showqueryString(this.value)">
        <option value="queryString" selected="selected">SPARQL[t]查询语言示例-请选择-</option>
        <div class="selectize-control endpointText form-control single plugin-allowRegularTextInput">
            <div class="optgroup1">
                <option value="queryString1">
                    <div class="endpointOptionRow selected" data-selectable="" data-value="queryString1">
                        <div class="queryAnnotation">SELECT查询姚明出生时间和地点。</div>
                    </div>
                </option>
                <option value="queryString2">
                    <div class="endpointOptionRow selected" data-selectable="" data-value="queryString2">
                        <div class="queryAnnotation">SELECT查询1992年到2001年A.C. 格林.的职业生涯信息。</div>
                    </div>
                </option>
                <option value="queryString3">
                    <div class="endpointOptionRow selected" data-selectable="" data-value="queryString3">
                        <div class="queryAnnotation">SELECT查询沙奎尔-奥尼尔的历史记录信息,选取前20条记录。</div>
                    </div>
                </option>
                <option value="queryString4">
                    <div class="endpointOptionRow selected" data-selectable="" data-value="queryString4">
                        <div class="queryAnnotation">SELECT查询阿伦-艾弗森在2006年到2007之间分别在费城76人和丹佛掘金两个队中的得分成绩。</div>
                    </div>
                </option>
                <option value="queryString5">
                    <div class="endpointOptionRow selected" data-selectable="" data-value="queryString5">
                        <div class="queryAnnotation">SELECT查询运动员信息，出生城市作为可选属性（OPTIONAL），结果集约束到5-15条数据。</div>
                    </div>
                </option>
                <option value="queryString6">
                    <div class="endpointOptionRow selected" data-selectable="" data-value="queryString6">
                        <div class="queryAnnotation">SELECT查询林书豪在火箭队或者在湖人队中最高总成绩（UNION）。</div>
                    </div>
                </option>
                <option value="queryString7">
                    <div class="endpointOptionRow selected" data-selectable="" data-value="queryString7">
                        <div class="queryAnnotation">ASK查询科比在1996到2016年是否效力湖人</div>
                    </div>
                </option>
                <option value="queryString8">
                    <div class="endpointOptionRow selected" data-selectable="" data-value="queryString8">
                        <div class="queryAnnotation">CONSTRUCT构建构造一个新的关系两个运动员是队友info:isTeammate。</div>
                    </div>
                </option>
                <option value="queryString9">
                    <div class="endpointOptionRow selected" data-selectable="" data-value="queryString9">
                        <div class="queryAnnotation">DESCRIBE查询2018年勇士的所有球员个人信息。</div>
                    </div>
                </option>
            </div>

            <div class="optgroup2">
                <option value="queryString10">
                    <div class="endpointOptionRow selected" data-selectable="" data-value="queryString10">
                        <div class="queryAnnotation">SELECT查询Fabio_Ongaro出生日期和地点。</div>
                    </div>
                </option>
                <option value="queryString11">
                    <div class="endpointOptionRow selected" data-selectable="" data-value="queryString11">
                        <div class="queryAnnotation">SELECT查询2001年到2006年Mauricio_Soler的职业生涯信息。</div>
                    </div>
                </option>
                <option value="queryString12">
                    <div class="endpointOptionRow selected" data-selectable="" data-value="queryString12">
                        <div class="queryAnnotation">SELECT查询Ricardo_Souza_Silva的信息,选取前15条记录。</div>
                    </div>
                </option>
                <option value="queryString13">
                    <div class="endpointOptionRow selected" data-selectable="" data-value="queryString13">
                        <div class="queryAnnotation">SELECT查询在1977年居住在Mestre的运动员的效力的队伍。</div>
                    </div>
                </option>
                <option value="queryString14">
                    <div class="endpointOptionRow selected" data-selectable="" data-value="queryString14">
                        <div class="queryAnnotation">SELECT查询运动员信息，出生城市作为可选属性（OPTIONAL），结果集约束到5-15条数据。</div>
                    </div>
                </option>
                <option value="queryString15">
                    <div class="endpointOptionRow selected" data-selectable="" data-value="queryString15">
                        <div class="queryAnnotation">SELECT查询两种关联的事物中的状态信息；UNION查询Gustavo_Bou效力且所属的球队。</div>
                    </div>
                </option>
                <option value="queryString16">
                    <div class="endpointOptionRow selected" data-selectable="" data-value="queryString16">
                        <div class="queryAnnotation">ASK查询The_Culture在1996到2005年是否效力Israel_national_football_team。</div>
                    </div>
                </option>
                <option value="queryString17">
                    <div class="endpointOptionRow selected" data-selectable="" data-value="queryString17">
                        <div class="queryAnnotation">CONSTRUCT构建构造一个新的关系两个运动员是队友isTeammate。</div>
                    </div>
                </option>
                <option value="queryString18">
                    <div class="endpointOptionRow selected" data-selectable="" data-value="queryString18">
                        <div class="queryAnnotation">DESCRIBE查询2018年Spandauer_SV的所有球员个人信息。</div>
                    </div>
                </option>
            </div>
        </div>
    </select>
    <div>
          <textarea id="queryStringTextarea" style="font-size:18px; height: 400px; width: 600px">
@base <http://www.neu.edu/2018/NBA_Sportinfo#> .
@prefix dbp: <http://dbpedia.org/ontology/> .
@prefix owl: <http://www.w3.org/2002/07/owl#> .
@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix skos: <http://www.w3.org/2004/02/skos/core#> .
@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .
@prefix rdft: <http://www.neu.edu/2018/rdft-syntax-ns#> .
          </textarea>
    <button class="RUN" onclick="SPARQLTQueryRunning()">查询</button>
</div>

<div class="ResultView">
    <p>查询结果展示</p>
    <div class="clearfix">
        <ul class="w_ulList">
            <li class="active">图形展示</li>
            <li>表格展示</li>
        </ul>
    </div>

    <div class="w_tableDemo">
        <table class="restable">
            <tr><td>ts,te,n</td><td>Team</td></tr>
            <tr><td>[1992-03-15,1993-07-21]-8</td><td> "Los_Angeles_Lakers" .</td></tr>
            <tr><td>[1993-01-23,1994-11-16]-1 </td><td>"Phoenix_Sun" .</td></tr>
            <tr><td>[1994-08-17,1995-10-21]-2 </td><td>"Phoenix_Sun" .</td></tr>
            <tr><td>[1995-03-21,1996-01-09]-3 </td><td>"Phoenix_Sun" .</td></tr>
            <tr><td>[1996-01-25,1997-06-13]-4 </td><td>"Phoenix_Sun" .</td></tr>
            <tr><td>[1996-03-07,1997-03-10]-1 </td><td>"Dallas_Mavericks" .</td></tr>
            <tr><td>[1997-08-27,1998-11-27]-2 </td><td>"Dallas_Mavericks" .</td></tr>
            <tr><td>[1998-03-22,1999-01-10]-3 </td><td>"Dallas_Mavericks" .</td></tr>
            <tr><td>[1999-02-08,2000-05-26]-9 </td><td>"Los_Angeles_Lakers" .</td></tr>
            <tr><td>[2000-03-24,2001-07-02]-1 </td><td>"Miami_Heat" .</td></tr>
        </table>
    </div>
    <div class="w_zoomEchart">
        <div id="echartResultShow" style="width:600px;height:330px"></div>
    </div>

</div>
</div>
</body>
</html>
