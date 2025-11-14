package com.example.demo.api;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyServlet extends ServletContainer {
    public JerseyServlet() {
        super(new ResourceConfig()
                .packages("com.example.demo.api")
                .register(JacksonFeature.class)
        );    }
}
