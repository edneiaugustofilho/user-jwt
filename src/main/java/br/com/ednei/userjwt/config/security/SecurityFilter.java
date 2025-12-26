package br.com.ednei.userjwt.config.security;

import br.com.ednei.userjwt.repository.UserRepository;
import br.com.ednei.userjwt.service.GenerateTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final GenerateTokenService generateTokenService;
    private final UserRepository userRepository;

    public SecurityFilter(GenerateTokenService generateTokenService,
                          UserRepository userRepository) {
        this.generateTokenService = generateTokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        var token = this.recoverToken(request);
        if (token != null) {
            var subject = generateTokenService.validateToken(token);
            UserDetails user = userRepository.findByEmail(subject);

            var authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return null;
        } else {
            return authHeader.replace("Bearer ", "");
        }

    }
}
