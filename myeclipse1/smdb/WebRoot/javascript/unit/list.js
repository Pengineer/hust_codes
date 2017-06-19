define(function(require, exports, module) {
	var list = require('javascript/list');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	/**
	 * 合并高校、院系、研究基地
	 * @param {Object} type
	 * @param {Object} nameSpace
	 * @memberOf {TypeName} 
	 */
	exports.merge = function(type, nameSpace){
		$("#list_merge").live("click",function(){
			var checkedIds = $("#checkedIds").val();
			$("input[name='entityIds']").each(function() {
				if (this.checked && (checkedIds.indexOf(this.value) == -1)) {
					checkedIds += $(this).val() + ",";
				}
			});
			var cnt = checkedIds.split(",").length - 1;
			if($("#canMerge").val() =="true"){
				if (cnt < 2 ) {
					alert("选择合并的单位至少为2个！");
				} else {
					popMerge({
						type : type,
						src : nameSpace + "/toMerge.action?checkedIds=" + checkedIds,
						callBack : function(result){
							$("#pagenumber").val($("#list_pagenumber").val());// 记录当前页码，以便回到此页面
							$("#checkedIds").val(result.data.checkedIds);
							$("#mainId").val(result.data.mainId);
							$("#unitName").val(result.data.name);
							$("#code").val(result.data.code);
							$("#list").attr("action", nameSpace + "/merge.action");
							$("#list").submit();
							$("#checkedIds").val("");
						}
					})
				}
			} else {
				alert("数据库结构发生变化，当前不可合并！");
			}
		});
	};
	
	exports.init = function(nameSpace) {
		list.pageShow({
			"nameSpace":nameSpace,
			"addUrl":nameSpace+"/toAdd.action",
			"delUrl":nameSpace+"/delete.action",
			"delParam":"entityIds",
			"viewUrl":nameSpace+"/toView.action",
			"viewParam":"entityId",
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7}
		});
		$(".link2").live("click", function() {
			popPerson(this.id, 1);
			return false;
		});
		$(".linkA").live("click", function() {
			popAgency(this.id, 1);
			return false;
		});
		$(".linkI").live("click", function() {
			popInst(this.id, 3);
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
					url: basePath + "inBox/checkAccountId.action?" + entityIds+"&nameSpace="+nameSpace,
					success: function(result){
						if (result.errorInfo == null || result.errorInfo == "") {
							location.href = basePath + "inBox/toAdd.action?" + entityIds+"&nameSpace="+nameSpace+ "&sendType=1";
						} else {
							var errorInfo = result.errorInfo;
							if(errorInfo.indexOf("确定发送") > 0){//一部分人员或机构有帐号
								if(confirm(errorInfo)){
									location.href = basePath + "inBox/toAdd.action?" + entityIds+"&nameSpace="+nameSpace+ "&sendType=1";
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
		//导出基地
		$("#list_export").live("click", function(){
			window.location.href = basePath + "unit/institute/export.action";
		});
	};
});