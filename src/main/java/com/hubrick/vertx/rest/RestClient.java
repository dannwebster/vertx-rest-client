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
package com.hubrick.vertx.rest;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;

/**
 * An REST client that maintains a pool of connections to a specific host, at a specific port. The client supports
 * pipelining of requests.<p>
 * If an instance is instantiated from an event loop then the handlers
 * of the instance will always be called on that same event loop.
 * If an instance is instantiated from some other arbitrary Java thread (i.e. when running embedded) then
 * and event loop will be assigned to the instance and used when any of its handlers
 * are called.<p>
 * Instances of RestClient are thread-safe.
 *
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
public interface RestClient {

    /**
     * Makes a GET call with no response value.
     *
     * @param uri The uri which should be called.
     * @param responseHandler The handler for the response callback
     * @return A reference to the {@link com.hubrick.vertx.rest.RestClientRequest}
     */
    RestClientRequest get(String uri, Handler<RestClientResponse<Void>> responseHandler);

    /**
     * Makes a GET call with a expected response value.
     *
     * @param uri The uri which should be called.
     * @param responseClass The class which represents the response
     * @param responseHandler The handler for the response callback
     * @return A reference to the {@link com.hubrick.vertx.rest.RestClientRequest}
     */
    <T> RestClientRequest get(String uri, Class<T> responseClass, Handler<RestClientResponse<T>> responseHandler);

    /**
     * Makes a POST call with no response value.
     *
     * @param uri The uri which should be called.
     * @param responseHandler The handler for the response callback
     * @return A reference to the {@link com.hubrick.vertx.rest.RestClientRequest}
     */
    RestClientRequest post(String uri, Handler<RestClientResponse<Void>> responseHandler);

    /**
     * Makes a POST call with a expected response value.
     *
     * @param uri The uri which should be called.
     * @param responseClass The class which represents the response
     * @param responseHandler The handler for the response callback
     * @return A reference to the {@link com.hubrick.vertx.rest.RestClientRequest}
     */
    <T> RestClientRequest post(String uri, Class<T> responseClass, Handler<RestClientResponse<T>> responseHandler);

    /**
     * Makes a PUT call with no response value.
     *
     * @param uri The uri which should be called.
     * @param responseHandler The handler for the response callback
     * @return A reference to the {@link com.hubrick.vertx.rest.RestClientRequest}
     */
    RestClientRequest put(String uri, Handler<RestClientResponse<Void>> responseHandler);

    /**
     * Makes a PUT call with a expected response value.
     *
     * @param uri The uri which should be called.
     * @param responseClass The class which represents the response
     * @param responseHandler The handler for the response callback
     * @return A reference to the {@link com.hubrick.vertx.rest.RestClientRequest}
     */
    <T> RestClientRequest put(String uri, Class<T> responseClass, Handler<RestClientResponse<T>> responseHandler);

    /**
     * Makes a DELETE call with no response value.
     *
     * @param uri The uri which should be called.
     * @param responseHandler The handler for the response callback
     * @return A reference to the {@link com.hubrick.vertx.rest.RestClientRequest}
     */
    RestClientRequest delete(String uri, Handler<RestClientResponse<Void>> responseHandler);

    /**
     * Makes a DELETE call with a expected response value.
     *
     * @param uri The uri which should be called.
     * @param responseClass The class which represents the response
     * @param responseHandler The handler for the response callback
     * @return A reference to the {@link com.hubrick.vertx.rest.RestClientRequest}
     */
    <T> RestClientRequest delete(String uri, Class<T> responseClass, Handler<RestClientResponse<T>> responseHandler);

    /**
     * Makes a GET, POST, PUT or DELETE call with no response value. It's a generic method for REST calls.
     *
     * @param method The http method to be used for this call
     * @param uri The uri which should be called.
     * @param responseHandler The handler for the response callback
     * @return A reference to the {@link com.hubrick.vertx.rest.RestClientRequest}
     */
    RestClientRequest request(HttpMethod method, String uri, Handler<RestClientResponse<Void>> responseHandler);

    /**
     * Makes a GET, POST, PUT or DELETE call with a expected response value. It's a generic method for REST calls.
     *
     * @param method The http method to be used for this call
     * @param uri The uri which should be called.
     * @param responseClass The class which represents the response
     * @param responseHandler The handler for the response callback
     * @return A reference to the {@link com.hubrick.vertx.rest.RestClientRequest}
     */
    <T> RestClientRequest request(HttpMethod method, String uri, Class<T> responseClass, Handler<RestClientResponse<T>> responseHandler);
}
