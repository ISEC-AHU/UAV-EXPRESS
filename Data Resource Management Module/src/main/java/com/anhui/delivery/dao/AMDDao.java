package com.anhui.delivery.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.anhui.delivery.vo.UAV;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class AMDDao {
	
     public List<UAV> findUncheckedUAV() throws UnknownHostException{
    	 
    	Mongo mongo = new Mongo();
 		DB myMongo = mongo.getDB("UAV_Delivery");
 		DBCollection uavCollection = myMongo.getCollection("uav");
 		BasicDBObject queryObject = new BasicDBObject("right","0");
 		DBCursor cursor=uavCollection.find(queryObject);
         
         List<UAV> uavList = new ArrayList<UAV>();
         while(cursor.hasNext()){
              UAV uav = new UAV();
              uav.parse(cursor.next());
             // System.out.println("==right=="+uav.getRight());
              uavList.add(uav);
         }
 		return uavList;
		
	}
     
    
     
     public void grantRight(String uid) throws UnknownHostException{
    	 
    	Mongo mongo = new Mongo();
  		DB myMongo = mongo.getDB("UAV_Delivery");
  		DBCollection uavCollection = myMongo.getCollection("uav");
  		
  		BasicDBObject baseDBO = new BasicDBObject();
		baseDBO.put("uid",uid);
		
		DBObject updatedValue=new BasicDBObject();
		updatedValue.put("right", "1");
		DBObject newDBO=new BasicDBObject("$set",updatedValue);
		
		//System.out.println();
		//System.out.print("==grantRight==");
		uavCollection.update(baseDBO, newDBO);
  		
 	}
	
     public void denyUAV(String uid) throws UnknownHostException{
    	 
     	Mongo mongo = new Mongo();
   		DB myMongo = mongo.getDB("UAV_Delivery");
   		DBCollection uavCollection = myMongo.getCollection("uav");
   		
   		BasicDBObject baseDBO = new BasicDBObject();
 		baseDBO.put("uid",uid);
 		
 		DBObject updatedValue=new BasicDBObject();
 		updatedValue.put("right", "2");
 		DBObject newDBO=new BasicDBObject("$set",updatedValue);
 		
 		//System.out.println();
 		//System.out.print("==grantRight==");
 		uavCollection.update(baseDBO, newDBO);
   		
  	}
     

}
