/**
 * seajs的配置信息
 * base: 默认为sea.js之前的路径（不包含版本信息），此处不需要配置
 * 		WebRoot/static/(seajs/javascript/tool/css/image)
 * alias: 可精简过长的链接
 * preload: 可能用到插件的配置都写在这里，在具体模块中不一定都使用，只需要复制需要的配置即可
 */
seajs.config({
	alias: {
		'jquery': 'javascript/lib/jquery/jquery',
		'template': 'javascript/lib/template',
		'common': 'javascript/common',
		
		'validate': 'javascript/lib/jquery/jquery.validate.js',
		'cookie': 'javascript/lib/jquery/jquery.cookie.js',
		'form': 'javascript/lib/jquery/jquery.form.js',
		'datepick': 'tool/jquery.datepick/jquery.datepick.js',
		'datepick-zh-CN': 'tool/jquery.datepick/jquery.datepick-zh-CN.js',// 依赖于datepick，需要手动包装为cmd模块
		'uploadify': 'tool/uploadify/js/jquery.uploadify.js',
		'uploadify-ext': 'tool/uploadify/js/jquery.uploadify-ext.js',
		'jquery-ui': 'tool/jquery.ui/js/jquery-ui-1.8.5.custom.min',
		'datepick-init': 'javascript/lib/jquery/jquery.datepick-init.js',
		'pop': 'tool/poplayer/js/pop',
		'pop-self': 'tool/poplayer/js/pop-self',
		'pop-init': 'javascript/pop/pop-init'
	},
	preload: [
		'jquery',
		'template',
		'common'
	],
	debug: true,
	map: [
		[ /^(.*\.(?:css|js))(.*)$/i, '$1?ver=' + (systemVersion) ]
	],
	charset: 'utf-8',
	timeout: 20000
})

//将 jQuery 暴露到全局
seajs.modify('jquery', function(require, exports) {
	window.jQuery = window.$ = exports
})

// 将 jQuery validate 插件自动包装成 CMD 接口
seajs.modify('validate', function(require, exports, module) {
	module.exports = $.validate
})

//将 jQuery cookie 插件自动包装成 CMD 接口
seajs.modify('cookie', function(require, exports, module) {
	module.exports = $.cookie
})

//将 jQuery form 插件自动包装成 CMD 接口
seajs.modify('form', function(require, exports, module) {
	module.exports = $.form
})

//将 jQuery datepick 插件自动包装成 CMD 接口
seajs.modify('datepick', function(require, exports, module) {
	module.exports = $.datepick
})

//将 jQuery uploadify 插件自动包装成 CMD 接口
seajs.modify('uploadify', function(require, exports, module) {
	module.exports = $.uploadify
})