package Microservice.Token_Service.Controller;

import Microservice.Token_Service.Service.JwtService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validate/token")
public class ValidateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateController.class);

    @Autowired
    private JwtService jwtService;

    @GetMapping("/")
    public ResponseEntity<?> validateToken(@RequestHeader(value = "Authorization", required = false) String token){
        if(token.isEmpty()){
            LOGGER.error("Token not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not found");
        }
        if(!token.startsWith("Bearer ")){
            LOGGER.error("Bearer not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not found");
        }
        LOGGER.info("Bearer removed from the token");
        token = token.substring(7);
        try {
            LOGGER.info("Validating the token");
            Claims claims = jwtService.validateAndGetClaim(token);
            return ResponseEntity.ok(claims);
        } catch (Exception e) {
            LOGGER.error("Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }

    }
}
