/*这个是显示查询语句的*/
var editor2 = CodeMirror.fromTextArea(document.getElementById("queryStringTextarea"), {
    mode: "application/xquery",
    styleActiveLine: true,
    lineNumbers: true,
    lineWrapping: true

});

editor2.setSize(650, 220);
//执行函数；
function SPARQLTQueryRunning() {

    var ontology = editor1.getValue();
    var query = editor2.getValue();

    if (ontology == "") {
        alert('Ontology is blank');
    } else if (query == "") {
        alert('SPARQL[t] Query is blank');
    } else {
        <!--url: 'D:/DataSet/yago_en/yagoDateTest.ttl',-->
        var schemaparam = {
            "SPARQLQuery": editor2.getValue(),
            url: 'D:/DataSet/yago_en/yagoDateTest.ttl',
            "option": 1
        };


        $.ajax({
            data: schemaparam,
            url: '/SeverletQuery',
            type: 'POST',
            beforeSend: function () {
                $('#result').append('Processing, Wait a moment please...');
            },
            success: function (response) {
//                    这是增加到list中调出的结果，只是list对象不能解析成jason格式，所以用不了；
//                 var json = eval(response);
//                    $.each(json,function(index,item){
//                       $ ('#result').html(item);
//                       $('#result').html("</br>");
//                    });
//                    var s = "s".toString();
//                    var p = "p".toString();
//                    var o = "o".toString();

//                    $('#result').html("s").css("width:20px");
//                    $('#result').html("p").css("width:20px");
//                    $('#result').html("o").css("width:20px");

                if (response.length > 80) {
                    alert("Success!!!");

                    $('#result').html("</br>");
                    $('#result').html(response);
                }
                else
                    alert("Error!!!");
            }
        });
    }
}