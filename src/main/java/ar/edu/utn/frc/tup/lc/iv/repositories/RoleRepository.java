package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.RoleEntity;
import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Conecta la aplicación con la base de datos para manejar roles.
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    /**
     * Busca un rol por su descripción.
     *
     * @param description La descripción del rol.
     * @return un {@link RoleEntity}
     */
    RoleEntity findByDescription(String description);
}
