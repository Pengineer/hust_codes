package org.csdc.controller.system;
/**
 * 用户管理模块 （包括用户的登录、注册、退出、找回密码）
 * @author skyger  E-mail: jintianfan@gmail.com
 * @version v0.1.0 created：2013-6-1 下午3:45:18
 * 
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.noise.StraightLineNoiseProducer;

import org.csdc.controller.BaseController;
import org.csdc.dao.IBaseDao;
import org.csdc.domain.system.LoginForm;
import org.csdc.domain.system.RegisterForm;
import org.csdc.service.imp.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 用户管理控制器
 * @author jintf
 * @date 2014-6-15
 */
@Controller
public class UserController extends BaseController{
	@Autowired
	private IBaseDao baseDao;
	@Autowired
	private AccountService accountService;    
    
	/**
	 * 用户登录
	 * @param request
	 * @param loginForm 登录表单
	 * @return
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/user/login", produces="application/json")
    @ResponseBody
    public Object login(HttpServletRequest request,LoginForm loginForm ){
        Map jsonMap = new HashMap();
    	Map validateErrorMap = loginForm.validate(accountService,request);    	
        if(!validateErrorMap.isEmpty()){
        	jsonMap.put("validateErrors", validateErrorMap);
        }else{
        	jsonMap.put("result", "success");
        }
        return jsonMap;
    }
    
    /**
     * 用户注册
     * @param request
     * @param response
     * @param registerForm 用户注册表单
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/user/register", produces="application/json")
    @ResponseBody
    @Transactional
    public Object register(HttpServletRequest request,RegisterForm registerForm ){
    	Map jsonMap = new HashMap();
    	Map validateErrorMap = registerForm.validate(accountService,request);
		if(!validateErrorMap.isEmpty()) {
    		jsonMap.put("error", validateErrorMap);
    	}else {
       		try {
       			accountService.register(registerForm);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}    		
    		jsonMap.put("result", "success");   		
    	}
    	return jsonMap;
    }  
    
   
    /**
     * 生成随机验证码（暂时不用）
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("user/rand")
    public void rand(HttpServletRequest request,
                        HttpServletResponse response) throws IOException {
    	Captcha captcha = new Captcha.Builder(150, 50)
		.addText()
		.addBackground( new GradiatedBackgroundProducer())
		.addNoise(new StraightLineNoiseProducer())
		.gimp()
		.build();
		request.getSession().setAttribute(Captcha.NAME, captcha.getAnswer());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    try {
			boolean flag = ImageIO.write(captcha.getImage(), "png", out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        byte[] data = out.toByteArray();
        response.setContentType("image/png");
        OutputStream stream = response.getOutputStream();
        stream.write(data);
        stream.flush();
        stream.close();
    }
    
}


