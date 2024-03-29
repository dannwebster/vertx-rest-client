/**
 * Copyright (C) 2015 Etaia AS (oss@hubrick.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hubrick.vertx.rest.rx.impl;

import org.vertx.java.core.Handler;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Emir Dizdarevic
 * @since 1.1.0
 */
public class MemoizeHandler<R, T> implements Handler<T> {

    /**
     * States
     */
    enum State {
        ACTIVE, COMPLETED, FAILED
    };

    /**
     * State
     */
    private State state;

    /**
     * Result
     */
    private R result;

    /**
     * Error
     */
    private Throwable error;

    /**
     * Reference to active subscriber
     */
    private AtomicReference<Subscriber<? super R>> subRef = new AtomicReference<>();

    /**
     * Create new MemoizeHandler
     */
    public MemoizeHandler() {
        this.state = State.ACTIVE;
        this.result = null;
        this.error = null;
    }

    /**
     * Subscription function
     */
    private Observable.OnSubscribe<R> subscribe = new Observable.OnSubscribe<R>() {
        public void call(Subscriber<? super R> newSubscriber) {
            // Check if complete
            switch (state) {

                // Completed. Forward the saved result
                case COMPLETED:
                    newSubscriber.onNext(result);
                    newSubscriber.onCompleted();
                    return;

                // Failed already. Forward the saved error
                case FAILED:
                    newSubscriber.onError(error);
                    return;
            }

            // State=ACTIVE
            if (!subRef.compareAndSet(null, newSubscriber))
                throw new IllegalStateException("Cannot have multiple subscriptions");
        }
    };

    /**
     * Dispatch complete
     */
    public void complete(R value) {
        this.result = value;
        this.state = State.COMPLETED;

        Observer<? super R> ob = getObserver();
        // Ignore if no active observer
        if (ob == null)
            return;

        ob.onNext(value);
        ob.onCompleted();
    }

    /**
     * Dispatch failure
     */
    public void fail(Throwable e) {
        this.error = e;
        this.state = State.FAILED;

        Observer<? super R> ob = getObserver();
        // Ignore if no active observer
        if (ob == null)
            return;

        ob.onError(e);
    }

    // Handler implementation
    @Override
    public void handle(T event) {
        complete((R) event);
    }

    public Observable.OnSubscribe<R> getSubscribe() {
        return subscribe;
    }

    /**
     * Return Observer
     */
    protected Observer<? super R> getObserver() {
        Subscriber<? super R> s = subRef.get();

        return (s != null) && !s.isUnsubscribed() ? s : null;
    }
}
