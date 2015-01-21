$(document).ready( function(){

$("html").hover(function(){ //Mouse Hover opacity
    $("html").stop().animate({"opacity": 1 });
}, function() {
    $("html").stop().animate({"opacity": 0.8});
});


//Get Game history ajax
$("#getGameHistory").click(function() {
        $.get("/getGameHistory" , function(data) {alert(data.content)});
});


var upperCase= new RegExp('[A-Z]');
var lowerCase= new RegExp('[a-z]');
var numbers = new RegExp('[0-9]');
var specialCharacters = new RegExp(/(.*[!,%,&,@,#,$,^,*,?,_,~].*[!,%,&,@,#,$,^,*,?,_,~])/);


var passWordStringth = "Weak";
var strength = 0;
        $("#checkPass").on('keyup',function(){
        strength = 0;
        var password = $("#checkPass").val();
        if(password.length >= 8)
            strength++;
        if(password.match(upperCase) && password.match(lowerCase))
            strength++;
        if(password.match(numbers))
            strength++;
        if(password.match(specialCharacters))
            strength++;


        var stringStrength = "";
        var color = "";



        switch (strength)

        {
        case 0 : {
                          stringStrength = "Weak";
                          color = "#F00600";
                          break;
        }
            case 1 : {
                        stringStrength = "Weak";
                        color = "#F00707";
                        break;
            }
            case 2 : {
                        stringStrength = "Fair";
                        color = "#DDB61C";
                        break;

            }
            case 3 : {
                        stringStrength = "Good";
                        color = "#DDDA1C";
                        break;
            }
            case 4 : {
                        stringStrength = "Strong";
                        color = "#AFDB11";
                        break;
            }
        }
            //strength += 100;
                $("#display").text(stringStrength).css('color', color);
        });


        //Setup page POST request
}
);