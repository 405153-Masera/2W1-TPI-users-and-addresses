package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.UserRoleCount;
import ar.edu.utn.frc.tup.lc.iv.services.dashboard.UserStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
