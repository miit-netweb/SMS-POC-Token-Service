package Microservice.Token_Service.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import Microservice.Token_Service.Dto.ExceptionResponse;
import Microservice.Token_Service.Entity.PartnerTokenValidation;
import Microservice.Token_Service.Repository.PartnerTokenValidationRepository;
import Microservice.Token_Service.exception.DbException;

@Service
public class PartnerTokenValidationService {
    @Autowired
    private PartnerTokenValidationRepository partnerTokenValidationRepository;
    
    Logger LOGGER=LoggerFactory.getLogger(PartnerTokenValidation.class);

    public PartnerTokenValidation isPartnerExpired(long partnerNumber){
        Optional<PartnerTokenValidation> partner = partnerTokenValidationRepository.findById(partnerNumber);
        if(partner.isEmpty()) return null;
        final LocalDateTime expiry = partner.get().getExpiry();
        if(expiry.isBefore(LocalDateTime.now())) return null;
        return partner.get();
    }

    public PartnerTokenValidation addNewPartner(PartnerTokenValidation partner) throws Exception{
        try{
            final PartnerTokenValidation save = partnerTokenValidationRepository.save(partner);
            return save;
        } catch (RuntimeException ex){
		    LOGGER.error("Original exception: {}", ex.getCause().getMessage());
			
			String errorMessage = ExceptionResponse.parseErrorMessage(ex.getMessage());
			throw new DbException(5555, errorMessage, HttpStatus.BAD_REQUEST);
        }
    }
}
