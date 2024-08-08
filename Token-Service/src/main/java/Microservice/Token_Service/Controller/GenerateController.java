package Microservice.Token_Service.Controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Microservice.Token_Service.Dto.TokenGenerationResponseDto;
import Microservice.Token_Service.Entity.PartnerTokenValidation;
import Microservice.Token_Service.Service.JwtService;
import Microservice.Token_Service.Service.PartnerTokenValidationService;

@RestController
@RequestMapping("/generate")
public class GenerateController {

    @Value("${generate.token.expiry}")
    private int expiryMinutes;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private PartnerTokenValidationService partnerTokenValidationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateController.class);

    @GetMapping("/token/{partner_number}")
    public ResponseEntity<?> tokenGenerationForPartner(@PathVariable("partner_number") long partnerNumber){
        try{
            LOGGER.info("Started creating token for partner number {}",partnerNumber);
            PartnerTokenValidation checkedPartnerToken = partnerTokenValidationService.isPartnerExpired(partnerNumber);
            if(checkedPartnerToken!=null){
                TokenGenerationResponseDto tokenGenerationResponseDto = new TokenGenerationResponseDto();
                tokenGenerationResponseDto.setToken(checkedPartnerToken.getToken());
                tokenGenerationResponseDto.setExpiry(checkedPartnerToken.getExpiry());
                LOGGER.info("Saved token return backed for {}", partnerNumber);
                return ResponseEntity.ok(tokenGenerationResponseDto);
            } else {
                String token = jwtService.generateToken(String.valueOf(partnerNumber));
                System.out.println(token);
                if (!token.isEmpty()) {
                    LOGGER.info("Token saved for {}", partnerNumber);
                    partnerTokenValidationService.addNewPartner(new PartnerTokenValidation(partnerNumber,LocalDateTime.now().plusMinutes(expiryMinutes),token));
                    TokenGenerationResponseDto tokenGenerationResponseDto = new TokenGenerationResponseDto();
                    tokenGenerationResponseDto.setToken(token);
                    tokenGenerationResponseDto.setExpiry(LocalDateTime.now().plusMinutes(expiryMinutes));
                    LOGGER.info("Token created for {}", partnerNumber);
                    return ResponseEntity.ok(tokenGenerationResponseDto);
                } else {
                    LOGGER.error("Unable to make token for partner Number {}", partnerNumber);
                }
            }
        }
        catch (Exception e){
            LOGGER.error(String.format("Exception occurred %s for partner number %s",e.getMessage(),partnerNumber));
        }
        return ResponseEntity.status(500).build();
    }
}
