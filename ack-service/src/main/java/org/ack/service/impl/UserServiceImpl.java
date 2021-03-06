package org.ack.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ack.base.service.impl.AckMapperServiceImpl;
import org.ack.persist.AckMapper;
import org.ack.persist.mapper.UserMapper;
import org.ack.pojo.Menu;
import org.ack.pojo.Permission;
import org.ack.pojo.Role;
import org.ack.pojo.User;
import org.ack.service.PermissionService;
import org.ack.service.RoleService;
import org.ack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户业务逻辑实现
 * 
 * @author ack
 *
 */
@Service
public class UserServiceImpl extends AckMapperServiceImpl<User, Long> implements
		UserService {

	/**
	 * 存放
	 */
	//private static Map<String, List<Role>> permissionMap= new HashMap<String, List<Role>>();
	/**
	 * 存放权限
	 */
	private static Map<String,List<Menu>> menuMap = new HashMap<String,List<Menu>>();

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RoleService roleServiceImpl;

	@Autowired
	private PermissionService permissionServiceImpl;

	@Override
	protected AckMapper<User, Long> getAckMapper() {
		return userMapper;
	}

	@Override
	public User findUserByLoginName(String loginName) {
		return userMapper.findUserByLoginName(loginName);
	}

	@Override
	public List<Permission> findAuthByUser(User user) {
		/*String loginName = user.getLoginName();
		if (null != permissionMap.get(loginName)) {
			return permissionMap.get(loginName);
		}
		List<Permission> permissionList = new ArrayList<Permission>();
		// 查询用户权限
		System.out.println(user);
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			// 根据角色查询权限
			Set<Permission> permissons = roleServiceImpl
					.findPermissoinsByRole(role);
			permissionList.addAll(permissons);
		}
		permissionMap.put(loginName, permissionList);*/
		return null;
	}
	
	private List<Menu> processMenus(Set<Menu> menuList) {
	    List<Menu> list = new ArrayList<Menu>();
	    for (Menu menu : menuList) {
	    	List<Menu> child = new ArrayList<Menu>();
	    	if (menu.getMenuType() == 0) {
	    		for(Menu m : menuList){
		    		if(m.getParentId() == -1){
		    			continue;
		    		}
		    		if(m.getParentId() == menu.getId()){
		    			child.add(m);
		    		}
		    	}
		    	menu.setChildMenus(child);
		    	list.add(menu);
	    	}
	    }
		return list;
	}

	@Override
	public List<Menu> findMenuByUser(User user) {
		String loginName = user.getLoginName();
		
		if (null != menuMap.get(loginName)) {
			return menuMap.get(loginName);
		}
		Set<Menu> menus = getMenus(user);
		// 组织菜单成树形结构
		List<Menu> menuList = processMenus(menus);
		menuMap.put(loginName, menuList);
		return menuList;
	}

	private Set<Menu> getMenus(User user) {
		Set<Menu> menus = new HashSet<Menu>();
		Set<Role> roles = findRolesByUser(user);
		
		for(Role role : roles){
			Set<Menu> ms = roleServiceImpl.findMenusByRole(role);
		    for(Menu m : ms){
		    	menus.add(m);
		    }
		}
		return menus;
	}

	@Override
	public List<Role> findAllRoles() {
		return  roleServiceImpl.findAll();
	}

	@Override
	public Set<Role> findRolesByUser(User user) {
		user = findById(user.getId());
		String ids = user.getRoleIds();
		String[] roleIds = ids.split(",");
		Set<Role> roles = roleServiceImpl.findByIds(roleIds);
		return roles;
	}

	@Override
	public int updateRoleByUser(User user, Integer[] rid) {
		return 0;
	}

	@Override
	public Set<String> getPermissionString(User user) {
		Set<Menu> menus = getMenus(user);
		Set<String> set = new HashSet<String>();
		for(Menu m : menus){
			String s = m.getPermission();
			set.add(s);
		}
		return set;
	}
}
