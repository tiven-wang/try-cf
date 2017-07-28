package wang.tiven.trycf.web;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wang.tiven.trycf.model.Hero;
import wang.tiven.trycf.repository.HeroRepository;

@RestController
@RequestMapping("/hero")
public class HeroController {

	@Autowired(required = false) HeroRepository heroRepository;

	@RequestMapping("")
	Page<Hero> getAll(Pageable pageable) {
	    return heroRepository.findAll(pageable);
	}

	@RequestMapping(path="", method=RequestMethod.POST)
	Hero create(@RequestBody Hero hero) {
	    return heroRepository.save(hero);
	}

	@RequestMapping("/{id}")
	Hero get(@PathVariable BigInteger id) {
	    return heroRepository.findOne(id);
	}

	@RequestMapping(path="/{id}", method=RequestMethod.DELETE)
	void delete(@PathVariable BigInteger id) {
	    heroRepository.delete(id);
	}
}
