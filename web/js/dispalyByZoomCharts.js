$(function(){
var chartContainer = document.getElementById("echartDemo");

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
            "nodes":[
                {"id":"n1", "loaded":true, "style":{ "fillColor": "rgba(236,46,46,0.8)", "label":"Node1"}},
                {"id":"n2", "loaded":true, "style":{ "fillColor": "rgba(47,195,47,0.8)", "label":"Node2"}},
                {"id":"n3", "loaded":true, "style":{ "fillColor": "rgba(28,124,213,0.8)", "label":"Node3"}}
            ],
                "links":[
                {"id":"l1","from":"n1", "to":"n2", "style":{"fillColor":"rgba(236,46,46,1)", "toDecoration":"arrow"}},
                {"id":"l2","from":"n2", "to":"n3", "style":{"fillColor":"rgba(47,195,47,1)", "toDecoration":"arrow"}},
                {"id":"l3","from":"n3", "to":"n1", "style":{"fillColor":"rgba(28,124,213,1)", "toDecoration":"arrow"}}
            ]
        }
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