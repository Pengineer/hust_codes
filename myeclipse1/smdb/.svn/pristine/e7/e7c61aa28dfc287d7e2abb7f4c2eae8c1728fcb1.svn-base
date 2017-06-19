$(function(){
	
	(function checkOStype(){
	    var ua = navigator.userAgent.toLowerCase();
	    if((ua.indexOf("ipad") != -1) || (ua.indexOf("iphone") != -1)) window.osType = "ios";
	    else if(ua.indexOf("android") != -1) window.osType = "android";
	    else window.osType = "unknown";
	})();//检测OS
	
	switch (window.osType){
		case "android":{
			$(".downlaodApp").click(function(){
				window.location = "mobile/setting/downloadApp.action";
			});
			break;
		}
		case "ios":{
			$(".downlaodApp").click(function(){
				window.open("https://itunes.apple.com/cn/app/she-ke-shu-ju/id897808624?mt=8", "_blank");
			});
			break
		}
		default:{
			$(".downlaodApp").click(function(){
				$("#L_dialog").fadeIn();
				$("#L_bg").show();
			});
		}
	}//更具os类型进行不同的点击处理
	
	$(".L_android").click(function(){
		window.location = "mobile/setting/downloadApp.action";
	});
	$(".L_iphone").click(function(){
		window.open("https://itunes.apple.com/cn/app/she-ke-shu-ju/id897808624?mt=8", "_blank");
	});
	$(".L_close_x").click(function(){
		$("#L_dialog").fadeOut();
		$("#L_bg").hide();
	});//关闭弹出层

})


