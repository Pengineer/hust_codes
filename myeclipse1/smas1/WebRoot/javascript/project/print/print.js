$(function(){
	//内容选择	
	$(".expand").live('click', function(){
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
	
	//选择立项通知打印范围
	$("#noticePrint").unbind("click").click(function(){
		var grantYear = $("#noticeYear").val();
		thisPopLayer.callBack({
			printType : 1,	//打印类型，1：打印通知，2：打印清单	
			noticeType : $("#noticeType").val(),
			grantYear : grantYear	//立项年份
		});
	});
	
	//选择立项清单打印范围
	$("#listPrint").unbind("click").click(function(){
		if($("#listType").val() == -1){
			alert("请选择清单类型！");
			return;
		}
		
		var grantYear = $("#listYear").val();
		thisPopLayer.outData = {//待传递的参数
			printType : 2,													//打印类型，1：打印通知，2：打印清单
			grantYear : grantYear,											//立项年份
			listType : encodeURI(encodeURI($("#listType").val())),			//清单类型，1：给高校，2：给省厅，3：给其他部委
			univName : encodeURI(encodeURI($("#univName").val())),			//高校名称
			univProvName : encodeURI(encodeURI($("#univProvName").val())),	//高校所在省
			provName : encodeURI(encodeURI($("#provName").val())),			//省厅名称
			minName : encodeURI(encodeURI($("#minName").val()))			//其他部委代码
		};
		thisPopLayer.callBack(thisPopLayer.outData);
	});
	
	var resultData = [null, null, null];//全局变量，用来存储异步返回的信息
	
	//选择立项清单打印范围
	$("#listType").change(function(){
		var listType = $(this).val();
		if(listType==1){//高校
			$("#selectUniv").show();
			$("#selectProv").hide();
			$("#selectMin").hide();
			$("#selectBox").show();//指定高校下拉框显示
		}else if(listType==2){//省厅
			$("#selectUniv").hide();
			$("#selectProv").show();
			$("#selectMin").hide();
			$("#selectBox").show();//指定高校下拉框显示
		}else if(listType==3){//其他部委
			$("#selectUniv").hide();
			$("#selectProv").hide();
			$("#selectMin").show();
			$("#selectBox").show();//指定高校下拉框显示
			//已经获取到值就不需再发请求
		}else{ 
			$("#selectUniv").hide();
			$("#selectProv").hide();
			$("#selectMin").hide();
			$("#selectBox").hide();
		}
		
		//已经获取到值就不需再发请求
		if(!resultData[listType-1]) {
			var types = ["univ2Prov", "puniv2Prov", "univ2Min"];
			$.ajax({
				url : "project/general/granted/getSelectBoxData.action",
				type : "post",
				data : "listType=" + listType,
				success: function(result){
					//给学校 
					resultData[listType-1] = result[types[listType-1]];
					var resultProv = result.provinceName;
					$(".provName").empty().append($("<option value=" + -1 + ">" + "--所有高校--" + "</option>"));
					for(var i in resultProv) {
						$(".provName").append($("<option value=" + resultProv[i] + ">" + resultProv[i] + "</option>"));
					}
				}
			});
		}
	});
	
	//控制指定高校下拉框是否失效
	$("select", $(".selectBoxIsDisabled")).change(function(){
		if($(this).val() == -1) {
			$("#univName").attr("disabled", true);
		} else {
			$("#univName").attr("disabled", false);
		}
	});
	
	//1、给学校，学校名称根据所选省份级联更新
	$("#univProvName").change(function(){
		var provName1 = resultData[0][$(this).val()];//给学校的地区与对应高校
		$("#univName").empty().append($("<option value=" + -1 + ">" + "--所选地区所有高校--" + "</option>"));
		for(var i in provName1) {
			$("#univName").append($("<option value=" + provName1[i] + ">" + provName1[i] + "</option>"));
		}//填充学校名称
	});
	
	//2、给省厅，学校名称根据所选省厅级联更新
	$("#provName").change(function(){
		var provName2 = resultData[1][$(this).val()];//给省厅的省厅与对应高校
		$("#univName").empty().append($("<option value=" + -1 + ">" + "--所选省厅所属高校--" + "</option>"));
		for(var i in provName2) {
			$("#univName").append($("<option value=" + provName2[i] + ">" + provName2[i] + "</option>"));
		}//填充学校名称
	});
	
	//3、给部委，学校名称根据所选部委级联更新
	$("#minName").change(function(){
		var minName = resultData[2][$(this).val()];//给部委的部委与对应高校
		$("#univName").empty().append($("<option value=" + -1 + ">" + "--所有高校--" + "</option>"));
		for(var i in minName) {
			$("#univName").append($("<option value=" + minName[i] + ">" + minName[i] + "</option>"));
		}//填充学校名称
	});
});