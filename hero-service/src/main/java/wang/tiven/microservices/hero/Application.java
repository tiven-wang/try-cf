package wang.tiven.microservices.hero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

@RestController
@RequestMapping("/{heroName}")
class HeroRestController {
	
	@Autowired
	VillainClient villainClient;
	
	@Autowired
	PoliceOfficeClient policeOfficeClient;
	
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody void move(@PathVariable String heroName, @RequestBody Villain villain) {
		
		System.out.println("I " + heroName + " move!");
		
		Resource resource = villainClient.get(villain.getName());
		
		System.out.println(villain.getName() + ", catch you!");
		
		policeOfficeClient.catched(villain.getName());
	}
}

@FeignClient("police-service")
interface PoliceOfficeClient {

    @RequestMapping(method = RequestMethod.POST, value = "/Gotham-City/villains/{villainName}")
    void catched(@PathVariable("villainName") String villainName);
}

@FeignClient("villain-service")
interface VillainClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{villainName}")
    Resource get(@PathVariable("villainName") String villainName);
}


class Villain {
	
	private String name;

    private Long id;

	private boolean catched;
	
	public Villain() {
	}
	
	public Villain(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isCatched() {
		return catched;
	}

	public void setCatched(boolean catched) {
		this.catched = catched;
	}
}
