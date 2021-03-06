package org.ack.admin.web.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ack.auth.authenticate.annotation.AckPermission;
import org.ack.base.service.AckMapperService;
import org.ack.common.Content;
import org.ack.common.datatable.DataTableTemplate;
import org.ack.persist.page.Page;
import org.ack.pojo.Menu;
import org.ack.pojo.Role;
import org.ack.pojo.User;
import org.ack.service.UserService;
import org.ack.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController extends AckPageController<User, Long> {
	
	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	private UserService userServiceImpl;

	@Override
	public AckMapperService<User, Long> getService() {
		return userServiceImpl;
	}
	
	@RequestMapping("/current")
	@ResponseBody
	public User getCurrentUser(HttpServletRequest request,
			HttpServletResponse response, Model model){
		HttpSession session = request.getSession(false);
		User user = (User)session.getAttribute(Content.USER);
		return user;
	}

	/**
	 * dashboard页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/dashboard")
	@AckPermission(value="user:dashboard")
	public String dashboard(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "user/dashboard";
	}

	/**
	 * 列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/list/ui")
	@AckPermission(value="user:list")
	public String listUI(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "user/userList";
	}
	
	/**
	 * 修改页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/edit/ui")
	@AckPermission(value="user:list")
	public String editUI(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "user/user-edit";
	}
	
	/**
	 * 修改页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/menus")
	@ResponseBody
	public List<Menu> getMenus(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = (User)request.getSession().getAttribute(Content.USER);
		List<Menu> menus = userServiceImpl.findMenuByUser(user);
		return menus;
	}
	
	/**
	 * 用户授予角色页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/role2user/ui")
	@AckPermission(value="user:role")
	public String role2UserUI(HttpServletRequest request, User user,
			HttpServletResponse response, Model model) {
		return "user/userRole";
	}
	/**
	 * 用户授予角色页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/role2user")
	@AckPermission(value="user:role")
	@ResponseBody
	public String role2User(HttpServletRequest request, User user, Integer[] rid,
			HttpServletResponse response, Model model) {
		if(logger.isDebugEnabled()){
			logger.debug("给用户 : [" + user + "] 设置角色");
		}
		String ids = StringUtils.array2String(rid);
		user.setRoleIds(ids);
		int r = userServiceImpl.update(user);
		return r + "";
	}
	
	/**
	 * 用户授予角色页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/roles/list")
	@ResponseBody
	public Set<Role> findRolesByUser(HttpServletRequest request, User user,
			HttpServletResponse response, Model model) {
		if(logger.isDebugEnabled()){
			logger.debug("user id is {}", user.getId());
		}
		Set<Role> roles = userServiceImpl.findRolesByUser(user);
		return roles;
	}

	@RequestMapping(value = "/page")
	@AckPermission(value="user:list")
	@ResponseBody
	@Override
	public Page<User> findPage(HttpServletRequest request,
			HttpServletResponse response, Model model, User t, int currentPage,
			int count, String orderColumn, String orderType) {
		return super.findPage(request, response, model, t, currentPage, count,
				orderColumn, orderType);
	}
	@RequestMapping(value = "/table")
	@AckPermission(value="user:list")
	@ResponseBody
	@Override
	public DataTableTemplate<User> dataTable(HttpServletRequest request,
			HttpServletResponse response, Model model, User t, int start,
			int length, int draw, String orderColumn, String orderType) {
		return super.dataTable(request, response, model, t, start, length, draw,
				orderColumn, orderType);
	}
    
	@RequestMapping(value = "/id/{id}")
	@AckPermission(value="user:find")
	@ResponseBody
	@Override
	public User findById(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable Long id) {
		return super.findById(request, response, model, id);
	}

	@RequestMapping(value = "/insert")
	@AckPermission(value="user:add")
	@ResponseBody
	@Override
	public Integer insert(HttpServletRequest request,
			HttpServletResponse response, Model model, User t) {
		return super.insert(request, response, model, t);
	}
	@RequestMapping(value = "/del/{id}")
	@AckPermission(value="user:delete")
	@ResponseBody
	@Override
	public Integer deleteById(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable Long id) {
		return super.deleteById(request, response, model, id);
	}

	@RequestMapping(value = "/edit")
	@AckPermission(value="user:update")
	@ResponseBody
	@Override
	public Integer edit(HttpServletRequest request,
			HttpServletResponse response, Model model, User t) {
		return super.edit(request, response, model, t);
	}
	
    
}
