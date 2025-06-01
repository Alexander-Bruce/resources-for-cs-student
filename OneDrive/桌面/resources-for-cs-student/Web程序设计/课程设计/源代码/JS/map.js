var map = new AMap.Map("slide2", {
    resizeEnable: true,
    mapStyle: 'amap://styles/macaron',
    viewMode:'3D',
    //34.72468,113.6401
    center: [111.6401, 31.72468],
    zoom: 5.5
});
//北纬34°32′27.00″，东经108°55′25.00
//343227.00，东经1085525.00。
// addMarker();

//添加marker标记
// function addMarker() {
//     map.clearMap();
//     var marker = new AMap.Marker({
//         map: map,
//         position: [116.481181, 39.989792]
//     });
//     //鼠标点击marker弹出自定义的信息窗体
//     marker.on('click', function () {
//         infoWindow.open(map, marker.getPosition());
//     });
// }

// const marker11 = new AMap.Marker({
//     position:[115.498138,  38.857502] //位置
//   })
//   map.add(marker11);
//   38.857502,115.498138
var marker1 = new AMap.Marker({
    icon: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
    position:[115.498138,  38.857502] ,
    content: `<input type="button" value="莲花池" onclick="change(10);" style="font-size: 17px;">`,
})
map.add(marker1); 

var marker2 = new AMap.Marker({
    icon: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
    position:[116.37336, 39.914124] ,
    content: ` <input type="button" value="颐和园" onclick="change(2);"  font-size: 17px;">`,
})
map.add(marker2); 

var marker3 = new AMap.Marker({
    icon: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
    position:[117.929619, 40.997711] ,
    content: `  <input type="button" value="避暑山庄" onclick="change(3);"  style="font-size: 17px;">`,
})
map.add(marker3); 

var marker4 = new AMap.Marker({
    icon: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
    position:[119.448975, 32.385643] ,
    content: `   <input type="button" value="何园" onclick="change(9);"  style="font-size: 17px;">`,
    offset: new AMap.Pixel(-60, -20)
})
map.add(marker4); 

var marker5 = new AMap.Marker({
    icon: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
    position:[119.443703, 32.399151] ,
    content: `   <input type="button" value="个园" onclick="change(8);"  style="font-size: 17px;">`,
    offset: new AMap.Pixel(16, -45)
})
map.add(marker5); 

var marker6 = new AMap.Marker({
    icon: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
    position:[120.271797,31.579641] ,
    content: `    <input type="button" value="寄畅园" onclick="change(5);"  style="font-size: 17px;">`,
    offset: new AMap.Pixel(-60, -10)
})
map.add(marker6); 

var marker7 = new AMap.Marker({
    icon: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
    position:[120.628761,31.324179] ,
    content: `     <input type="button" value="拙政园" onclick="change(1);"  style="font-size: 17px;">`,
    offset: new AMap.Pixel(16, -45)
})
map.add(marker7); 
var marker8 = new AMap.Marker({
    icon: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
    position:[120.592407,31.315411] ,
    content: `     <input type="button" value="留园" onclick="change(4);"  style="font-size: 17px;">`,
    offset: new AMap.Pixel(-46, 15)
})
map.add(marker8); 

var marker9 = new AMap.Marker({
    icon: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
    position:[120.947357,30.516699] ,
    content: `    <input type="button" value="绮园" onclick="change(6);"  style="font-size: 17px;">`,
    offset: new AMap.Pixel(-36, 20)
})
map.add(marker9); 

var marker10 = new AMap.Marker({
    icon: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
    position:[113.097794,22.9553799] ,
    content: `    <input type="button" value="清晖园" onclick="change(7);" style="font-size: 17px;">`,
})
map.add(marker10); 





// var infoWindow = new AMap.InfoWindow({ 
//     isCustom: true,  // 使用自定义窗体
//     content: '<div>HELLO,AMAP!</div>', // 信息窗体的内容可以是任意 html 片段
//     offset: new AMap.Pixel(16, -45)
//   });
//   var onMarkerClick = function(e) {
//     infoWindow.open(map, e.target.getPosition()); // 打开信息窗体
//     // e.target 就是被点击的 Marker
//   } 
  
//   const marker = new AMap.Marker({
//     position: [116.39, 39.9]
//   })
//   map.add(marker);
//   marker.on('click', onMarkerClick); // 绑定 click 事件


    // marker = new AMap.Marker({
    //     icon: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
    //     position: [116.406315, 39.908775],
    //     offset: new AMap.Pixel(-13, -30)
    // });
    // marker.setMap(map);
  

//   var markerSpan = document.createElement("span");
//   markerSpan.className = 'marker';
//   markerSpan.innerHTML = "Hi，我被更新啦！";
//   markerContent.appendChild(markerSpan);

//   marker.setContent(markerContent); //更新点标记内容
//   marker.setPosition([116.391467, 39.927761]); //更新点标记位置


//实例化信息窗体
// var title = '方恒假日酒店<span style="font-size:11px;color:#F00;">价格:318</span>',
//     content = [];
// content.push("< img src='http://tpc.googlesyndication.com/simgad/5843493769827749134'>地址：北京市朝阳区阜通东大街6号院3号楼东北8.3公里");
// content.push("电话：010-64733333");
// content.push("< a href=' '>详细信息</ a>");
// var infoWindow = new AMap.InfoWindow({
//     isCustom: true,  //使用自定义窗体
//     content: createInfoWindow(title, content.join("<br/>")),
//     offset: new AMap.Pixel(16, -45)
// });

// //构建自定义信息窗体
// function createInfoWindow(title, content) {
//     var info = document.createElement("div");
//     info.className = "custom-info input-card content-window-card";

//     //可以通过下面的方式修改自定义窗体的宽高
//     info.style.width = "400px";
//     // 定义顶部标题
//     var top = document.createElement("div");
//     var titleD = document.createElement("div");
//     var closeX = document.createElement("img");
//     top.className = "info-top";
//     titleD.innerHTML = title;
//     closeX.src = "https://webapi.amap.com/images/close2.gif";
//     closeX.onclick = closeInfoWindow;

//     top.appendChild(titleD);
//     top.appendChild(closeX);
//     info.appendChild(top);

//     // 定义中部内容
//     var middle = document.createElement("div");
//     middle.className = "info-middle";
//     middle.style.backgroundColor = 'white';
//     middle.innerHTML = content;
//     info.appendChild(middle);

//     // 定义底部内容
//     var bottom = document.createElement("div");
//     bottom.className = "info-bottom";
//     bottom.style.position = 'relative';
//     bottom.style.top = '0px';
//     bottom.style.margin = '0 auto';
//     var sharp = document.createElement("img");
//     sharp.src = "https://webapi.amap.com/images/sharp.png";
//     bottom.appendChild(sharp);
//     info.appendChild(bottom);
//     return info;
// }

//关闭信息窗体
// function closeInfoWindow() {
//     map.clearInfoWindow();
// }