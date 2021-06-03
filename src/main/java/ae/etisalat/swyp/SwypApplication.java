package ae.etisalat.swyp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableAutoConfiguration
@EnableAspectJAutoProxy
@ComponentScan
public class SwypApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwypApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	/*
	 * @Bean ServletRegistrationBean h2servletRegistration(){
	 * ServletRegistrationBean registrationBean = new ServletRegistrationBean(new
	 * WebServlet()); registrationBean.addUrlMappings("/console/*"); return
	 * registrationBean; }
	 */
}
