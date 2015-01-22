var map = new Object();
var rankings = new Object();
var inputPlayers = new Object();
var PLAYERS = 5;


$(document).ready( function(){
        setDeck();




        $.get("/getLoggedInPlayerName" , function(data) {
                $("#player1Name").val(data.content);
                inputPlayers[0] = data.content;
        });


  for(var j = 0; j< PLAYERS ; j++)
  {

    $("#player" + j).text(inputPlayers[j]);
    var deckInfo = $("#deckInfo"+ j).text();
    $("#deckInfo"+ j).text("");
    rankings["player" +j] = deckInfo;
    var deckSplit = deckInfo.split("#");
    var deck = deckSplit[0].replace("(", "");
    var deck = deck.replace(")", "");

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
                   inputPlayers[1] = $("#player2Name").val();
                   inputPlayers[2] = $("#player3Name").val();
                   inputPlayers[3] = $("#player4Name").val();
                   inputPlayers[4] = $("#player5Name").val();
                    $("#deckInfo" +0).attr('style', '');
                     $("#deckInfo" +1).attr('style', '');
                      $("#deckInfo" +2).attr('style', '');
                       $("#deckInfo" +3).attr('style', '');
                        $("#deckInfo" +4).attr('style', '');
                         $("#gameSetup").css("display" , "none");


                           $("#player" + 0).attr('style', 'inline');
                           $("#player" + 1).attr('style', 'inline');
                           $("#player" + 2).attr('style', 'inline');
                           $("#player" + 3).attr('style', 'inline');
                           $("#player" + 4).attr('style', 'inline');

                            $("#deckType" + 0).attr('style', 'inline');
                            $("#deckType" + 1).attr('style', 'inline');
                             $("#deckType" + 2).attr('style', 'inline');
                            $("#deckType" + 3).attr('style', 'inline');
                              $("#deckType" + 4).attr('style', 'inline');

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


/*        if(eval.isFourOfAKindFunctional(hand))
              return "Four of a kind!";
          else if(eval.isFlushFunctional(hand))
              return "Flush!";
          else if(eval.isFullHouseFunctional(hand))
              return "Full house!";
          else if(eval.isThreeOfAKindFunctional(hand))
              return "Three of a kind";
          else if(eval.isTwoPairFunctional(hand))
              return "Two Pair";
          else if(eval.isStraightFlush(hand))
              return "Straight flush!";
          else if(eval.isOnePairFunctional(hand))
              return "One Pair!";

          else
          {
              return "High card.";
          }*/