package wang.tiven.trycf.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import wang.tiven.trycf.message.Sender;

@RestController
public class HomeController {
	
	@Autowired
	private Sender sender;

    @RequestMapping(value="/villains", method=RequestMethod.POST)
    String home(@RequestParam("message") String message) throws Exception {
    	sender.send(message);
        return "Done!";
    }
}
