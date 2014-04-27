var $ = window.$ = require('jquery');
var derryShim = require('derryshim').shim;
var Derry = window.Derry || derryShim;

var start =  function () {
  console.log("started");
}

function setupButtons() {
  $("#watchdogBtn").click(function () {
    Derry.watchdog(10000);
  })
  $("#orientationBtn").click(function () {
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
