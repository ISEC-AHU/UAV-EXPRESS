package com.anhui.delivery.controller;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anhui.delivery.service.AMDService;
import com.anhui.delivery.service.DeliveryStationService;
import com.anhui.delivery.service.ReceiverService;
import com.anhui.delivery.service.UAVService;
import com.anhui.delivery.service.UserService;
import com.anhui.delivery.vo.DeliveryTask;
import com.anhui.delivery.vo.Order;
import com.anhui.delivery.vo.UAV;
import com.anhui.delivery.vo.User;

@Controller
@Scope(value="prototype")
@RequestMapping("index")
public class LoginController   {
	
	private UserService userService = new UserService();
	private AMDService amdService = new AMDService();
	private DeliveryStationService dsService=new DeliveryStationService();
	private UAVService uavService=new UAVService();
	private ReceiverService receiverService=new ReceiverService();

    @RequestMapping(value="/login")
    public String Login(User user,ModelMap model,HttpServletRequest request,HttpServletResponse response,Writer out,HttpSession session,Map<String, Object> map)
    		throws Exception {
    	response.setContentType("text/html;charset=UTF-8");
    	//userService.deleteUser("Li Si");
    	/*user.setEthAccount("12345678910");
    	userService.save(user);
    	return null;*/
    	String checkStr=userService.checkUser(user);
    	if(checkStr.equals("OK")) {
    		String ethAccount=userService.getEthAccount(user.getUsername());
    		user.setEthAccount(ethAccount);
    		request.getSession().setAttribute("user", user);
    		if(user.getType().equals("UAV")) {
    			
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
    		}else if(user.getType().equals("deliveryStation")) {
    			
    			List<UAV>  certifiedUAVList=new ArrayList<UAV>();
    			List<UAV>  waitingVerifyUAVs=new ArrayList<UAV>();
    			List<UAV>  deniedUAVs=new ArrayList<UAV>();
    			certifiedUAVList=dsService.findCertifiedUAV(user.getUsername());
    			waitingVerifyUAVs=dsService.findUncheckedUAV(user.getUsername());
    			deniedUAVs=dsService.findDeniedUAV(user.getUsername());
    			map.put("certifiedUAVList",certifiedUAVList);
    			map.put("waitingVerifyUAVs",waitingVerifyUAVs);
    			map.put("deniedUAVs",deniedUAVs);
    			return "jsp/main_deliveryStation2";
    		}else if(user.getType().equals("AMD")) {
    			
    			List<UAV>  uavList=new ArrayList<UAV>();
    			uavList=amdService.findUncheckedUAV();
    			map.put("uavList",uavList);
    			return "jsp/main_AMD";
    		}else {
    			List<Order>   orderList=new ArrayList<Order>();
    			orderList=receiverService.getAllOrder(user.getUsername());
    			map.put("orderList",orderList);
    			return "jsp/main_receiver";
    		}
    		
    	}else if(checkStr.equals("N")) {
    		out=response.getWriter();
        	out.write("<script>alert('The user does not exist, please login again!');window.location.href='../login.html'</script>");
        	 return null;
    	}else{
    		out=response.getWriter();
        	out.write("<script>alert('Password error, please login again!');window.location.href='../login.html'</script>");
        	 return null;
    	}
    	

		
    }
    
    
}
