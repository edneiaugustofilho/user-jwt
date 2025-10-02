package br.com.ednei.userjwt.dto;

import br.com.ednei.userjwt.entity.User;
import br.com.ednei.userjwt.entity.UserRole;

public record UserRequest(String name, String email, String password, UserRole role) {

    public User asEntitty() {
        return new User(name, email, password, role);
    }

}
