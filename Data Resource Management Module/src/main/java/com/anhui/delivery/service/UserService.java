package com.anhui.delivery.service;


import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.util.List;

import com.anhui.delivery.dao.UserDao;
import com.anhui.delivery.vo.User;
 
public class UserService {
 
	private UserDao userDao = new UserDao();
	
	/**
	 * 保存
	 * @param user
	 * @throws UnknownHostException
	 */
	public void save(User user) throws UnknownHostException{
		this.userDao.save(user);
	}
	
	
	public String checkUser(User user) throws UnknownHostException{
		return this.userDao.checkUser(user);
	}
	
	public void deleteUser(String userName) throws UnknownHostException{
		 userDao.deleteUser(userName);
	}
	
	/**
	 * 查询所有
	 * @return
	 * @throws UnknownHostException 
	 */
	public List<User> findAll() throws UnknownHostException{
		return this.userDao.findAll();
	}
	
	public String getEthAccount(String userName) throws UnknownHostException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return this.userDao.getEthAccount(userName);
	}
	
	/**
	 * 删除操作
	 * @param id
	 * @throws UnknownHostException
	 
	public void remove(int id) throws UnknownHostException{
		this.userDao.remove(id);
	}
	 */
	
}
