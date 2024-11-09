package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.AgeRange;
import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.UserRoleCount;
import ar.edu.utn.frc.tup.lc.iv.services.dashboard.UserStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final UserStatsService userStatsService;


    @GetMapping("/users-by-role")
    public ResponseEntity<List<UserRoleCount>> getUserCountByRole() {
        List<UserRoleCount> stats = userStatsService.getUserCountByRole();
        return ResponseEntity.ok(stats);
    }

    // Endpoint para obtener la distribución de edades
    @GetMapping("/age-distribution")
    public ResponseEntity<List<AgeRange>> getAgeDistribution() {
        try {
            // Llamamos al servicio para obtener la distribución de edades
            List<AgeRange> distribution = userStatsService.getAgeDistribution();
            return ResponseEntity.ok(distribution);
        } catch (Exception e) {
            // En caso de error, respondemos con un 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Endpoint para obtener las estadísticas de edades
    @GetMapping("/age-statistics")
    public ResponseEntity<Map<String, Object>> getAgeStatistics() {
        try {
            // Llamamos al servicio para obtener las estadísticas de edad
            Map<String, Object> statistics = userStatsService.getAgeStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            // En caso de error, respondemos con un 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
