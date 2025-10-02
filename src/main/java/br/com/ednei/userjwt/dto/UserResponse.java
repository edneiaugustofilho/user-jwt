package br.com.ednei.userjwt.dto;

import br.com.ednei.userjwt.entity.User;

import java.util.UUID;

public record UserResponse(UUID id, String name, String email) {

    public static UserResponse fromEntity(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

}
