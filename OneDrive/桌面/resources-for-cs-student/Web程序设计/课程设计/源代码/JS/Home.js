function register(){
    window.location.href="register.html";
}        
function login(){
    window.location.href="index.html";
}

var Check=0;
var btn =document.getElementById("sidebar-btn");
btn.onclick=function()
{
    if(Check==0)
    {
        document.getElementById("sidebar-btn").style.right='310px';
        document.getElementById("sidebar-btn").style.transition='0.5s'
        document.getElementById("search-sidebar").style.right='0';
        document.getElementById("search-sidebar").style.transition='0.5s'
        Check=1;
    }
    else
    {
        document.getElementById("sidebar-btn").style.right='10px';
        document.getElementById("sidebar-btn").style.transition='0.5s'
        document.getElementById("search-sidebar").style.right='-300px';
        document.getElementById("search-sidebar").style.transition='0.5s'
        Check=0;
    }
}

function hiden()
{
   document.getElementById("search-container").style.display='none';
}

function showsearchbox() {
  document.getElementById("search-container").style.display='block';
}


var check=0;
function showslidebar() {
document.getElementById('sidebar').classList.toggle('active');
for(var i=0;i<4;i++)
{
  if(  document.getElementsByClassName('list')[i].style.display=='block')
  {
      document.getElementsByClassName('list')[i].style.display='none';
      check1=0;
  }
}
}

var check1=0;

function showlistword()
{

    for(var i=0;i<4;i++)
    {
    if( document.getElementsByClassName('list')[i].style.display=='block')
    {
    document.getElementsByClassName('list')[i].style.display='none';
    check1++;
    }
    else
    {
    document.getElementsByClassName('list')[i].style.display='block';
    check1--;
    }
    }
}


var show=1;
var titalarray = ["先秦秦汉","唐宋时期","明清时期"];
var imgpath =[ 'images/园林2.jpg','images/园林13.jpg','images/园林4.jpg'];
var wordarray =[ " &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;此时期或可称为“自然时期”，是从“囿”到“苑”的发展时期,这种最早期的“囿”到汉代有了新的发展，它不仅仅是一种自然山林的原始状态的存在，而是日趋专门化了。帝王们在这里建“宫”设“馆”，除了为游猎所需要，增添了寝宫殿宇生活设施，还配置了观赏植物、人工山水等景色，初步具有了“园林”性质，从汉代起它的名称也从古代的“囿”改称“苑”或“苑圈”了。但这些园林，建筑和山水的安排，也并不融洽有序，奇树异花的种植，只是猎奇罗列，虽然它有了某些园林的性质，开启了日后造园的新生面。总的说，仍是处于自然发展的时期。"," &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;它是中国古典园林的形成时期。由汉代开端的中国园林发展进程，经过东汉、三国、魏晋南北朝到隋代统一中国的过渡，至唐代出现了一个兴盛的局面。由于疆域的扩大、经济的发达、民族的融合，促进了文化艺术的发展，达到了一个空前繁荣时期，在这个时期迭石造山，凿池引泉。布局关系也趋于融洽，使之形成优美的环境,发挥了休憩、游赏，甚至宴乐之功能。另外绘画技术的发展与造园艺术的发展的互相促进，如南朝梁·张僧怒擅长画山水，能“咫尺之间便觉万里之遥”，画家所提炼的构图、排列、层次和色彩，极大地丰宫了造园技巧。","&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;这中国古典园林的全盛时期。北宋为辽金取代后，辽、金、元三代后先相继，在燕京一带兴修皇家园林。金代从开封拆运至中都大量的艮岳花石，元代在建筑艺术中促进了国内各民族和东西方文化的交流，使中国各民族丰富奇特的建筑形式更添异彩 。明代及清代初期，在中国园林发展史上是个辉煌的时期，达到了它的全盛时期。这有我们今天仍能亲眼目睹的很多实物实景所证明。"]
var infotitle = document.getElementsByClassName('info-title')[0];
var infotxt = document.getElementsByClassName('info-txt')[0];
var img = document.getElementById("img");
img.onclick = function(){
   document.getElementById("img").setAttribute("src",imgpath[show]);
    infotitle.innerHTML = titalarray[show];
    infotxt.innerHTML = wordarray[show];
    show++;
    show=show%3;
}

ScrollReveal({
    reset: true,
    distance: '60px',
    duration: 1500,
    delay: 400
  });
  ScrollReveal().reveal('.main-title, .section-title', { delay: 500, origin: 'left' });
  ScrollReveal().reveal('.sec-02 .info', { delay: 500, origin: 'left' });
  ScrollReveal().reveal('.sec-01 .image, .sec-03 .info', { delay: 600, origin: 'bottom' });
  ScrollReveal().reveal('.text-box', { delay: 700, origin: 'right' });
  ScrollReveal().reveal('.media-icons i', { delay: 500, origin: 'bottom', interval: 200 });
  ScrollReveal().reveal('.sec-02 .image, .sec-03 .image,, .sec-04 .image', { delay: 500, origin: 'top' });
  ScrollReveal().reveal('.media-info li', { delay: 500, origin: 'left', interval: 200 });