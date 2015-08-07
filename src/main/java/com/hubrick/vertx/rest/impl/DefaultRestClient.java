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
package com.hubrick.vertx.rest.impl;

import com.hubrick.vertx.rest.RestClient;
import com.hubrick.vertx.rest.RestClientRequest;
import com.hubrick.vertx.rest.RestClientResponse;
import com.hubrick.vertx.rest.converter.HttpMessageConverter;
import com.sun.corba.se.impl.presentation.rmi.ExceptionHandler;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The default implementation.
 *
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
public class DefaultRestClient implements RestClient {

    private static final Logger log = LoggerFactory.getLogger(DefaultRestClient.class);

    private final HttpClient httpClient;
    private final List<HttpMessageConverter> httpMessageConverters;
    private final int globalRequestTimeout;
    private Handler<Throwable> exceptionHandler;


    public DefaultRestClient(HttpClient httpClient,
                             List<HttpMessageConverter> httpMessageConverters,
                             Handler<Throwable> exceptionHandler,
                             int globalRequestTimeout) {
        this.httpMessageConverters = httpMessageConverters;
        this.globalRequestTimeout = globalRequestTimeout;
        this.exceptionHandler = exceptionHandler;
        this.httpClient = httpClient;
    }

    @Override
    public RestClientRequest<Void> get(String uri, Handler<RestClientResponse<Void>> responseHandler) {
        return get(uri, Void.class, responseHandler);
    }

    @Override
    public <T> RestClientRequest<T> get(String uri, Class<T> responseClass, Handler<RestClientResponse<T>> responseHandler) {
        log.debug("Calling uri: {}", uri);
        return handleRequest(HttpMethod.GET, uri, responseClass, responseHandler);
    }

    @Override
    public RestClientRequest<Void> post(String uri, Handler<RestClientResponse<Void>> responseHandler) {
        return post(uri, Void.class, responseHandler);
    }

    @Override
    public <T> RestClientRequest<T> post(String uri, Class<T> responseClass, Handler<RestClientResponse<T>> responseHandler) {
        log.debug("Calling uri: {}", uri);
        return handleRequest(HttpMethod.POST, uri, responseClass, responseHandler);
    }

    @Override
    public RestClientRequest<Void> put(String uri, Handler<RestClientResponse<Void>> responseHandler) {
        return put(uri, Void.class, responseHandler);
    }

    @Override
    public <T> RestClientRequest<T> put(String uri, Class<T> responseClass, Handler<RestClientResponse<T>> responseHandler) {
        log.debug("Calling uri: {}", uri);
        return handleRequest(HttpMethod.PUT, uri, responseClass, responseHandler);
    }

    @Override
    public RestClientRequest<Void> delete(String uri, Handler<RestClientResponse<Void>> responseHandler) {
        return delete(uri, Void.class, responseHandler);
    }

    @Override
    public <T> RestClientRequest<T> delete(String uri, Class<T> responseClass, Handler<RestClientResponse<T>> responseHandler) {
        log.debug("Calling uri: {}", uri);
        return handleRequest(HttpMethod.DELETE, uri, responseClass, responseHandler);
    }

    @Override
    public RestClientRequest<Void> request(HttpMethod method, String uri, Handler<RestClientResponse<Void>> responseHandler) {
        return request(method, uri, Void.class, responseHandler);
    }

    @Override
    public <T> RestClientRequest<T> request(HttpMethod method, String uri, Class<T> responseClass, Handler<RestClientResponse<T>> responseHandler) {
        log.debug("Calling uri: {}", uri);
        return handleRequest(method, uri, responseClass, responseHandler);
    }

    private <T> DefaultRestClientRequest<T> handleRequest(HttpMethod method, String uri, Class<T> responseClass, Handler<RestClientResponse<T>> responseHandler) {
        return new DefaultRestClientRequest(
                httpClient,
                httpMessageConverters,
                method,
                uri,
                responseClass,
                responseHandler,
                globalRequestTimeout,
                exceptionHandler
        );
    }

}
