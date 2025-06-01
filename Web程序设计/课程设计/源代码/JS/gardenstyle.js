function register(){
    window.location.href="register.html";
}        
function login(){
    window.location.href="index.html";
}
function change(j)
{
    var x=j;
    for(var i=0;i<10;i++)
    {
        let elem1 =  document.getElementsByClassName('slide1')[i];
        if(elem1.style.display=='block')
        {
            let left=0;
            let timer = setInterval(function(){
                if(left<1150){
                    elem1.style.marginLeft = left+'px';
                    if(left+20<=1150)
                    {
                      left = left + 20;
                      console.log(left);
                    }
                    else{
                       left=1150;
                    }
                }else {
                    elem1.style.marginLeft = left+'px';
                    clearInterval(timer);
                }
            },16);
        }
    }

setTimeout(function(){
for(var i=0;i<10;i++)
    { document.getElementById("slide2").style.display='none';
        if(i!=x-1){
            document.getElementsByClassName('slide1')[i].style.display='none';
            document.getElementsByClassName('slide1')[i].style.marginLeft=1150+'px';
            console.log(document.getElementsByClassName('slide1')[i].style.marginLeft);
        }
        else{
            document.getElementsByClassName('slide1')[i].style.display='block';
            let elem =  document.getElementsByClassName('slide1')[i];
		    let left = window.innerWidth* 0.745;
		    let timer = setInterval(function(){
			if(left>0){
				elem.style.marginLeft = left+'px';
                if(left-20>=0){
				  left = left - 20;
                }
                else{
                   left=0;
                }
			}else {
				clearInterval(timer);
			}
		},16);
        }
    }
},1000);
}

function cstyle1(){
    for(var i=0;i<45;i++){
        document.getElementsByClassName('pstyle')[i].style.fontFamily= "华文新魏";
    }
}

function cstyle2(){
    for(var i=0;i<45;i++){
        document.getElementsByClassName('pstyle')[i].style.fontFamily= "宋体";
    }
}
function cstyle3(){
    for(var i=0;i<45;i++){
        document.getElementsByClassName('pstyle')[i].style.fontFamily= "华文行楷";
    }
    
}
function cstyle4(){
    for(var i=0;i<45;i++){
        document.getElementsByClassName('pstyle')[i].style.fontFamily= "方正舒体";
    }
    
}


var Check=0 , Check1=0;
function showslidebar() {
   document.getElementById('sidebar').classList.toggle('active');
   if(  document.getElementsByClassName('list')[0].style.display=='block')
    {
        document.getElementsByClassName('list')[0].style.display='none';
        Check=0;
    }
    for(var i=0;i<4;i++)
  {
    if(  document.getElementsByClassName('listword')[i].style.display=='block')
    {
        document.getElementsByClassName('listword')[i].style.display='none';
        Check1=0;
    }
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


function showlistword()
{
if(Check1==0)
{
for(var i=0;i<4;i++)
{
    document.getElementsByClassName('listword')[i].style.display='block';
    Check1++;
}
}
else
{

for(var i=0;i<4;i++)
{
    document.getElementsByClassName('listword')[i].style.display='none';
    Check1--;
}
}
}

function update()
{
    document.getElementById("slide2").style.display='block';
}


function hidelist()
{
  document.getElementById('northgarden').style.transform='rotate(0deg)';
  document.getElementById('maingarden').style.transform='rotate(0deg)';
  document.getElementById('southgarden').style.transform='rotate(0deg)';

  for(var i=0;i<3;i++)
  {
         document.getElementsByClassName('northlist')[i].style.display='none';
  }
  for (var i=0;i<6;i++)
  {
      document.getElementsByClassName('mainlist')[i].style.display='none';
  }
  for(var i=0;i<1;i++)
  {
      document.getElementsByClassName('southlist')[i].style.display='none';
  }

}


function showmap(){
 document.getElementById("slide2").style.display='block';
 for(var i=0;i<10;i++)
 {
     document.getElementsByClassName('slide1')[i].style.display='none';
 }
}


 var check2=0;
 function shownorthgarden()
 {
     hidelist();
     if(check2==0)
     {
         for(var i=0;i<3;i++)
         {
             document.getElementsByClassName('northlist')[i].style.display='block';
         }
         document.getElementById('northgarden').style.transform='rotate(90deg)';
         document.getElementById('northgarden').style.transition='0.5s';

         check2++;
     }
     else
     {
         for(var i=0;i<3;i++)
         {
             document.getElementsByClassName('northlist')[i].style.display='none';
         }
         document.getElementById('northgarden').style.transform='rotate(0deg)';
         check2--;
     }
 }
 var check1=0;
 function showmaingarden()
 {
     hidelist();
     if(check1==0)
     {
         for(var i=0;i<6;i++)
         {
             document.getElementsByClassName('mainlist')[i].style.display='block';
         }
         document.getElementById('maingarden').style.transform='rotate(90deg)';
         document.getElementById('maingarden').style.transition='0.5s';
         check1++;
     }
     else
     {
         for(var i=0;i<6;i++)
         {
             document.getElementsByClassName('mainlist')[i].style.display='none';
         }
         document.getElementById('maingarden').style.transform='rotate(0deg)';
         check1--;
     }
 }

 var check3=0;
 function showsouthgarden()
 {
     hidelist();
     if(check3==0)
     {
         for(var i=0;i<1;i++)
         {
             document.getElementsByClassName('southlist')[i].style.display='block';
         }
         document.getElementById('southgarden').style.transform='rotate(90deg)';
         document.getElementById('southgarden').style.transition='0.5s';
             check3++;
     }
     else
     {
         for(var i=0;i<1;i++)
         {
             document.getElementsByClassName('southlist')[i].style.display='none';
         }
         document.getElementById('southgarden').style.transform='rotate(0deg)';
             check3--;
     }
 }

