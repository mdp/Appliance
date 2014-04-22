function startWatchdog() {
  Derry.watchdog(10000);
}

function setOrientation(s) {
  console.log("orientation: " + s);
  Derry.setOrientation(s);
}
