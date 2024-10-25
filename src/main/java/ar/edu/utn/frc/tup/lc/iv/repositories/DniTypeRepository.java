package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.DniTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Conecta la aplicación con la base de datos para manejar la tabla DniType.
 */
@Repository
public interface DniTypeRepository extends JpaRepository<DniTypeEntity,Integer> {
}
