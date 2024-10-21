package ar.edu.utn.frc.tup.lc.iv.helpers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetRoleDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostRoleDto;
import ar.edu.utn.frc.tup.lc.iv.entities.RoleEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserRoleEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RoleTestHelper {

    public static final RoleEntity ROLE_ENTITY = RoleEntity.builder()
            .id(1)
            .description("ADMIN")
            .createdDate(LocalDateTime.now())
            .createdUser(1)
            .lastUpdatedDate(LocalDateTime.now())
            .lastUpdatedUser(1)
            .userRoles(new ArrayList<>())
            .build();

    public static final RoleEntity ROLE_ENTITY_2 = RoleEntity.builder()
            .id(2)
            .description("OWNER")
            .createdDate(LocalDateTime.now())
            .createdUser(2)
            .lastUpdatedDate(LocalDateTime.now())
            .lastUpdatedUser(2)
            .userRoles(new ArrayList<>())
            .build();

    public static final RoleEntity ROLE_ENTITY_3 = RoleEntity.builder()
            .id(3)
            .description("USER")
            .createdDate(LocalDateTime.now())
            .createdUser(3)
            .lastUpdatedDate(LocalDateTime.now())
            .lastUpdatedUser(3)
            .userRoles(new ArrayList<>())
            .build();

    public static final UserRoleEntity USER_ROLE_ENTITY = UserRoleEntity.builder()
            .id(1)
            .role(ROLE_ENTITY)
            .user(new UserEntity())
            .createdDate(LocalDateTime.now())
            .createdUser(1)
            .lastUpdatedDate(LocalDateTime.now())
            .lastUpdatedUser(1)
            .build();

    public static final UserRoleEntity USER_ROLE_ENTITY_2 = UserRoleEntity.builder()
            .id(2)
            .role(ROLE_ENTITY_2)
            .user(new UserEntity())
            .createdDate(LocalDateTime.now())
            .createdUser(2)
            .lastUpdatedDate(LocalDateTime.now())
            .lastUpdatedUser(2)
            .build();

    public static final UserRoleEntity USER_ROLE_ENTITY_3 = UserRoleEntity.builder()
            .id(3)
            .role(ROLE_ENTITY_3)
            .user(new UserEntity())
            .createdDate(LocalDateTime.now())
            .createdUser(3)
            .lastUpdatedDate(LocalDateTime.now())
            .lastUpdatedUser(3)
            .build();

    public static final PostRoleDto POST_ROLE_DTO = PostRoleDto.builder()
            .description("NUEVO")
            .build();

}
