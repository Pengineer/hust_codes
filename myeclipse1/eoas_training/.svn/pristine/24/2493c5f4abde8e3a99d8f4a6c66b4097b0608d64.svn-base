$(function(){
	$("#view_add").click(function(){
		window.location.href = "right/toAdd.action";
	});
	$("#view_mod").click(function(){
		window.location.href = "role/toModify.action?roleId=" + $("#rightId").val();
	});
	$("#view_del").click(function(){
		dialog({
		    title: '提示',
		    content: '确定要删除吗?',
		    width:250,
		    okValue: '确定',
		    ok: function () {
		    	var url = "right/delete.action?rightId=" + $("#rightId").val();
		    	$.get(url, function(data){
		    		if(data.result == "1"){
		    			dialog({
		    			    title: '提示',
		    			    content: '删除成功',
		    			    width:250,
		    			    okValue: '确定',
		    			    ok: function () {
		    			    	window.location.href = "right/toList.action";
		    			    }
		    			}).showModal();
		    		} else {
		    			dialog({
		    			    title: '提示',
		    			    content: '删除失败!',
		    			    width:250,
		    			    okValue: '确定',
		    			    ok: function () {
		    			    	window.location.href = "right/toList.action";
		    			    }
		    			}).showModal();
		    		}
		    	})

		    	
		    },
		    cancelValue: '取消',
		    cancel: function () {}
		}).showModal();
	});
	$("#view_back").click(function(){
		window.location.href = "right/toList.action";
	});
});