var map = new Object();
var playerNames = new Object();
$(document).ready(function(){
setDeck();
        $("#dealHand").on("click" , function()
            {
                var players = $("#displayLobby").text();
                 players = players.replace("]" , "");
                players = players.replace("[","");
                playerNames = players.split(",");
                var playerCount = playerNames.length;

                for( var j = 0 ; j < playerCount ; j++)
                {
                 alert(j);
                    $.get("/getHand",function(response){

                            var hand = response.content;
                            hand = hand.replace("(","");
                            hand = hand.replace(")", "");
                            var cardList = hand.split(",");


                            for(var k = 0 ; k < cardList.length ; k++)
                            {
                                //alert(cardList[k]);
                                $("#cardDisplay").append("<img class = 'img-rounded' onload = 'resizeImg(this,200,100);' id = 'card"+k+"' src = '/assets/Images/"+map[ cardList[k] ]+ "'/>");

                            }
                                                $("#cardDisplay").append("</br>");

                                               // $("#cardDisplay").append("<label class = 'form-control'>"+playerNames[j]+"</label>");
                    });


                }



            }
        );


});


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