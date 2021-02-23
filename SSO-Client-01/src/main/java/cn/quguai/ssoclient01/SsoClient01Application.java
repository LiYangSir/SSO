package cn.quguai.ssoclient01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SsoClient01Application {

	public static void main(String[] args) {
		SpringApplication.run(SsoClient01Application.class, args);
	}

}
