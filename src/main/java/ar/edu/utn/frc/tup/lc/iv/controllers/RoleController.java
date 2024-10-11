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
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PostMapping()
    public ResponseEntity<GetRoleDto> createRole(@RequestBody PostRoleDto postRoleDto) { return ResponseEntity.ok(roleService.createRole(postRoleDto)); }
}
