package wang.tiven.trycf.repository;

import java.math.BigInteger;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import wang.tiven.trycf.model.Villains;

@RepositoryRestResource(collectionResourceRel = "villains", path = "/villains")
public interface VillainsRepository extends PagingAndSortingRepository<Villains, BigInteger> {

}
