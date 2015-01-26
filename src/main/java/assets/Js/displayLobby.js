$(document).ready( function() {



        $.get("/getLobbyGames" , function(data){
             $("#displayLobby").html(data.content);
        });
});

function joinGame(ip)
{
    alert(ip);
}