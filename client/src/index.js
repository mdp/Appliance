var $ = window.$ = require('jquery');
var Derry = window.Derry;

var start =  function () {
  console.log("started");
}

function setupButtons() {
  $("watchdogBtn").click(function () {
    Derry.watchdog(10000);
  })
  $("orientationBtn").click(function () {
    if (Derry.getOrientation() === "L") {
      Derry.setOrientation('P');
    } else {
      Derry.setOrientation('L');
    }
  })
}

$(document).ready(function(){
  setupButtons();
  start();
})
