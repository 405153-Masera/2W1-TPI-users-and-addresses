package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity,Integer> {
    List<UserRoleEntity> findByUserId(int userId);

    void deleteByUser(UserEntity user);

    List<UserRoleEntity> findByUser(UserEntity userSaved);

    Optional<List<UserRoleEntity>> findByRoleId(Integer roleId);
}
