define(function(require, exports, module) {
	var merge = require('javascript/person/merge');
	exports.init = function(nameSpace) {
		var checkedIds = $('#checkedIds').val();
		$.getJSON(
			nameSpace+"/fetchMergeData.action",
			{"checkedIds":checkedIds},
			function(data){
				initMerge(data);
			}
		);
	};

	function initMerge(data){		
		//学籍信息
		var unitNames = [];
		var studentTypes = [];
		var studentStatus = [];
		var studentCardNumbers = [];
		var studentStartDates = [];
		var studentEndDates = [];
		var tutorNames = [];
		var studentMajors = [];
		var studentResearchFields = [];
		var studentProjects = [];
		//学位论文信息
		var thesisTitles = [];
		var thesisFees = [];
		var isExcellents = [];
		var excellentGrades = [];
		var excellentYears = [];
		var excellentSessions = [];	
		
		for(var i=0;i<data.incomeStudents.length;i++){
			//unitNames[i] = data.incomeStudents[i].;
			studentTypes[i] = data.incomeStudents[i].type;
			studentStatus[i] = data.incomeStudents[i].status;
			studentCardNumbers[i] = data.incomeStudents[i].studentCardNumber;
			studentStartDates[i] = data.incomeStudents[i].startDate;
			studentEndDates[i] = data.incomeStudents[i].endDate;
			studentMajors[i] = data.incomeStudents[i].major;
			studentResearchFields[i] = data.incomeStudents[i].researchField;
			studentProjects[i] = data.incomeStudents[i].project;
			//学位论文信息
			thesisTitles[i] = data.incomeStudents[i].thesisTitle;
			thesisFees[i] = data.incomeStudents[i].thesisFee;
			isExcellents[i] = data.incomeStudents[i].isExcellent;
			excellentGrades[i] = data.incomeStudents[i].excellentGrade;
			excellentYears[i] = data.incomeStudents[i].excellentYear;
			excellentSessions[i] = data.incomeStudents[i].excellentSession;
		}
		
		//学籍信息
		bindUnitData($("#unitName"),data.incomeSubjectionNames,data.unitIds,data.unitTypes);
		bindBlockData($("#form_person_student_type"),studentTypes);
		bindBlockData($("#form_person_student_status"),studentStatus);
		bindBlockData($("#form_person_student_studentCardNumber"),studentCardNumbers);
		bindBlockData($("#form_person_student_startDate"),studentStartDates,true);
		bindBlockData($("#form_person_student_endDate"),studentEndDates,true);
		bindTutorData($("#tutorName"),data.incomeTutorNames,data.tutorIds);
		bindBlockData($("#form_person_student_major"),studentMajors);
		bindBlockData($("#form_person_student_researchField"),studentResearchFields);
		bindBlockData($("#form_person_student_project"),studentProjects,true);
		//学位论文信息
		bindBlockData($("#form_person_student_thesisTitle"),thesisTitles);
		bindBlockData($("#form_person_student_thesisFee"),thesisFees);
		bindRadioData($("#is_excellent"),isExcellents);
		bindBlockData($("#form_person_student_excellentGrade"),excellentGrades);
		bindBlockData($("#form_person_student_excellentYear"),excellentYears);
		bindBlockData($("#form_person_student_excellentSession"),excellentSessions,true);		
		
		merge.initBasic(data);
		merge.initContact(data);
		merge.initAcademic(data);
		
		if($("#account_select option").length>2){
			$("#account_select").addClass("select_warn");
		}
		$("#account_select").get(0).selectedIndex=1;//设置默认选中为选项1
	}
	
	function bindUnitData(selector,data,unitIds,unitTypes){			
		if(!isSame(data)){
			selector.addClass('input_warn');
		}
		
		selector.live("click",function(){
			pos=selector;
			$('.choose_block').remove();
			var blockHtml = "<div class='choose_block'>";
			for(var i=0;i<data.length;i++){
				if(data[i]==null||data[i]==undefined){
					data[i]="";
				}					
				blockHtml += '<div class="radio_row"><input class="normal_radio" type="radio" name="items" value="'+i+'"/>'+'<span>'+data[i]+'</span></div>';
			}
			blockHtml +="</div>";
			$(this).after(blockHtml);
			var x = $(this).offset().top;
			var y = $(this).offset().left;
			$(".choose_block").css("position","absolute");
			$(".choose_block").css("top",x+6);
			$(".choose_block").css("left",y);
			$(".choose_block").css("margin-top","19px");
			$(".choose_block").css("z-index", 2000);
			$(".choose_block").css("text-align", "left");

			$(".choose_block").css("top",x-76);

			
			//raido选择操作
			$('.normal_radio').die().live('click',function(){
				pos.html($(this).next().text()); 
				$("#form_person_departmentId").val(unitIds[$(this).val()]);
				$("#form_person_instituteId").val(unitIds[$(this).val()]);
				$("#form_person_unitType").val(unitTypes[$(this).val()]);
			});
		});
		
		selector.bind('blur',function(){
			$('.choose_block').remove();
		});
	}
	
	/**
	 * 绑定导师数据
	 */
	function bindTutorData(selector,data,ids){			
		if(!isSame(data)){
			selector.addClass('input_warn');
		}
		
		selector.live("click",function(){
			pos=selector;
			$('.choose_block').remove();
			var blockHtml = "<div class='choose_block'>";
			for(var i=0;i<data.length;i++){
				if(data[i]==null||data[i]==undefined){
					data[i]="";
				}					
				blockHtml += '<div class="radio_row"><input class="normal_radio" type="radio" name="items" value="'+i+'"/>'+'<span>'+data[i]+'</span></div>';
			}
			blockHtml +="</div>";
			$(this).after(blockHtml);
			var x = $(this).offset().top;
			var y = $(this).offset().left;
			$(".choose_block").css("position","absolute");
			$(".choose_block").css("top",x+6);
			$(".choose_block").css("left",y);
			$(".choose_block").css("margin-top","19px");
			$(".choose_block").css("z-index", 2000);
			$(".choose_block").css("text-align", "left");

			$(".choose_block").css("top",x-76);

			
			//raido选择操作
			$('.normal_radio').die().live('click',function(){
				pos.html($(this).next().text()); 
				$("#form_person_student_tutor_id").val(ids[$(this).val()]);
				
			});
		});
		
		selector.bind('blur',function(){
			$('.choose_block').remove();
		});
	}
	
	function bindRadioData(selector,data){			
		if(!isSame(data)){
			selector.addClass('input_warn');
		}
		
		selector.live("click",function(){
			pos=selector;
			$('.choose_block').remove();
			var blockHtml = "<div class='choose_block'>";
			for(var i=0;i<data.length;i++){
				if(data[i]==null||data[i]==undefined){
					data[i]="";
				}
				if(data[i]==0){
					data[i]='否';
				}else if(data[i]==1) {
					data[i]='是';
				}
				blockHtml += '<div class="radio_row"><input class="normal_radio" type="radio" name="items" value="'+i+'"/>'+'<span>'+data[i]+'</span></div>';
			}
			blockHtml +="</div>";
			$(this).after(blockHtml);
			var x = $(this).offset().top;
			var y = $(this).offset().left;
			$(".choose_block").css("position","absolute");
			$(".choose_block").css("top",x+6);
			$(".choose_block").css("left",y);
			$(".choose_block").css("margin-top","19px");
			$(".choose_block").css("z-index", 2000);
			$(".choose_block").css("text-align", "left");
			$(".choose_block").css("top",x-76);
			
			//raido选择操作
			$('.normal_radio').die().live('click',function(){
				if(data[$(this).val()]=='是') {
					$("#form_person_student_isExcellent1").attr("checked",'checked');
				}else if(data[$(this).val()]=='否') {
					$("#form_person_student_isExcellent0").attr("checked",'checked');
				}
			});
		});
		
		selector.bind('blur',function(){
			$('.choose_block').remove();
		});
	}

});