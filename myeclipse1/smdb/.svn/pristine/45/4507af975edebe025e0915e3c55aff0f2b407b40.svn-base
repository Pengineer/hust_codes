define(function(require, exports, module) {
	var view = require('javascript/view');
	var list = require('javascript/list');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('javascript/template_tool');
	
	var download_product = function(){
		$(".download_product").live("click",function(){
			downLoadProduct(this.id,this.name,this.type);
			return false;
		});
	}
	
	/**
	 * 下载成果文件
	 * @param id 成果id
	 * @param fileName 文件名
	 * @param type 成果形式
	 */
	function downLoadProduct(id,fileName,type){
		var nameSpace=(type=="论文")? "product/paper" :(function(){return (type=="著作")? "product/book" :"product/consultation";})();
		var validateUrl = nameSpace+"/validateFile.action?entityId="+id+"&fileFileName="+fileName;
		var successUrl = nameSpace+"/download.action?fileFileName="+fileName;
		downloadFile(validateUrl, successUrl);
	}
	
	var toggleKey = function(obj, nameSpace) {// 设置是否关键人
		var isKey = 1 - $(obj).attr("isKey");
		$.ajax({
			url: nameSpace + "/toggleKey.action",
			type: "post",
			data: "personId=" + $(obj).attr("alt") + "&isKey=" + isKey,
			dataType: "json",
			success: function(result) {
				if (result.errorInfo == null || result.errorInfo == "") {
					$(obj).attr("isKey", isKey);
					if (isKey) {
						$(obj).find("img").eq(0).attr("src", "image/person_on.png");
						$(obj).find("img").eq(0).attr("title", "重点人，点击切换至非重点人");
					} else {
						$(obj).find("img").eq(0).attr("src", "image/person_off.png");
						$(obj).find("img").eq(0).attr("title", "非重点人，点击切换至重点人");
					}
				} else {
					alert(result.errorInfo);
				}
			}
		})
	}
	
	exports.toggleKey = function(obj, nameSpace) {
		toggleKey(obj, nameSpace);
	}
	$(".linkAF").live("click", function() {
		$.ajax({
			url: "linkedin/checkToAddFriend.action",
			type: "post",
			data: "entityId="+this.id,
			dataType: "json",
			success: function(result) {
				if (result.errorInfo == null || result.errorInfo == "") {
					popAddF(this.id);
				} else {
					alert(result.errorInfo);
				}
			}
		})
		return false;
	});
	
	exports.init = function(nameSpace, showDetails) {
		view.show(nameSpace, showDetails);
		view.add(nameSpace);
		view.mod(nameSpace);
		view.del(nameSpace, "您确定要删除此人员吗？");
		view.prev(nameSpace, showDetails);
		view.next(nameSpace, showDetails);
		view.back(nameSpace);// 所有查看页面公共部分
		download_product();//在专家、教师、学生页面起作用
		Template_tool.init();
		view.inittabs();
		$(".toggleKey").live('click', function(){
			var that = this;
			toggleKey(that, nameSpace);
			return false;
		});
	}
})

