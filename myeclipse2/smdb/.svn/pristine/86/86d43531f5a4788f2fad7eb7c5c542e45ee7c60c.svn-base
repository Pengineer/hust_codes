define(function(require, exports, module) {
	/**
	 * 学术信息预合并数据初始化
	 */
	exports.initAcademic = function(data){
		var lastEducations = [];
		var lastDegrees = [];
		var acCountryRegions = [];
		var degreeDates = [];
		var acdemicTeacherTypes = [];
		var postdoctors = [];
		var specialityTitles = [];
		var subTitles = [];
		var positionLevels = [];
		var tutorTypes = [];
		var academicTalents = [];	
		var ethnicLanguages = [];
		var languages = [];
		var computerLevels = [];	
		var disciplineTypes = [];
		var disciplines = [];
		var relativeDisciplines = [];
		var academicMajors = [];
		var academicResearchFields = [];
		var researchSpecialities = [];
		var furtherEducations = [];
		var parttimeJobs = [];
		//初始化数据
		for(var i=0;i<data.incomeAcademics.length;i++){
			lastEducations[i] = data.incomeAcademics[i].lastEducation;
			lastDegrees[i] = data.incomeAcademics[i].lastDegree;
			acCountryRegions[i] = data.incomeAcademics[i].countryRegion;
			degreeDates[i] = data.incomeAcademics[i].degreeDate;
			acdemicTeacherTypes[i] = data.expertTypes[i]; 
			if(data.incomeAcademics[i].postdoctor == '0'){
				postdoctors[i] = '否';
			}else if(data.incomeAcademics[i].postdoctor == '1'){
				postdoctors[i] = '在站';
			}else{
				postdoctors[i] = '出站';
			}
			specialityTitles[i] = data.specialityTitleNames[i];
			subTitles[i] = data.incomeAcademics[i].specialityTitle;
			positionLevels[i] = data.incomeAcademics[i].positionLevel;
			tutorTypes[i] = data.incomeAcademics[i].tutorType;
			academicTalents[i] = data.incomeAcademics[i].talent;
			
			ethnicLanguages[i] = data.incomeAcademics[i].ethnicLanguage;
			languages[i] = data.incomeAcademics[i].language;
			computerLevels[i] = data.computerLevels[i];
			
			disciplineTypes[i] = data.incomeAcademics[i].disciplineType;
			disciplines[i] = data.incomeAcademics[i].discipline;
			relativeDisciplines[i] = data.incomeAcademics[i].relativeDiscipline;
			academicMajors[i] = data.incomeAcademics[i].major;
			academicResearchFields[i] = data.incomeAcademics[i].researchField;
			researchSpecialities[i] = data.incomeAcademics[i].researchSpeciality;
			furtherEducations[i] = data.incomeAcademics[i].furtherEducation;
			parttimeJobs[i] = data.incomeAcademics[i].parttimeJob;
		}
		//绑定时间
		bindBlockData($("#form_person_academic_lastEducation"),lastEducations);
		bindBlockData($("#form_person_academic_lastDegree"),lastDegrees);
		bindBlockData($("#form_person_academic_countryRegion"),acCountryRegions);
		bindBlockData($("#datepick"),degreeDates,true);
		bindBlockData($("#form_person_academic_expertType_id"),acdemicTeacherTypes);
		bindBlockData($("#form_person_academic_postdoctor"),postdoctors);
		bindBlockData($("#title"),specialityTitles);
		bindBlockData($("#subTitle"),subTitles);	
		bindBlockData($("#form_person_academic_positionLevel"),positionLevels);
		bindBlockData($("#form_person_academic_tutorType"),tutorTypes);
		bindBlockData($("#form_person_academic_talent"),academicTalents);		
		bindBlockData($("#form_person_academic_computerLevel_id"),computerLevels);
	};
	
	/**
	 * 初始化人员基本信息预合并页面
	 */
	exports.initBasic = function(data){
		var names = [];
		var idcardTypes = [];
		var idcardNumbers = [];
		var englishNames = [];
		var usedNames = [];
		var genders = [];
		var birthdays = [];
		var memberships = [];
		var countryRegions = [];
		var ethnics = [];
		var birthplaces = [];
		
		for(var i=0;i<data.incomePerson.length;i++){
			names[i]=data.incomePerson[i].name;
			idcardTypes[i]=data.incomePerson[i].idcardType;
			idcardNumbers[i]=data.incomePerson[i].idcardNumber;
			englishNames[i]=data.incomePerson[i].englishName;
			usedNames[i]=data.incomePerson[i].usedName;
			genders[i]=data.incomePerson[i].gender;
			birthdays[i]=(data.incomePerson[i].birthday!=null)?(data.incomePerson[i].birthday.substr(0,10)):"";		
			memberships[i]=data.incomePerson[i].membership;
			countryRegions[i]=data.incomePerson[i].countryRegion;
			ethnics[i]=data.incomePerson[i].ethnic;
			birthplaces[i]=data.incomePerson[i].birthplace;		
		}
		
		bindBlockData($("#form_person_person_name"),names);
		bindBlockData($("#form_person_person_idcardType"),idcardTypes);		
		bindBlockData($("#form_person_person_idcardNumber"),idcardNumbers);
		bindBlockData($("#form_person_person_englishName"),englishNames);
		bindBlockData($("#form_person_person_usedName"),usedNames);
		bindBlockData($("#form_person_person_gender"),genders);
		bindBlockData($("#form_person_person_birthday"),birthdays,true);
		bindBlockData($("#form_person_person_membership"),memberships);		
		bindBlockData($("#form_person_person_countryRegion"),countryRegions);
		bindBlockData($("#form_person_person_ethnic"),ethnics);
		bindBlockData($("#form_person_person_birthplace"),birthplaces);
	}
	
	/**
	 * 初始化联系信息预合并页面
	 */
	exports.initContact = function(data){	
		
	}
	
	/**
	 * 初始化银行信息预合并页面
	 */
	exports.initBank = function(data){
		var bankNames = [];
		var bankBranches = [];
		var cupNumbers = [];
		var bankAccountNames = [];
		var bankAccounts = [];
		
		for(var i=0;i<data.incomePerson.length;i++){
			bankNames[i] = data.incomePerson[i].bankName;
			bankBranches[i] = data.incomePerson[i].bankBranch;
			cupNumbers[i] = data.incomePerson[i].cupNumber;
			bankAccountNames[i] = data.incomePerson[i].bankAccountName;
			bankAccounts[i] = data.incomePerson[i].bankAccount;
		}
		
		bindBlockData($("#form_person_person_bankName"),bankNames);
		bindBlockData($("#form_person_person_bankBranch"),bankBranches);
		bindBlockData($("#form_person_person_cupNumber"),cupNumbers);
		bindBlockData($("#form_person_person_bankAccountName"),bankAccountNames);
		bindBlockData($("#form_person_person_bankAccount"),bankAccounts);
	}
	
	
	
	
});