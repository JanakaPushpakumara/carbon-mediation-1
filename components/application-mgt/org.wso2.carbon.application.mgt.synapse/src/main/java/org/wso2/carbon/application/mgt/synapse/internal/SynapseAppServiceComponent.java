/*
*  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.carbon.application.mgt.synapse.internal;

import org.wso2.carbon.registry.core.service.RegistryService;
import org.wso2.carbon.application.deployer.service.ApplicationManagerService;
import org.wso2.carbon.mediation.initializer.services.SynapseConfigurationService;
import org.osgi.service.component.ComponentContext;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

@Component(
         name = "application.mgt.synapse.dscomponent", 
         immediate = true)
public class SynapseAppServiceComponent {

    private static Log log = LogFactory.getLog(SynapseAppServiceComponent.class);

    private static RegistryService registryServiceInstance;

    private static SynapseConfigurationService scService;

    private static ApplicationManagerService applicationManager;

    @Activate
    protected void activate(ComponentContext ctxt) {
        if (log.isDebugEnabled()) {
            log.debug("Activated SynapseAppServiceComponent");
        }
    }

    @Deactivate
    protected void deactivate(ComponentContext ctxt) {
        if (log.isDebugEnabled()) {
            log.debug("Deactivated SynapseAppServiceComponent");
        }
    }

    @Reference(
             name = "registry.service", 
             service = org.wso2.carbon.registry.core.service.RegistryService.class, 
             cardinality = ReferenceCardinality.MANDATORY, 
             policy = ReferencePolicy.DYNAMIC, 
             unbind = "unsetRegistryService")
    protected void setRegistryService(RegistryService registryService) {
        registryServiceInstance = registryService;
    }

    protected void unsetRegistryService(RegistryService registryService) {
        registryServiceInstance = null;
    }

    @Reference(
             name = "synapse.config.service", 
             service = org.wso2.carbon.mediation.initializer.services.SynapseConfigurationService.class, 
             cardinality = ReferenceCardinality.MANDATORY, 
             policy = ReferencePolicy.DYNAMIC, 
             unbind = "unsetSynapseConfigurationService")
    protected void setSynapseConfigurationService(SynapseConfigurationService synapseConfigurationService) {
        scService = synapseConfigurationService;
    }

    protected void unsetSynapseConfigurationService(SynapseConfigurationService synapseConfigurationService) {
        scService = null;
    }

    @Reference(
             name = "application.manager", 
             service = org.wso2.carbon.application.deployer.service.ApplicationManagerService.class, 
             cardinality = ReferenceCardinality.MANDATORY, 
             policy = ReferencePolicy.DYNAMIC, 
             unbind = "unsetAppManager")
    protected void setAppManager(ApplicationManagerService appManager) {
        applicationManager = appManager;
    }

    protected void unsetAppManager(ApplicationManagerService appManager) {
        applicationManager = null;
    }

    public static ApplicationManagerService getAppManager() throws Exception {
        if (applicationManager == null) {
            String msg = "Before activating Synapse App management service bundle, an instance of " + "ApplicationManager should be in existance";
            log.error(msg);
            throw new Exception(msg);
        }
        return applicationManager;
    }

    public static RegistryService getRegistryService() throws Exception {
        if (registryServiceInstance == null) {
            String msg = "Before activating Synapse App management service bundle, an instance of " + "RegistryService should be in existance";
            log.error(msg);
            throw new Exception(msg);
        }
        return registryServiceInstance;
    }

    public static SynapseConfigurationService getScService() throws Exception {
        if (scService == null) {
            String msg = "Before activating Synapse App management service bundle, an instance of " + "SynapseConfigurationService should be in existance";
            log.error(msg);
            throw new Exception(msg);
        }
        return scService;
    }
}
