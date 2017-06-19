(function($) {
	$.popMsg = function(options){
		var defaults ={
			cls:"alert-success",
			wrapperAttrs : {
				id: "pop-window-div",
			},
			background:"D9EDF7",
			direction:"bottom",
			height:60,
			width:600,
			content:"I am showing...",
			flyDelay:500,
			showDelay:500,
			hideDelay:1000,
		};
		if(typeof arguments[0] == "string"){
			if(arguments.length==1){
				options = {content:arguments[0]};
			}else{
				options = {cls:arguments[0],content:arguments[1]};
			}
			
		}
		var settings = $.extend(defaults,options);
		var popWindow = $( "<div />" )
			.attr( settings.wrapperAttrs ).appendTo('body');
		popWindow.html('<p style="text-align:center">'+settings.content+'</p>');
		
		var left = $(document).width()/2 - settings.width/2;		
		var top =  $(document).height() - settings.height-100;
		if(settings.direction == "top"){
			top = 100;
		}
		
		if(settings.cls=="alert-success"){
			popWindow.css({	
				"background-color":"#DFF0D8",
				"border-color":"#D6E9C6",
				"color":"#3C763D",
			});
		}else if(settings.cls=="alert-info"){
			popWindow.css({	
				"background-color":"#D9EDF7",
				"border-color":"#BCE8F1",
				"color":"#31708F",
			});
		}else if(settings.cls=="alert-warn"){
			popWindow.css({	
				"background-color":"#FCF8E3",
				"border-color":"#FAEBCC",
				"color":"#8A6D3B",
			});
		}else{
			popWindow.css({	
				"background-color":"#F2DEDE",
				"border-color":"#EBCCD1",
				"color":"#A94442",
			});
		}
		popWindow.css({
			display:"none",
			"border-style":"solid",
			"border-width":"1px",
			"font-weight":"normal",
			"border-radius":"4px",
			position:"absolute",
			
			height:settings.height,
			width:settings.width,
			top:top,
			left:left,
			zIndex:"99999",
		});
		
		
		popWindow.slideDown(settings.flyDelay);
		popWindow.delay(settings.showDelay).fadeOut(settings.hideDelay);
	}
 
})(jQuery);