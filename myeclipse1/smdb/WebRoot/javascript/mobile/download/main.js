(function() {

    window.isWechat = false;
    window.osType = "";
    function isWechat() {
        var ua = navigator.userAgent.toLowerCase();
        return /micromessenger/i.test(ua) || /qq\//i.test(ua) || typeof navigator.wxuserAgent !== 'undefined';
    }

    function checkOStype(){
        var ua = navigator.userAgent.toLowerCase();
        if((ua.indexOf("ipad") != -1) || (ua.indexOf("iphone") != -1)) window.osType = "ios";
        else if(ua.indexOf("android") != -1) window.osType = "android";
        else window.osType = "unknown";
    }
    checkOStype();//判断OS
    
    if (isWechat()) {
         window.wechat = true;
    } else {
         window.wechat = false;
    }//判断是否为微信
    
    var $ = document.getElementById.bind(document);
    if(window.osType == "ios"){
    	$("version").innerHTML = "1.0";
        $("appType").innerHTML = "  · IOS";
        $("appSize").innerHTML = " · 2.9MB";
        $("updateInfo").innerHTML ="更新于 2015-08-04";
        
    } else {
    	$("version").innerHTML = "1.0";
        $("appType").innerHTML = "  · 安卓";
        $("appSize").innerHTML = " · 2.33MB";
        $("updateInfo").innerHTML ="更新于 2015-05-15";
    }
    document.getElementById("download").addEventListener("click", function() {
        if (window.wechat) {
            document.getElementsByClassName("wechat-tips")[0].classList.remove("hide");
            document.getElementsByClassName("cover")[0].classList.remove("hide");
        } else {
            if(window.osType == "ios"){
            	window.location = "https://itunes.apple.com/cn/app/she-ke-shu-ju/id897808624?mt=8";
            } else{
            	console.log(window.wechat);
                window.location = "mobile/setting/downloadApp.action"
            }
            
        }
    });
    document.getElementsByClassName("cover")[0].addEventListener("click", function() {
        this.classList.add("hide");
        document.getElementsByClassName("wechat-tips")[0].classList.add("hide");
    });
})();