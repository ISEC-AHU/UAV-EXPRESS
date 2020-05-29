package com.anhui.delivery.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.mongodb.DBObject;

public class DBObjectToBean {
	 public static <T> T dbObject2Bean(DBObject dbObject, T bean) throws IllegalAccessException,InvocationTargetException, NoSuchMethodException {  
         
		 if (bean == null) {  
            	 return null;  
              }  
           Field[] fields = bean.getClass().getDeclaredFields();  
           for (Field field : fields) {  
              String varName = field.getName();  
              Object object = dbObject.get(varName);  
              if (object != null) {  
                 BeanUtils.setProperty(bean, varName, object);  
                
                 }  
              }  
         return bean;  
} 

}
