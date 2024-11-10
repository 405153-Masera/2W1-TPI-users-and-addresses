package ar.edu.utn.frc.tup.lc.iv.services.dashboard;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.*;
import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio que contiene la lógica de negocio para obtener información
 * para su uso en los gráficos del dashboard.
 */
@Service
@RequiredArgsConstructor
public class UserStatsService {

    /**
     * Repositorio para manejar los datos de los usuarios.
     */
    private final UserRepository userRepository;

    public List<UserRoleCount> getUserCountByRole() {

        return userRepository.countUsersByRole();
    }


    public AgeDistributionResponse getAgeData() {
        List<UserEntity> users = userRepository.findAll();
        AgeDistributionResponse response = new AgeDistributionResponse();
        response.setAgeDistribution(calculateAgeDistribution(users));
        response.setStatics(calculateAgeStatics(users));
        response.setUserStatusDistribution(calculateStatusDistribution(users));

        return response;
    }

    public List<AgeDistribution> calculateAgeDistribution(List<UserEntity> users) {
        Map<String, MutablePair<Long, Long>> ageGroups = new HashMap<>();
        String[] ageRanges = {"0-10", "11-17", "18-25", "26-35", "36-50", "51-65", "65+"};

        for (String range : ageRanges) {
            ageGroups.put(range, MutablePair.of(0L, 0L)); //activos, inactivos
        }

        for (UserEntity user : users) {
            int age = calculateAge(user.getDatebirth());
            String range = getAgeRange(age);
            if (range != null) {
                MutablePair<Long, Long> counts = ageGroups.get(range);
                if (user.getActive()) {
                    counts.setLeft(counts.getLeft() + 1);
                } else {
                    counts.setRight(counts.getRight() + 1);
                }
            }
        }

        long totalUsers = users.size();
        List<AgeDistribution> distribution = new ArrayList<>();

        for (Map.Entry<String, MutablePair<Long, Long>> entry : ageGroups.entrySet()) {
            MutablePair<Long, Long> counts = entry.getValue();
            distribution.add(new AgeDistribution(
                    entry.getKey(),
                    counts.getLeft(),
                    counts.getRight(),
                    (counts.getLeft() * 100.0) / totalUsers,
                    (counts.getRight() * 100.0) / totalUsers
            ));
        }
        distribution.sort((d1, d2) -> {
            int start1 = Integer.parseInt(d1.getAgeRange().split("-")[0].replace("+", ""));
            int start2 = Integer.parseInt(d2.getAgeRange().split("-")[0].replace("+", ""));
            return Integer.compare(start1, start2);
        });

        return distribution;
    }

    public AgeStatics calculateAgeStatics(List<UserEntity> users) {
        long totalUsers = users.size();
        double averageAge = users.stream().mapToInt(user -> calculateAge(user.getDatebirth())).average().orElse(0);
        int minAge = users.stream().mapToInt(user -> calculateAge(user.getDatebirth())).min().orElse(0);
        int maxAge = users.stream().mapToInt(user -> calculateAge(user.getDatebirth())).max().orElse(0);

        return new AgeStatics(
                totalUsers,
                Math.round(averageAge * 10.0) / 10.0,
                minAge,
                maxAge
        );
    }

    private UserStatusDistribution calculateStatusDistribution(List<UserEntity> users) {
        long totalUsers = users.size();
        long activeUsers = users.stream().filter(UserEntity::getActive).count();
        long inactiveUsers = totalUsers - activeUsers;

        return new UserStatusDistribution(
                activeUsers,
                inactiveUsers,
                (activeUsers * 100.0) / totalUsers,
                (inactiveUsers * 100.0) / totalUsers
        );
    }

    /**
     * Obtiene el rango de edad al que pertenece una persona.
     * @param age la edad de la persona.
     *
     * @return un range de edad.
     */
    public String getAgeRange(int age) {
        if (age >= 0 && age <= 10) {
            return "0-10";
        }
        if (age >= 11 && age <= 17) {
            return "11-17";
        }
        if (age >= 18 && age <= 25) {
            return "18-25";
        }
        if (age >= 26 && age <= 35) {
            return "26-35";
        }
        if (age >= 36 && age <= 50) {
            return "36-50";
        }
        if (age >= 51 && age <= 65) {
            return "51-65";
        }
        if (age > 65) {
            return "65+";
        }
        return null;
    }

    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return 0;
        }
        Period period = Period.between(birthDate, LocalDate.now());
        return period.getYears();
    }
}
