package br.com.ednei.userjwt.gateway.restful;

import br.com.ednei.userjwt.dto.UserRequest;
import br.com.ednei.userjwt.dto.UserResponse;
import br.com.ednei.userjwt.entity.User;
import br.com.ednei.userjwt.service.UserService;
import br.com.ednei.userjwt.service.UserVerifyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserResource {

    private final UserService userService;
    private final UserVerifyService userVerifyService;

    public UserResource(UserService userService, UserVerifyService userVerifyService) {
        this.userService = userService;
        this.userVerifyService = userVerifyService;
    }

    @GetMapping
    public ResponseEntity<UserResponse> findById(@RequestParam("id") UUID id) {
        User user = userService.findById(id);
        if (Objects.nonNull(user)) {
            return ResponseEntity.ok(UserResponse.fromEntity(user));
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest) {
        User user = userService.registerUser(userRequest.asEntitty());
        return ResponseEntity.ok(UserResponse.fromEntity(user));
    }

    @GetMapping("/verify/{id}/{code}")
    public ResponseEntity<?> verify(@PathVariable UUID id, @PathVariable String code) {
        try {
            userVerifyService.execute(UserVerifyService.Input.builder().id(id).code(code).build());
            return ResponseEntity.ok("verify_sucess");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
