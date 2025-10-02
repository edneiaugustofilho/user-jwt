package br.com.ednei.userjwt.service;

import br.com.ednei.userjwt.dto.AuthenticationRequest;
import br.com.ednei.userjwt.dto.AuthenticationResponse;
import br.com.ednei.userjwt.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final GenerateTokenService generateTokenService;
    private final AuthenticationManager authenticationManager;

    public LoginService(GenerateTokenService generateTokenService,
                        AuthenticationManager authenticationManager) {
        this.generateTokenService = generateTokenService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.email(), authenticationRequest.password()
        );
        var auth = authenticationManager.authenticate(usernamePasswordToken);
        var token = generateTokenService.generateToken((User) auth.getPrincipal());

        return new AuthenticationResponse(token);
    }
}
