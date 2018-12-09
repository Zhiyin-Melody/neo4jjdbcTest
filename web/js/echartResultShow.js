$(function(){
var chartContainer = document.getElementById("echartResultShow");

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
        preloaded: {

            "nodes": [
                {
                    "name": "AC_Green_Jr",
                    "id": 5097,
                    "type": "AC_Green_Jr",
                    "loaded": true,
                    "style": {
                        "fillColor": "rgba(28,124,213,0.8)"
                    }
                },
                {
                    "name": "Dallas_Mavericks",
                    "id": 5106,
                    "type": "Dallas_Mavericks",
                    "loaded": true,
                    "style": {
                        "fillColor": "rgba(28,124,213,0.8)"
                    }
                },
                {
                    "name": "Los_Angeles_Lakers",
                    "id": 6201,
                    "type": "Los_Angeles_Lakers",
                    "loaded": true,
                    "style": {
                        "fillColor": "rgba(28,124,213,0.8)"
                    }
                },
                {
                    "name": "Phoenix_Sun",
                    "id": 6192,
                    "type": "Phoenix_Sun",
                    "loaded": true,
                    "style": {
                        "fillColor": "rgba(28,124,213,0.8)"
                    }
                },
                {
                    "name": "Miami_Heat",
                    "id": 6211,
                    "type": "Miami_Heat",
                    "loaded": true,
                    "style": {
                        "fillColor": "rgba(28,124,213,0.8)"
                    }
                }
            ],
            "links": [
                {
                    "from": 5097,
                    "to": 5106,
                    "type": "Plays_For",
                    "style": {
                        "fillColor": "rgba(0,0,0,1)",
                        "toDecoration": "arrow"
                    },
                    "rdf_type": "rdft_property",
                    "rdft_hasEndTime": "1997-03-10",
                    "rdft_hasStartTime": "1996-03-07",
                    "rdft_hasNumUpdate": "1"
                },
                {
                    "from": 5097,
                    "to": 6192,
                    "type": "Plays_For",
                    "style": {
                        "fillColor": "rgba(0,0,0,1)",
                        "toDecoration": "arrow"
                    },
                    "rdf_type": "rdft_property",
                    "rdft_hasEndTime": "1997-06-13",
                    "rdft_hasStartTime": "1996-01-25",
                    "rdft_hasNumUpdate": "4"
                },
                {
                    "from": 5097,
                    "to": 5106,
                    "type": "Plays_For",
                    "style": {
                        "fillColor": "rgba(0,0,0,1)",
                        "toDecoration": "arrow"
                    },
                    "rdf_type": "rdft_property",
                    "rdft_hasEndTime": "1999-01-10",
                    "rdft_hasStartTime": "1998-03-22",
                    "rdft_hasNumUpdate": "3"
                },
                {
                    "from": 5097,
                    "to": 6192,
                    "type": "Plays_For",
                    "style": {
                        "fillColor": "rgba(0,0,0,1)",
                        "toDecoration": "arrow"
                    },
                    "rdf_type": "rdft_property",
                    "rdft_hasEndTime": "1994-11-16",
                    "rdft_hasStartTime": "1993-01-23",
                    "rdft_hasNumUpdate": "1"
                },
                {
                    "from": 5097,
                    "to": 6201,
                    "type": "Plays_For",
                    "style": {
                        "fillColor": "rgba(0,0,0,1)",
                        "toDecoration": "arrow"
                    },
                    "rdf_type": "rdft_property",
                    "rdft_hasEndTime": "2000-05-26",
                    "rdft_hasStartTime": "1999-02-08",
                    "rdft_hasNumUpdate": "9"
                },
                {
                    "from": 5097,
                    "to": 6192,
                    "type": "Plays_For",
                    "style": {
                        "fillColor": "rgba(0,0,0,1)",
                        "toDecoration": "arrow"
                    },
                    "rdf_type": "rdft_property",
                    "rdft_hasEndTime": "1995-10-21",
                    "rdft_hasStartTime": "1994-08-17",
                    "rdft_hasNumUpdate": "2"
                },
                {
                    "from": 5097,
                    "to": 6201,
                    "type": "Plays_For",
                    "style": {
                        "fillColor": "rgba(0,0,0,1)",
                        "toDecoration": "arrow"
                    },
                    "rdf_type": "rdft_property",
                    "rdft_hasEndTime": "1993-07-21",
                    "rdft_hasStartTime": "1992-03-15",
                    "rdft_hasNumUpdate": "8"
                },
                {
                    "from": 5097,
                    "to": 5106,
                    "type": "Plays_For",
                    "style": {
                        "fillColor": "rgba(0,0,0,1)",
                        "toDecoration": "arrow"
                    },
                    "rdf_type": "rdft_property",
                    "rdft_hasEndTime": "1998-11-27",
                    "rdft_hasStartTime": "1997-08-27",
                    "rdft_hasNumUpdate": "2"
                },
                {
                    "from": 5097,
                    "to": 6211,
                    "type": "Plays_For",
                    "style": {
                        "fillColor": "rgba(0,0,0,1)",
                        "toDecoration": "arrow"
                    },
                    "rdf_type": "rdft_property",
                    "rdft_hasEndTime": "2001-07-02",
                    "rdft_hasStartTime": "2000-03-24",
                    "rdft_hasNumUpdate": "1"
                },
                {
                    "from": 5097,
                    "to": 6192,
                    "type": "Plays_For",
                    "style": {
                        "fillColor": "rgba(0,0,0,1)",
                        "toDecoration": "arrow"
                    },
                    "rdf_type": "rdft_property",
                    "rdft_hasEndTime": "1996-01-09",
                    "rdft_hasStartTime": "1995-03-21",
                    "rdft_hasNumUpdate": "3"
                }
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