package com.thinkenterprise.domain.route.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * GraphQL Spring Boot Training Design and Development by Michael Schäfer
 * Copyright (c) 2020 All Rights Reserved.
 * 
 * @author Michael Schäfer
 */

@Service
public class DiscountService {

	protected static Logger log = LoggerFactory.getLogger(DiscountService.class);
	
	@Value("${thinkenterprise.longRequest.enable}")
	private Boolean delay = false;
	

	public Map<Long, Float> discountData = new HashMap<Long, Float>() {
		private static final long serialVersionUID = 1L;

		{
			put(Long.valueOf(1l), Float.valueOf(1.00f));
			put(Long.valueOf(2l), Float.valueOf(0.89f));
			put(Long.valueOf(3l), Float.valueOf(0.90f));

		}
	};

	/**
	 * Access Discount over REST API call from a legacy system
	 **/
	public Float getDiscount(Long id) {
		
		if(delay)
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}

		log.info("Discount for Flight " + id);

		if (discountData.containsKey(id))
			return discountData.get(id);
		else
			return Float.valueOf(1.00f);
	}

	public List<Float> getDiscountByIds(List<Long> ids) {
		
		if(delay)
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}

		List<Float> floats = new ArrayList<Float>();

		log.debug("Discount for Flights " + ids);

		for (Long id : ids) {
			if (discountData.containsKey(id))
				floats.add(discountData.get(id));
		}
		return floats;
	}

}
