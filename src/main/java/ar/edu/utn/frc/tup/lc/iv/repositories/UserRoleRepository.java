package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Conecta la aplicación con la base de datos para manejar la tabla intermedia de user y role.
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Integer> {

    /**
     * Busca por identificador de un usuario.
     *
     * @param userId el identificador de un usuario.
     * @return una lista de {@link UserRoleEntity}
     */
    List<UserRoleEntity> findByUserId(int userId);

    /**
     * Borra una relación pasándole un usuario por parámetro.
     *
     * @param user un UserEntity.
     */
    void deleteByUser(UserEntity user);

    /**
     * Busca una relación UserRoleEntity por medio de un usuario.
     *
     * @param user un UserEntity.
     * @return una lista de {@link UserRoleEntity}
     */
    List<UserRoleEntity> findByUser(UserEntity user);

    /**
     * Busca una relación UserRoleEntity por medio de un identificador de rol.
     *
     * @param roleId un identificador de rol.
     * @return una lista de {@link UserRoleEntity}
     */
    Optional<List<UserRoleEntity>> findByRoleId(Integer roleId);
}
