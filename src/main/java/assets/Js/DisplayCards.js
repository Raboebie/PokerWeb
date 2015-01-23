var map = new Object();
var rankings = new Object();
var inputPlayers = new Object();
var PLAYERS = 5;
 HANDS = new Object();



$(document).ready( function(){
        setDeck();


        $("#gameName").change(
          function(){
          var gameName = $("#gameName").val();
                $.ajax(
                   {
                      type: "POST",
                      url: "/setGameName",
                      data: {"page": gameName},
                      dataType: "json",
                      success: function(response) {
                      if(response.content == "TRUE")
                      {
                        $("#gameName").css("color" , "green");
                        $("#isGameAvailable").text("Name is available").css("color" , "green")
                        }
                        else
                        {
                        $("#gameName").css("color" , "red");
                        $("#isGameAvailable").text("Name is not available").css("color" , "red")
                        }
                       },
                      error: function(xhr, ajaxOptions, thrownError) { alert(xhr.responseText); }
                   }

                );
          }
        );



        $("input[type=text]").not("#gameName, #player1Name").change(    //FIX THIS
          function(){

                $.ajax(
                   {
                      type: "POST",
                      url: "/checkPlayer",
                      data: {"player2": $("#player2Name").val() , "player3": $("#player3Name").val(), "player4": $("#player4Name").val() , "player5": $("#player5Name").val() },
                      dataType: "json",
                      success: function(response) {

                          $("#player2Name").css("color" , "red");
                            $("#player3Name").css("color" , "red");
                            $("#player4Name").css("color" , "red");
                           $("#player5Name").css("color" , "red");


                                     var player2 = ("#player2Name").val;
                                     var player3 = ("#player3Name").val;
                                     var player4 = ("#player4Name").val;
                                     var player5 = ("#player5Name").val;

                      if(response.content.search(player2) != -1)
                      {
                        $("#player2Name").css("color" , "green");
                       // $("#isGameAvailable").text("Name is available").css("color" , "green")
                        }


                       // $("#isGameAvailable").text("Name is not available").css("color" , "red")


                         if(response.content.search(player3) != -1)
                         {
                           $("#player3Name").css("color" , "green");
                          // $("#isGameAvailable").text("Name is available").css("color" , "green")
                           }



                          // $("#isGameAvailable").text("Name is not available").css("color" , "red")


                       if(response.content.search(player4) != -1)
                        {
                          $("#player4Name").css("color" , "green");
                         // $("#isGameAvailable").text("Name is available").css("color" , "green")
                          }




                        if(response.content.search(player5) != -1)
                         {
                           $("#player5Name").css("color" , "green");
                          // $("#isGameAvailable").text("Name is available").css("color" , "green")
                           }


                       },
                      error: function(xhr, ajaxOptions, thrownError) { alert(xhr.responseText); }
                   }

                );
          }
        );


        $.get("/getLoggedInPlayerName" , function(data) {
                $("#player1Name").val(data.content);
                inputPlayers[0] = data.content;
        });


  for(var j = 0; j < 5 ; j++)
  {
    $("#player" + j).text(inputPlayers[j]);
    var deckInfo = $("#deckInfo"+ j).text();
    $("#deckInfo"+ j).text("");
    rankings["player" +j] = deckInfo;
    var deckSplit = deckInfo.split("#");
    var deck = deckSplit[0].replace("(", "");

    var deck = deck.replace(")", "");
    HANDS[j] = (deck);

    var deckType = deckSplit[1];

    var cardList = deck.split(",");
    cardList[cardList.length-1] = cardList[cardList.length-1].replace(" " ,"");

     $("#player"+j).text(rankings["player"+j]);


    for(var k = 0 ; k < cardList.length ; ++k)
    {

             $("#deckInfo" + j).append("<img class = 'img-rounded' onload = 'resizeImg(this,200,100);' id = 'card"+k+"' src = '/assets/Images/"+map[ cardList[k] ]+ "'/>");
             $("#deckInfo" +j).css("display" , "none"); //Reveal this line!!!!
    }

    $("#deckType"+j).append("<br/><label class = 'alert alert-danger'>"+deckType+"</label>").css("color" , "red").css("display","none"); //Reveal this line!!!
   }

   var winner = getWinner();
   winner++;

           $("#startGame").on("click",function(){

                   var gameName = $("#gameName").val();


                   inputPlayers[1] = $("#player2Name").val();
                   inputPlayers[2] = $("#player3Name").val();
                   inputPlayers[3] = $("#player4Name").val();
                   inputPlayers[4] = $("#player5Name").val();
                     $("#gameSetup").css("display" , "none");
                                         for(var k = 0 ; k < 5 ;k++)
                                             if(inputPlayers[k].length == 0)
                                                 PLAYERS--;

                    for(var x = 0 ; x < PLAYERS ; x++)
                    {
                         $("#player" + x).text(inputPlayers[x]);
                         $("#deckInfo" +x).attr('style', '');
                         $("#player" + x).attr('style', 'inline');
                         $("#deckType" + x).attr('style', 'inline');
                    }

                                            $.ajax(
                                               {
                                                  type: "POST",
                                                  url: "/createGame",
                                                  data: {"gameName": gameName , "Players" : JSON.stringify(inputPlayers) , "hands" : JSON.stringify(HANDS)},
                                                  dataType: "json",
                                                  success: function(response) {
                                                   },
                                                  error: function(xhr, ajaxOptions, thrownError) { alert(xhr.responseText); }
                                               }

                                            );



            });


});
//(9♣,6♣,5♦,8♥,3♠) #High card.


function resizeImg(img, height, width) {
    img.height = height;
    img.width = width;
    return img;
}

function setDeck()
{
//ACE
    map["A♣"] = "AceClovers.jpg";
    map["A♦"] = "AceDiamonds.jpg";
    map["A♥"] = "AceHearts.jpg";
    map["A♠"] = "AceSpades.jpg";


    //TWO

        map["2♣"] = "2Clovers.jpg";
        map["2♦"] = "2Diamonds.jpg";
        map["2♥"] = "2Hearts.jpg";
        map["2♠"] = "2Spades.jpg";

     //Three

         map["3♣"] = "3Clovers.jpg";
         map["3♦"] = "3Diamonds.jpg";
         map["3♥"] = "3Hearts.jpg";
         map["3♠"] = "3Spades.jpg";

         //Four

        map["4♣"] = "4Clovers.jpg";
        map["4♦"] = "4Diamonds.jpg";
        map["4♥"] = "4Hearts.jpg";
        map["4♠"] = "4Spades.jpg";

        //Five
           map["5♣"] = "5Clovers.jpg";
           map["5♦"] = "5Diamonds.jpg";
           map["5♥"] = "5Hearts.jpg";
           map["5♠"] = "5Spades.jpg";


      //Six

          map["6♣"] = "6Clovers.jpg";
          map["6♦"] = "6Diamonds.jpg";
          map["6♥"] = "6Hearts.jpg";
          map["6♠"] = "6Spades.jpg";


       //Seven

           map["7♣"] = "7Clovers.jpg";
           map["7♦"] = "7Diamonds.jpg";
           map["7♥"] = "7Hearts.jpg";
           map["7♠"] = "7Spades.jpg";

        //eight

                   map["8♣"] = "8Clovers.jpg";
                   map["8♦"] = "8Diamonds.jpg";
                   map["8♥"] = "8Hearts.jpg";
                   map["8♠"] = "8Spades.jpg";

       //9
                map["9♣"] = "9Clovers.jpg";
                map["9♦"] = "9Diamonds.jpg";
                map["9♥"] = "9Hearts.jpg";
                map["9♠"] = "9Spades.jpg";
       //10

                  map["10♣"] = "10Clovers.jpg";
                  map["10♦"] = "10Diamonds.jpg";
                  map["10♥"] = "10Hearts.jpg";
                  map["10♠"] = "10Spades.jpg";

        //Jack

                   map["J♣"] = "JackClovers.jpg";
                   map["J♦"] = "JackDiamonds.jpg";
                   map["J♥"] = "JackHearts.jpg";
                   map["J♠"] = "JackSpades.jpg";

         //Queen

                    map["Q♣"] = "QueenClovers.jpg";
                    map["Q♦"] = "QueenDiamonds.jpg";
                    map["Q♥"] = "QueenHearts.jpg";
                    map["Q♠"] = "QueenSpades.jpg";

         //King


                    map["K♣"] = "KingClovers.jpg";
                    map["K♦"] = "KingDiamonds.jpg";
                    map["K♥"] = "KingHearts.jpg";
                    map["K♠"] = "KingSpades.jpg";
}

function getWinner() //Update to support ties more reliably
{

var highest = 0;
var playerHighest = 0;

            for(var k = 0 ; k < PLAYERS ;k++)
            {
                var deckInfo = rankings["player"+k].split("#");
                var deckType = deckInfo[1];
                var rankOfCard = getRank(deckType);

                if(rankOfCard > highest)
                {
                    highest = rankOfCard;
                    playerHighest = k;
                }
            }
            return playerHighest;
}


function getRank(r)
{
    switch(r){
        case "High card." : return 0;
        case "One Pair!" : return 1;
        case "Two Pair": return 2;
        case "Three of a Kind" : return 3;
        case "Four of a kind!" : return 4;
        case "Straight": return 5;
        case "Flush!" : return 6;
        case "Straight flush!": return 7;
        case "Full house!" : return 8;

    }
}

