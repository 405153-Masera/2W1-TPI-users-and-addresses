package ar.edu.utn.frc.tup.lc.iv.services.Implementation;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetDniTypeDto;
import ar.edu.utn.frc.tup.lc.iv.entities.DniTypeEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.DniTypeRepository;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.DniTypeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz {@link DniTypeService}
 * contiene toda la lógica relacionada con los tipos de DNI.
 */
@Service
@RequiredArgsConstructor
public class DniTypeServiceImpl implements DniTypeService {

    /**
     * Repositorio para manejar los DNI types entities.
     */
    private final DniTypeRepository dniTypeRepository;

    /**
     * Obtiene una lista de todos los tipos de DNI.
     *
     * @return una lista de DTO que representan los tipos de DNI.
     */
    @Override
    public List<GetDniTypeDto> findAll() {
        List<DniTypeEntity> entities = dniTypeRepository.findAll();
        List<GetDniTypeDto> dniTypeDtos = new ArrayList<>();

        for (DniTypeEntity entity : entities) {
            GetDniTypeDto dto = new GetDniTypeDto();
            dto.setDescription(entity.getDescription());
            dniTypeDtos.add(dto);
        }
        return dniTypeDtos;
    }

    /**
     * Obtiene un tipo de DNI por su ID.
     *
     * @param id el ID del tipo de DNI que se desea obtener.
     * @throws EntityNotFoundException si no se encuentra el tipo de DNI.
     * @return el DTO que representa el tipo de DNI encontrado.
     */
    @Override
    public GetDniTypeDto findById(Integer id) {
        Optional<DniTypeEntity> optionalEntity = dniTypeRepository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new EntityNotFoundException("DNI type not found with ID: " + id);
        }
        DniTypeEntity entity = optionalEntity.get();
        return new GetDniTypeDto(entity.getDescription());
    }

    /**
     * Crea un nuevo tipo de DNI.
     *
     * @param dniTypeDto el DTO con la información del tipo de DNI a crear.
     * @return el DTO que representa el tipo de DNI creado.
     */
    @Override
    public GetDniTypeDto create(GetDniTypeDto dniTypeDto) {
        DniTypeEntity newEntity = new DniTypeEntity();
        newEntity.setDescription(dniTypeDto.getDescription());
        newEntity.setCreatedDatetime(LocalDateTime.now());
        newEntity.setLastUpdatedDatetime(LocalDateTime.now());
        newEntity.setCreatedUser(99);
        newEntity.setLastUpdatedUser(99);
        DniTypeEntity savedEntity = dniTypeRepository.save(newEntity);

        return new GetDniTypeDto(savedEntity.getDescription());
    }

    /**
     * Actualiza un tipo de DNI existente.
     *
     * @param id el ID del tipo de DNI a actualizar.
     * @param dniTypeDto el DTO con la información del tipo de DNI a actualizar.
     * @throws EntityNotFoundException si no se encuentra el tipo de DNI.
     * @return el DTO que representa el tipo de DNI actualizado.
     */
    @Override
    public GetDniTypeDto update(Integer id, GetDniTypeDto dniTypeDto) {
        Optional<DniTypeEntity> optionalEntity = dniTypeRepository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new EntityNotFoundException("DNI type not found with ID: " + id);
        }

        DniTypeEntity existingEntity = optionalEntity.get();
        existingEntity.setDescription(dniTypeDto.getDescription());
        existingEntity.setLastUpdatedDatetime(LocalDateTime.now());
        DniTypeEntity updatedEntity = dniTypeRepository.save(existingEntity);

        return new GetDniTypeDto(updatedEntity.getDescription());
    }
}
