package br.com.ednei.userjwt.restful;

import br.com.ednei.userjwt.dto.AuthenticationRequest;
import br.com.ednei.userjwt.dto.AuthenticationResponse;
import br.com.ednei.userjwt.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginResource {

    private final LoginService loginService;

    public LoginResource(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    private ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(loginService.authenticate(authenticationRequest));
    }

}
