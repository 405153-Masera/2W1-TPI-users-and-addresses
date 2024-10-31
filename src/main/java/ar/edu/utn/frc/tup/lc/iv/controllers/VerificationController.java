package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.services.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    @GetMapping("/username")
    public ResponseEntity<Map<String, Boolean>> isUsernameUnique(@RequestParam String username) {
        boolean isUnique = verificationService.isUsernameUnique(username);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isUnique", isUnique);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email")
    public ResponseEntity<Map<String, Boolean>> isEmailUnique(@RequestParam String email) {
        boolean isUnique = verificationService.isEmailUnique(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isUnique", isUnique);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dni")
    public ResponseEntity<Map<String, Boolean>> isDniUnique(@RequestParam String dni) {
        boolean isUnique = verificationService.isDniUnique(dni);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isUnique", isUnique);
        return ResponseEntity.ok(response);
    }
}
