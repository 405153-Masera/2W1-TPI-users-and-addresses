package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetOwnerUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutUserDto;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.access.AccessPost;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.access.AccessPut;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.access.RestAccess;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * Controlador REST para manejar operaciones de Usuarios.
 * Expone Endpoints para agregar, borrar, actualizar y obtener usuarios.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    /** Servicio para manejar la lógica de usuarios. */
    private final UserService userService;

    /** Servicio para manejar el restTemplate de accesos. */
    private final RestAccess restAccess;


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
     * Guarda un nuevo usuario.
     *
     * @param postUserDto body del usuario a guardar.
     * @return el usuario creado.
     */
    @PostMapping("/post/owner")
    public ResponseEntity<GetOwnerUserDto> createOwnerUser(@Valid @RequestBody PostOwnerUserDto postUserDto) {
        GetOwnerUserDto result = userService.createOwnerUser(postUserDto);

        //Si falla el service
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

        //Crea el usuario
        return ResponseEntity.ok(result);
    }

    /**
     * Obtiene un usuario determinado por el ID.
     *
     * @param userId id del usuario a buscar.
     * @return el usuario encontrado.
     */
    @GetMapping("getById/{userId}")
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
     * Modifica un usuario buscado por el ID.
     *
     * @return el usuario modificado con campos actualizados.
     * @param userId identificador de un usuario.
     * @param putUserDto dto con información necesaria para modificar un usuario.
     */
    @PutMapping("/put/{userId}")
    public ResponseEntity<GetUserDto> updateUser(@PathVariable Integer userId, @RequestBody PutUserDto putUserDto) {
        GetUserDto result = userService.updateUser(userId, putUserDto);
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Retorna una lista de todos los usuarios por estado.
     *
     * @param isActive estado de los usuarios a buscar.
     * @return una lista de usuarios.
     */
    @GetMapping("/getall/status/{isActive}")
    public ResponseEntity<List<GetUserDto>> getUsersByStatus(@PathVariable boolean isActive) {
        List<GetUserDto> result = userService.getUsersByStatus(isActive);

        // Si no trae la lista
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

        // Si trae la lista
        return ResponseEntity.ok(result);
    }


    /**
     * Retorna una lista de todos los usuarios por rol.
     *
     * @param roleId identificador del rol.
     * @return una lista de usuarios coincidentes con el rol.
     */
    @GetMapping("/getall/role/{roleId}")
    public ResponseEntity<List<GetUserDto>> getUsersByRole(@PathVariable Integer roleId) {
        List<GetUserDto> result = userService.getUsersByRole(roleId);
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }


    /**
     * Baja lógica de un usuario.
     *
     * @param userId identificador del usuario.
     * @param userIdUpdate identificador de la persona que realiza la baja lógica.
     * @return una confirmación de la baja lógica.
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
    @GetMapping("/getByEmail/{email}")
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
     * Obtiene una lista de usuarios de rol "Owner" perteneciente a un lote específico.
     *
     * @param plotId del lote perteneciente al usuario a buscar.
     * @return lista de usuarios encontrado.
     */
    @GetMapping("getall/{plotId}")
    public ResponseEntity<List<GetUserDto>> getAllUsersByPlotId(@PathVariable Integer plotId) {
        List<GetUserDto> result = userService.getAllUsersByPlotId(plotId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    /**
     * Permite registrar un nuevo acceso para el usuario asociado a un lote específico.
     *
     * @param accessPost Objeto que contiene los datos necesarios para registrar el acceso.
     * @return `ResponseEntity<Void>` con estado 204 (sin contenido) si la operación es exitosa.
     */
    @PostMapping("/access")
    public ResponseEntity<Void> postAccess(@RequestBody AccessPost accessPost) {
        List<AccessPost> lst = List.of(accessPost);
        restAccess.postAccess(lst);
        return ResponseEntity.noContent().build();
    }

    /**
     * Permite eliminar el acceso asociado a un documento específico.
     *
     * @param accessPut Objeto que contiene el documento del acceso que se desea eliminar.
     * @return `ResponseEntity<Void>` con estado 204 (sin contenido) si la operación es exitosa.
     */
    @PutMapping("/access/delete")
    public ResponseEntity<Void> putAccess(@RequestBody AccessPut accessPut) {
        restAccess.deleteAccess(accessPut.getDocument());
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene una lista de usuarios asociados a un propietario específico.
     *
     * @param ownerId id del propietario para el cual se desean obtener los usuarios.
     * @return `ResponseEntity<List<GetUserDto>>` con estado 204 si no se encuentran usuarios, o estado 200 con la lista de usuarios.
     */
    @GetMapping("/byOwner/{ownerId}")
    public ResponseEntity<List<GetUserDto>> getUsersByOwner(@PathVariable Integer ownerId) {
        List<GetUserDto> users = userService.getUsersByOwner(ownerId);
        // Verifica si la lista está vacía
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve 204 si no hay usuarios
        } else {
            return ResponseEntity.ok(users); // Devuelve 200 con los usuarios
        }
    }

    @GetMapping("/byOwner/{ownerId}/WithoutTheOwner")
    public ResponseEntity<List<GetUserDto>> getUsersByOwnerV2(@PathVariable Integer ownerId) {
        List<GetUserDto> users = userService.getUsersByOwnerWithoutOwner(ownerId);
        // Verifica si la lista está vacía
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve 204 si no hay usuarios
        } else {
            return ResponseEntity.ok(users); // Devuelve 200 con los usuarios
        }
    }

    @PutMapping("/recoveryPassword/{email}")
    public ResponseEntity<Void> recoveryPassword(@PathVariable String email){
        userService.passwordRecovery(email);
        return ResponseEntity.ok().build();
    }
}
