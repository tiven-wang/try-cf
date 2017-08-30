package wang.tiven.microservices.police;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import wang.tiven.microservices.police.domain.PoliceOffice;
import wang.tiven.microservices.police.domain.Villain;
import wang.tiven.microservices.police.repositories.PoliceOfficeRepository;
import wang.tiven.microservices.police.repositories.VillainRepository;

@EnableJpaRepositories
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(PoliceOfficeRepository policeOfficeRepository) {
        return args -> {
        	policeOfficeRepository.deleteAll();

            Arrays.asList("Gotham-City", "New York").forEach(n ->
            	policeOfficeRepository.save(new PoliceOffice(n,
                            "http://some-other-host" + n + ".com/",
                            "A description for " + n + "'s link",
                            n)));
        };
    }

}

@RestController
@RequestMapping("/{officeName}")
class PoliceOfficeRestController {

    @Autowired
    private PoliceOfficeRepository policeOfficeRepository;
    
    @Autowired
    private VillainRepository villainRepository;
    
    @Autowired
    HeroClient heroClient;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody PoliceOffice getPoliceOffice(@PathVariable String officeName) {
        return this.policeOfficeRepository.findByOfficeName(officeName);
    }

    @RequestMapping(value = "/villains", method = RequestMethod.GET)
    @ResponseBody Collection<Villain> getVillains(@PathVariable String officeName) {
    	PoliceOffice policeOffice = policeOfficeRepository.findByOfficeName(officeName);

        return this.villainRepository.findByPoliceOffice(policeOffice);
    }

    @RequestMapping(value = "/villains/{villainName}", method = RequestMethod.GET)
    @ResponseBody Villain getVillain(@PathVariable String officeName,
                         @PathVariable String villainName) {
    	PoliceOffice policeOffice = policeOfficeRepository.findByOfficeName(officeName);

        return this.villainRepository.findByPoliceOfficeAndName(policeOffice, villainName);
    }

    @RequestMapping(value = "/villains", method = RequestMethod.POST)
    @ResponseBody Villain createVillain(@PathVariable String officeName, @RequestBody Villain villain) {
    	
    	System.out.println("This is " + officeName + ", you " + villain.getName() + " just wait!");
    	
    	PoliceOffice policeOffice = policeOfficeRepository.findByOfficeName(officeName);
    	
    	villain.setPoliceOffice(policeOffice);
    	
    	villain = this.villainRepository.save(villain);
        
        heroClient.move("Batman", villain);
        
        return villain;
    }
    
    @RequestMapping(value = "/villains/{villainName}", method = RequestMethod.POST)
    @ResponseBody void catchVillain(@PathVariable String officeName, @PathVariable String villainName) {
    	
    	System.out.println("Have catched " + villainName);
    	
    	PoliceOffice policeOffice = policeOfficeRepository.findByOfficeName(officeName);
    	Villain villain = villainRepository.findByPoliceOfficeAndName(policeOffice, villainName);
    	villain.setCatched(true);
    	
        this.villainRepository.save(villain);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody PoliceOffice createPoliceOffice(@PathVariable String officeName,
                            @RequestBody PoliceOffice policeOffice) {

    	PoliceOffice policeOfficeInstance = new PoliceOffice(
    			officeName,
    			policeOffice.getHref(),
    			policeOffice.getDescription(),
    			policeOffice.getLabel());

        return this.policeOfficeRepository.save(policeOfficeInstance);
    }

}

@FeignClient("hero-service")
interface HeroClient {

    @RequestMapping(method = RequestMethod.POST, value = "/{heroName}")
    void move(@PathVariable("heroName") String heroName, @RequestBody Villain villain);
    
}