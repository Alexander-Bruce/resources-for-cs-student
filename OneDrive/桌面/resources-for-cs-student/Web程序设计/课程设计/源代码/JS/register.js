function register(){
    window.location.href="register.html";
}        

function login(){
    window.location.href="index.html";
}
 
 
//点击按钮验证
var code = document.getElementById("code_input");
var btn = document.getElementById("btn");
btn.onclick = function () {
    var res = verifyCode.validate(code.value);
    var password = document.getElementById("password").value;
    var password2 = document.getElementById("password2").value;
    var check=1;
    if (password.length <= 0 && password2.length <= 0) {
        check=0;
    }
    else if (password.length != password2.length) {
        check=0;
    }
    else{
        var i=0;
        for(var j=0;j<password.length;j++){
            if(password[j] != password2[j]){
                i=1;
                break;
            }
        }
        if(i==0&&password.length==password2.length){
        }
        else{
            check=0;
        }
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