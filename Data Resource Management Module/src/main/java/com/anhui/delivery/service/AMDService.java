package com.anhui.delivery.service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.anhui.delivery.dao.AMDDao;
import com.anhui.delivery.vo.UAV;

public class AMDService {
	
	private AMDDao amdDao = new AMDDao();
	
	public List<UAV> findUncheckedUAV() throws UnknownHostException{
		List<UAV>   uavList=new ArrayList<UAV>();
		uavList=amdDao.findUncheckedUAV();
		System.out.print("=findUncheckedUAV Length="+uavList.size());
		return uavList;
	}
	
	public void grantRight(String uid) throws UnknownHostException{
		amdDao.grantRight(uid);
	}
	
	public void denyUAV(String uid) throws UnknownHostException{
		amdDao.denyUAV(uid);
	}
	
	

}
