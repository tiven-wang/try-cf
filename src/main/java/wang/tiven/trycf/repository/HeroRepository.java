package wang.tiven.trycf.repository;

import java.math.BigInteger;

import org.springframework.data.repository.CrudRepository;

import wang.tiven.trycf.model.Hero;

public interface HeroRepository  extends CrudRepository<Hero, BigInteger> {

}
