package org.ack.admin.web.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ack.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 首页
 * 
 * @author ack
 *
 */
@Controller
public class HomeController {

	private static Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private UserService userServiceImpl;
	public final ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(value = "/")
	public String findById(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		if (log.isDebugEnabled()) {
			log.debug("进入首页...保存用户");
		}
		return "index";
	}
}
