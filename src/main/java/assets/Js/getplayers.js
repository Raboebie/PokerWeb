$(document).ready(function(){



        $.getJSON("/setplayers", function(data)
        {

        var players = data.content.split(",");

          for(var k = 0 ; k < 5 ; k++)
          {
              $("#player" + k).text(players[k]).css("display" , "none"); //!!!!!!!!!! Reveal this line!

          }
        }
        );
   });