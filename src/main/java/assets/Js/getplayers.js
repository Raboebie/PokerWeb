$(document).ready(
  $.get("/setplayers",function(data,status){
    var json = JSON.parse(data);
    alert("Data: " + json + "\nStatus: " + status);
  });
);