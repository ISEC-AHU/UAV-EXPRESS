package com.anhui.delivery.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.anhui.delivery.vo.DeliveryTask;
import com.anhui.delivery.vo.Order;
import com.anhui.delivery.vo.UAV;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class DeliveryStationDao {
	
	public void save(UAV uav) throws UnknownHostException {
		Mongo mongo = new Mongo();
		DB myMongo = mongo.getDB("UAV_Delivery");
		DBCollection uavCollection = myMongo.getCollection("uav");
 
		DBObject dbo = (DBObject) JSON.parse(uav.toJson());
		uavCollection.insert(dbo);
	}
	
	public void delete(String uid) throws UnknownHostException{
		Mongo mongo = new Mongo();
		DB myMongo = mongo.getDB("UAV_Delivery");
		DBCollection uavCollection = myMongo.getCollection("uav");
		
		BasicDBObject baseDBO =new BasicDBObject();
		baseDBO.put("uid", uid);

	    //删除某一条记录
		uavCollection.remove(baseDBO);
		//this.uavDao.save(uav);
	}
	
	public void saveOrder(Order order) throws UnknownHostException{
		Mongo mongo = new Mongo();
		DB myMongo = mongo.getDB("UAV_Delivery");
		DBCollection orderCollection = myMongo.getCollection("order");
 
		DBObject dbo = (DBObject) JSON.parse(order.toJson());
		orderCollection.insert(dbo);
	}
	
	public void orderUAVSchedule(DeliveryTask deliveryTask) throws UnknownHostException{
		Mongo mongo = new Mongo();
		DB myMongo = mongo.getDB("UAV_Delivery");
		DBCollection deliveryTaskCollection = myMongo.getCollection("deliveryTask");
 
		DBObject dbo = (DBObject) JSON.parse(deliveryTask.toJson());
		deliveryTaskCollection.insert(dbo);
	}
	
	 public List<UAV> findUncheckedUAV(String registrant) throws UnknownHostException{
    	 
	     	Mongo mongo = new Mongo();
	  		DB myMongo = mongo.getDB("UAV_Delivery");
	  		DBCollection uavCollection = myMongo.getCollection("uav");
	  		BasicDBObject queryObject1 = new BasicDBObject("right","0");
	  		BasicDBObject queryObject2 = new BasicDBObject("registrant",registrant);
	  	    BasicDBObject queryObject = new BasicDBObject("$and",Arrays.asList(queryObject1,queryObject2));
	  		
	  	    DBCursor cursor=uavCollection.find(queryObject);
	          
	          List<UAV> uavList = new ArrayList<UAV>();
	          while(cursor.hasNext()){
	               UAV uav = new UAV();
	               uav.parse(cursor.next());
	               uavList.add(uav);
	          }
	  		return uavList;
	 		
	 	}
	 
	 public List<UAV> findDeniedUAV(String registrant) throws UnknownHostException{
    	 
	     	Mongo mongo = new Mongo();
	  		DB myMongo = mongo.getDB("UAV_Delivery");
	  		DBCollection uavCollection = myMongo.getCollection("uav");
	  		BasicDBObject queryObject1 = new BasicDBObject("right","2");
            BasicDBObject queryObject2 = new BasicDBObject("registrant",registrant);		
	  	    BasicDBObject queryObject = new BasicDBObject("$and",Arrays.asList(queryObject1,queryObject2));
	  		
	  	    DBCursor cursor=uavCollection.find(queryObject);
	          
	          List<UAV> uavList = new ArrayList<UAV>();
	          while(cursor.hasNext()){
	               UAV uav = new UAV();
	               uav.parse(cursor.next());
	               uavList.add(uav);
	          }
	  		return uavList;
	 		
	 	}
	 
	 
	 public List<UAV> findCertifiedUAV(String registrant) throws UnknownHostException{
    	 
	     	Mongo mongo = new Mongo();
	  		DB myMongo = mongo.getDB("UAV_Delivery");
	  		DBCollection uavCollection = myMongo.getCollection("uav");
	  		BasicDBObject queryObject1 = new BasicDBObject("right","1");
	  		BasicDBObject queryObject2 = new BasicDBObject("registrant",registrant);		
	  	    BasicDBObject queryObject = new BasicDBObject("$and",Arrays.asList(queryObject1,queryObject2));
	  		
	  		DBCursor cursor=uavCollection.find(queryObject);
	          
	          List<UAV> uavList = new ArrayList<UAV>();
	          while(cursor.hasNext()){
	               UAV uav = new UAV();
	               uav.parse(cursor.next());
	            //   System.out.println("==right=="+uav.getRight());
	               uavList.add(uav);
	          }
	  		return uavList;
	 		
	 	}

	 public void deleteTask() throws UnknownHostException{
		    Mongo mongo = new Mongo();
			DB myMongo = mongo.getDB("UAV_Delivery");
			DBCollection uavCollection = myMongo.getCollection("deliveryTask");
			
			BasicDBObject baseDBO =new BasicDBObject();
			baseDBO.put("uav_id", "u_001");

		    //删除某一条记录
			uavCollection.remove(baseDBO);
			
		}
	 
	 public void deleteOrder(String order_id) throws UnknownHostException{
		 Mongo mongo = new Mongo();
			DB myMongo = mongo.getDB("UAV_Delivery");
			DBCollection uavCollection = myMongo.getCollection("order");
			
			BasicDBObject baseDBO =new BasicDBObject();
			baseDBO.put("order_id", "or_001");

		    //删除某一条记录
			uavCollection.remove(baseDBO);
			
			BasicDBObject baseDBO2 =new BasicDBObject();
			baseDBO2.put("order_id", "or_002");

		    //删除某一条记录
			uavCollection.remove(baseDBO2);
			//this.uavDao.save(uav);
		}
}
