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

    /**
     * Busca por una lista de PlotUserEntity mediante un ID de usuario pasado por parámetro.
     *
     * @param userId un ID de usuario.
     * @return una lista de {@link PlotUserEntity}
     */
    List<PlotUserEntity> findByUserId(int userId);

    /**
     * Elimina un PlotOwnerEntity por el id de un propietario y el id de un lote.
     *
     * @param ownerId el id de un propietario.
     * @param plotId el id de un lote.
     */
    void deleteByUserIdAndPlotId(int ownerId, int plotId);

    /**
     * Busca un PlotUserEntity por el id de un lote.
     *
     * @param plotId el id de un lote.
     * @return una lista de {@link PlotUserEntity}
     */
    PlotUserEntity findByPlotId(int plotId);
}
