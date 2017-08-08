package wang.tiven.trycf.web;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wang.tiven.trycf.model.Hero;
import wang.tiven.trycf.repository.HeroRepository;
import wang.tiven.trycf.service.HeroService;
import wang.tiven.trycf.web.assembler.HeroAssembler;
import wang.tiven.trycf.web.entity.HeroResource;

@RestController
@RequestMapping("/hero")
public class HeroController {

	@Autowired(required = false) HeroRepository heroRepository;
	@Autowired HeroService heroService;

	@RequestMapping("")
	PagedResources<HeroResource> getAll(Pageable pageable, PagedResourcesAssembler<Hero> assembler) {
	    return assembler.toResource(heroRepository.findAll(pageable), new HeroAssembler(HeroController.class, HeroResource.class));
	}

	@RequestMapping(path="", method=RequestMethod.POST)
	BigInteger create(@RequestBody Hero hero) {
	    return heroRepository.save(hero).getId();
	}

	@RequestMapping("/{id}")
	HeroResource get(@PathVariable("id") Hero hero) {
	    return new HeroAssembler(HeroController.class, HeroResource.class).toResource(hero);
	}

	@RequestMapping(path="/{id}", method=RequestMethod.DELETE)
	void delete(@PathVariable("id") Hero hero) {
	    heroRepository.delete(hero);
	}
	
	@RequestMapping(path="/{id}/catch/{villains}", method=RequestMethod.POST)
	void catchVillains(@PathVariable("id") Hero hero, @PathVariable("villains") String villains) throws InterruptedException {
		heroService.catchVillains(hero, villains);
	}
}
