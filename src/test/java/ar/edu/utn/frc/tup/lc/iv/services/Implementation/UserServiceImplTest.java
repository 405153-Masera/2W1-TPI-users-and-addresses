package ar.edu.utn.frc.tup.lc.iv.services.Implementation;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetRoleDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.entities.RoleEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserRoleEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.UserRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.UserRoleRepository;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.GetContactDto;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.RestContact;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepositoryMock;

    @MockBean
    private RoleService roleServiceMock;

    @MockBean
    private RestContact restContactMock;

    @MockBean
    private UserRoleRepository userRoleRepositoryMock;

    @SpyBean
    private UserServiceImpl userServiceSpy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserByEmail_Success() {
        //Given
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("Paola");
        userEntity.setDni("45646545");
        userEntity.setDatebirth(LocalDate.now());
        userEntity.setActive(true);

        GetRoleDto roleDto = new GetRoleDto();
        roleDto.setId(12);
        roleDto.setDescription("descripcion");

        GetContactDto contactDto = new GetContactDto();
        contactDto.setType_contact(1);
        contactDto.setValue("hola@hola");

        List<GetContactDto> lcontacts = new ArrayList<>();
        lcontacts.add(contactDto);

        //When
        Mockito.when(restContactMock.getUserIdByEmail("hola@hola")).thenReturn(1);
        Mockito.when(userRepositoryMock.findById(1)).thenReturn(Optional.of(userEntity));
        Mockito.when(restContactMock.getContactById(1)).thenReturn(lcontacts);

        GetUserDto result = userServiceSpy.getUserByEmail("hola@hola");
        System.out.println(result);
        //Then
        assertNotNull(result);
        assertEquals(userEntity.getName(), result.getName());
        assertEquals(userEntity.getDatebirth(), result.getDatebirth());
        assertEquals(userEntity.getActive(), result.getActive());
        assertEquals("hola@hola", result.getEmail());
    }

    @Test
    void  getUserByEmail_EntityNotFound(){
        //When
        Mockito.when(restContactMock.getUserIdByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> {
            userServiceSpy.getUserByEmail("");
        });
    }

    @Test
    void deleteUser_Success() {
        //Given
        LocalDateTime oldDate = LocalDateTime.now().minus(Duration.ofDays(1));

        UserEntity userEntity = new UserEntity();
        userEntity.setActive(true);
        userEntity.setLastUpdatedDate(oldDate);
        //When
        Mockito.when(userRepositoryMock.findById(1)).thenReturn(Optional.of(userEntity));

        //Then
        userServiceSpy.deleteUser(1);

        Mockito.verify(userRepositoryMock, Mockito.times(1)).save(userEntity);
        assertEquals(false, userEntity.getActive());
        assertEquals(1, userEntity.getLastUpdatedUser());
        assertNotEquals(oldDate, userEntity.getLastUpdatedDate());
    }

    @Test
    void deleteUser_EntityNotFound() {
        //When
        Mockito.when(userRepositoryMock.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            userServiceSpy.deleteUser(1);
        });
    }

    @Test
    void getUsersByRole_Success() {
        //Given
        GetUserDto userDto = new GetUserDto();
        userDto.setId(10);
        userDto.setName("Pablo");
        userDto.setLastname("Ortega");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(10);
        userEntity.setName("Pablo");
        userEntity.setLastname("Ortega");

        List<GetRoleDto> lgetRoleDtos = new ArrayList<>();
        GetRoleDto roleDto = new GetRoleDto();
        roleDto.setId(1);
        roleDto.setDescription("Admin");

        lgetRoleDtos.add(roleDto);

        List<UserRoleEntity> luserRolesEntity = new ArrayList<>();
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUser(userEntity);
        luserRolesEntity.add(userRoleEntity);

        //When
        Mockito.when(userRoleRepositoryMock.findByRoleId(1)).thenReturn(Optional.of(luserRolesEntity));
        Mockito.when(userRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(userEntity));
        Mockito.when(roleServiceMock.getRolesByUser(Mockito.anyInt())).thenReturn(lgetRoleDtos);

        //Then
        List<GetUserDto> result = userServiceSpy.getUsersByRole(1);

        assertEquals(luserRolesEntity.size(), result.size());
        assertEquals(luserRolesEntity.get(0).getUser().getName(), result.get(0).getName());
        assertNotNull(result);
    }

    @Test
    void getUsersByRole_EntityNotFound() {
        //When
        Mockito.when(userRoleRepositoryMock.findByRoleId(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->{
            userServiceSpy.getUsersByRole(1);
        });
    }

    @Test
    void verifyLogin_True(){
        //Given
        String password = "123456";
        String dni = "45236220";

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(password);

        //When
        Mockito.when(userRepositoryMock.findByDni(dni)).thenReturn(userEntity);

        //Then
        boolean result = userServiceSpy.verifyLogin(password, dni);

        assertTrue(result);
    }

    @Test
    void verifyLogin_False(){
        //Given
        String password = "123456";
        String dni = "45236220";

        //When
        Mockito.when(userRepositoryMock.findByDni(dni)).thenReturn(null);

        //Then
        boolean result = userServiceSpy.verifyLogin(password, dni);

        assertFalse(result);
    }

    @Test
    void getUserByStatus_Success(){
        //Given
        List<UserEntity> userEntityList = new ArrayList<>();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(10);
        userEntity.setName("Pablo");
        userEntity.setDni("35654225");
        userEntity.setActive(true);

        userEntityList.add(userEntity);
        userEntityList.add(userEntity);

        //When
        Mockito.when(userRepositoryMock.findByActive(true)).thenReturn(Optional.of(userEntityList));

        //Then
        List<GetUserDto> result = userServiceSpy.getUsersByStatus(true);

        assertEquals(2, result.size());
        assertTrue(true, String.valueOf(result.get(0).getActive()));
        assertEquals(10, result.get(1).getId());
    }

    @Test
    void getUserByStatus_EntityNotFound(){
        //When
        Mockito.when(userRepositoryMock.findByActive(true)).thenReturn(Optional.empty());

        //Then
        assertThrows(EntityNotFoundException.class, () ->{
            userServiceSpy.getUsersByStatus(true);
        });
    }

    @Test
    void getUserById_Success(){
        //Given
        UserEntity userEntity = new UserEntity();
        userEntity.setId(10);
        userEntity.setName("Pablo");
        userEntity.setDni("35654225");
        userEntity.setActive(true);

        //When
        Mockito.when(userRepositoryMock.findById(10)).thenReturn(Optional.of(userEntity));

        //Then
        GetUserDto result = userServiceSpy.getUserById(10);

        assertNotNull(result);
        assertEquals(userEntity.getName(), result.getName());
        assertEquals(userEntity.getId(), result.getId());
    }

    @Test
    void getUserById_EntityNotFound(){
        //When
        Mockito.when(userRepositoryMock.findById(10)).thenReturn(Optional.empty());

        //Then
        assertThrows(EntityNotFoundException.class, () -> {
            userServiceSpy.getUserById(10);
        });
    }

    @Test
    void getAllUsers_Success(){
        //Given
        List<UserEntity> userEntityList = new ArrayList<>();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(10);
        userEntity.setName("Pablo");
        userEntity.setDni("35654225");
        userEntity.setActive(true);

        userEntityList.add(userEntity);

        //When
        Mockito.when(userRepositoryMock.findAll()).thenReturn(userEntityList);

        //Then
        List<GetUserDto> result = userServiceSpy.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userEntity.getName(), result.get(0).getName());
    }
}