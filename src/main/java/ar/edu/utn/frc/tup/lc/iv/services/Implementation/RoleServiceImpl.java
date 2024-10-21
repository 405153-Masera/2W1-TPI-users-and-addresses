package ar.edu.utn.frc.tup.lc.iv.services.Implementation;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetRoleDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostRoleDto;
import ar.edu.utn.frc.tup.lc.iv.entities.RoleEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserRoleEntity;
import ar.edu.utn.frc.tup.lc.iv.exceptions.RoleUserException;
import ar.edu.utn.frc.tup.lc.iv.repositories.RoleRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.UserRoleRepository;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.RoleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementacion de {@link RoleService},
 * contiene toda la lógica relacionada con roles.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    /**
     * Repositorio para manejar Role entities.
     */
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Repositorio para manejar UserRole entities.
     */
    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * ModelMapper para convertir entre entidades y dtos.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtener todos los roles.
     *
     * @return una lista con todos los roles.
     */
    @Override
    public List<GetRoleDto> getAllRoles() {
        List<RoleEntity> roleEntities = roleRepository.findAll();

        return roleEntities.stream()
                .map(roleEntity -> {
                    GetRoleDto roleDto = modelMapper.map(roleEntity, GetRoleDto.class);
                    roleDto.setDescription(roleEntity.getDescription());
                    return roleDto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Obtener todos los roles por usuario.
     *
     * @param userId identificador de un usuario.
     * @return una lista con todos los roles coincidentes al usuario.
     * @throws RoleUserException si el usuario no tiene roles asignados o no existe.
     */
    @Override
    public List<GetRoleDto> getRolesByUser(int userId) {
        List<UserRoleEntity> userRoles = userRoleRepository.findByUserId(userId);
        List<GetRoleDto> roles = new ArrayList<>();
        GetRoleDto getRoleDto = new GetRoleDto();
        if (userRoles.isEmpty()) {
            throw new RoleUserException("The user has no assigned roles or does not exist", HttpStatus.NOT_FOUND);
        }
        RoleEntity role = new RoleEntity();
        for (UserRoleEntity userRole : userRoles) {

            role = roleRepository.findById(userRole.getRole().getId()).orElse(null);

            if (role != null) {
                getRoleDto.setId(role.getId());
                getRoleDto.setDescription(role.getDescription());

                roles.add(getRoleDto);
            }
        }
        return roles;
    }

    /**
     * Crea un rol.
     *
     * @param postRoleDto dto con información requerida para el alta de un rol.
     * @return el rol creado.
     */
    @Override
    public GetRoleDto createRole(PostRoleDto postRoleDto) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setDescription(postRoleDto.getDescription());
        roleEntity.setCreatedUser(postRoleDto.getUserUpdateId());
        roleEntity.setCreatedDate(LocalDateTime.now());
        roleEntity.setLastUpdatedDate(LocalDateTime.now());
        roleEntity.setLastUpdatedUser(postRoleDto.getUserUpdateId());

        roleEntity = roleRepository.save(roleEntity);

        GetRoleDto getRoleDto = new GetRoleDto();

        modelMapper.map(roleEntity, getRoleDto);

        return getRoleDto;
    }
}
