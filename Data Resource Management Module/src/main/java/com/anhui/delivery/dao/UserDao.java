package com.anhui.delivery.dao;

import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.anhui.delivery.utils.DBObjectToBean;
import com.anhui.delivery.vo.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;
 
public class UserDao {
	
	
	
	public String checkUser(User user) throws UnknownHostException{
		Mongo mongo = new Mongo();
		DB myMongo = mongo.getDB("UAV_Delivery");
		DBCollection userCollection = myMongo.getCollection("user");
		BasicDBObject nameObj = new BasicDBObject("username",user.getUsername());
	    BasicDBObject typeObj = new BasicDBObject("type",user.getType());
	    BasicDBObject andObj = new BasicDBObject("$and",Arrays.asList(nameObj,typeObj));
	    BasicDBObject pwdObj = new BasicDBObject("password",user.getPassword());
	    BasicDBObject andObj2 = new BasicDBObject("$and",Arrays.asList(andObj,pwdObj));
	    DBObject obj =userCollection.findOne(andObj);
	    
	    if(obj!=null) {
	    	//System.out.print(obj.toString());
	    	
	    	DBObject obj2 =userCollection.findOne(andObj2);
	    	
	    	if(obj2!=null) {
	    		return "OK";
	    	}else {
	    		return "wrong";
	    	}
	    	
	    	
	    }else {
	    	return "N"; //用户不存在
	    }
		
	}
	
	
 
	/**
	 * 保存
	 * @param user
	 * @throws UnknownHostException
	 */
	public void save(User user) throws UnknownHostException {
		Mongo mongo = new Mongo();
		DB myMongo = mongo.getDB("UAV_Delivery");
		DBCollection userCollection = myMongo.getCollection("user");
 
		DBObject dbo = (DBObject) JSON.parse(user.toJson());
		userCollection.insert(dbo);
	}
	
	/**
	 * 查询所有
	 * @return
	 * @throws UnknownHostException 
	 */
	public List<User> findAll() throws UnknownHostException{
		Mongo mongo = new Mongo();
		DB myMongo = mongo.getDB("UAV_Delivery");
		DBCollection userCollection = myMongo.getCollection("user");
		DBCursor cursor=userCollection.find();
        
        List<User> userList = new ArrayList<User>();
        while(cursor.hasNext()){
             User user = new User();
             user.parse(cursor.next());
             userList.add(user);
        }
		return userList;
	}
	
 
	public void deleteUser(String userName) throws UnknownHostException{
		Mongo mongo = new Mongo();
		DB myMongo = mongo.getDB("UAV_Delivery");
		DBCollection userCollection = myMongo.getCollection("user");
		
		BasicDBObject baseDBO =new BasicDBObject();
		baseDBO.put("username", userName);

	    //删除某一条记录
		userCollection.remove(baseDBO);
		//this.uavDao.save(uav);
	}
	
	public String getEthAccount(String userName) throws UnknownHostException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Mongo mongo = new Mongo();
		DB myMongo = mongo.getDB("UAV_Delivery");
		DBCollection userCollection = myMongo.getCollection("user");
		BasicDBObject queryObject = new BasicDBObject("username",userName);
		DBObject dbObject=userCollection.findOne(queryObject);
		User user=new User();
		User user1= DBObjectToBean.dbObject2Bean(dbObject,user);
		return user1.getEthAccount();

	}
	
	
	/**
	 * 更新
	 * @param user
	 * @throws UnknownHostException
	 
	public void update(User user) throws UnknownHostException {
		Mongo mongo = new Mongo();
		DB myMongo = mongo.getDB("UAV_Delivery");
		DBCollection userCollection = myMongo.getCollection("user");
 
		BasicDBObject baseDBO = new BasicDBObject();
		baseDBO.put("username", user.getUsername());
		
		DBObject newDBO = (DBObject) JSON.parse(user.toJson());
		
		userCollection.update(baseDBO, newDBO);
	}
	*/
	
	
	
	/**
	 * 删除操作
	 * @param id
	 * @throws UnknownHostException
	 
	public void remove(int id) throws UnknownHostException{
		Mongo mongo = new Mongo();
		DB myMongo = mongo.getDB("UAV_Delivery");
		DBCollection userCollection = myMongo.getCollection("user");
		
		
		BasicDBObject baseDBO =new BasicDBObject();
		baseDBO.put("id", id);
 
        //删除某一条记录
        userCollection.remove(baseDBO);
	}
	*/
 
}
