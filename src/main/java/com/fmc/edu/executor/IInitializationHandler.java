package com.fmc.edu.executor;

import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Yove on 5/24/2015.
 */
public interface IInitializationHandler {

	void initialize(WebApplicationContext pWebApplicationContext);
}
