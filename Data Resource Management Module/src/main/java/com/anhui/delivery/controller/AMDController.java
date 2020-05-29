package com.anhui.delivery.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anhui.delivery.service.AMDService;
import com.anhui.delivery.service.UavBcService;
import com.anhui.delivery.utils.IPFSUtil;
import com.anhui.delivery.vo.UAV;

@Controller
@Scope(value="prototype")
@RequestMapping("AMD")
public class AMDController   {
	
	private AMDService amdService = new AMDService();
	private UavBcService bcService=new UavBcService();

	@RequestMapping(value="/uncheckedUAV")
	public String findUncheckedUAV(Map<String, Object> map) throws Exception {
		List<UAV>   uavList=new ArrayList<UAV>();
		uavList=amdService.findUncheckedUAV();
		map.put("uavList",uavList);
		return "jsp/main_AMD";
		
	}
	
	
	
	@RequestMapping(value="/grantRight")
	public String grantRight(@RequestParam(value="uid") String uid,@RequestParam(value="manufacturer") String manufacturer,@RequestParam(value="companyAccount") String companyAccount,
			@RequestParam(value="limitedHeight") String limitedHeight,@RequestParam(value="limitedLoad") String limitedLoad) throws Exception{
		File file = new File("d:" + File.separator + uid+".txt");
		
		Writer fw = new FileWriter(file);
		
		fw.write("uid:"+uid+",manufacturer:"+manufacturer+",companyAccount:"+companyAccount+",limitedHeight:"+limitedHeight+",limitedLoad:"+limitedLoad);
		
		fw.close();
		
		//update mongoDB
		amdService.grantRight(uid);
	    //save in IPFS and blockchain
		String uavLink=IPFSUtil.add(file);
		
		bcService.saveCerifiedUAVOnBc(uid,uavLink);
		
		return "redirect:uncheckedUAV.do";
	


	}
	
	@RequestMapping(value="/denyUAV")
	public String denyUAV(@RequestParam(value="uid") String uid) throws Exception{
		
		//The unauthorized UAVs are not stored in IPFS and blockchain
		amdService.denyUAV(uid);
		return "redirect:uncheckedUAV.do";
	
	}
	
	
	
}

