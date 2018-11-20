$(function(){
var chartContainer = document.getElementById("echartResulShow");

// dynamically create the element that will be used for the context menu.
// an existing element can be used as well.
var menuElement = document.createElement("div");
menuElement.style.zIndex = 9999;
menuElement.style.display = "none";
menuElement.style.position = "absolute";
menuElement.style.background = "#eee";
menuElement.style.border = "1px solid #09c";
menuElement.style.padding = "10px";

// the context menu element has to be the direct descendant of the document.body
document.body.appendChild(menuElement);

var chart = new ZoomCharts.NetChart({
    container: chartContainer,
    data: { 
        preloaded:
        {
            "nodes":[
                {"name":"Ricardo_Souza_Silva","id":5011,"type":"Ricardo_Souza_Silva", "loaded":true, "style":{ "fillColor": "rgba(236,46,46,0.8)", "label":"Node1"}},
                {"name":"de_Luisa_Iovane","id":1554,"type":"de_Luisa_Iovane", "loaded":true, "style":{ "fillColor": "rgba(47,195,47,0.8)", "label":"Node2"}},
                {"name":"Karsten_Nied","id":1555,"type":"Karsten_Nied", "loaded":true, "style":{ "fillColor": "rgba(28,124,213,0.8)", "label":"Node3"}}
            ],
                "links":[
                {"id":"l1","from":"5011", "to":"1554", "style":{"fillColor":"rgba(236,46,46,1)", "toDecoration":"arrow"}},
                {"id":"l2","from":"1554", "to":"5011", "style":{"fillColor":"rgba(47,195,47,1)", "toDecoration":"arrow"}},
                {"id":"l3","from":"1555", "to":"5011", "style":{"fillColor":"rgba(28,124,213,1)", "toDecoration":"arrow"}}
            ]
        }


        },
    style: {
        nodeStyleFunction: nodeStyle,
        linkStyleFunction: linkStyle
    },
    events: {
        onRightClick: function (event, args) {
            // the menu element is positioned based on the mouse pointer coordinates.
            // if you need to position it based on the node, use NetChart.getNodeDimensions() method.
            menuElement.style.display = "block";
            menuElement.style.left = event.pageX + "px";
            menuElement.style.top = event.pageY + "px";

            // fill the menu element based on the node that was clicked.
            if (args.clickNode) {
                menuElement.innerHTML = "Node menu";
            } else if (args.clickLink) {
                menuElement.innerHTML = "Link menu";
            } else {
                hidePopup();
            }

            // disable the default context menu
            event.preventDefault();
        }
    }
});

function hidePopup() {
    menuElement.style.display = "none";
}

function preventDefault(event) {
    event.preventDefault();
}

// prevent additional context menu if the menu element is right clicked
menuElement.addEventListener("contextmenu", preventDefault);

// hide the popup whenever the mouse is clicked outside the chart
window.addEventListener("mousedown", hidePopup);

// the chart captures all mouse events so the workaround is to subscribe to the mouse event within the chart
// these event handlers make sure that clicking the chart anywhere will hide the popup
var chartInteraction = chartContainer.getElementsByClassName("DVSL-interaction")[0];
chartInteraction.addEventListener("mousedown", hidePopup);
chartInteraction.addEventListener("pointerdown", hidePopup);

// function should be called whenever the chart is removed
function disposeDemo() {
    // remove the event handlers to avoid memory leaks.
    window.removeEventListener("mousedown", hidePopup);

    chartInteraction.removeEventListener("mousedown", hidePopup);
    chartInteraction.removeEventListener("pointerdown", hidePopup);

    menuElement.removeEventListener("contextmenu", preventDefault);

    // remove the menu element that was created dynamically
    menuElement.parentNode.removeChild(menuElement);

    // only required for documentation page on zoomcharts.com
    disposeDemo = null;
}
});

function nodeStyle(node) {

        node.label = node.data.type;

}

function linkStyle(link) {
    //普通三元组；
if(link.data.type=="Stature"||link.data.type=="Weight"||link.data.type=="High_School"||link.data.type=="hasGender"){
    link.label = link.data.type;
}

//带有时间点的三元组；
if(link.data.type=="wasBornIn"||link.data.type=="hasBirthCity"){
    link.label = link.data.type+"["+link.data.rdft_hasTime+"]-"+link.data.rdft_hasNumUpdate;
}

//带有时间段的三元组；
    if(link.data.type=="Plays_For"||link.data.type=="Rebound"||link.data.type=="Assist"||link.data.type=="Score1"||link.data.type=="playsFor"||link.data.type=="isAffiliatedTo"){
        link.label = link.data.type+"["+link.data.rdft_hasEndTime+","+link.data.rdft_hasStartTime+"]-"+link.data.rdft_hasNumUpdate;
    }

}