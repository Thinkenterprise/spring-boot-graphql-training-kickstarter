package com.thinkenterprise.domain.route.publisher;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;

import com.thinkenterprise.domain.route.Route;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.observables.ConnectableObservable;

/**  
* GraphQL Spring Boot Samples 
* Design and Development by Michael Schäfer 
* Copyright (c) 2019 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/

public class RxJavaRouteSubscriptionNotifier implements RouteSubscriptionNotifier {

    private final Flowable<Route> publisher;
    private ObservableEmitter<Route> observableEmitter;

    public RxJavaRouteSubscriptionNotifier() {
        Observable<Route> stockPriceUpdateObservable = Observable.create(emitter -> {
            observableEmitter=emitter;
        });

        ConnectableObservable<Route> connectableObservable = stockPriceUpdateObservable.share().publish();
        connectableObservable.connect();

        publisher = connectableObservable.toFlowable(BackpressureStrategy.BUFFER);
    }

	@Override
	public void emit(Route route) {
		 observableEmitter.onNext(route);
		
	}

	@Override
	public Publisher<Route> getPublisher() {
		return publisher;
	}
    
    
   
}
