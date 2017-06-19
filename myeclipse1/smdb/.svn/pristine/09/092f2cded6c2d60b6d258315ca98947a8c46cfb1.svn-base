define(function(require, exports, module) {
	var list = require('javascript/list');
	var viewPerson = require('javascript/person/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	var nameSpace = "person/provinceOfficer";
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":nameSpace,
			"addUrl":"person/provinceOfficer/toAdd.action",
			"delUrl":"person/provinceOfficer/delete.action",
			"delParam":"entityIds",
			"viewUrl":"person/provinceOfficer/toView.action",
			"viewParam":"entityId",
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5}
		});
		$(".linkA").live("click", function() {
			popAgency(this.id, 1);
			return false;
		});
		$(".toggleKey").live('click', function(){
			var that = this;
			viewPerson.toggleKey(that, nameSpace);
			return false;
		});
		$("#list_sendInBox").live("click", function() {
			var checkedboxs = $("input[type='checkbox'][name='entityIds']:checked");
			if(checkedboxs.length == 0) {
				alert("请选择..."); 
				return;
			} else if(checkedboxs.length > 0) {
				var entityIds = "";
				checkedboxs.each(function(index){
					if(index == 0){
						entityIds = "entityIds[" + index + "]=" + $(this).val();
					}else{
						entityIds += "&entityIds[" + index + "]=" + $(this).val();
					}
				});
				
				$.ajax({
					url: basePath + "inBox/checkAccountId.action?" + entityIds + "&nameSpace=" + nameSpace,
					success: function(result){
						if (result.errorInfo == null || result.errorInfo == "") {
							location.href = basePath + "inBox/toAdd.action?" + entityIds + "&nameSpace=" + nameSpace + "&sendType=1";
						} else {
							var errorInfo = result.errorInfo;
							if(errorInfo.indexOf("确定发送") > 0){//一部分人员或机构有帐号
								if(confirm(errorInfo)){
									location.href = basePath + "inBox/toAdd.action?" + entityIds + "&nameSpace="+ nameSpace + "&sendType=1";
								}
							}else{
								alert(result.errorInfo);
							}
						}
					}
				});
			}
			return false;
		});
	};
});
