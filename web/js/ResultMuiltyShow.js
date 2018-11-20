window.onload=function(){
    var aInput=document.getElementsByTagName("input");//获取所有的input标签元素
    var aDiv=document.getElementById("ResultView").getElementsByTagName("div");//获取div元素

    for(var i=0;i<aInput.length;i++){  //循环，给input加上点击事件，在点击时循环让所有选项卡的所有样式去掉，所有内容隐藏，然后让所点击的选项卡及对应内容显示。
        aInput[i].index=i;
        aInput[i].onclick=function(){

            for(var j=0;j<aInput.length;j++){//去掉所有input的class
                aInput[j].className="";//设置所有的className为空
                aDiv[j].style.display="none";//设置所有的样式为不显示
            }
            this.className="active";//设置当前className为active
            aDiv[this.index].style.display="block";//设置当前点击的相对的div的样式内容为显示
        }
    }
}
