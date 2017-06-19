/**
 * 添加修改备忘
 */
$(document).ready(function(){
	/**
	 * 是否提醒(radio)
	 */
		$(".r_type").click(function(){
			var types = ["0","1"],val = $(this).val();
			if(val == types[1]){
				$("#s_type").show();
				
				detect();//修改点击"是"时初始化展开提醒方式
			}else{
				$("#s_type").hide();
				$("._remind").hide();
			}
		});
	detect();//修改时初始化展开提醒方式
	setMemoWeek();//读取星期的初始值
		
	/**
	 * 选择提醒方式(select)
	 */
	$("#_type").change(function(){
		var val=$(this).val();//this指代_type所指代的select控件
		
		$("._remind").each(function(index){
			if(index == val - 1){
				$(this).show();
			}else{
				$(this).hide();
			}
		});
	});
	/**
	 * 指定月、日
	 */
	$(".month").toggle(function(){
		$(this).css("background", "#A69CD1").addClass("selected");
	}, function(){
		$(this).css("background", "#F5F5F5").removeClass("selected");
	}).mouseover(function(){
		$(this).css("cursor","pointer");
	});
	/**
	 * 提交表单
	 * @memberOf {TypeName} 
	 */
	$("#form_memo").submit(function(){
		var month = [];
		$("td .selected").each(function(){
			month.push($.trim($(this).text()));
		})
		
		$("#memo_month").val(month.join("; "));
		
		//return false;
	});
	
	
});	


	

/**
 * 展开提醒方式
 */
function detect(){
	if($(".r_type:checked").val() == 1 && $("#_type").val()!=0){
	$("#s_type").show();
	$("._remind").eq($("#_type").val() - 1).show();
	}
}
/**
 * 读取星期的初始值
 * @memberOf {TypeName} 
 */
function setMemoWeek(){
	var memoWeek = $("#m_week").val();
	if (memoWeek != undefined && memoWeek != null && memoWeek != ""){
		$("[name='memo.week']").each(function() {
			var tmp = memoWeek.match($(this).val());
			this.checked = (tmp != null ? true : false);
		});
	}
}

