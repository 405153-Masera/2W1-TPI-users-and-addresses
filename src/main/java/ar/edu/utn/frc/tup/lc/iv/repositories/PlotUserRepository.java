package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.PlotUserEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Conecta la aplicación con la base de datos para manejar la tabla intermedia PlotUser.
 */
@Repository
public interface PlotUserRepository extends JpaRepository<PlotUserEntity, Integer> {

    /**
     * Busca por un PlotUserEntity mediante un UserEntity pasado por parametro
     *
     * @param userEntity un UserEntity.
     * @return un {@link PlotUserEntity}
     */
    PlotUserEntity findByUser(UserEntity userEntity);
}
