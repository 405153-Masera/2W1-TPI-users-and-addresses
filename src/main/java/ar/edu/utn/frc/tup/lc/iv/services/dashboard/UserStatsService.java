package ar.edu.utn.frc.tup.lc.iv.services.dashboard;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.AgeRange;
import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.UserRoleCount;
import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserStatsService {

    private final UserRepository userRepository;

    public List<UserRoleCount> getUserCountByRole() {

        return userRepository.countUsersByRole();
    }

    public List<AgeRange> getAgeDistribution() {
        List<UserEntity> users = userRepository.findAll();

        Map<String, Long> ageGroups = new HashMap<>();
        for (UserEntity user : users) {
            int age = calculateAge(user.getDatebirth());

            // Definir los rangos de edades
            int ageGroupStart = (age / 10) * 10; // Ejemplo: si la edad es 25, el grupo será 20-29
            int ageGroupEnd = ageGroupStart + 9;

            String ageRange = ageGroupStart + "-" + ageGroupEnd;
            ageGroups.put(ageRange, ageGroups.getOrDefault(ageRange, 0L) + 1);
        }

        // Calculamos el total de usuarios
        long totalUsers = users.size();

        // Creamos la lista de AgeRangeDTO con la distribución de edades
        List<AgeRange> distribution = new ArrayList<>();
        for (Map.Entry<String, Long> entry : ageGroups.entrySet()) {
            String ageRange = entry.getKey();
            long count = entry.getValue();
            double percentage = (count * 100.0) / totalUsers;

            distribution.add(new AgeRange(ageRange, count, percentage));
        }

        return distribution;
    }

    public Map<String, Object> getAgeStatistics() {
        // Traemos todos los usuarios
        List<UserEntity> users = userRepository.findAll();

        // Calculamos las estadísticas
        long totalUsers = users.size();
        double averageAge = users.stream().mapToInt(user -> calculateAge(user.getDatebirth())).average().orElse(0);
        int minAge = users.stream().mapToInt(user -> calculateAge(user.getDatebirth())).min().orElse(0);
        int maxAge = users.stream().mapToInt(user -> calculateAge(user.getDatebirth())).max().orElse(0);

        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalUsers", totalUsers);
        statistics.put("averageAge", Math.round(averageAge * 10.0) / 10.0);
        statistics.put("youngestAge", minAge);
        statistics.put("oldestAge", maxAge);

        return statistics;
    }

    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return 0; // Si no hay fecha de nacimiento, consideramos edad 0 (aunque lo ideal sería filtrar estos usuarios antes)
        }
        // Calcular la edad a partir de la fecha de nacimiento
        Period period = Period.between(birthDate, LocalDate.now());
        return period.getYears();
    }
}
