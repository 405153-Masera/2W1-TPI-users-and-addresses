package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Conecta la aplicación con la base de datos para manejar usuarios.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username el nombre de usuario.
     * @return un {@link UserEntity}
     */
    UserEntity findByUsername(String username);

    /**
     * Busca un usuario por su id.
     *
     * @param userId el identificador de un usuario.
     * @return un {@link UserEntity}
     */
    @Override
    Optional<UserEntity> findById(Integer userId);

    /**
     * Busca una lista de usuarios por estado.
     *
     * @param isActive el estado a buscar.
     * @return una lista de {@link UserEntity}
     */
    Optional<List<UserEntity>> findByActive(boolean isActive);

    /**
     * Busca un usuario que tenga rol "Owner" y que coincida con el plotId
     * pasado por parametro.
     *
     * @param plotId el identificador de un lote.
     * @return un {@link UserEntity}
     */
    @Query("SELECT u FROM UserEntity u "
            + "JOIN UserRoleEntity ur ON ur.user.id = u.id "
            + "JOIN RoleEntity r ON ur.role.id = r.id "
            + "JOIN PlotUserEntity pu ON pu.user.id = u.id "
            + "WHERE r.description = 'Propietario' "
            + "AND pu.plotId = :plotId "
            + "AND u.active = true")
    Optional<UserEntity> findUserByPlotIdAndOwnerRole(@Param("plotId") Integer plotId);

    /**
     * Busca una lista de usuarios activos por lote.
     *
     * @param plotId identificador de lote.
     * @return una lista de {@link UserEntity}
     */
    @Query("SELECT u FROM UserEntity u "
            + "JOIN UserRoleEntity ur ON ur.user.id = u.id "
            + "JOIN RoleEntity r ON ur.role.id = r.id "
            + "JOIN PlotUserEntity pu ON pu.user.id = u.id "
            + "AND u.active = true")
    Optional<List<UserEntity>> findUsersByPlotId(@Param("plotId") Integer plotId);

    /**
     * Busca una lista de usuarios activos.
     *
     * @return una lista de {@link UserEntity}
     */
    @Query("SELECT o FROM UserEntity o WHERE o.active = true")
    List<UserEntity> findAllActives();

    /**
     * Busca un usuario por su dni.
     *
     * @param dni numero de dni.
     * @return un {@link UserEntity}
     */
    UserEntity findByDni(String dni);
}
