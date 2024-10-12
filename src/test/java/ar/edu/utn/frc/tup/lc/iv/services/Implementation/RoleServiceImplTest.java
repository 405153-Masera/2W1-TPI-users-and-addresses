package ar.edu.utn.frc.tup.lc.iv.services.Implementation;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetRoleDto;
import ar.edu.utn.frc.tup.lc.iv.entities.RoleEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserRoleEntity;
import ar.edu.utn.frc.tup.lc.iv.exceptions.RoleUserException;
import ar.edu.utn.frc.tup.lc.iv.helpers.RoleTestHelper;
import ar.edu.utn.frc.tup.lc.iv.repositories.RoleRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void getAllRoles() {
        List<RoleEntity> rolesEntity = new ArrayList<>();

        rolesEntity.add(RoleTestHelper.ROLE_ENTITY);
        rolesEntity.add(RoleTestHelper.ROLE_ENTITY_2);
        rolesEntity.add(RoleTestHelper.ROLE_ENTITY_3);

        when(roleRepository.findAll()).thenReturn(rolesEntity);

        List<GetRoleDto> result = this.roleService.getAllRoles();

        assertEquals(3, result.size());
        assertEquals("USER", result.get(2).getDescription());
        assertEquals(2, result.get(1).getId());
        verify(roleRepository).findAll();
    }

    @Test
    void getRolesByUser() {
        List<UserRoleEntity> userRoleEntities = new ArrayList<>();
        userRoleEntities.add(RoleTestHelper.USER_ROLE_ENTITY);
        userRoleEntities.add(RoleTestHelper.USER_ROLE_ENTITY_2);
        userRoleEntities.add(RoleTestHelper.USER_ROLE_ENTITY_3);

        when(userRoleRepository.findByUserId(9)).thenReturn(userRoleEntities);
        when(roleRepository.findById(1)).thenReturn(Optional.ofNullable(RoleTestHelper.ROLE_ENTITY));
        when(roleRepository.findById(2)).thenReturn(Optional.ofNullable(RoleTestHelper.ROLE_ENTITY_2));
        when(roleRepository.findById(3)).thenReturn(Optional.ofNullable(RoleTestHelper.ROLE_ENTITY_3));

        List<GetRoleDto> result = this.roleService.getRolesByUser(9);

        assertEquals(3, result.size());
        assertEquals("ADMIN", result.get(0).getDescription());
        assertEquals("OWNER", result.get(1).getDescription());
        assertEquals("USER", result.get(2).getDescription());

        verify(userRoleRepository).findByUserId(9);
        verify(roleRepository).findById(1);
        verify(roleRepository).findById(2);
        verify(roleRepository).findById(3);
    }

    @Test
    void getRolesByUserWithException(){
        List<UserRoleEntity> listaVacia = new ArrayList<>();
        when(userRoleRepository.findByUserId(879)).thenReturn(listaVacia);

        RoleUserException exception = assertThrows(RoleUserException.class, () -> {
            roleService.getRolesByUser(879);
        });

        assertEquals("The user has no assigned roles or does not exist", exception.getMessage());
        verify(userRoleRepository).findByUserId(879);

        // Se verifica que el roleRepository nunca se llamó
        verifyNoInteractions(roleRepository);
    }

    @Test
    void createRole() {
        when(roleRepository.save(any())).thenReturn(RoleTestHelper.ROLE_ENTITY_3);

        GetRoleDto result = this.roleService.createRole(RoleTestHelper.POST_ROLE_DTO);

        assertEquals(3, result.getId());
        verify(roleRepository).save(any());
    }
}