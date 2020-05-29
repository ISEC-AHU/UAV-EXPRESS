package com.anhui.delivery.controller;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.anhui.delivery.service.UAVService;
import com.anhui.delivery.service.UavBcService;
import com.anhui.delivery.utils.IPFSUtil;
import com.anhui.delivery.vo.DeliveryTask;
import com.anhui.delivery.vo.Order;
import com.anhui.delivery.vo.User;

@Controller
@Scope(value="prototype")
@RequestMapping("uav")
public class UAVController {
	
	private UAVService uavService=new UAVService();
	private UavBcService bcService=new UavBcService();
	
	private static int delivery_stage; 
	
	private static int pathLength;
	private static int pathFlag;
	private static int i=0;
	

	
	
	@RequestMapping(value="/startDelivery")
	public String startDelivery(@RequestParam(value="order_id") String order_id, Map<String, Object> map,HttpSession session) throws Exception{
		User uav=(User)session.getAttribute("user");
		delivery_stage=1;
		DeliveryTask task=uavService.getTask(order_id);
		String route=task.getRoute();
		String[] locations=route.split("\\|");
		pathLength=locations.length;
		
		pathFlag=0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=df.format(new Date());
		String location1=locations[0];
		
		
		uavService.startDelivery(order_id,time,location1); 
		uavService.updateOrderState(order_id,"start");
		
		User receiver=uavService.getReceiverByOrder_id(order_id);
		
		
		bcService.setNegotiateResult(order_id, receiver.getEthAccount(), BigInteger.valueOf(5L), BigInteger.valueOf(10L), time);
		bcService.recordDeliveryProcess(task.getUav_id(), uav.getEthAccount(), order_id, location1, time, "start delivery");
		
		map.put("startFlag","startFlag");
		map.put("route",route);
		map.put("delivery_stage", delivery_stage);
		map.put("order_id",order_id);
		map.put("deliveryState", "start");
		map.put("time", time);
		map.put("location1", location1);
		map.put("uav_id", task.getUav_id());
		return "jsp/uav_deliveryProcess";
		
	}
	
	@RequestMapping(value="/saveDeliveryProcess")
	public String saveDeliveryProcess(@RequestParam(value="order_id") String order_id,HttpSession session,@RequestParam(value="uav_id") String uav_id, @RequestParam(value="route") String route, Map<String, Object> map,HttpServletResponse response,Writer out) throws Exception{
		User uav=(User)session.getAttribute("user");
		
		i++;
		
		//time
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=df.format(new Date());
		map.put("time", time);
		
		//location
		//speed:20m/s，distance between two nodes：100m
		int nodeNum=i*3*20/180; 
		int distance=180-i*3*20%180;
		String[] locations=route.split("\\|");
		String currentLoc;
		if(i%3==0) {
			currentLoc=locations[nodeNum];
			map.put("currentLoc", currentLoc);
		}else {
			currentLoc="It's +distance+ meters from"+ locations[nodeNum+1];
			map.put("currentLoc", currentLoc);
		}
		
		
		//status
		Random random=new Random();
		int randomInt=random.nextInt(100);//[0,99]
		//System.out.println("randomInt："+randomInt);
		if(randomInt==0||randomInt==1) { // 2%: malfunction
			
			delivery_stage=3;
			
			bcService.recordDeliveryProcess(uav_id, uav.getEthAccount(), order_id, currentLoc, time, "UAV malfunction");
			uavService.saveDeliveryProcess(order_id,time,currentLoc,"UAV malfunction");
		    uavService.updateOrderState(order_id,"UAV malfunction");
			map.put("deliveryState", "UAV malfunction");
			
		}else if(randomInt==3||randomInt==4) { //2%: package loss
			
			delivery_stage=4;
			
			bcService.recordDeliveryProcess(uav_id, uav.getEthAccount(), order_id, currentLoc, time, "package loss");
			uavService.saveDeliveryProcess(order_id,time,currentLoc,"package loss");
			uavService.updateOrderState(order_id,"package loss");
			map.put("deliveryState", "package loss");
			
		}else {  //94%: in transport
			
			if(i%3==0) { 
				
				pathFlag++;
				
			}
			
			if(pathFlag==pathLength-1) {
				
				delivery_stage=5; //wating for pickup
				map.put("deliveryState", "waiting for pickup");
				uavService.saveDeliveryProcess(order_id,time,currentLoc,"waiting for pickup");
				uavService.updateOrderState(order_id,"waiting for pickup");
				bcService.recordDeliveryProcess(uav_id, uav.getEthAccount(), order_id, currentLoc, time, "waiting for pickup");
				//The  smart contract is triggered to generate a verification code (greater than 0 and less than 10).The recipient gestures according to the verification code to verify the drone's identity.
				//The system omits the drone's verification of the recipient's identity and only prints random codes in the console
				BigInteger randomCode=bcService.generateCode(order_id); 
			//	System.out.println();
		    //	System.out.println("randomCode:"+randomCode);
			}else {
				delivery_stage=2; 
				map.put("deliveryState", "in transit");
				if(i%3==0) {
					uavService.saveDeliveryProcess(order_id,time,currentLoc,"in transit");
					uavService.updateOrderState(order_id,"in transit");
					bcService.recordDeliveryProcess(uav_id, uav.getEthAccount(), order_id, currentLoc, time, "in transit");
				}
			}
			
		}
		
		map.put("uav_id", uav_id);
		map.put("flyFlag","flyFlag");
		map.put("order_id", order_id);
		map.put("route", route);
		map.put("delivery_stage",delivery_stage);
		//System.out.println("route:"+route);
		//System.out.println("order_id:"+order_id);
		return "jsp/uav_deliveryProcess";
		
	}
	
	 @RequestMapping("/uploadImage")
	     public String uploadImage(@RequestParam(value="image") MultipartFile filename,@RequestParam(value="order_id") String order_id,@RequestParam(value="currentLoc") String currentLoc) throws Exception {
	         if (filename != null) {
	             
	             String originalFilename = filename.getOriginalFilename();
	             if (originalFilename != null && originalFilename.length() > 0) {
	                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	                 String newFileName = sdf.format(new Date()) + "/" + UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));    
	                 File saveFile = new File("d:/image/" + newFileName);
	                 if (!saveFile.exists()) {
	                     saveFile.mkdirs();
	                 }
	                 try {   
	                     filename.transferTo(saveFile);
	                 } catch (IOException e) {
	                     e.printStackTrace();
	                 }
	                String imageLink=IPFSUtil.add(saveFile);
	         		System.out.println("receipt image's hash value:"+imageLink); 
	         	    bcService.saveReceipt(order_id, imageLink);//Store the pick-up receipt on the blockchain (order_number  and photo hash value)
	         	    
	         	   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   			   String time=df.format(new Date());
	         	   uavService.saveDeliveryProcess(order_id,time,currentLoc,"Finish");
				   uavService.updateOrderState(order_id,"Finish");
	         	  
	             }
	         }
	         return "redirect:getTasks.do";
	     }
	 
	 @RequestMapping("/getTasks")
     public String getTasks(HttpServletRequest request,Map<String, Object> map) throws Exception {
		 User user=(User)request.getSession().getAttribute("user");
		 List<DeliveryTask>  deliveryTasks=new ArrayList<DeliveryTask>();
			deliveryTasks=uavService.getDeliveryTasks(user.getUsername());
			List<Order> waitingOrders=new ArrayList<Order>();
			
			for(int i=0;i<deliveryTasks.size();i++) {
				Order order=new Order();
				order=uavService.findWaitingOrders(deliveryTasks.get(i).getOrder_id());
				waitingOrders.add(order);
			}
			map.put("waitingOrders",waitingOrders);
			return "jsp/main_UAV";
	 }
		 
	 
	   

	 


}
