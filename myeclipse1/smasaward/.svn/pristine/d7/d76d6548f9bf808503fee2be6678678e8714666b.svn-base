/**
 * 退评选中的项目
 */
function confirmWin() {
	var pojids = document.getElementsByName('projectIds');
	var cnt = 0;
	var ids = '';
	for(var i = 0; i < pojids.length; i++) {
		if(pojids[i].checked) {
			cnt++;
			ids = ids + pojids[i].value + ',';
		}
	}
	if(cnt > 0 && ids.length > 1) {
		ids = ids.substr(0, ids.length - 1);
	}
	if(cnt == 0) {
		alert('请至少选择一个需要退评的项目！');
	}
	if(cnt > 0 && ids != '' && confirm('您确定针对此专家退评所选中的[' + cnt + ']个项目吗？')) {
		$.ajax({
			url: "project/general/deleteExpertProjects.action",
			type: "post",
			data : "expertId=" + $('#entityId').val() + "&projectId=" + ids,
			dataType: "json",
			success: function(result){
				if (result == null || result.errorInfo == null || result.errorInfo == "") {
					thisPopLayer.callBack(result);//调用回调函数
					thisPopLayer.destroy();
				}else{
					alert(result.errorInfo);
				}
			}
		});
	}
}