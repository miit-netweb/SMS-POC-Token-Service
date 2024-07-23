package Microservice.Token_Service.Repository;

import Microservice.Token_Service.Entity.PartnerTokenValidation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerTokenValidationRepository extends JpaRepository<PartnerTokenValidation,Long> {
}
