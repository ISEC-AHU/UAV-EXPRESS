package com.anhui.delivery.dao;

import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.anhui.delivery.utils.DBObjectToBean;
import com.anhui.delivery.vo.DeliveryTask;
import com.anhui.delivery.vo.Order;
import com.anhui.delivery.vo.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class UAVDao {
	
	public List<DeliveryTask> getDeliveryTasks(String uav_id) throws UnknownHostException{
   	 
     	Mongo mongo = new Mongo();
  		DB myMongo = mongo.getDB("UAV_Delivery");
  		DBCollection deliveryTaskCollection = myMongo.getCollection("deliveryTask");
  		BasicDBObject queryObject1 = new BasicDBObject("uav_id",uav_id);
  		BasicDBObject queryObject2 = new BasicDBObject("delviery_process","");
  	   BasicDBObject queryObject = new BasicDBObject("$and",Arrays.asList(queryObject1,queryObject2));
  	    DBCursor cursor=deliveryTaskCollection.find(queryObject);
          
  	  List<DeliveryTask>   deliveryTasks=new ArrayList<DeliveryTask>();
          while(cursor.hasNext()){
        	  DeliveryTask deliveryTask = new DeliveryTask();
        	  deliveryTask.parse(cursor.next());
               deliveryTasks.add(deliveryTask);
          }
  		return deliveryTasks;
 		
 	}
	
	public Order findWaitingOrders(String order_id) throws UnknownHostException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		
		Mongo mongo = new Mongo();
  		DB myMongo = mongo.getDB("UAV_Delivery");
  		DBCollection orderCollection = myMongo.getCollection("order");
  		BasicDBObject queryObject = new BasicDBObject("order_id",order_id);
  		
  		DBObject dbObject=orderCollection.findOne(queryObject);
  		Order order=new Order();
  		Order order1= DBObjectToBean.dbObject2Bean(dbObject,order);
		return order1;
	
	}
	
	
	 
	 public void startDelivery(String order_id,String time,String location1) throws UnknownHostException{
		    Mongo mongo = new Mongo();
	  		DB myMongo = mongo.getDB("UAV_Delivery");
	  		DBCollection deliveryTaskCollection = myMongo.getCollection("deliveryTask");
	  		
	  		BasicDBObject baseDBO = new BasicDBObject();
			baseDBO.put("order_id",order_id);
			
			String delviery_process=time+"|"+location1+"|"+"开始配送";
			
			DBObject updatedValue=new BasicDBObject();
			updatedValue.put("delviery_process", delviery_process);
			DBObject newDBO=new BasicDBObject("$set",updatedValue);
			deliveryTaskCollection.update(baseDBO, newDBO);
		}
	 
	 public void updateOrderState(String order_id,String deliveryState) throws UnknownHostException {
		// System.out.println();
		// System.out.println("更新配送状态.....");
		    Mongo mongo = new Mongo();
	  		DB myMongo = mongo.getDB("UAV_Delivery");
	  		DBCollection deliveryTaskCollection = myMongo.getCollection("order");
	  		BasicDBObject baseDBO = new BasicDBObject();
			baseDBO.put("order_id",order_id);
			DBObject updatedValue=new BasicDBObject();
			updatedValue.put("state", deliveryState);
			DBObject newDBO=new BasicDBObject("$set",updatedValue);
			deliveryTaskCollection.update(baseDBO, newDBO);
	  		
		}
	 
	 public void saveDeliveryProcess(String order_id,String time,String currentLoc,String deliveryState) throws UnknownHostException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		    Mongo mongo = new Mongo();
	  		DB myMongo = mongo.getDB("UAV_Delivery");
	  		DBCollection deliveryTaskCollection = myMongo.getCollection("deliveryTask");
	  		
	  		String oldDeliveryProcess=getTask(order_id).getDelviery_process();
	  		String newDeliveryProcess=oldDeliveryProcess+"."+time+"|"+currentLoc+"|"+deliveryState;
	  		//System.out.println("newDeliveryProcess:"+newDeliveryProcess);
	  		
	  		DBObject updatedValue=new BasicDBObject();
			updatedValue.put("delviery_process", newDeliveryProcess);
			DBObject newDBO=new BasicDBObject("$set",updatedValue);
	  		
	  		BasicDBObject baseDBO = new BasicDBObject();
			baseDBO.put("order_id",order_id);
			
			deliveryTaskCollection.update(baseDBO, newDBO);
			
		}
		
	 
	 public void delete() throws UnknownHostException{
			Mongo mongo = new Mongo();
			DB myMongo = mongo.getDB("UAV_Delivery");
			DBCollection deliveryTaskCollection = myMongo.getCollection("deliveryTask");
			
			BasicDBObject baseDBO =new BasicDBObject();
			baseDBO.put("order_id", "or_001");

		    //删除某一条记录
			deliveryTaskCollection.remove(baseDBO);
			//this.uavDao.save(uav);
		}
	 
	 public DeliveryTask getTask(String order_id) throws UnknownHostException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		 
	     	Mongo mongo = new Mongo();
	  		DB myMongo = mongo.getDB("UAV_Delivery");
	  		DBCollection deliveryTaskCollection = myMongo.getCollection("deliveryTask");
	  		BasicDBObject queryObject = new BasicDBObject("order_id",order_id);
	  		
	  		DBObject dbObject=deliveryTaskCollection.findOne(queryObject);
	  		DeliveryTask task=new DeliveryTask();
	  		DeliveryTask task1= DBObjectToBean.dbObject2Bean(dbObject,task);
			return task1;
		}
	 
	 public User getReceiverByOrder_id(String order_id) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, UnknownHostException{
		    Mongo mongo = new Mongo();
	  		DB myMongo = mongo.getDB("UAV_Delivery");
	  		DBCollection orderCollection = myMongo.getCollection("order");
	  		BasicDBObject queryObject = new BasicDBObject("order_id",order_id);
	  		
	  		DBObject dbObject=orderCollection.findOne(queryObject);
	  		Order order=new Order();
	  		Order order1= DBObjectToBean.dbObject2Bean(dbObject,order);
	  		User user=getReceiverByName(order1.getReceiverName());
	  		return user;
		}
	 
	 public User getReceiverByName(String receiverName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, UnknownHostException{
		    Mongo mongo = new Mongo();
	  		DB myMongo = mongo.getDB("UAV_Delivery");
	  		DBCollection userCollection = myMongo.getCollection("user");
	  		BasicDBObject queryObject = new BasicDBObject("username",receiverName);
	  		
	  		DBObject dbObject=userCollection.findOne(queryObject);
	  		User user=new User();
	  		User user1= DBObjectToBean.dbObject2Bean(dbObject,user);
	  		return user1;
		}

}
