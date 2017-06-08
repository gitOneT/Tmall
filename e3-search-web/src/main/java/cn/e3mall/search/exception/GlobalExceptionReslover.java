package cn.e3mall.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
/**
 * 
 * <p>Title: GlobalExceptionReslover</p>
 * <p>Description:全局异常处理 </p>
 * @version 1.0
 */
public class GlobalExceptionReslover implements HandlerExceptionResolver {
	Logger logger = LoggerFactory.getLogger(GlobalExceptionReslover.class);
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception e) {
		//写日志
		logger.error("程序出现异常", e);
		//发邮件,发短息
		//展示错误页面
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("message", "亲 ,系统有点懒了...需要鞭策..");
		modelAndView.setViewName("error/exception");
		return modelAndView;
	}

}
