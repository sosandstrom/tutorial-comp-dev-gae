/*
 * INSERT COPYRIGHT HERE
 */

package com.wadpam.tutorial.service;

import com.wadpam.oauth2.domain.DFactory;
import com.wadpam.oauth2.service.FactoryService;
import com.wadpam.oauth2.service.OAuth2Service;
import com.wadpam.oauth2.web.OAuth2Interceptor;
import com.wadpam.open.domain.DAppDomain;
import com.wadpam.open.service.DomainService;
import com.wadpam.open.web.DomainNamespaceFilter;
import java.io.IOException;
import javax.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author sosandstrom
 */
public class TutorialService {
    public static final String TUTORIAL = "tutorial";

    @Autowired
    private DomainService domainService;
    
    @Autowired
    private FactoryService factoryService;
    
    public void init() throws IOException, ServletException {
        
        // create a tutorial app domain:
        DAppDomain tutorial = new DAppDomain();
        tutorial.setId(TUTORIAL);
        tutorial.setUsername(TUTORIAL);
        tutorial.setPassword(TUTORIAL);
        domainService.create(tutorial);
        
        // create a facebook Factory in tutorial namespace:
        DomainNamespaceFilter.doInNamespace(TUTORIAL, new Runnable() {
            public void run() {
                DFactory facebook = new DFactory();
                facebook.setId(OAuth2Service.PROVIDER_ID_FACEBOOK);
                facebook.setClientId("255653361131262");
                factoryService.create(facebook);
            }
        });
    }
    
}
