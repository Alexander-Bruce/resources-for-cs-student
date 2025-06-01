function register(){
    window.location.href="register.html";
}        

function login(){
    window.location.href="index.html";
}
 


var code = document.getElementById("code_input");
var btn = document.getElementById("btn");
var password = document.getElementById("password").value;
btn.onclick = function () {
    var res = verifyCode.validate(code.value);
    var check=1;
    if (password.length <= 0) 
    {
        check=0;
    }
    else{    
    }
    if (res&&check==1) {
        alert("验证通过");
        window.location.href = "Home.html";
    }
    else {
        if(res==0&&check==0)
        {
            alert("验证码和密码均有错误错误");
        }
        else if(check==0)
        {
            alert("密码错误");
        }
        else{
            alert("验证码错误");
        }
    }
}