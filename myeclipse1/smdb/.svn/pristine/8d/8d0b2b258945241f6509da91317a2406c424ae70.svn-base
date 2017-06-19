define(function(require, exports, module) {
	var view = require('javascript/view');
	var viewProduct = require('javascript/product/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	thisPopLayer = top.PopLayer.instances[top.PopLayer.id];	
	
	//审核成果
	var audit = function(url, flag, nameSpace, showDetails){
		if(flag == 1) {//1.列表页面
			$("#pagenumber").attr("value", $("#list_goto").val());
			$("#list").attr("action", url).submit();
		} else if(flag == 2) {//2.查看页面
			$.ajax({
				url: url,
				type: "post",
				data: "entityIds=" + $("#entityId").val(),
				dataType: "json",
				success: function(result){
					if(result.errorInfo == null || result.errorInfo == "") {
						$("#update").val(1);
						view.show(nameSpace, showDetails);
					} else {
						alert(result.errorInfo);
					};
				}
			});
		};
	};
	
	exports.audit = function(url, flag, nameSpace, showDetails) {
		audit(url, flag, nameSpace, showDetails);
	};
	
	exports.init = function(){
		$("#confirm").unbind("click").click(function(){
//			if(!$("#audit_product").valid()){
//				return;
//			}
			var auditResult = $("input[name='auditResult'][type='radio']:checked").val();
			if(confirm("确定提交审核结果吗？")){
				thisPopLayer.callBack({"auditResult" : auditResult}, thisPopLayer);
				thisPopLayer.destroy();
			};
			return false;
		});
	
//		$("#audit_product").validate({//校验审核结果不能为空
//			errorElement: "span",
//			event: "blur",
//			rules: {"auditResult":{required: true}},
//			errorPlacement: function(error, element){
//				error.appendTo( element.parent("td").next("td") );
//			}
//		});
	};
});



