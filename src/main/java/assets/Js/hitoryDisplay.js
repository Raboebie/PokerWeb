$( document ).ready(function() {
    alert($('[id^=history]').length);
});
/*for(var j = 0; j < 5 ; j++)
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
*/
