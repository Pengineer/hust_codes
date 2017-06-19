$(function(){
	var nameSpace = "project/general/granted";
	
	pageShow({
		"nameSpace":nameSpace,
		"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7"],
		"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7}
	});
	
	$(".linkView").live("click", function(){
		var url = basePath + nameSpace + "/toView.action?entityId=" + this.id;	//不再需要页码
		url += (this.type) ? "&listType=" + this.type : "";//(项目列表类型先如是判别)
		url += "&businessType=" + $("#businessType").val();//项目业务类型
		window.location.href = url;
		return false;
	});
	
	//弹出层选择打印内容
	$("#popPrint").click(function(){
		popProject({
			title : "选择打印内容",
			src : nameSpace + "/toPrint.action",
			callBack : function(result){
				if(result.printType == 1){//打印立项通知
//					document.location.href = "project/general/granted/printNotice.action" + getNoticePara(result);
					var src = nameSpace + "printNotice.action" + getNoticePara(result);
					printPage(src, "#printNotice");
				}else if(result.printType == 2){//打印立项清单
//					document.location.href = "project/general/granted/printGrantedList.action" + getListPara(result);
					var src = nameSpace + "printGrantedList.action" + getListPara(result);
					printPage(src, "#printList");
				}
			}
		});
		var getNoticePara = function(obj){
				return "?noticeType=" + obj["noticeType"] + "&grantYear=" + obj["grantYear"];
			};
		var getListPara = function(obj){
				return "?grantYear=" + obj["grantYear"] + "&listType=" + obj["listType"] + "&univName=" + obj["univName"] + "&univProvName=" + obj["univProvName"] +
				"&provName=" + obj["provName"] + "&minName=" + obj["minName"];
			};
	});
	
	$(".expand").live('click', function(){
		$("#startTime").datetimepicker({ dateFormat: 'yy-mm-dd' }).datetimepicker('setDate', (new Date(new Date().valueOf() - 240000)) );
		$("#endTime").datetimepicker({ dateFormat: 'yy-mm-dd' }).datetimepicker('setDate', (new Date(new Date().valueOf() + 60000)) );
		$(".hide").each(function(){
			$(this).hide();
		});
		$(".base_info").each(function(){
			$(this).removeClass("close");
			$(this).addClass("expand");
		});
		$(this).next().show();
		$(this).removeClass("expand");
		$(this).addClass("close");
	});
	
	$(".close").live('click', function(){
		$(this).next().hide();
		$(this).removeClass("close");
		$(this).addClass("expand");
	});
});

/**
 * 打印
 * @param {Object} src		action url
 * @param {Object} divId	如："#printAll"
 */
function printPage(src, divId){
	//获取打印页面
	var ver = Math.random();
	printIframe = $("<iframe src='' name='print" + ver +"'></iframe>");
	printIframe.attr("src", src);
	printIframe.css({
		filter:"Alpha(Opacity=0)",
		opacity: "0",
		scrolling : "no",
		vspale : "0",
		height: "0px",
		width : "98%",
		border : "0px",
		frameBorder : "0px"
	});
	$("body").append(printIframe);
	ifr_window = window.frames["print" + ver];
	var isLoaded = false;
	$(ifr_window).load(function(){
		isLoaded = true;
	});
	if(confirm("您确定要打印吗？")){
		if(isLoaded){
			var links = $("iframe[name='print" + ver +"']").contents().find("link");
			var div = $("iframe[name='print" + ver +"']").contents().find(divId);
			printArea(links, div);
		}
		$(ifr_window).load(function(){
			var links = $("iframe[name='print" + ver +"']").contents().find("link");
			var div = $("iframe[name='print" + ver +"']").contents().find(divId);
			printArea(links, div);
		});
	}
}

/**
 * 选择区域打印
 * @param {Object} links	link标签
 * @param {Object} printDiv	待打印的div
 */
function printArea(links, div) {
	$("frames[name='printArea']").remove();
	var iframe = document.createElement('IFRAME');
	var doc = null;
	$(iframe).attr('name', 'printArea');
	$(iframe).attr('style', 'position:absolute;width:0px;height:0px;left:-500px;top:-500px;');
	document.body.appendChild(iframe);
	doc = iframe.contentWindow.document;
	for ( var i = 0; i < links.length; i++) {
		if (links[i].rel.toLowerCase() == 'stylesheet') {
			doc.write('<link type="text/css" rel="stylesheet" href="' + links[i].href + '"></link>');
		}
	}
	doc.write('<style type="text/css">p {margin: 0;padding: 0;line-height: 30px;}</style>');//解决IE6下兼容问题
	doc.write('<div style="' + div.attr("style") + '">' + div.html() + '</div>');
	doc.close();
	
	var ifr_win = iframe.contentWindow;
	var isLoaded = false;
	$(ifr_win).load(function(){
		isLoaded = true;
	});
//	alert(iframe.contentWindow.document.body.innerHTML);
	if(isLoaded){
		ifr_win.focus();
		ifr_win.print();
	}
	$(ifr_win).load(function(){
		ifr_win.focus();
		ifr_win.print();
	});
}