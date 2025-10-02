package br.com.ednei.userjwt.service;

import br.com.ednei.userjwt.entity.User;
import br.com.ednei.userjwt.repository.UserRepository;
import br.com.ednei.userjwt.util.RandomString;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Service
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    @Transactional
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("This email already exists");
        } else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            String randomCode = RandomString.generateRandomString(64);
            user.setVerificationCode(randomCode);

            //TODO: quando a verificação por e-mail funcionar inicializar o usuario desabilitado
            user.setEnabled(true);

            User savedUser = userRepository.save(user);

            try {
                mailService.sendVerificationEmail(user);
            } catch (UnsupportedEncodingException | MessagingException | MailSendException e) {
                log.error(e.getMessage());
            }

            return savedUser;
        }
    }

    public boolean verify(String verificationCode) {

        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }
}
