package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.repositories.UserRepository;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.RestContact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final UserRepository userRepository;

    private final RestContact restContact;

    public boolean isUsernameUnique(String username) {
        return !userRepository.existsByUsername(username);
    }

    public boolean isEmailUnique(String email) {
        List<String> emails = restContact.getAllEmails();
        return !emails.contains(email);
    }

    public boolean isDniUnique(String dni) {
        return !userRepository.existsByDni(dni);
    }
}
