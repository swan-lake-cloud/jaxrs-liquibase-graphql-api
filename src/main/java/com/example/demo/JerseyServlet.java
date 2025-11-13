package com.example.demo;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.server.ResourceConfig;

import jakarta.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/api/*", loadOnStartup = 1)
public class JerseyServlet extends ServletContainer {
    public JerseyServlet() {
        super(new ResourceConfig().packages("com.example.demo"));
    }
}
