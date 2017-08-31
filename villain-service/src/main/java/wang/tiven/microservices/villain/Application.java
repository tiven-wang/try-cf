package wang.tiven.microservices.villain;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;


@SpringBootApplication
@EnableDiscoveryClient
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

@RestController
@RequestMapping("/{villainId}")
class VillainRestController {

    @Autowired
    private GridFsTemplate gridFsTemplate;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    ResponseEntity<?> set(@PathVariable String villainId,
                          @RequestParam MultipartFile multipartFile,
                          UriComponentsBuilder uriBuilder) throws IOException {

        try (InputStream inputStream = multipartFile.getInputStream()) {
            this.gridFsTemplate.store(inputStream, villainId);
        }
        
        System.out.println("I " + villainId + " am here!");
        
        this.restTemplate.exchange(
                "http://police-service/Gotham-City/villains",
                HttpMethod.POST,
                new HttpEntity<Villain>(new Villain(villainId)),
                new ParameterizedTypeReference<Villain>() {
                });
        
        URI uri = uriBuilder.path("/{villainId}").buildAndExpand(villainId).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<Resource> get(@PathVariable String villainId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(
                this.gridFsTemplate.getResource(villainId), httpHeaders, HttpStatus.OK);
    }
    
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
