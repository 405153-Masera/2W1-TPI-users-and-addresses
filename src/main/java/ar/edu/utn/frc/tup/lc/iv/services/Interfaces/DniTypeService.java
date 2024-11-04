package ar.edu.utn.frc.tup.lc.iv.services.Interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetDniTypeDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interfaz que contiene la lógica de los tipos de DNI.
 */
@Service
public interface DniTypeService {

    /**
     * Obtiene todos los tipos de DNI.
     *
     * @return una lista con todos los tipos de DNI.
     */
    List<GetDniTypeDto> findAll();

    /**
     * Obtiene un tipo de DNI por ID.
     *
     * @param id es el ID del tipo de DNI a buscar.
     * @return un tipo de DNI si existe.
     */
    GetDniTypeDto findById(Integer id);

    /**
     * Crea un tipo de DNI.
     *
     * @param dniTypeDto el dto con la información necesaria para crear un tipo de DNI.
     * @return el tipo de DNI creado.
     */
    GetDniTypeDto create(GetDniTypeDto dniTypeDto);

    /**
     * Actualiza un tipo de DNI.
     *
     * @param id el ID del tipo de DNI a actualizar.
     * @param dniTypeDto el dto con la información necesaria para actualizar un tipo de DNI.
     * @return el tipo de DNI actualizado.
     */
    GetDniTypeDto update(Integer id, GetDniTypeDto dniTypeDto);
}
