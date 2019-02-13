/**
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.transports.http.bridge;

import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.transport.base.threads.WorkerPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.transport.http.netty.contract.HttpConnectorListener;
import org.wso2.transport.http.netty.message.HttpCarbonMessage;

/**
 * {@code ConnectorListenerToAxisBridge} receives the {@code HttpCarbonMessage} coming from the Netty HTTP transport,
 * converts them to {@code MessageContext} and finally deliver them to the axis engine.
 *
 */
public class ConnectorListenerToAxisBridge implements HttpConnectorListener {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectorListenerToAxisBridge.class);

    private ConfigurationContext configurationContext;
    private WorkerPool workerPool = null;


    public ConnectorListenerToAxisBridge(ConfigurationContext configurationContext, WorkerPool workerPool) {
        this.configurationContext = configurationContext;
        this.workerPool = workerPool;
    }

    public void onMessage(HttpCarbonMessage httpCarbonMessage) {
         LOG.info("Message Received");
         workerPool.execute(new HttpWorker(httpCarbonMessage, configurationContext));
    }

    public void onError(Throwable throwable) {

    }


}
