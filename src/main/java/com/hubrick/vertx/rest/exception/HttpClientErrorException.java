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
package com.hubrick.vertx.rest.exception;

import com.hubrick.vertx.rest.converter.HttpMessageConverter;
import org.vertx.java.core.http.HttpClientResponse;

import java.util.List;

/**
 * Exception thrown when an HTTP 4xx is received.
 *
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
public class HttpClientErrorException extends HttpStatusCodeException {

    private static final long serialVersionUID = 5177019431887513952L;

    public HttpClientErrorException(HttpClientResponse httpClientResponse, List<HttpMessageConverter> httpMessageConverters, byte[] responseBody) {
        super(httpClientResponse, httpMessageConverters, responseBody);
    }
}
