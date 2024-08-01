package Microservice.Token_Service.Controller;

import Microservice.Token_Service.Service.JwtService;
import io.jsonwebtoken.Claims;
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

    @Autowired
    private JwtService jwtService;

    @GetMapping("/")
    public ResponseEntity<?> validateToken(@RequestHeader(value = "Authorization", required = false) String token){
        if(token.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not found");
        }
        if(!token.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not found");
        }
        token = token.substring(7);
        try {
            Claims claims = jwtService.validateAndGetClaim(token);
            return ResponseEntity.ok(claims);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }

    }
}
