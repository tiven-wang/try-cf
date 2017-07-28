package wang.tiven.trycf.repository;

import java.math.BigInteger;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import wang.tiven.trycf.model.Hero;

@RepositoryRestResource(collectionResourceRel = "heros", path = "/heros")
public interface HeroRepository extends PagingAndSortingRepository<Hero, BigInteger> {

}
