package ar.edu.utn.frc.tup.lc.iv.services.Implementation;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetRoleDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutUserDto;
import ar.edu.utn.frc.tup.lc.iv.entities.RoleEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserRoleEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.RoleRepository;
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
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static javax.management.Query.eq;
import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepositoryMock;

    @MockBean
    private RoleRepository roleRepositoryMock;

    @MockBean
    private RoleService roleServiceMock;

    @MockBean
    private RestContact restContactMock;

    @MockBean
    private UserRoleRepository userRoleRepositoryMock;

    @SpyBean
    private UserServiceImpl userServiceSpy;

    @Mock
    private ModelMapper modelMapperMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_Success(){
        //Given
        PostUserDto postUserDto = new PostUserDto();
        postUserDto.setName("Juan");
        postUserDto.setLastname("Perez");
        postUserDto.setUsername("JuanPa");
        postUserDto.setPassword("123456");
        postUserDto.setEmail("juanpa@gmail.com");
        postUserDto.setPhone_number("12345678");
        postUserDto.setDni("12345678");
        postUserDto.setActive(true);
        postUserDto.setAvatar_url("url");
        postUserDto.setDatebirth(LocalDate.now());
        postUserDto.setRoles(new String[]{"Admin"});

        UserEntity userEntity = new UserEntity();
        userEntity.setId(null);
        userEntity.setName(postUserDto.getName());
        userEntity.setLastname(postUserDto.getLastname());
        userEntity.setUsername(postUserDto.getUsername());
        userEntity.setPassword(postUserDto.getPassword());
        userEntity.setDni(postUserDto.getDni());
        userEntity.setActive(postUserDto.getActive());
        userEntity.setAvatar_url(postUserDto.getAvatar_url());
        userEntity.setDatebirth(postUserDto.getDatebirth());

        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(1);
        getUserDto.setName(postUserDto.getName());
        getUserDto.setLastname(postUserDto.getLastname());
        getUserDto.setUsername(postUserDto.getUsername());
        getUserDto.setPassword(postUserDto.getPassword());
        getUserDto.setDni(postUserDto.getDni());
        getUserDto.setActive(postUserDto.getActive());
        getUserDto.setAvatar_url(postUserDto.getAvatar_url());
        getUserDto.setDatebirth(postUserDto.getDatebirth());
        getUserDto.setRoles(postUserDto.getRoles());
        getUserDto.setPhone_number(postUserDto.getPhone_number());



        // Establecer valores de auditoría
        userEntity.setCreatedDate(LocalDateTime.now());
        userEntity.setCreatedUser(1);  // ID del usuario creador
        userEntity.setLastUpdatedDate(LocalDateTime.now());
        userEntity.setLastUpdatedUser(1);  // ID del usuario que realiza la actualización

        // Rol Entity
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1);
        roleEntity.setDescription("Admin");
        roleEntity.setCreatedDate(LocalDateTime.now());
        roleEntity.setLastUpdatedDate(LocalDateTime.now());
        roleEntity.setCreatedUser(1);
        roleEntity.setLastUpdatedUser(1);

        // User Rol Entity
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setId(1);
        userRoleEntity.setUser(userEntity);
        userRoleEntity.setRole(roleEntity);
        userRoleEntity.setCreatedDate(LocalDateTime.now());
        userRoleEntity.setLastUpdatedDate(LocalDateTime.now());
        userRoleEntity.setCreatedUser(1);
        userRoleEntity.setLastUpdatedUser(1);
        roleEntity.setUserRoles(List.of(userRoleEntity));



        //When
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(userEntity);
        when(roleRepositoryMock.findByDescription("Admin")).thenReturn(roleEntity);
        when(userRoleRepositoryMock.save(userRoleEntity)).thenReturn(userRoleEntity);

        //Then
        GetUserDto result = userServiceSpy.createUser(postUserDto);

        assertEquals(getUserDto.getName(), result.getName());
        assertEquals(getUserDto.getLastname(), result.getLastname());
        assertEquals(getUserDto.getUsername(), result.getUsername());
        assertEquals(getUserDto.getPassword(), result.getPassword());
        assertEquals(getUserDto.getDni(), result.getDni());
        assertEquals(getUserDto.getActive(), result.getActive());
        assertEquals(getUserDto.getAvatar_url(), result.getAvatar_url());
        assertEquals(getUserDto.getDatebirth(), result.getDatebirth());
        assertEquals(getUserDto.getPhone_number(), result.getPhone_number());

    }

    @Test
    void createUser_UsernameInUse(){
        //Given
        PostUserDto postUserDto = new PostUserDto();
        postUserDto.setUsername("JuanPa");

        //When
        when(userRepositoryMock.findByUsername("JuanPa")).thenReturn(new UserEntity());

        //Then
        assertThrows(IllegalArgumentException.class, () -> {
            userServiceSpy.createUser(postUserDto);
        });
    }

    @Test
    void createUser_EmailInUse(){
        //Given
        PostUserDto postUserDto = new PostUserDto();
        postUserDto.setEmail("juan@pa.com");

        //When
        when(restContactMock.getAllEmails()).thenReturn(List.of("mail@mail.com", "juan@pa.com"));

        //Then
        assertThrows(IllegalArgumentException.class, () -> {
            userServiceSpy.createUser(postUserDto);
        });
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
        when(restContactMock.getUserIdByEmail("hola@hola")).thenReturn(1);
        when(userRepositoryMock.findById(1)).thenReturn(Optional.of(userEntity));
        when(restContactMock.getContactById(1)).thenReturn(lcontacts);

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
    void updateUser_Success(){
        //Given
        Integer userId = 1;
        PutUserDto putUserDto = new PutUserDto();
        putUserDto.setName("New Name");
        putUserDto.setLastName("New Lastname");
        putUserDto.setDni("30752987");
        putUserDto.setAvatar_url("urlAvatar");
        putUserDto.setDatebirth(LocalDate.of(1997, 12, 3));
        putUserDto.setEmail("email@email");
        putUserDto.setPhoneNumber("12345678");
        putUserDto.setRoles(new String[]{"Admin"});

        //User to update
        UserEntity userToUpdated = new UserEntity();
        userToUpdated.setId(userId);

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setDescription("Admin");

        //When
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(userToUpdated));
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(userToUpdated);
        when(roleRepositoryMock.findByDescription("Admin")).thenReturn(roleEntity);
        when(modelMapperMock.map(userToUpdated, GetUserDto.class)).thenReturn(new GetUserDto());

        GetUserDto result = userServiceSpy.updateUser(userId, putUserDto);

        assertEquals(putUserDto.getName(), result.getName());
        assertEquals(putUserDto.getLastName(), result.getLastname());
        assertEquals(putUserDto.getDni(), result.getDni());
        assertEquals(putUserDto.getAvatar_url(), result.getAvatar_url());
        assertEquals(putUserDto.getDatebirth(), result.getDatebirth());
        assertEquals(putUserDto.getEmail(), result.getEmail());
    }

    @Test
    void updateUser_EntityNotFound(){
        when(userRepositoryMock.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            userServiceSpy.updateUser(1, new PutUserDto());
        });
        assertEquals("User not found with id: 1", exception.getMessage());
    }

    @Test
    void updateUser_RoleNotFound(){
        //Given
        Integer userId = 1;
        PutUserDto putUserDto = new PutUserDto();
        putUserDto.setName("New Name");
        putUserDto.setLastName("New Lastname");
        putUserDto.setDni("30752987");
        putUserDto.setAvatar_url("urlAvatar");
        putUserDto.setDatebirth(LocalDate.of(1997, 12, 3));
        putUserDto.setEmail("email@email");
        putUserDto.setPhoneNumber("12345678");
        putUserDto.setRoles(new String[]{"Admins"});

        UserEntity userToUpdated = new UserEntity();
        userToUpdated.setId(userId);

        //When
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(userToUpdated));
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(userToUpdated);
        when(roleRepositoryMock.findByDescription("Admins")).thenReturn(null);

         Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            userServiceSpy.updateUser(userId, putUserDto);
        });

         assertEquals("Role not found with description: Admins", exception.getMessage());
    }

    @Test
    void  getUserByEmail_EntityNotFound(){
        //When
        when(restContactMock.getUserIdByEmail(anyString())).thenReturn(null);
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
        when(userRepositoryMock.findById(1)).thenReturn(Optional.of(userEntity));

        //Then
        userServiceSpy.deleteUser(1,5);

        Mockito.verify(userRepositoryMock, times(1)).save(userEntity);
        assertEquals(false, userEntity.getActive());
        assertEquals(1, userEntity.getLastUpdatedUser());
        assertNotEquals(oldDate, userEntity.getLastUpdatedDate());
    }

    @Test
    void deleteUser_EntityNotFound() {
        //When
        when(userRepositoryMock.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            userServiceSpy.deleteUser(1,5);
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
        when(userRoleRepositoryMock.findByRoleId(1)).thenReturn(Optional.of(luserRolesEntity));
        when(userRepositoryMock.findById(anyInt())).thenReturn(Optional.of(userEntity));
        when(roleServiceMock.getRolesByUser(anyInt())).thenReturn(lgetRoleDtos);

        //Then
        List<GetUserDto> result = userServiceSpy.getUsersByRole(1);

        assertEquals(luserRolesEntity.size(), result.size());
        assertEquals(luserRolesEntity.get(0).getUser().getName(), result.get(0).getName());
        assertNotNull(result);
    }

    @Test
    void getUsersByRole_EntityNotFound() {
        //When
        when(userRoleRepositoryMock.findByRoleId(1)).thenReturn(Optional.empty());
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
        when(userRepositoryMock.findByDni(dni)).thenReturn(userEntity);

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
        when(userRepositoryMock.findByDni(dni)).thenReturn(null);

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
        when(userRepositoryMock.findByActive(true)).thenReturn(Optional.of(userEntityList));

        //Then
        List<GetUserDto> result = userServiceSpy.getUsersByStatus(true);

        assertEquals(2, result.size());
        assertTrue(true, String.valueOf(result.get(0).getActive()));
        assertEquals(10, result.get(1).getId());
    }

    @Test
    void getUserByStatus_EntityNotFound(){
        //When
        when(userRepositoryMock.findByActive(true)).thenReturn(Optional.empty());

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
        when(userRepositoryMock.findById(10)).thenReturn(Optional.of(userEntity));

        //Then
        GetUserDto result = userServiceSpy.getUserById(10);

        assertNotNull(result);
        assertEquals(userEntity.getName(), result.getName());
        assertEquals(userEntity.getId(), result.getId());
    }

    @Test
    void getUserById_EntityNotFound(){
        //When
        when(userRepositoryMock.findById(10)).thenReturn(Optional.empty());

        //Then
        assertThrows(EntityNotFoundException.class, () -> {
            userServiceSpy.getUserById(10);
        });
    }

    @Test
    void getAllUsers(){
        //Given
        List<UserEntity> userEntityList = new ArrayList<>();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(10);
        userEntity.setName("Pablo");
        userEntity.setDni("35654225");
        userEntity.setActive(true);

        userEntityList.add(userEntity);

        //When
        when(userRepositoryMock.findAll()).thenReturn(userEntityList);

        //Then
        List<GetUserDto> result = userServiceSpy.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userEntity.getName(), result.get(0).getName());
    }

    @Test
    void validateEmail_True(){
        //Given
        String email = "mail@mail.com";
        //TODO: hace falta terminar el método del service
        // Todo: Lo hice, pero no estoy seguro si está bien xq es void

        //When
        when(restContactMock.getAllEmails()).thenReturn(List.of("asd@asd.com"));

        //Then
        assertDoesNotThrow(() -> {
            userServiceSpy.validateEmail(email);
        });


    }

    @Test
    void validateEmail_IllegalArgument() {
        //Given
        String email = "mail@mail.com";

        //When
        when(restContactMock.getAllEmails()).thenReturn(List.of("mail@mail.com", "asd@asd.com"));

        //Then
        assertThrows(IllegalArgumentException.class, () -> {
            userServiceSpy.validateEmail(email);
        });

    }

    @Test
    void validateUsername_True(){
        //Given
        String username = "JuanPa";

        //When
        when(userRepositoryMock.findByUsername(username)).thenReturn(null);

        //Then
        assertDoesNotThrow(() -> {
            userServiceSpy.validateUsername(username);
        });
    }

    @Test
    void validateUsername_IllegalArgument() {
        //Given
        String username = "JuanPa";

        //When
        when(userRepositoryMock.findByUsername(username)).thenReturn(new UserEntity());

        //Then
        assertThrows(IllegalArgumentException.class, () -> {
            userServiceSpy.validateUsername(username);
        });
    }


//    @Test
//    void validateUsername_IllegalArgument(){
//        //Given
//        String username = "martaaa1";
//
//        //When
//        Mockito.when(userRepositoryMock.findByUsername(username)).thenReturn(null);
//
//        //Then
//        assertThrows(IllegalArgumentException.class,
//                () -> userServiceSpy.validateUsername(username));
//    }

    //Test para los mapeadores
    @Test
    public void mapUserEntityToPost() {
        PostUserDto postUserDto = new PostUserDto();
        postUserDto.setName("Martin");
        postUserDto.setLastname("Masera");
        postUserDto.setUsername("MartinMasera");
        postUserDto.setPassword("5358365");
        postUserDto.setDni("12345678");
        postUserDto.setActive(true);
        postUserDto.setAvatar_url("url");
        postUserDto.setDatebirth(LocalDate.now());

        UserEntity userEntity = new UserEntity();

        userServiceSpy.mapUserEntitytoPost(userEntity, postUserDto);

        assertEquals("Martin", userEntity.getName());
        assertEquals("Masera", userEntity.getLastname());
        assertEquals("MartinMasera", userEntity.getUsername());
        assertEquals("5358365", userEntity.getPassword());
        assertEquals("12345678", userEntity.getDni());
        assertEquals(true, userEntity.getActive());
        assertEquals("url", userEntity.getAvatar_url());
        assertEquals(postUserDto.getDatebirth(), userEntity.getDatebirth());
    }

    @Test
    public void testMapUserEntityToGet() {

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("Martin");
        userEntity.setLastname("Masera");
        userEntity.setUsername("MartinMasera");
        userEntity.setPassword("5358365");
        userEntity.setDni("12345678");
        userEntity.setActive(true);
        userEntity.setAvatar_url("url");
        userEntity.setDatebirth(LocalDate.now());

        GetUserDto getUserDto = new GetUserDto();
        getUserDto = userServiceSpy.mapUserEntitytoGet(userEntity, getUserDto);
        assertEquals(1, getUserDto.getId());
        assertEquals("Martin", getUserDto.getName());
        assertEquals("Masera", getUserDto.getLastname());
        assertEquals("MartinMasera", getUserDto.getUsername());
        assertEquals("5358365", getUserDto.getPassword());
    }


    @Test
    public void testMapUserRolesAndContacts() {

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);

        List<GetRoleDto> roles = Arrays.asList(
                new GetRoleDto(1,"Admin"),
                new GetRoleDto(2 ,"User")
        );
        when(roleServiceMock.getRolesByUser(userEntity.getId())).thenReturn(roles);

        List<GetContactDto> contacts = Arrays.asList(
                new GetContactDto(1, "email@email.com"),
                new GetContactDto(2, "1234567")
        );
        when(restContactMock.getContactById(userEntity.getId())).thenReturn(contacts);

        GetUserDto getUserDto = new GetUserDto();
        userServiceSpy.mapUserRolesAndContacts(userEntity, getUserDto);

        assertEquals("Admin", getUserDto.getRoles()[0]);
        assertEquals("User", getUserDto.getRoles()[1]);
        assertEquals("email@email.com", getUserDto.getEmail());
        assertEquals("1234567", getUserDto.getPhone_number());
    }

    @Test
    public void testMapUserRolEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1);

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userServiceSpy.mapUserRolEntity(userRoleEntity, userEntity, roleEntity);

        assertEquals(userEntity.getId(), userRoleEntity.getUser().getId());
        assertEquals(roleEntity.getId(), userRoleEntity.getRole().getId());
        assertEquals(LocalDateTime.now().getYear(), userRoleEntity.getCreatedDate().getYear());
        assertEquals(userEntity.getId(), userRoleEntity.getCreatedUser());
    }
}