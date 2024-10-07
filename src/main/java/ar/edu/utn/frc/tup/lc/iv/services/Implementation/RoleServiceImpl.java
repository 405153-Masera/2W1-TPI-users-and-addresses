package ar.edu.utn.frc.tup.lc.iv.services.Implementation;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetRoleDto;
import ar.edu.utn.frc.tup.lc.iv.entities.RoleEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserRoleEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.RoleRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.UserRoleRepository;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private ModelMapper modelMapper;

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

    public List<GetRoleDto> getRolesByUser(int userId) {
        List<UserRoleEntity> userRoles = userRoleRepository.findByUserId(userId);
        List<GetRoleDto> roles = new ArrayList<>();

        for (UserRoleEntity userRole : userRoles) {

            RoleEntity role = roleRepository.findById(userRole.getRole().getId()).orElse(null);

            if (role != null) {
                // Mapear RoleEntity a GetRoleDto
                GetRoleDto getRoleDto = new GetRoleDto();
                getRoleDto.setId(role.getId());
                getRoleDto.setDescription(role.getDescription());

                roles.add(getRoleDto);
            }
        }
        return roles;
    }
}
