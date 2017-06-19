var start= new Number(0);
var text="";
var pageSize=new Number(10);   //每页记录数
var showPages = new Number(10); //分页条中的可见页码数
var data;
function showResult(o){
	$('#s_topinfo').html("");
	$('#s_result').html('');
	$('#s_foot').html('');
	data = o;
	createTopinfo(data);
	createPageBar(data);
	$.each(data.response.docs,function(id,obj){
	    $("#s_result").append(createItem(obj));
	});
	$('#scrollUp').click();
	
}

function createTopinfo(data){
    var costTime = Number(data.responseHeader.QTime/1000);
    var topHtml = '<span>获得约&nbsp;'+data.response.numFound+'&nbsp;条结果，以下是第&nbsp;'+ (Math.floor(data.response.start/pageSize)+1)+'&nbsp; 页 （用时 &nbsp;'+costTime.toFixed(2)+'&nbsp;秒）</span>';
    $("#s_topinfo").append(topHtml);    
}

function createItem(obj){
	var myContent = data.highlighting[obj.id].content !=undefined ? data.highlighting[obj.id].content :obj.content;
	var myTitle = data.highlighting[obj.id].title != undefined ? data.highlighting[obj.id].title:obj.title;
    var html='<li class="g">';
    html += '<div class="s_item">';
    html += '<img src="resources/images/file_extension/file_extension_'+obj.content_type+'.png">';
    html += '<h3 class="s_title"><a target="_blank"                    href="http://lucene.apache.org/solr/">'+myTitle+'</a></h3>';
    html += '<div class="s_content"><span class="st">'+myContent+'<b>...</b></span></div>';
    html += '<div><span class="s_ds">评分:'+obj.rating.toFixed(2)+'</span>&nbsp;&nbsp<span class="s_ds">文件大小:'+getFileSize(obj.file_size)+'</span>&nbsp;&nbsp<span class="s_ds">tags:'+obj.tags+'</span></div>';
    html += '<div><span class="s_ds">分类:'+obj.category+'</span><span class="s_ds">作者:'+obj.author+'/   &nbsp;&nbsp'+obj.last_modified_date.replace(/[a-z]/ig,' ')+'</span><a><span class="s_tip_icon"></span></a><a class="s_dd" target="blank" href="fm/document/download/'+obj.id+'">点此下载</a>&nbsp;&nbsp<a class="s_dd" target="blank" href="fm/document/preview/'+obj.id+'">点此预览</a></div>';
    html += '</div></li>';
    return html;
}

function createPageBar(data){
    var curPage = data.response.start/pageSize+1; //当前显示的页
    var numFound = data.response.numFound;
    var totalPages = Math.ceil(numFound/pageSize);
    var startPage = (curPage-parseInt(showPages/2)>0)?(curPage-parseInt(showPages/2)):1;
    var endPage = (curPage+showPages -parseInt(showPages/2)-1>totalPages)?totalPages : (curPage+showPages -parseInt(showPages/2)-1);
    if(endPage-startPage+1 !=showPages){ //说明被截掉了
					if(curPage-parseInt(showPages/2)<1){ //前面不够
						endPage = (startPage +showPages-1 > totalPages)?totalPages:(startPage +showPages-1 );
					}
					if(curPage+showPages -parseInt(showPages/2)-1 >totalPages){ //后面不够
                        startPage = (endPage -showPages+1 <1)?1:(endPage -showPages+1 );
					}
				}
    //显示页码
    var html='<table class="page_nav"><tr>';
    if(curPage != startPage){
        html += '<td><a class="f1" href="javascript:void(0)"><span class="e_pre e_indecator">上一页</span></a></td>';       
    }
    
    for(var i=startPage;i<=endPage;i++){
        if(i == curPage){
            html += '<td><a class="f1" href="javascript:void(0)"><span class="e_num"><strong>'+ i +'</strong></span></a></td>';
        }else{
            html += '<td><a class="f1" href="javascript:void(0)"><span class="e_num">' + i + '</span></a></td>';
        }
    }
    if(curPage != endPage){
        html += '<td><a class="f1" href="javascript:void(0)"><span class="e_next e_indecator">下一页</span></a></td>';    
    }
    $("#s_foot").append(html);
    
}

/**
 * 获取文件大小的字符串形式
 * @param {} v
 * @return {}
 */
function getFileSize(v){
	if(v/1024/1024/1024>=1){
		var num = new Number(v/1024/1024/1024);
		return num.toFixed(2)+"GB";
	}else if(v/1024/1024>=1){
		var num = new Number(v/1024/1024);
		return num.toFixed(2)+"MB";
	}else if(v/1024>=1){
		var num = new Number(v/1024);
		return num.toFixed(2)+"KB";
	}else{
		return v+"B";
	}
}