package com.anhui.delivery.dao;

import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.anhui.delivery.utils.DBObjectToBean;
import com.anhui.delivery.vo.DeliveryTask;
import com.anhui.delivery.vo.Order;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class ReceiverDao {
	
	public List<Order> getAllOrder(String receiverName) throws UnknownHostException{
		Mongo mongo = new Mongo();
  		DB myMongo = mongo.getDB("UAV_Delivery");
  		DBCollection orderCollection = myMongo.getCollection("order");
  		BasicDBObject queryObject = new BasicDBObject("receiverName",receiverName);
  		
  	    DBCursor cursor=orderCollection.find(queryObject);
          
        List<Order> orderList = new ArrayList<Order>();
          while(cursor.hasNext()){
               Order order=new Order();
               order.parse(cursor.next());
               orderList.add(order);
          }
		return orderList;
	}
	
	public DeliveryTask getDeliveryTask(String order_id) throws UnknownHostException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Mongo mongo = new Mongo();
  		DB myMongo = mongo.getDB("UAV_Delivery");
  		DBCollection orderCollection = myMongo.getCollection("deliveryTask");
  		BasicDBObject queryObject = new BasicDBObject("order_id",order_id);
  		DBObject dbObject=orderCollection.findOne(queryObject);
  		DeliveryTask deliveryTask=new DeliveryTask();
  		DeliveryTask deliveryTask1= DBObjectToBean.dbObject2Bean(dbObject,deliveryTask);
		return deliveryTask1;
	}
	


}
