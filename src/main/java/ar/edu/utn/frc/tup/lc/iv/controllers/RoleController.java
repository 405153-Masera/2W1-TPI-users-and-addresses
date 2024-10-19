package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetRoleDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostRoleDto;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para manejar operaciones de Roles.
 *
 * Expone Enpoints para agregar y obetener Roles.
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

    /** Servicio para manejar la lógica de roles. */
    @Autowired
    private RoleService roleService;

    /**
     * Retorna una lista de todos los roles.
     *
     * @return una lista de roles.
     */
    @GetMapping()
    public ResponseEntity<List<GetRoleDto>> getRoles() {
        List<GetRoleDto> result = roleService.getAllRoles();

        if(result == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Agrega un nuevo rol.
     *
     * @param postRoleDto El DTO con los datos del contacto.
     * @return una respuesta indicando que la creacion fue correcta o un badRequest
     * en caso de que no pueda crearse el rol.
     */
    @PostMapping()
    public ResponseEntity<GetRoleDto> createRole(@RequestBody PostRoleDto postRoleDto) {
        GetRoleDto result = roleService.createRole(postRoleDto);

        if(result == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }
}
