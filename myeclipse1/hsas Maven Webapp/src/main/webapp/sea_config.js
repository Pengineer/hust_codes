/**
 * seajs的配置信息
 * base: 默认为sea.js之前的路径（不包含版本信息），此处不需要配置
 * 		WebRoot/static/(seajs/javascript/tool/css/image)
 * alias: 可精简过长的链接
 * preload: 可能用到插件的配置都写在这里，在具体模块中不一定都使用，只需要复制需要的配置即可
 */
seajs.config({
	alias: {
		'jquery': 'lib/jquery-2.1.3.js',
		'template': 'lib/template.js',
		'loading':'lib/jquery.isloading.js',
		'dialog': 'lib/artDialog-6.0.4-3/js/dialog-plus.js',
		'bootstrap': 'lib/bootstrap-3.3.5/js/bootstrap.js',
		'list': 'tools/list/list.js',
		'common': 'js/common.js',
		'form': 'lib/jquery.form.js',
		'cookie': 'lib/jquery.cookie.js',
		'uploadify':'lib/uploadify/js/jquery.uploadify.js',
		'uploadify-ext':'lib/uploadify/js/jquery.uploadify-ext.js',
		'uploadify-photo':'lib/uploadify/js/jquery.uploadify-photo.js',
		'uploadify.min':'lib/uploadify/js/jquery.uploadify,min.js',
		'validation':'lib/jQuery-Validation-Engine-2.6.2/js/jquery.validationEngine.js',
		'validationInit':'lib/jQuery-Validation-Engine-2.6.2/js/jquery.validationEngine-zh_CN.js',
		'datePicker': 'lib/bootstrap-datepicker-1.4.0-dist/js/bootstrap-datepicker.js',
		'view':'js/view.js',
		"dhtmlxtree":"lib/dhtmlxTree_v44_std/dhtmlxtree.js"
	},
	preload: [
		'jquery',
		'bootstrap',
		'template',
		'dialog',
		'common'
	],
	debug: true,
	map: [
		[ /^(.*\.(?:css|js))(.*)$/i, '$1?v0.0.1' ]
	],
	charset: 'utf-8',
	timeout: 20000
})

//将 jQuery 暴露到全局
seajs.modify('jquery', function(require, exports) {
	window.jQuery = window.$ = exports
})
//将 jQuery isLoading 插件自动包装成 CMD 接口
seajs.modify('loading', function(require, exports) {
	module.exports = $.isLoading
})

//将 jQuery cookie 插件自动包装成 CMD 接口
seajs.modify('cookie', function(require, exports, module) {
	module.exports = $.cookie
})

//将 jQuery form 插件自动包装成 CMD 接口
seajs.modify('form', function(require, exports, module) {
	module.exports = $.form
})
