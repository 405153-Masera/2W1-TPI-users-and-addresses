package ar.edu.utn.frc.tup.lc.iv.services.Interfaces;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetRoleDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostRoleDto;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Interfaz que contiene la lógica de roles.
 */
@Service
public interface RoleService {

    /**
     * Obtener todos los roles.
     *
     * @return una lista con todos los roles.
     */
    List<GetRoleDto> getAllRoles();

    /**
     * Obtener todos los roles por usuario.
     *
     * @param userId identificador de un usuario.
     * @return una lista con todos los roles coincidentes al usuario.
     */
    List<GetRoleDto> getRolesByUser(int userId);

    /**
     * Crea un rol.
     *
     * @param postRoleDto dto con información requerida para el alta de un rol.
     * @return el rol creado.
     */
    GetRoleDto createRole(PostRoleDto postRoleDto);
}
