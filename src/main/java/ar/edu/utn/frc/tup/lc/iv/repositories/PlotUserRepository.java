package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.PlotUserEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlotUserRepository extends JpaRepository<PlotUserEntity, Integer> {
    PlotUserEntity findByUser(UserEntity userEntity);
}
