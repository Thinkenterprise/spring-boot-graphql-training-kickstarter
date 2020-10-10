package com.thinkenterprise.domain.route.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {
	
	protected static Logger log = LoggerFactory.getLogger(DiscountService.class);
			
	public Map<Long,Float> discountData = new HashMap<Long,Float>(){
	private static final long serialVersionUID = 1L;

	{
        put(Long.valueOf(1l), Float.valueOf(1.00f));
        put(Long.valueOf(2l), Float.valueOf(0.89f));
        put(Long.valueOf(3l), Float.valueOf(0.90f));
          
    }};
	
	public Float getDiscount(Long id) {
		
		log.debug("Discount for Flight " + id );
		if( discountData.containsKey(id)) 
			return discountData.get(id);
		else 
			return Float.valueOf(1.00f);
	}
	public List<Float> getDiscountByIds(List<Long> ids) {
		
		List<Float> floats = new ArrayList<Float>();
		
		log.debug("Discount for Flights " + ids );
		
		for (Long id : ids) {
			if( discountData.containsKey(id)) 
				floats.add(discountData.get(id));
		}
		return floats;
	}
	
}
