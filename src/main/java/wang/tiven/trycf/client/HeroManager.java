package wang.tiven.trycf.client;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import wang.tiven.trycf.model.Hero;
import wang.tiven.trycf.web.entity.HeroResource;

@Component
public class HeroManager implements CommandLineRunner {
	
	public static String BASE_URL = "http://localhost";
	
	@Value("${server.port}")
	public String serverPort;
	
	private final RestTemplate template = new RestTemplate();
	
	@Override
	public void run(String... args) throws Exception {
		
		String heroUrl = BASE_URL + ":" + serverPort + "/hero";
		
		Hero batman = new Hero("Batman");
		
		BigInteger id = template.postForObject(heroUrl, batman, BigInteger.class);
		
		HeroResource hero = template.getForObject(heroUrl + "/{hero}", HeroResource.class, id);
		
		System.out.println("Got you " + hero.getName());
	}

}
