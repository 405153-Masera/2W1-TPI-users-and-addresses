package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetDniTypeDto;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.DniTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para manejar operaciones de Tipos de DNI.
 * Expone Endpoints para agregar, borrar, actualizar y obtener tipos de DNI.
 */
@RestController
@RequestMapping("/dniType")
@RequiredArgsConstructor
public class DniTypeController {

    /** Servicio para manejar la lógica de usuarios. */
    private final DniTypeService dniTypeService;

    /**
     * Obtiene todos los tipos de DNI.
     *
     * @return lista de tipos de DNI.
     */
    @GetMapping
    public List<GetDniTypeDto> getAllDniTypes() {
        return dniTypeService.findAll();
    }

    /**
     * Obtiene un tipo de DNI por su id.
     *
     * @param id identificador del tipo de DNI.
     * @return tipo de DNI.
     */
    @GetMapping("/{id}")
    public GetDniTypeDto getDniTypeById(@PathVariable Integer id) {
        return dniTypeService.findById(id);
    }

    /**
     * Crea un tipo de DNI.
     *
     * @param dniTypeDto tipo de DNI a crear.
     * @return tipo de DNI creado.
     */
    @PostMapping
    public GetDniTypeDto createDniType(@RequestBody GetDniTypeDto dniTypeDto) {
        return dniTypeService.create(dniTypeDto);
    }

    /**
     * Actualiza un tipo de DNI.
     *
     * @param id identificador del tipo de DNI.
     * @param dniTypeDto tipo de DNI a actualizar.
     * @return tipo de DNI actualizado.
     */
    @PutMapping("/{id}")
    public GetDniTypeDto updateDniType(@PathVariable Integer id, @RequestBody GetDniTypeDto dniTypeDto) {
        return dniTypeService.update(id, dniTypeDto);
    }

}
