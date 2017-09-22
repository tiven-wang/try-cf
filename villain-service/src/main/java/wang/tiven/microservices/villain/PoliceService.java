package wang.tiven.microservices.villain;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.core.ParameterizedTypeReference;

@Service
public class PoliceService {

  private final RestTemplate restTemplate;

  public PoliceService(RestTemplate rest) {
    this.restTemplate = rest;
  }

  @HystrixCommand(fallbackMethod = "reliable")
  public String notify(String villainId) {

    this.restTemplate.exchange(
            "http://police-service/Gotham-City/villains",
            HttpMethod.POST,
            new HttpEntity<Villain>(new Villain(villainId)),
            new ParameterizedTypeReference<Villain>() {
            });
    return "Successful! " + villainId;
  }

  public String reliable(String villainId) {
    return "Ops! " + villainId;
  }

}
