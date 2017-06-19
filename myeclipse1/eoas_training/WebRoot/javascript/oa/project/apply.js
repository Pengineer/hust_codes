$(document).ready(function(){
	$("#startTime").datepicker({
		startDate: '1920-1-1',
		startView: 'month',
		minView: 'month',
		maxView: 'decade',
		autoclose: true,
		todayHighlight: true,
		format: 'yyyy-mm-dd'
	});
	
	$("#endTime").datepicker({
		startDate: '1920-1-1',
		startView: 'month',
		minView: 'month',
		maxView: 'decade',
		autoclose: true,
		todayHighlight: true,
		format: 'yyyy-mm-dd'
	});
})
