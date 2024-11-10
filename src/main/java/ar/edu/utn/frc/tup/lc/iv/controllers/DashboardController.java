package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.AgeDistributionResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.UserRoleCount;
import ar.edu.utn.frc.tup.lc.iv.services.dashboard.UserStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    /**
     * Servicio para manejar las estadísticas de los gráficos.
     */
    private final UserStatsService userStatsService;


    /**
     * Maneja las estadísticas de las manzanas.
     *
     * @return ResponseEntity con una lista de BlockData
     * que contiene datos de las manzanas.
     */
    @GetMapping("/users-by-role")
    public ResponseEntity<List<UserRoleCount>> getUserCountByRole() {
        List<UserRoleCount> stats = userStatsService.getUserCountByRole();
        return ResponseEntity.ok(stats);
    }

    /**
     * Retorna la distribución de edades de los usuarios.
     * @return ResponseEntity con información de la distribución de edades.
     */
    @GetMapping("/age-data")
    public ResponseEntity<AgeDistributionResponse> getAgeData() {
        try {
            AgeDistributionResponse response = userStatsService.getAgeData();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
