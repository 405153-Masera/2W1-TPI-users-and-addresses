package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.PlotUserEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Conecta la aplicación con la base de datos para manejar la tabla intermedia PlotUser.
 */
@Repository
public interface PlotUserRepository extends JpaRepository<PlotUserEntity, Integer> {

    /**
     * Busca por una lista de PlotUserEntity mediante un UserEntity pasado por parámetro.
     *
     * @param userEntity un UserEntity.
     * @return una lista de {@link PlotUserEntity}
     */
    List<PlotUserEntity> findByUser(UserEntity userEntity);
}
