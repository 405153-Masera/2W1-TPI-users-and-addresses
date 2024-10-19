package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostLoginDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutUserDto;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * Controlador REST para manejar operaciones de Usuarios.
 *
 * Expone Enpoints para agregar , borrar , actualizar y obtener usuarios.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /** Servicio para manejar la lógica de usuarios. */
    @Autowired
    private UserService userService;

    /**
     * Guarda un nuevo usuario.
     *
     * @param postUserDto body del usuario a guardar.
     * @return el usuario creado.
     */
    @PostMapping("/post")
    public ResponseEntity<GetUserDto> createUser(@Valid @RequestBody PostUserDto postUserDto) {
        GetUserDto result = userService.createUser(postUserDto);

        //Si falla el service
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

        //Crea el usuario
        return ResponseEntity.ok(result);
    }

    /**
     * Obtiene un usuario determinado por id.
     *
     * @param userId id del usuario a buscar.
     * @return el usuario encontrado.
     */
    @GetMapping("get/{userId}")
    public ResponseEntity<GetUserDto> getUserById(@PathVariable Integer userId) {
        GetUserDto response = userService.getUserById(userId);

        if (response == null) {
          return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Retorna una lista de todos los usuarios.
     *
     * @return una lista de usuarios.
     */
    @GetMapping("/getall")
    public ResponseEntity<List<GetUserDto>> getUsers() {
        List<GetUserDto> result = userService.getAllUsers();

        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(result);
    }

    /**
     * Modifica un usuario buscado por id.
     *
     * @return el usuario modificado con campos actualizados.
     * @param userId identificador de un usuario.
     * @param putUserDto dto con inforamción necesaria para modificar un usuario.
     */
    @PutMapping("/put/{userId}")
    public ResponseEntity<GetUserDto> updateUser(@PathVariable Integer userId, @RequestBody PutUserDto putUserDto) {
        GetUserDto result = userService.updateUser(userId, putUserDto);

        //Si falla el service
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

        //Si hace la modificación
        return ResponseEntity.ok(result);
    }

    /**
     * Retorna una lista de todos los usuarios por estado.
     *
     * @param isActive estado de los usuarios a buscar.
     * @return una lista de usuarios.
     */
    @GetMapping("/getall/status")
    public ResponseEntity<List<GetUserDto>> getUsersByStatus(@PathVariable boolean isActive) {
        List<GetUserDto> result = userService.getUsersByStatus(isActive);

        //Si no trae la lista
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

        //Si trae la lista
        return ResponseEntity.ok(result);
    }

    /**
     * Retorna una lista de todos los usuarios por rol.
     *
     * @param roleId identificador del rol.
     * @return una lista de usuarios coincidentes con el rol.
     */
    @GetMapping("/getall/role")
    public ResponseEntity<List<GetUserDto>> getUsersByRole(@PathVariable Integer roleId) {
        List<GetUserDto> result = userService.getUsersByRole(roleId);

        //Si no trae la lista
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

        //Si trae la lista
        return ResponseEntity.ok(result);
    }

    /**
     * Baja logica de un usuario.
     *
     * @param userId identificador del usuario.
     * @param userIdUpdate identificador de la persona que realiza la baja logica.
     * @return una confirmacion de la baja lógica.
     */
    @DeleteMapping("/delete/{userId}/{userIdUpdate}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId, @PathVariable Integer userIdUpdate) {
        userService.deleteUser(userId, userIdUpdate);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene un usuario determinado por un email.
     *
     * @param email email del usuario a buscar.
     * @return el usuario encontrado.
     */
    @GetMapping("/getbyemail/{email}")
    public ResponseEntity<GetUserDto> getUserByEmail(@PathVariable String email) {
        GetUserDto result = userService.getUserByEmail(email);

        //Si no encuentra al usuario
        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        //Si encuentra al usuario
        return ResponseEntity.ok(result);
    }

    /**
     * Obtiene un usuario de rol "Owner" perteneciente a un lote específico.
     *
     * @param plotId del lote perteneciente al usuario a buscar.
     * @return el usuario encontrado.
     */
    @GetMapping("/get/owner/{plotId}")
    public ResponseEntity<GetUserDto> getUserByPlotIdAndOwnerRole(@PathVariable Integer plotId) {
        GetUserDto result = userService.getUserByPlotIdAndOwnerRole(plotId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    /**
     * Devuelve la confirmación de un inicio de sesión exitoso.
     *
     * @param postLoginDto credenciales del logueo.
     * @return respuesta de confirmación de logueo.
     */
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody PostLoginDto postLoginDto) {
        boolean result = userService.verifyLogin(postLoginDto);

        //Si no está registrado
        if (!result) {
            return ResponseEntity.notFound().build();
        }

        //Está registrado
        return ResponseEntity.ok(result);
    }

}
