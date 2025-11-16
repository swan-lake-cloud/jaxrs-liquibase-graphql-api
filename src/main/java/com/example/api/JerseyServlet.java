package com.example.api;

import com.example.filter.JwtAuthenticationFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyServlet extends ServletContainer {
    public JerseyServlet() {
        super(new ResourceConfig()
                .packages("com.example.api")
                .register(JacksonFeature.class)
                .register(JwtAuthenticationFilter.class) // Register the JWT filter
        );
    }
}
