package ar.edu.utn.frc.tup.lc.iv.services.dashboard;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.UserRoleCount;
import ar.edu.utn.frc.tup.lc.iv.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserStatsService {

    private final UserRepository userRepository;

    public List<UserRoleCount> getUserCountByRole() {
        return userRepository.countUsersByRole();
    }
}
