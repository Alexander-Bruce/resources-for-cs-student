var arr = ["导航条", "中国古典园林概览", "唐宋时期", "先秦秦汉", "明清时期", "个人观点", "澳大利亚", "西班牙", "德国", "孟买加", "阿拉伯", "印度", "印度尼西亚"];
var search = document.getElementsByClassName("blue-input")[0];
var selectedId = document.getElementById("selectedId");

function showList() {
    var res = searchByIndexOf(search.value, arr);
    for (var i = 0; i < res.length; i++) {
        var li = document.createElement("li");
        var a = document.createElement("a");
        li.id = res[i];
        li.innerHTML = res[i];
        // li.a.href="https://zhuanlan.zhihu.com/";
        document.getElementById("drop").appendChild(li);
        document.getElementById(res[i]).appendChild(a);
        
        console.log(a);
    }
}


search.oninput = function getMoreContents() {

    //删除ul
    var drop = document.getElementById("drop");
    selectedId.removeChild(drop);
    //把ul添加回来
    var originalUl = document.createElement("ul");
    originalUl.id = "drop";
    selectedId.appendChild(originalUl);
    showList();
}

// 添加获取焦点事件
search.onfocus = function () {
    // 初始下拉列表
    var originalUl = document.createElement("ul");
    originalUl.id = "drop";
    selectedId.appendChild(originalUl);
    showList();
}

//添加失去焦点事件
search.onblur = function(){
//	console.log("soutsout")
	var drop = document.getElementById("drop");
	selectedId.removeChild(drop);
}



//模糊查询:利用字符串的indexOf方法
function searchByIndexOf(keyWord, list) {
    if (!(list instanceof Array)) {
        return;
    }
    if (keyWord == "") {
        return [];
    } else {
        var len = list.length;
        var arr = [];
        for (var i = 0; i < len; i++) {
            //如果字符串中不包含目标字符会返回-1
            if (list[i].indexOf(keyWord) >= 0) {
                arr.push(list[i]);
            }
        }
        return arr;
    }

}



//正则匹配
/*function searchByRegExp(keyWord, list){
    if(!(list instanceof Array)){
        return ;
    }
    var len = list.length;
    var arr = [];
    var reg = new RegExp(keyWord);
    for(var i=0;i<len;i++){
        //如果字符串中不包含目标字符会返回-1
        if(list[i].match(reg)){
            arr.push(list[i]);
        }
    }
    return arr;
}*/