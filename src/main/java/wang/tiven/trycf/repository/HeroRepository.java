package wang.tiven.trycf.repository;

import org.springframework.data.repository.CrudRepository;

import wang.tiven.trycf.model.Hero;

public interface HeroRepository extends CrudRepository<Hero, Long> {

}
