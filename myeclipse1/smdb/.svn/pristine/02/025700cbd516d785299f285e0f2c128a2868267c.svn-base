function updateDW() {
    if(confirm('您确定要更新数据仓库吗？')) {
    	$("#updateDW").attr("disabled", true);
		$("#updating").css("display", "inline-block");
		$.ajax({
			url: "statistic/updateDW.action",
			type: "post",
			dataType: "json",
			success: function(result){
				if (result.errorInfo == null || result.errorInfo == "") {
					$("#updating").css("display", "none");
					$("#updateDW").attr("disabled", false);
					alert('数据仓库更新成功！');
					location.reload(true);
				}
				else {
					alert(result.errorInfo);
					location.reload(true);
				}
			}
		});
	}
}

$(function(){
	$("#updateDW").click(function(){
		updateDW();
	});
});
/**
 * 显示统计图
 * id:页面标签id
 * config：统计图配置
 * mdxid：常规统计分析id
 */
function showHighChart(id, config, mdxid) {
    var configs = config.split(';');//多个图表配置用分号隔开
     for(var i = 0; i < configs.length; i++) {
            var tmp = configs[i].trim().split(':');
            var type = tmp[0].trim();//统计图类型
            var height = 300;//统计图高度
            var chartid = id + i;
            var myElement = document.createElement('div');//插入统计图的div
            myElement.id = chartid;
            var e = document.createElement('div');
            var xchart = document.getElementById(id);
            xchart.appendChild(myElement);
            xchart.appendChild(e);
            var url = "statistic/showChart.action?MDXQueryId=" + mdxid + "%26chartType=" + type + "%26chartShowIndexes=" + tmp[1];
            $.ajax({
            	url: "statistic/showChart.action",
            	data: "MDXQueryId=" + mdxid + "&chartType=" + type + "&chartShowIndexes=" + tmp[1],
            	dateType: "json",
    			type: "post",
            	success: function(json){
            		
            		 chart = new Highcharts.Chart({                   //图表展示容器，与div的id保持一致
            	            chart: {
            	                type: json.type,
            	                renderTo: chartid,
            	                height:300
            	            },
            	            legend: {
            	            	enabled: false
            	            },
            	            title: {
            	                text: json.title || ""     //指定图表标题
            	            },
            	            subtitle: {
            	                text: json.subtitle || ""
            	            },
            	            plotOptions: {
            	                line: {
            	                    dataLabels: {
            	                        enabled: true,
            	                        format: json.isPercent == "1" ? '{y:.2f}%' : ''
            	                    }
            	                },
            	                 pie: {
            	                    allowPointSelect: true,  //启用饼图点击动画效果
            	                    cursor: 'pointer'
            	                },
            	                series: {
            	                    dashStyle: "solid"
            	                }
            	            },
            	            yAxis: {
            	                title: {
            	                    text:json.yAxis ? json.yAxis.title : ""
            	                    
            	                },
            	                min: json.yAxis ? json.yAxis.min : 0,
            	                max: json.yAxis ? json.yAxis.max : 0,
            	                tickInterval: json.yAxis ? json.yAxis.step : 1,
            	                labels: {
            	                	formatter: function() {
            	                		if(json.isPercent == "1") return this.value + '%';
            	                		else return this.value;
				                	}
            	             	}
            	            },
            	            xAxis: {
            	                title: {
            	                    text: json.xAxis ? json.xAxis.title : ""
            	                    	
            	                },
            	                labels: {
            	                    rotation: -45 //x轴文字逆时针旋转45°
            	                },
            	                categories: json.xAxis ? json.xAxis.categories : ""
            	            },
            	           tooltip: {
            	                formatter: function () {
            	                    return this.point.options.tip; // 设置鼠标悬浮显示的内容
            	             }
            	            },
            	            series:[{
            	                data:json.data //设置数据
            	            }]
            	               
            	        });
            	}
            	
            });
    }
}

$(document).ready(function() {
	var homeStat = JSON.parse($("#homeStat").val());
	if(homeStat == "") {
		return ;
	} else {
		for(var i = 0; i < homeStat.length; i++) {
			$("#picture").attr("id", "picture" + i);
			var config = homeStat[i].chartConfig;
			var mdxid = homeStat[i].mdxid;
			showHighChart("picture" + i, config, mdxid);
		}
	}
	
	/**
	 * 滚动图片处理
	 */
	$("#slide").each(function() {
        //xSlider对象
        var xslider = {
            settings:   {
                'children':         null,
                'width':            null,
                'height':           null,
                'effect':           'slide',
                'speed':            'normal',
                'order':            'sequence',
                'timeout':          5000,
                'easing':           null,
                'navigation':       true,
                'prevNext':         true,
                'wrapperClass':     'xslider',
                'navigationClass':  'xslider-nav',
                'autoPlay':         true,
                'pauseOnHover':     true,
                'onLoaded':         function(current, last, currentItem, lastItem, elements) {
				        	        	$(".caption", currentItem).fadeIn(200);
				            	    },
                'onBefore':         function(current, last, currentItem, lastItem, elements) {
					        	        $(".caption", lastItem).hide();
					        	        $(".caption", currentItem).hide();
					        	    },
                'onComplete':       function(current, last, currentItem, lastItem, elements) {
				        	        	$(".caption", currentItem).fadeIn(200);
				            	    }
            },
            container:  null,
            timeout:    null,
            current:    0,
            last:       0,
            state:      null,
            //返回滚动图片数组
            items: function() {
                var self = this;
                if (self.settings.children) return $(self.container).children();
                else return $(self.container).children(self.settings.children);
            },
            //初始化
            init: function(container) {
                var self = this;
                //初始化容器
                self.container = container;
                var elements = self.items();
                //没有找到图片
                if (elements.length == 0) return;
                $(self.container)
                .wrap($('<div />').addClass(self.settings.wrapperClass))
                .wrap($('<div />').addClass('xslider-container'));
                //根据设置顺序设定当前图片和最后的图片
                if (self.settings.order == 'random') {
                    self.last = Math.floor(Math.random() * elements.length);
                    do {
                        self.current = Math.floor(Math.random() * elements.length);
                    } while (self.last == self.current );
                } else if (self.settings.order == 'random-start') {
                    self.current = Math.floor(Math.random() * elements.length);
                    self.settings.order = 'sequence';
                } else if (self.settings.order != 'sequence') {
                    alert('xslider:order must either be \'sequence\', \'random\' or \'random-start\'');
                }
                // 调用设置程序
                self.setup(false);
                //设置滑动容器
                $(self.container).css({
                    position: 'relative',
                    width: $(elements[0]).width(),
                    height: $(elements[0]).height(),
                    overflow: 'hidden'
                });
                if (self.settings.width != null) $(self.container).css('width', self.settings.width);
                //构建导航
                self.navigation();
                //显示第一项
                $(elements[self.current]).fadeIn('normal', function() {
                    //调用回调函数onLoaded
                    self.onLoaded(self.current, self.last, $(elements[self.current]), $(elements[self.last]), elements);
                });
                //设置状态
                $(self.container).data('xslider:playback', 'standby');
                //如果设置为自动播放就播放动画
                if (self.settings.autoPlay) self.play();
            },
            //根据适当属性进行设置
            setup: function(refresh) {
                var self = this;
                $.each(self.items(), function(i, child) {
                    if (refresh) {
                      if (i != self.current && i != self.last) $(child).css({'position': 'absolute', 'top': 0, 'left': 0, 'display': 'none'});
                    } else {
                      if (i != self.current) $(child).css({'position': 'absolute', 'top': 0, 'left': 0, 'display': 'none'});
                    }
                    if (self.settings.effect == 'fade') $(child).css('z-index', self.items().length - i);
                    else $(child).css('z-index', 0);
                    if (self.settings.autoPlay && self.settings.pauseOnHover) {
                        $(child).unbind('mouseover.xslider').bind('mouseover.xslider', function() {
                            self.stop();
                        }).unbind('mouseout.xslider').bind('mouseout.xslider', function() {
                            self.play();
                        });
                    }
                });
            },
            //构建导航
            navigation: function(refresh) {
                var self = this;
                if (self.settings.navigation == null) return;
                var $wrapper = $(self.container).parents('.' + self.settings.wrapperClass);
                if (!refresh && self.settings.prevNext) {
                    var $prevnext = $('<div class="xslider-prevnext" />');
                    $('<a href="#xslider-prev" class="prev">Prev</a>').bind('click.xslider', function() { self.prev(); return false; }).appendTo($prevnext);
                    $('<a href="#xslider-next" class="next">Next</a>').bind('click.xslider', function() { self.next(); return false; }).appendTo($prevnext);
                    $prevnext.appendTo($wrapper);
                }
                if (typeof(self.settings.navigation) == 'object' && self.settings.navigation.jquery) { //导航提供的是一个jquery dom对象
                    $('a', self.settings.navigation).each(function() {
                        $(this).unbind('click.xslider').bind('click.xslider', function() { self.navigate(this); return false;})
                    });
                    for (var i = 0; i < self.items().length; i++) {
                        // 失去导航
                        if ($('a[href=#xslider-' + i + ']', self.settings.navigation).length == 0) {
                          var $li = $('<li />');

                          $('<a href="#xslider-' + i + '">' + (i + 1) + '</a>')
                          .unbind('click.xslider').bind('click.xslider', function() { self.navigate(this); return false; })
                          .appendTo($li);
                          $li.appendTo($('ul', self.settings.navigation));
                        }
                    }
                } else if (self.settings.navigation === true) { //生成导航
                    var $nav = $('<nav>').addClass(self.settings.navigationClass).appendTo($wrapper);
                    var $ul = $('<ul />');
                    for (var i = 0; i < self.items().length; i++) {
                        var $li = $('<li />');

                        $('<a href="#xslider-' + i + '">' + (i + 1) + '</a>')
                        .unbind('click.xslider').bind('click.xslider', function() { self.navigate(this); return false; })
                        .appendTo($li);
                        $li.appendTo($ul);
                    }
                    $ul.appendTo($nav);
                    self.settings.navigation = $nav;
                }
                if (!refresh) self.setNavigation();
            },
            //根据链接运行到适当项
            navigate: function(el) {
                var match = $(el).attr('href').match(/#xslider-(\d+)/);
                if (match != null) {
                    //停止自动播放
                    this.stop();
                    this.to(match[1]);
                }
            },
            //添加当前类到当前导航
            setNavigation: function(index) {
                if (!index) index = this.current;
                if (typeof(this.settings.navigation) == 'object') {
                    $('a', this.settings.navigation).each(function() {
                        var match = $(this).attr('href').match(/#xslider-(\d+)/);
                        if (match != null && match[1] == index) $(this).addClass('current');
                        else $(this).removeClass('current');
                    });
                }
            },
            //播放动画
            animate: function() {
                var self = this;
                var elements = self.items();
                self.onBefore(self.current, self.last, $(elements[self.current]), $(elements[self.last]), elements);
                $(self.container).animate({'height': $(elements[self.current]).height(), 'width': $(elements[self.current]).width()}, self.settings.speed);
                if (self.settings.effect == 'slide') { // 滑动
                    var width = $(self.container).width()
                    //初始位置
                    $(elements[self.current]).css({'left': width, 'display': '', 'z-index': elements.length});
                    $(elements[self.last]).animate({'left': - width}, self.settings.speed, self.settings.easing, function() {
                      $(this).css({'left': 0, 'z-index': 0, 'display': ''});
                    });
                    //完成位置
                    $(elements[self.current]).animate({'left': 0}, self.settings.speed, self.settings.easing, function() {
                        self.onComplete(self.current, self.last, $(elements[self.current]), $(elements[self.last]), elements);
                    });
                } else if (self.settings.effect == 'roll') { // 转动
                    //初始位置
                    $(elements[self.current]).css({'top': - $(elements[self.last]).outerHeight(), 'display': '', 'z-index': elements.length});
                    $(elements[self.last]).animate({'top': $(elements[self.current]).outerHeight()}, self.settings.speed, self.settings.easing, function() {
                      $(this).css({'left': 0, 'z-index': 0, 'display': 'none'});
                    });
                    //完成位置
                    $(elements[self.current]).animate({'top': 0}, self.settings.speed, self.settings.easing, function() {
                        self.onComplete(self.current, self.last, $(elements[self.current]), $(elements[self.last]), elements);
                    });
                } else if (self.settings.effect == 'fall') { //瀑布
                    //初始位置
                    $(elements[self.last]).css({'z-index': 0});
                    $(elements[self.current]).css({'top': - $(elements[self.current]).outerHeight(), 'display': '', 'z-index': 1});
                    //隐藏过去的滑动
                    $(elements[self.last]).fadeOut(self.settings.speed);
                    //完成位置
                    $(elements[self.current]).animate({'top': 0}, self.settings.speed, self.settings.easing, function() {
                        self.onComplete(self.current, self.last, $(elements[self.current]), $(elements[self.last]), elements);
                    });
                } else if (self.settings.effect == 'fade') { //淡入淡出
                    $(elements[self.last]).fadeOut(self.settings.speed);
                    $(elements[self.current]).fadeIn(self.settings.speed, function() {
                        removeFilter($(this)[0]);
                        self.onComplete(self.current, self.last, $(elements[self.current]), $(elements[self.last]), elements);
                    });
                } else
                    alert('xslider:effect must either be \'slide\', \'roll\', \'fall\' or \'fade\'');
                self.setNavigation();
            },
            //展示某一项
            //此项可以是滚动图片的索引或者jquery对象
            to: function(element) {
                var self = this;
                if (typeof(element) == 'object' && element.jquery) {
                    if (element.length == 0) return false;
                    $.each(self.items(), function(i) {
                        if ($(this)[0] == element[0]) element = i;
                    });
                }
                //当前显示项
                if (self.current == element) return;
                self.last = self.current;
                self.current = parseInt(element);
                self.animate();
            },
            //展示下一项
            next: function() {
                var self = this;
                var next = self.current;
                if (self.settings.order == 'random') {
                    while (next == self.current) next = Math.floor(Math.random() * self.items().length);
                } else {
                    next++;
                    if (next > this.items().length - 1) next = 0;
                }
                this.to(next);
            },
            //展示前一项
            prev: function() {
                var self = this;
                var prev = self.current;
                if (self.settings.order == 'random') {
                    while (prev == self.current) prev = Math.floor(Math.random() * self.items().length);
                } else {
                    prev--;
                    if (prev < 0) prev = self.items().length - 1;
                }
                this.to(prev);
            },
            //开始回放
            play: function() {
                var self = this;
                //存储回放状态
                $(self.container).data('xslider:playback', 'play');
                //为下一项设置定时器
                timeout = setInterval(function() {
                    //展示下一项
                    self.next();
                }, self.settings.timeout);
                $(self.container).data('xslider:timeout', timeout);
            },
            //停止播放
            stop: function() {
                var self = this;
                clearInterval($(self.container).data('xslider:timeout'));
                $(self.container).data('xslider:playback', 'stop');
            },
            //暂停播放
            pause: function() {
                var self = this;
                clearInterval($(self.container).data('xslider:timeout'));
                $(self.container).data('xslider:playback', 'pause');
                if (self.settings.autoPlay) self.play();
            },
            //获取播放状态
            status: function() {
                var self = this;
                return {
                    state: $(self.container).data('xslider:playback'),
                    current: self.current,
                    last: self.last
                };
            },
            //刷新xSlider对象
            refresh: function() {
                var self = this;
                var elements = self.items();
                //重建子元素和刷新
                self.setup(true);
                //刷新导航
                self.navigation(true);
            },
            //完全加载后触发
            onLoaded: function(current, last, currentItem, lastItem, elements) {
                if (typeof(this.settings.onLoaded) == 'function') this.settings.onLoaded(current, last, currentItem, lastItem, elements);
            },
            //动画之前触发
            onBefore: function(current, last, currentItem, lastItem, elements) {
                if (typeof(this.settings.onBefore) == 'function') this.settings.onBefore(current, last, currentItem, lastItem, elements);
            },
            //动画完成后触发
            onComplete: function(current, last, currentItem, lastItem, elements) {
                if (typeof(this.settings.onComplete) == 'function') this.settings.onComplete(current, last, currentItem, lastItem, elements);
            }
        };
        //初始化xSlider对象
        xslider.init(this);
        //存储xSlider实例
        $(this).data('xslider:instance', xslider);
	});
});