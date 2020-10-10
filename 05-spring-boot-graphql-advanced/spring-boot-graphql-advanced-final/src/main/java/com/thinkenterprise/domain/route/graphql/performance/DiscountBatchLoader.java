package com.thinkenterprise.domain.route.graphql.performance;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.dataloader.BatchLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinkenterprise.domain.route.service.DiscountService;

public class DiscountBatchLoader implements BatchLoader<Long, Float>{
	
	protected static Logger log = LoggerFactory.getLogger(DiscountService.class);
	
	private DiscountService discountService;
	
	public DiscountBatchLoader(DiscountService discountService) {
		super();
		this.discountService = discountService;
	}

	
	@Override
	public CompletionStage<List<Float>> load(List<Long> ids) {	
		log.debug("Discount for Flights " + ids );
		return CompletableFuture.supplyAsync(() -> discountService.getDiscountByIds(ids));
	}

}
