package ar.edu.utn.frc.tup.lc.iv.controllers;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetRoleDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostRoleDto;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping()
    public ResponseEntity<List<GetRoleDto>> getRoles() {
        List<GetRoleDto> result = roleService.getAllRoles();

        //Si falla el service
        if(result == null) {
            return ResponseEntity.internalServerError().build();
        }

        //Si trae la lista vacia
        else if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        //Trae la lista
        return ResponseEntity.ok(result);
    }

    @PostMapping()
    public ResponseEntity<GetRoleDto> createRole(@RequestBody PostRoleDto postRoleDto) {
        GetRoleDto result = roleService.createRole(postRoleDto);

        //Si no puede crear el rol
        if(result == null){
            return ResponseEntity.badRequest().build();
        }

        //Crea el rol
        return ResponseEntity.ok(result);
    }
}
