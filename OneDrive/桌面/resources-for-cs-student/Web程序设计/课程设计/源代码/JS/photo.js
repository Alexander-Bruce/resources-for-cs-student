function register(){
    window.location.href="register.html";
}        

function login(){
    window.location.href="index.html";
}

var number =RandomNum(40, 60);
var timelasting = RandomNum(800, 1200);

function RandomNum(Min, Max) {
    var num = Min + Math.floor(Math.random()* (Max - Min)); //舍去
    return num;
}


        var imagearr=['images/皇家园林1.jpg','images/皇家园林2.jpg','images/皇家园林3.jpg','images/皇家园林4.jpg','images/皇家园林5.jpg','images/皇家园林6.jpg','images/皇家园林7.jpg','images/皇家园林8.jpg','images/皇家园林9.jpg','images/皇家园林10.jpg',"images/嘉兴绮园.jpg","images/嘉兴绮园1.jpg","images/嘉兴绮园2.jpg","images/嘉兴绮园3.jpg","images/顺德清晖园1.jpg","images/顺德清晖园2.jpg","images/顺德清晖园3.png","images/苏州留园1.jpg","images/苏州留园2.jpg","images/无锡寄畅园3.jpg"];
        function show(){
            var ul=document.querySelector('.img-wrapper');
            var li=document.createElement('li');
            var img=document.createElement('img');
            img.src=imagearr[number%20];
            li.appendChild(img);
            ul.appendChild(li);
            number++;
        }
        var timer=setInterval(show,0.1);
        setTimeout(function(){
            clearInterval(timer);
},1000);


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






var Check=0 ;
function showslidebar() {
   document.getElementById('sidebar').classList.toggle('active');
   if(  document.getElementsByClassName('list')[0].style.display=='block')
    {
        document.getElementsByClassName('list')[0].style.display='none';
        Check=0;
    }
}

function showlist()
{
    if(Check==0)
    {
        document.getElementsByClassName('list')[0].style.display='block';
        Check++;
    }
    else
    {
        document.getElementsByClassName('list')[0].style.display='none';
        Check--;
    }
}

function update()
{
    document.getElementById("slide2").style.display='block';
}

        





