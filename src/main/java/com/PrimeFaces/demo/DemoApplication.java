package com.PrimeFaces.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	//JSF Configration
/*	@Bean
	public ServletRegistrationBean<FacesServlet> facesServletRegistraiton() {
		ServletRegistrationBean<FacesServlet> registration = new ServletRegistrationBean<FacesServlet>(new FacesServlet(), new String[] { "*.xhtml" });
		registration.setName("Faces Servlet");
		registration.setLoadOnStartup(1);

		return registration;
	}*/

	@Bean
	public ServletContextInitializer servletContextInitializer() {
		return servletContext -> {
			servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
			//Primefacesin ücretsiz temalarından bootstrap örneği yaptık değiştirebilirsiniz
			servletContext.setInitParameter("primefaces.THEME", "bootstrap");
			//Primefaces client browser tarafında kontrol edilebilme örneğin textbox 10 karakter olmalı vs..
			servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", Boolean.TRUE.toString());
			//Xhtml sayfalarında commentlerin parse edilmemesi.
			servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", Boolean.TRUE.toString());
			//primefaces icon set için
			servletContext.setInitParameter("primefaces.FONT_AWESOME", Boolean.TRUE.toString());
		};
	}

/*
	@Bean
	public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
		return new ServletListenerRegistrationBean<ConfigureListener>(new ConfigureListener());
	}
	*/

}
