package ae.etisalat.swyp;

import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ae.etisalat.swyp.interceptor.RestTemplateHeaderModifierInterceptor;

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
		 return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}
	
	@Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        restTemplate.setInterceptors(Collections.singletonList(restTemplateHeaderModifierInterceptor()));
        return restTemplate;
    }
	
	@Bean
	public RestTemplateHeaderModifierInterceptor restTemplateHeaderModifierInterceptor() {
	    return new RestTemplateHeaderModifierInterceptor();
	}
}
