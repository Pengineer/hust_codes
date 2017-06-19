define(function(require,exports,module){
	var list = require('javascript/list');
	var nameSpace = "management/recruit/applicant";
	function popSetStatus(args){
		new top.PopLayer({"title" : "审核意见", "src" : args.src, 
			"document" : top.document, "inData" : args.inData, "callBack" : args.callBack});
	}
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3},
			"selectedTab":"basic",
			"dealWith": function(){
				$(".applyDate").each(function(){
					var date = $(this).html().substring(0,10);
					$(this).html(date);
				});
			}
		});
		$("#setVerifyResult").live("click",function(){
			if($(":radio:checked").length){
				var args = {
						src :"management/recruit/applicant/toVerify.action",
						callBack:function(data){
							var status;
							switch (data.status){
							case "1": status = " <span style = 'color:#31B0D5'>审核通过</span>"; break;
							case "2": status = "<span style = 'color:#D9534F'>审核不通过</span>"; break;							
							}
							$(":radio:checked").parent().parent().children(".status").html(status);
							
						},//回调函数，用于在弹层里更该数据后在负页面更新数据。
						inData:{
							entityId: $(":radio:checked").val()
						}//将id传给弹层。
				}
				
				popSetStatus(args);
			} else {
				alert("您没有选择人员，请选择！");
			}
			
			return false;
		});
		
	};
});