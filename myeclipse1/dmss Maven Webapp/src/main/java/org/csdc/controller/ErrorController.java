package org.csdc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 错误处理控制器
 * @author jintf
 * @date 2014-6-15
 */
@Controller
@RequestMapping ("/error")
public class ErrorController {
	
	/**
	 * 预览错误
	 * @return
	 */
	@RequestMapping("/preview")
	public String preview(){
		return "error/preview";
	}
	
	/**
	 * 未知错误
	 * @return
	 */
	@RequestMapping("/unknown")
	public String unknown(){
		return "error/unknown";
	}
}
