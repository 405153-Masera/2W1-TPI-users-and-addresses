package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutUserDto;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @PostMapping()
    public ResponseEntity<GetUserDto> createUser(@Valid @RequestBody PostUserDto postUserDto) {
        GetUserDto result = userService.createUser(postUserDto);

        //Si falla el service
        if(result == null){
            return ResponseEntity.badRequest().build();
        }

        //Crea el usuario
        return ResponseEntity.ok(result);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<GetUserDto> getUserById(@PathVariable Integer id) {
        GetUserDto response = userService.getUserById(id);

        //Si no encuentra el usuario
        if(response == null){
          return ResponseEntity.notFound().build();
        }

        //Si lo encuentra
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<GetUserDto>> getUsers() {
        List<GetUserDto> result = userService.getAllUsers();

        //Si no trae la lista
        if(result == null){
            return ResponseEntity.badRequest().build();
        }

        //Si trae la lista
        return ResponseEntity.ok(result);
    }

    @PutMapping("{id}")
    public ResponseEntity<GetUserDto> updateUser(@PathVariable Integer id,@RequestBody PutUserDto putUserDto) {
        GetUserDto result = userService.updateUser(id,putUserDto);

        //Si falla el service
        if(result == null){
            return ResponseEntity.badRequest().build();
        }

        //Si hace la modificación
        return ResponseEntity.ok(result);
    }

    @GetMapping("/status")
    public ResponseEntity<List<GetUserDto>> getUsersByStatus(@RequestParam boolean isActive) {
        List<GetUserDto> result = userService.getUsersByStatus(isActive);

        //Si no trae la lista
        if(result == null){
            return ResponseEntity.badRequest().build();
        }

        //Si trae la lista
        return ResponseEntity.ok(result);
    }

    @GetMapping("/role")
    public ResponseEntity<List<GetUserDto>> getUsersByRole(@RequestParam Integer roleId) {
        List<GetUserDto> result = userService.getUsersByRole(roleId);

        //Si no trae la lista
        if(result == null){
            return ResponseEntity.badRequest().build();
        }

        //Si trae la lista
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}/{userIdUpdate}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id, @PathVariable Integer userIdUpdate) {
        userService.deleteUser(id, userIdUpdate);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{email}")
    public ResponseEntity<GetUserDto> getUserByEmail(@PathVariable String email) {
        GetUserDto result = userService.getUserByEmail(email);

        //Si no encuentra al usuario
        if(result == null){
            return ResponseEntity.notFound().build();
        }

        //Si encuentra al usuario
        return ResponseEntity.ok(result);
    }

    @GetMapping("/plot/{plotId}")
    public ResponseEntity<GetUserDto> getUserByPlotIdAndOwnerRole(@PathVariable Integer plotId) {
        GetUserDto result = userService.getUserByPlotIdAndOwnerRole(plotId);
        if(result == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("all/plot/{plotId}")
    public ResponseEntity<List<GetUserDto>> getAllUsersByPlotId(@PathVariable Integer plotId) {
        List<GetUserDto> result = userService.getAllUsersByPlotId(plotId);
        if(result == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

}
