package ae.etisalat.swyp.integration;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ae.etisalat.swyp.dto.WeatherDomain;
import ae.etisalat.swyp.dto.WeatherDto;
import ae.etisalat.swyp.interceptor.RestTemplateHeaderModifierInterceptor;

@Service
public class WeatherIntegrationService {

	@Autowired
	private RestTemplate restTemplate;

	public WeatherDto getWeatherDomain(WeatherDomain req) {
		
		String baseUrl = "http://api.openweathermap.org/data/2.5/weather?q="+req.getCityName()+"&appid="+req.getApiKey();
		HttpHeaders requestHeaders = new HttpHeaders();
		
		
		HttpEntity<WeatherDomain> entity = new HttpEntity<>(req, requestHeaders);
		
		ResponseEntity<WeatherDto> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.GET, entity,
				WeatherDto.class);

		/*
		 * HttpServletRequest requests = ((ServletRequestAttributes)
		 * RequestContextHolder.getRequestAttributes()).getRequest();
		 * 
		 * requests.setAttribute("javax.servlet.forward.request_uri", baseUrl);
		 * 
		 * RequestContextHolder.setRequestAttributes(new
		 * ServletRequestAttributes(requests));
		 */
		
		
		
		WeatherDto dto = responseEntity.getBody();
		
		
		
		return convertToSMSDto(dto);
		
		
	}
	
	private WeatherDto convertToSMSDto(WeatherDto dto) {
		
		return dto;
		
		
		
	}
}
