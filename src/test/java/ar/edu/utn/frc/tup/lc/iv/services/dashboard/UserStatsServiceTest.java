package ar.edu.utn.frc.tup.lc.iv.services.dashboard;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.AgeDistribution;
import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.AgeDistributionResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.AgeStatics;
import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserStatsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserStatsService userStatsService;


    @Test
    void getAgeData() {
        LocalDate startDate = LocalDate.of(2000, 1, 1);
        LocalDate endDate = LocalDate.of(2020, 1, 1);
        List<UserEntity> users = Arrays.asList(
                new UserEntity(1, "John", "Doe", "johndoe", "password", "12345678", null, true, null, LocalDate.of(1990, 1, 1), null, null, null, null, null),
                new UserEntity(2, "Jane", "Doe", "janedoe", "password", "87654321", null, false, null, LocalDate.of(2005, 1, 1), null, null, null, null, null)
        );
        when(userRepository.findAll()).thenReturn(users);

        AgeDistributionResponse response = userStatsService.getAgeData(startDate, endDate);

        assertNotNull(response);

        assertEquals(7, response.getAgeDistribution().size());
    }

    @Test
    void calculateAgeDistribution() {
        List<UserEntity> users = Arrays.asList(
                new UserEntity(1, "John", "Doe", "johndoe", "password", "12345678", null, true, null, LocalDate.of(1990, 1, 1), null, null, null, null, null),
                new UserEntity(2, "Facundo", "Ruiz", "facuruiz", "password", "87654321", null, false, null, LocalDate.of(2005, 1, 1), null, null, null, null, null),
                new UserEntity(3, "Ulises", "Lara", "uliseslara", "password", "11223344", null, true, null, LocalDate.of(2010, 1, 1), null, null, null, null, null),
                new UserEntity(4, "Martin", "Masera", "martinmas", "password", "44332211", null, false, null, LocalDate.of(1980, 1, 1), null, null, null, null, null)
        );

        List<AgeDistribution> distribution = userStatsService.calculateAgeDistribution(users);

        assertNotNull(distribution);
        assertEquals(7, distribution.size());
    }

    @Test
    void calculateAgeStatics() {
        List<UserEntity> users = Arrays.asList(
                new UserEntity(1, "John", "Doe", "johndoe", "password", "12345678", null, true, null, LocalDate.of(1990, 1, 1), null, null, null, null, null),
                new UserEntity(2, "Facundo", "Ruiz", "facuruiz", "password", "87654321", null, false, null, LocalDate.of(2005, 1, 1), null, null, null, null, null),
                new UserEntity(3, "Ulises", "Lara", "uliseslara", "password", "11223344", null, true, null, LocalDate.of(2010, 1, 1), null, null, null, null, null),
                new UserEntity(4, "Martin", "Masera", "martinmas", "password", "44332211", null, false, null, LocalDate.of(1980, 1, 1), null, null, null, null, null)
        );

        AgeStatics ageStatics = userStatsService.calculateAgeStatics(users);

        assertNotNull(ageStatics);
        assertEquals(4, ageStatics.getTotalUsers());
        assertEquals(28.8, ageStatics.getAverageAge(), 0.1);
        assertEquals(45, ageStatics.getOldestAge());
        assertEquals(15, ageStatics.getYoungestAge());
    }

    @Test
    void getAgeRange() {
        assertEquals("0-10", userStatsService.getAgeRange(5));
        assertEquals("11-17", userStatsService.getAgeRange(15));
        assertEquals("18-25", userStatsService.getAgeRange(20));
        assertEquals("26-35", userStatsService.getAgeRange(30));
        assertEquals("36-50", userStatsService.getAgeRange(40));
        assertEquals("51-65", userStatsService.getAgeRange(60));
        assertEquals("65+", userStatsService.getAgeRange(70));
    }
}