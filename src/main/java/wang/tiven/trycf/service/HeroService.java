package wang.tiven.trycf.service;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import wang.tiven.trycf.model.Hero;
import wang.tiven.trycf.model.Villains;
import wang.tiven.trycf.repository.VillainsRepository;

@Service
public class HeroService {
	
	@Autowired VillainsRepository villainsRepository;
	
	@Async
	public CompletableFuture<BigInteger> catchVillains(Hero hero, String name) throws InterruptedException {
		Thread.sleep(3000L);
		Villains villains = new Villains(name);
		villains.setCatchedBy(hero);
		return CompletableFuture.completedFuture(villainsRepository.save(villains).getId());
	}
}
