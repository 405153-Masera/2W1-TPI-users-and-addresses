package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetRoleDto;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RoleControllerTest {

    @InjectMocks
    private RoleController roleController;

    @Mock
    private RoleService roleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRoles() {
        GetRoleDto role1 = new GetRoleDto(1, "Admin");
        GetRoleDto role2 = new GetRoleDto(2, "User");
        List<GetRoleDto> mockRoles = Arrays.asList(role1, role2);

        when(roleService.getAllRoles()).thenReturn(mockRoles);

        ResponseEntity<List<GetRoleDto>> response = roleController.getRoles();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockRoles, response.getBody());
    }
}
