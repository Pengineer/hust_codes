<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
	<head>
	<meta charset="utf-8">
	<title>jQuery UI Datepicker - Select a Date Range</title>
	<link rel="stylesheet" href="javascript/selfspace/memo/jquery.ui.all.css">
	<script type="text/javascript" src="javascript/selfspace/memo/jquery-1.6.2.js"></script>
	<script type="text/javascript" src="javascript/selfspace/memo/jquery.ui.core.js"></script>
	<script type="text/javascript" src="javascript/selfspace/memo/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="javascript/selfspace/memo/jquery.ui.datepicker.js"></script>
	<link rel="stylesheet" href="javascript/selfspace/memo/demos.css">
	<script type="text/javascript">
	$(function() {
		var dates = $( "#from, #to" ).datepicker({
			defaultDate: "+1w",
			changeMonth: true,
			numberOfMonths: 3,
			onSelect: function( selectedDate ) {
				var option = this.id == "from" ? "minDate" : "maxDate",
					instance = $( this ).data( "datepicker" ),
					date = $.datepicker.parseDate(
						instance.settings.dateFormat ||
						$.datepicker._defaults.dateFormat,
						selectedDate, instance.settings );
				dates.not( this ).datepicker( "option", option, date );
			}
		});
	});
	</script>
</head>
<body>

<div class="demo">

<label for="from">From</label>
<input type="text" id="from" name="from"/>
<label for="to">to</label>
<input type="text" id="to" name="to"/>

</div><!-- End demo -->



<div class="demo-description">
<p>Select the date range to search for.</p>
</div><!-- End demo-description -->

</body>


</html>