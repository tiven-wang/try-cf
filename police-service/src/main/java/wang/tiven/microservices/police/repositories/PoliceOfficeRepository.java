package wang.tiven.microservices.police.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import wang.tiven.microservices.police.domain.PoliceOffice;

public interface PoliceOfficeRepository extends JpaRepository<PoliceOffice, Long> {

    PoliceOffice findByOfficeName(String officeName);
}