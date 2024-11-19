package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.AgeDistributionResponse;
import ar.edu.utn.frc.tup.lc.iv.services.dashboard.UserStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST para manejar las estadísticas de los gráficos.
 * Expone Endpoints para obtener estadísticas de los usuarios.
 */
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    /**
     * Servicio para manejar las estadísticas de los gráficos.
     */
    private final UserStatsService userStatsService;

    /**
     * Retorna la distribución de edades de los usuarios.
     * @param startDate Fecha de inicio del rango de fechas.
     * @param endDate Fecha de fin del rango de fechas.
     * @return ResponseEntity con información de la distribución de edades.
     */
    @GetMapping("/age-data")
    public ResponseEntity<AgeDistributionResponse> getAgeData(@RequestParam(required = false) LocalDate startDate,
                                                             @RequestParam(required = false) LocalDate endDate) {
        try {
            AgeDistributionResponse response = userStatsService.getAgeData(startDate, endDate);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
