package ar.edu.utn.frc.tup.lc.iv.services.Implementation;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetDniTypeDto;
import ar.edu.utn.frc.tup.lc.iv.entities.DniTypeEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.DniTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DniTypeServiceImplTest {

    @InjectMocks
    private DniTypeServiceImpl dniTypeService;

    @Mock
    private DniTypeRepository dniTypeRepository;

    @Test
    void findAll() {
        List<DniTypeEntity> dniTypes = new ArrayList<>();
        DniTypeEntity dniType1 = new DniTypeEntity(1, "DNI", LocalDateTime.now(), LocalDateTime.now(),
                1, 1);
        DniTypeEntity dniType2 = new DniTypeEntity(2, "Pasaporte", LocalDateTime.now(),
                LocalDateTime.now(), 1, 1);
        dniTypes.add(dniType1);
        dniTypes.add(dniType2);

        when(dniTypeRepository.findAll()).thenReturn(dniTypes);

        List<GetDniTypeDto> result = dniTypeService.findAll();

        assertEquals(2, result.size());
        assertEquals("DNI", result.get(0).getDescription());
        assertEquals("Pasaporte", result.get(1).getDescription());
        verify(dniTypeRepository).findAll();
    }

    @Test
    void findById() {
        DniTypeEntity dniType = new DniTypeEntity(1, "DNI", LocalDateTime.now(), LocalDateTime.now(), 1, 1);
        when(dniTypeRepository.findById(1)).thenReturn(Optional.of(dniType));

        GetDniTypeDto result = dniTypeService.findById(1);

        assertNotNull(result);
        assertEquals("DNI", result.getDescription());
        verify(dniTypeRepository).findById(1);
    }

    @Test
    void findByIdNotFound() {
        when(dniTypeRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dniTypeService.findById(99);
        });

        assertEquals("DNI type not found with ID: 99", exception.getMessage());
        verify(dniTypeRepository).findById(99);
    }

    @Test
    void create() {
        GetDniTypeDto dniTypeDto = new GetDniTypeDto("DNI");
        DniTypeEntity dniTypeEntity = new DniTypeEntity(1, "DNI", LocalDateTime.now(), LocalDateTime.now(), 1, 1);

        when(dniTypeRepository.save(any())).thenReturn(dniTypeEntity);

        GetDniTypeDto result = dniTypeService.create(dniTypeDto);

        assertEquals("DNI", result.getDescription());
        verify(dniTypeRepository).save(any());

    }

    @Test
    void update() {
        DniTypeEntity existingDniType = new DniTypeEntity(1, "DNI", LocalDateTime.now(), LocalDateTime.now(), 1, 1);
        when(dniTypeRepository.findById(1)).thenReturn(Optional.of(existingDniType));

        GetDniTypeDto updatedDniTypeDto = new GetDniTypeDto("Updated DNI");
        when(dniTypeRepository.save(any())).thenReturn(new DniTypeEntity(1, "Updated DNI", LocalDateTime.now(), LocalDateTime.now(), 1, 1));

        GetDniTypeDto result = dniTypeService.update(1, updatedDniTypeDto);

        assertEquals("Updated DNI", result.getDescription());
        verify(dniTypeRepository).findById(1);
        verify(dniTypeRepository).save(any());
    }

    @Test
    void updateNotFound() {
        when(dniTypeRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dniTypeService.update(99, new GetDniTypeDto("DNI"));
        });

        assertEquals("DNI type not found with ID: 99", exception.getMessage());
        verify(dniTypeRepository).findById(99);
    }

}