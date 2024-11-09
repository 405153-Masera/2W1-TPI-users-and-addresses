package ar.edu.utn.frc.tup.lc.iv.services.Interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetOwnerUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.ChangePassword;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostLoginDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutUserDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interfaz que contiene la lógica de usuarios.
 */
@Service
public interface UserService {

    /**
     * Crea un usuario.
     *
     * @param postUserDto dto con información requerida para el alta de un usuario.
     * @return el usuario creado.
     */
    GetUserDto createUser(PostUserDto postUserDto);

    /**
     * Crea un usuario.
     *
     * @param postOwnerUserDto dto con información requerida para el alta de un usuario.
     * @return el usuario creado.
     */
    GetOwnerUserDto createOwnerUser(PostOwnerUserDto postOwnerUserDto);

    /**
     * Obtener todos los usuarios.
     *
     * @return una lista con todos los usuarios.
     */
    List<GetUserDto> getAllUsers();

    /**
     * Obtener un usuario por id.
     * @param userId id del usuario.
     * @return un usuario si existe.
     */
    GetUserDto getUserById(Integer userId);

    /**
     * Actualiza un usuario.
     *
     * @param userId el ID del usuario a actualizar.
     * @param putUserDto el dto con la información necesaria para actualizar un usuario.
     * @return el usuario actualizado.
     */
    GetUserDto updateUser(Integer userId, PutUserDto putUserDto);

    /**
     * Obtener todos los usuarios por un estado.
     *
     * @param active representa el estado del usuario.
     * @return una lista de usuarios con ese estado.
     */
    List<GetUserDto> getUsersByStatus(boolean active);

    /**
     * Realiza una baja lógica de un usuario.
     *
     * @param userId id del usuario a borrar.
     * @param userUpdateId id del usuario que realiza la acción.
     */
    void deleteUser(Integer userId, Integer userUpdateId);

    /**
     * Obtener un usuario por email.
     *
     * @param email correo electrónico de un usuario
     * @return un usuario si existe.
     */
    GetUserDto getUserByEmail(String email);

    /**
     * Obtener un usuario de rol "Owner" con un plotId especifíco.
     *
     * @param plotId identificador de un lote.
     * @return un usuario si existe.
     */
    GetUserDto getUserByPlotIdAndOwnerRole(Integer plotId);

    /**
     * Obtener un usuario por un rol.
     *
     * @param roleId identificador de un rol.
     * @return una lista de usuarios coincidentes con el rol.
     */
    List<GetUserDto> getUsersByRole(Integer roleId);

    /**
     * Verifica un inicio de sesión.
     *
     * @param postLoginDto dto con información requerida para el metodo.
     * @return un booleano de confirmación.
     */
    GetUserDto verifyLogin(PostLoginDto postLoginDto);

    /**
     * Obtener una lista de usuarios activos por lote.
     *
     * @param plotId identificador de un lote.
     * @return lista de GetUserDto.
     */
    List<GetUserDto> getAllUsersByPlotId(Integer plotId);

    /**
     * Busca todos los usuarios asociados a un lote,
     * incluido el propietario.
     *
     * @return la lista de usuarios por lote.
     */
    List<GetPlotUserDto> getAllPlotUsers();

    /**
     * Busca todos los usuarios (familiares) asociados a un propietario,
     * incluido el propietario.
     *
     * @param ownerId id del propietario.
     * @return la lista de usuarios por propietario.
     */
    List<GetUserDto> getUsersByOwner(Integer ownerId);

    List<GetUserDto> getUsersByOwnerWithoutOwner(Integer ownerId);

    /**
     * Cambia la contraseña de un usuario.
     *
     * @param changePassword dto con información requerida para el cambio de contraseña.
     */
    void changePassword(ChangePassword changePassword);

    GetUserDto updateTelegramId(String dni, Integer telegramId);
    void passwordRecovery(String userEmail);
}
