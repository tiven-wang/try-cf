package wang.tiven.microservices.police.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import wang.tiven.microservices.police.domain.PoliceOffice;
import wang.tiven.microservices.police.domain.Villain;

public interface VillainRepository extends JpaRepository<Villain, Long> {

	List<Villain> findByPoliceOffice(PoliceOffice policeOffice);

	Villain findByPoliceOfficeAndName(PoliceOffice policeOffice, String name);
}
