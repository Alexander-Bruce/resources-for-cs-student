var arrcopy = [];
function register(){
    window.location.href="register.html";
}        
function login(){
    window.location.href="index.html";
}

window.addEventListener("scroll", function(){var header = document.querySelector("header");
header.classList.toggle("sticky", window.scrollY > 0);
})

function Start()
{
    document.getElementsByClassName('aside1')[0].style.display='none';
    document.getElementsByClassName('formset')[1].style.display='none';
    document.getElementsByClassName('aside')[0].style.display='block';
}

function Submit(socore)
{
    var Length  = 20;
    for(var i=0;i<Length;i++){
        document.getElementsByClassName('question')[i].style.display='none';
    }
    document.getElementsByClassName('aside1')[0].style.display='block';
    document.getElementsByClassName('aside')[0].style.display='none';
    document.getElementsByClassName('formset')[1].style.display='block';
    document.getElementById("container").style="height:800px"; 
    start(socore,'root', 3,)
}

function RandomNum(Min, Max) {
    var num = Min + Math.floor(Math.random()* (Max - Min)); //舍去
    return num;
}



function generate(){   
    var Length=20;
    var check= document.getElementsByName("check");
    var obj = document.getElementsByName("1");
     for(var i=0;i<1;i++){
        document.getElementsByClassName('formset')[i].style.display='none';
     }    
    for(var i=0;i<obj.length;i++){
        if(obj[i].checked==true){
           obj[i].checked=null;
        }
    }
    for(var i=0;i<check.length;i++){
        check[i].style="background-color: white;";
    }
    for(var i=0;i<Length;i++){
        document.getElementsByClassName('question')[i].style.display='none';
    } 
    for (var i = 0; i < 9; i++) {
        arrcopy[i] = RandomNum(0, Length);  
        for (var j = 0; j < i; j++) {
            if(arrcopy[i]==arrcopy[j]){
                i --;
                break;
            }
        }
    }    
    Checked(arrcopy);
    var height=0;
    for(var i=0;i<9;i++){
        height+= 302;
        document.getElementsByClassName('question')[arrcopy[i]].style.display='block';
    }
    document.getElementById("container").style.height=height+"px"; 
}


var inputwarp = document.getElementsByName("1");

for(var i=0;i<inputwarp.length;i++)
{ 
   inputwarp[i].ondblclick = function(){
        this.checked = false;
   }
}


function judge()
{
    var socore=10;
    var obj = document.getElementsByName("1");
    for (var i = 0; i < obj.length; i++) { 
        if ((obj[i].checked==true)&&(obj[i].value==1)) { 
                socore+=10;      
        }
    }
    Submit(socore);
    window.location.href="#container";
}


function Checked(arrcopy){
    var min;
    for(var i=0; i<arrcopy.length; i++){
        for(var j=i; j<arrcopy.length;j++){
            if(arrcopy[i]>arrcopy[j]){
              min=arrcopy[j];
              arrcopy[j]=arrcopy[i];
              arrcopy[i]=min;
            }
        }
    }
    var obj = document.getElementsByName("1");
    var check= document.getElementsByName("check");
    for(var i=0;i<check.length;i++){
        check[i].style="background-color: white;";
    }
   for(var i=0;i<obj.length;i++){   
        if(obj[i].checked==true){
            var n=(i+4)%4;
            var t=((i+4)-n)/4-1;
            for(var j=0;j<check.length;j++){
                if(t==arrcopy[j]){
                    check[j].className=arrcopy[j];
                    check[j].style="background-color: #AAC8A7;";
                }
            }
        }
   }
}

var imagelength=10;
var clicknumber=20;
var first=true;
var btnleft=document.getElementsByClassName('btnleft')[0];
var btnright=document.getElementsByClassName('btnright')[0];
var block=document.getElementsByClassName('sonup')[0];
var imagearr=['images/皇家园林1.jpg','images/皇家园林2.jpg','images/皇家园林3.jpg','images/皇家园林4.jpg','images/皇家园林5.jpg','images/皇家园林6.jpg','images/皇家园林7.jpg','images/皇家园林8.jpg','images/皇家园林9.jpg','images/皇家园林10.jpg'];
btnleft.onclick=function(){
    var image=document.getElementById('image');
    if(first==true)
    {
        first=false;
        image.setAttribute("src",imagearr[clicknumber%10]);
    }
    else
    {
        clicknumber=(clicknumber+1)%10+20;
        image.setAttribute("src",imagearr[clicknumber%10]);
    }
}

btnright.onclick=function(){
    var image=document.getElementById('image');
    if(first==true)
    {
        clicknumber=22;
        image.setAttribute("src",imagearr[clicknumber%10]);
        clicknumber=(clicknumber-1)%10+20;
        first=false;
    }
    else
    {
        clicknumber=(clicknumber-1)%10+20;
        image.setAttribute("src",imagearr[clicknumber%10]);
    }

}

var time = null;


block.onmouseleave = show;
block.onmouseenter = hide;

function show() {
    time = setInterval(btnleft.onclick,1000)
};


function hide() {
    clearInterval(time);
};




setInterval(Checked,50,arrcopy);


function roll(total, idname, step) {
		let n = 0;
		return function () {
			n = (n + step) >= total ? total : (n + step);
			if (n <= total) {

				document.getElementById(idname).innerHTML = n;
			}
		}
}

function start(index, idname, step, runtime = 1000){
		let rolling = roll(index, idname, step)
		runtime = (runtime >= 300) ? runtime : 1000;
		for (let i = 0; i < (index/step); i++) {
			let timer = setTimeout(rolling, (runtime/index)*i*step)
		}
		clearTimeout(timer);
}


var check=0;
var btn =document.getElementById("sidebar-btn");
btn.onclick=function()
{
    if(check==0)
    {
        document.getElementById("sidebar-btn").style.right='310px';
        document.getElementById("sidebar-btn").style.transition='0.5s'
        document.getElementById("search-sidebar").style.right='0';
        document.getElementById("search-sidebar").style.transition='0.5s'
        check=1;
    }
    else
    {
        document.getElementById("sidebar-btn").style.right='10px';
        document.getElementById("sidebar-btn").style.transition='0.5s'
        document.getElementById("search-sidebar").style.right='-300px';
        document.getElementById("search-sidebar").style.transition='0.5s'
        check=0;
    }
}

function hiden()
{
   document.getElementById("search-container").style.display='none';
}



function showsearchbox() {
  document.getElementById("search-container").style.display='block';
}






