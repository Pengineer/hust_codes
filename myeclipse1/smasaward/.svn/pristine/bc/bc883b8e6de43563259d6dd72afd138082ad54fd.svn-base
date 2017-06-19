$(document).ready(function() {
	
	/*
	 * 导出专家弹层中的折叠效果
	*/
	$(".expand").live('click', function(){
		$(".hide").each(function(){
			$(this).hide();
		});
		$(".base_info").each(function(){
			$(this).removeClass("close");
			$(this).addClass("expand");
		});
		$(this).next().show(500);
		$(this).removeClass("expand");
		$(this).addClass("close");
	});
	
	$(".close").live('click', function(){
		$(this).next().hide();
		$(this).removeClass("close");
		$(this).addClass("expand");
	});
	
	/*
	 * 选择所有职称或者部分职称的折叠效果
	*/
	$("input[name=chooseSpecialityTitles]").change(function(){
		if($("input[name=chooseSpecialityTitles]:checked").val() == "所有"){
			$("#partSpecialityTitles").hide(500);
		}else{
			$("#partSpecialityTitles").show(500);
		}
	});
	
	/*
	 * 选择所有学科则全部学科都选中，否则全部未选中，给用户自己点击选择
	*/
	$("#selectAllDisciplines").live("click",function(){
		if($("#selectAllDisciplines").is(":checked") == true){
			$("input[name^=disciplines]").each(function(){
				$(this).attr("checked",true);
			});
		}else{
			$("input[name^=disciplines]").each(function(){
				$(this).attr("checked",false);
			});
		}
	})
	
	$("#confirm").unbind("click").live('click', function(){
		var $isValid =$("input[name=maximunNumber]").val();
		//判断是否为合法的正整数
		var isInteger = function(obj) {
			return typeof Number(obj) === 'number' && Number(obj)%1 === 0 && Number(obj) > 0;
		};
		var $number = isInteger($isValid);
		if (!$number){
			alert("请输入合法的正整数！");
			return;
		} else {
			//定义传递参数，分别对应专家职称、学科代码、参评类型、参评项目类型、高校类型、数量上限
			var specialityTitles = "",
				disciplines = "",
				reviewExpertType = "",
				reviewExpertProjectType = "",
				universityType = "",
				maximunNumber = "";
				
			/*
			 * 如果选择所有职称，则发送“所有”；否则，遍历部分支持，取值并发送，如果选择部分职称，但没有选择具体职称，则默认发送所有职称。
			 */
			if($("#allSpecialityTitles").is(":checked") == true){
				specialityTitles = encodeURI(encodeURI($("#allSpecialityTitles").val()));
			}else{
				$("input[name^=specialityTitles]:checked").each(function(){
					specialityTitles += encodeURI(encodeURI($(this).val()));
					specialityTitles += ";";
				});
				if("" == specialityTitles){
					specialityTitles = encodeURI(encodeURI($("#allSpecialityTitles").val()));
				};
			}
			
			/*
			 * 遍历选择的学科并发送，如果没有选择学科，则默认发送所有学科
			 */
			$("input[name^=disciplines]:checked").each(function(){
				disciplines += $(this).val();
				disciplines += ";";
			});
			if("" == disciplines){
				$("input[name^=disciplines]").each(function(){
					disciplines += $(this).val();
					disciplines += ";";
				});
			}
			
			/*
			 * 遍历选择的专家参评类型并发送，如果没有选择参评专家类型，则默认发送“参与匹配专家”
			 */
			$("input[name^=reviewExpertType]:checked").each(function(){
				reviewExpertType += encodeURI(encodeURI($(this).val()));
				reviewExpertType += ";";
			});
			if("" == reviewExpertType){
				reviewExpertType += encodeURI(encodeURI("参与匹配专家"));
			}
			
			reviewExpertProjectType = encodeURI(encodeURI($("input[name=reviewExpertProjectType]:checked").val()));
			
			/*
			 * 数量默认是5000，即如果用户没有选择数量，则默认发送5000
			 */
			maximunNumber = $("input[name=maximunNumber]").val();
			if("" == maximunNumber){
				maximunNumber = "5000";
			}
			
			//reviewExpertType = $("input[name^=reviewExpertType]:checked").val();
			universityType = $("input[name=universityType]:checked").val();
			
			document.location.href = "expert/exportExpert.action?specialityTitles=" + specialityTitles
									+ "&disciplines=" + disciplines + "&reviewExpertType=" + reviewExpertType 
									+ "&universityType=" + universityType + "&maximunNumber=" + maximunNumber
									+ "&reviewExpertProjectType=" + reviewExpertProjectType;
//			$.ajax({
//				url: "expert/exportExpert.action",
//				type: "post",
//				data: "specialityTitles=" + specialityTitles + "&disciplines=" + disciplines + "&reviewExpertType=" + reviewExpertType + "&universityType=" + universityType,
//				//dataType: "json",
//				success: function(data, textStatus){
//					//thisPopLayer.callBack(result);
//					thisPopLayer.callBack(data);
//					thisPopLayer.destroy();
//				},
//				error:function(XMLHttpRequest, textStatus, errorThrown){
//					alert(textStatus);
//					alert("导出失败");
//					thisPopLayer.destroy();
//				}
//			});
			
		}
	});
	
	
	
});