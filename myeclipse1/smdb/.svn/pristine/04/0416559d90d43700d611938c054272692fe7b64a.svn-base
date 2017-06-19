/**
 * @author 肖宁
 */

define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('pop-init');
	require('form');
	require('validate');
	var datepick = require("datepick-init");
	var viewGranted = require("javascript/project/project_share/view");
	var viewSpecial = require('javascript/project/special/view');
	
	var projectType="special";
	
	var valid = function(){
		$("#form_addGrantedFee").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"bookFee":{number:true, range:[0,parseInt($("#approveFee").val(),10)]},
				"dataFee":{number:true, range:[0,parseInt($("#approveFee").val(),10)]},
				"travelFee":{number:true, range:[0,parseInt($("#approveFee").val(),10)]},
				"conferenceFee":{number:true, range:[0,parseInt($("#approveFee").val(),10)]},
				"internationalFee":{number:true, range:[0,parseInt($("#approveFee").val(),10)]},
				"deviceFee":{number:true, range:[0,parseInt($("#approveFee").val(),10)]},
				"consultationFee":{number:true, range:[0,parseInt($("#approveFee").val(),10)]},
				"laborFee":{number:true, range:[0,parseInt($("#approveFee").val(),10)]},
				"printFee":{number:true, range:[0,parseInt($("#approveFee").val(),10)]},
				"indirectFee":{number:true, range:[0,parseInt($("#approveFee").val(),10)]},
				"otherFeeD":{number:true, range:[0,parseInt($("#approveFee").val(),10)]}
			},
			errorPlacement: function(error, element){
				error.appendTo( element.parent("td").next("td").next("td") );
			}
		});
	};
	
	
		//说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。  
	//调用：accAdd(arg1,arg2)  
	//返回值：arg1加上arg2的精确结果  
	var accAdd = function accAdd(arg1,arg2){  
	    var r1,r2,m;  
	    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}  
	    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}  
	    m=Math.pow(10,Math.max(r1,r2))  
	    return (arg1*m+arg2*m)/m  
	}  
	   
	//给Number类型增加一个add方法，调用起来更加方便。  
	Number.prototype.add = function (arg){  
	    return accAdd(arg,this);  
	} 
	
	var add = function(var1,var2,var3,var4,var5,var6,var7,var8,var9,var10,var11){
		var result = 0;
		if(!isNaN(var1))
			result = accAdd(result, var1);
		if(!isNaN(var2))
			result = accAdd(result, var2);
		if(!isNaN(var3))
			result = accAdd(result, var3);
		if(!isNaN(var4))
			result = accAdd(result, var4);
		if(!isNaN(var5))
			result = accAdd(result, var5);
		if(!isNaN(var6))
			result = accAdd(result, var6);
		if(!isNaN(var7))
			result = accAdd(result, var7);
		if(!isNaN(var8))
			result = accAdd(result, var8);
		if(!isNaN(var9))
			result = accAdd(result, var9);
		if(!isNaN(var10))
			result = accAdd(result, var10);
		if(!isNaN(var11))
			result = accAdd(result, var11);
		return result;
	}
	
	
	//走流程添加立项预算
	var toAddGrantedFeeApplyPop = function(projectType, showProjectDetails) {
		var projectid = $("#projectid").val();
		var approveFee = $("#approveFee").val();
		popAddGrantedFee({
			title : "添加立项经费预算明细",
			src : "project/special/application/granted/toAddGrantedFeeApply.action?projectid=" + $("#projectid").val()+ "&approveFee=" + $("#approveFee").val(),
//			inData : {"varPending" : $("#varPending").val(),"midPending" : $("#midPending").val()},
			callBack : function(dis){
				$("#update").val(1);
				$.ajax({
					url: "project/special/application/granted/addGrantedFeeApply.action",
					type: "post",
					data: "bookFee=" + dis.bookFee + "&bookNote=" + dis.bookNote + "&dataFee=" + dis.dataFee + "&dataNote=" + dis.dataNote +
						  "&travelFee=" + dis.travelFee + "&travelNote=" + dis.travelNote + "&conferenceFee=" + dis.conferenceFee +
					  	  "&conferenceNote=" + dis.conferenceNote + "&internationalFee=" + dis.internationalFee + "&internationalNote=" + dis.internationalNote +
					      "&deviceFee=" + dis.deviceFee + "&deviceNote=" + dis.deviceNote + "&consultationFee=" + dis.consultationFee + 
					      "&consultationNote=" + dis.consultationNote + "&laborFee=" + dis.laborFee + "&laborNote=" + dis.laborNote + 
					      "&printFee=" + dis.printFee + "&printNote=" + dis.printNote + "&indirectFee=" + dis.indirectFee + 
					      "&indirectNote=" + dis.indirectNote + "&otherFeeD=" + dis.otherFeeD + "&otherNote=" + dis.otherNote + 
					      "&feeNote=" + dis.feeNote + "&totalFee=" + dis.totalFee + "&projectid=" + $("#projectid").val(),
					dataType: "json",
					success: function(result){
						if (result.errorInfo == null || result.errorInfo == "") {
							$("#update").val(1);
							viewGranted.viewDeltails(projectType, showProjectDetails);
						}
						else {
							alert(result.errorInfo);
						}
					}	
				});
			}
		});
	};
	
	var addGrantedFeeApply = function(layer){
		if(!$("#form_addGrantedFee").valid()){//用户输入校验
			return;
		}else{
			
			var total = $("#totalFeeD").html()*1;
			var grantedtotal = $("#approveFee").val()*1;
			if(total != grantedtotal){
				alert("经费合计总额与批准经费不相等");
				return;
			}
			
			if(!confirm("此操作一旦提交无法修改，请慎重选择。您确定要提交么？")){
				return;
			}
			var dis={
				"bookFee":$("#bookFee").val(),
				"bookNote":$("#bookNote").val(),
				"dataFee":$("#dataFee").val(),
				"dataNote":$("#dataNote").val(),
				"travelFee":$("#travelFee").val(),
				"travelNote":$("#travelNote").val(),
				"conferenceFee":$("#conferenceFee").val(),
				"conferenceNote":$("#conferenceNote").val(),
				"internationalFee":$("#internationalFee").val(),
				"internationalNote":$("#internationalNote").val(),
				"deviceFee":$("#deviceFee").val(),
				"deviceNote":$("#deviceNote").val(),
				"consultationFee":$("#consultationFee").val(),
				"consultationNote":$("#consultationNote").val(),
				"laborFee":$("#laborFee").val(),
				"laborNote":$("#laborNote").val(),
				"printFee":$("#printFee").val(),
				"printNote":$("#printNote").val(),
				"indirectFee":$("#indirectFee").val(),
				"indirectNote":$("#indirectNote").val(),
				"otherFeeD":$("#otherFeeD").val(),
				"otherNote":$("#otherNote").val(),
				"feeNote":$("#feeNote").val(),
				"totalFee":$("#totalFeeD").html()
			};
			layer.callBack(dis);
			layer.destroy();
		}
	};
	
	exports.init = function() {
		valid();
		
		var ids = ["#bookFee","#dataFee","#travelFee","#conferenceFee","#internationalFee","#deviceFee","#consultationFee","#laborFee","#printFee","#indirectFee","#otherFeeD"];
		for(var i=0;i<ids.length;i++){
			$(ids[i]).blur(function() {
				var sum = add(parseFloat($("#bookFee").val()),parseFloat($("#dataFee").val()),parseFloat($("#travelFee").val()),parseFloat($("#conferenceFee").val()),parseFloat($("#internationalFee").val()),parseFloat($("#deviceFee").val()),parseFloat($("#consultationFee").val()),parseFloat($("#laborFee").val()),parseFloat($("#printFee").val()),parseFloat($("#indirectFee").val()),parseFloat($("#otherFeeD").val()));
				$("#totalFeeD").attr("value",sum);
				$("#totalFeeD").html(sum);
				if(sum > $("#approveFee").val()*1){
					$("#totalFeeD").css("color", "red");
					$("#totalFeeD").html("您输入的经费总额大于申请经费");
					return;
				}
			});
		}
		
		$(".j_toAddGrantedFeeApplyPop").live('click',function(){//进入项目状态设置弹层页面
			toAddGrantedFeeApplyPop(projectType, viewSpecial.showProjectDetails);
		});
		$("#submit").live('click',function(){//项目状态设置弹层页面设置项目状态
			addGrantedFeeApply(thisPopLayer);
		});
//		window.setUpProjectStatusPop = function(){setUpProjectStatusPop(projectType, viewSpecial.showProjectDetails)};
//		window.setUpProjectStatus = function(layer){setUpProjectStatus(layer)};
	};
	
});