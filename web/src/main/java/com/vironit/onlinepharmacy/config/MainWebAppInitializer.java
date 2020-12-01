package com.vironit.onlinepharmacy.config;

import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;

/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.Application}.
 */
@Deprecated
public class MainWebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(final ServletContext sc) {
//
//        AnnotationConfigWebApplicationContext root =
//                new AnnotationConfigWebApplicationContext();
//
//        root.scan("com.vironit.onlinepharmacy");
//        sc.addListener(new ContextLoaderListener(root));
//        ServletRegistration.Dynamic appServlet =
//                sc.addServlet("mvc", new DispatcherServlet(new GenericWebApplicationContext()));
//        sc.addFilter("SecurityFilter",new DelegatingFilterProxy("springSecurityFilterChain"))
//                .addMappingForUrlPatterns(null,false,"/*");
//        appServlet.setLoadOnStartup(1);
//        appServlet.addMapping("/");
//        root.setServletContext(sc);
    }
}
