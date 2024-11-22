package ar.edu.utn.frc.tup.lc.iv.services.Implementation;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.*;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutUserOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.entities.*;
import ar.edu.utn.frc.tup.lc.iv.helpers.UserTestHelper;
import ar.edu.utn.frc.tup.lc.iv.repositories.*;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.access.RestAccess;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.contacts.GetContactDto;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.contacts.RestContact;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.notifications.RestNotifications;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.plotOwner.RestPlotOwner;
import ar.edu.utn.frc.tup.lc.iv.security.jwt.PasswordUtil;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private RoleRepository roleRepositoryMock;

    @Mock
    private RoleService roleServiceMock;

    @Spy
    private PasswordUtil passwordEncoderMock;

    @Mock
    private RestContact restContactMock;

    @Mock
    private RestAccess restAccessMock;

    @Mock
    private RestNotifications restNotificationsMock;

    @Mock
    private RestPlotOwner restPlotOwner;

    @Mock
    private UserRoleRepository userRoleRepositoryMock;

    @Mock
    private PlotUserRepository plotUserRepositoryMock;

    @Mock
    private DniTypeRepository dniTypeRepositoryMock;

    @InjectMocks
    private UserServiceImpl userServiceSpy;

    @Spy
    private ModelMapper modelMapperMock;


    @Test
    void createUser_Success() {
        PostUserDto postUserDto = UserTestHelper.createPostUserDto();
        UserEntity userEntity = UserTestHelper.createUserEntity(postUserDto);
        GetUserDto getUserDto = UserTestHelper.createGetUserDto(userEntity, postUserDto);


        RoleEntity roleEntity = UserTestHelper.createRoleEntity("Gerente", postUserDto.getUserUpdateId());

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setId(null);
        userRoleEntity.setUser(userEntity);
        userRoleEntity.setRole(roleEntity);
        userRoleEntity.setCreatedDate(LocalDateTime.now());
        userRoleEntity.setLastUpdatedDate(LocalDateTime.now());
        userRoleEntity.setCreatedUser(postUserDto.getUserUpdateId());
        userRoleEntity.setLastUpdatedUser(postUserDto.getUserUpdateId());
        roleEntity.setUserRoles(List.of(userRoleEntity));

        PlotUserEntity plotUserEntity = UserTestHelper.createPlotUserEntity(userEntity, postUserDto.getPlot_id(), postUserDto.getUserUpdateId());

        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(userEntity);
        when(dniTypeRepositoryMock.findById(1)).thenReturn(Optional.of(new DniTypeEntity()));
        when(roleRepositoryMock.findByDescription("Gerente")).thenReturn(roleEntity);
        when(userRoleRepositoryMock.save(any(UserRoleEntity.class))).thenReturn(userRoleEntity);
        when(restContactMock.saveContact(userEntity.getId(), postUserDto.getEmail(), 1,1)).thenReturn(true);
        when(restContactMock.saveContact(userEntity.getId(), postUserDto.getPhone_number(), 2,1)).thenReturn(true);
        when(plotUserRepositoryMock.save(any(PlotUserEntity.class))).thenReturn(plotUserEntity);


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
        assertArrayEquals(getUserDto.getPlot_id(), result.getPlot_id());
        assertArrayEquals(postUserDto.getRoles(), result.getRoles());

    }


    @Test
    void createOwnerUser_Success() {
        PostOwnerUserDto postUserDto = UserTestHelper.createPostOwnerUserDto();
        UserEntity userEntity = UserTestHelper.createUserEntity(postUserDto);
        GetUserDto getUserDto = UserTestHelper.createGetUserDto(userEntity, postUserDto);
        RoleEntity roleEntity = UserTestHelper.createRoleEntity("Gerente", postUserDto.getUserUpdateId());

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setId(null);
        userRoleEntity.setUser(userEntity);
        userRoleEntity.setRole(roleEntity);
        userRoleEntity.setCreatedDate(LocalDateTime.now());
        userRoleEntity.setLastUpdatedDate(LocalDateTime.now());
        userRoleEntity.setCreatedUser(postUserDto.getUserUpdateId());
        userRoleEntity.setLastUpdatedUser(postUserDto.getUserUpdateId());
        roleEntity.setUserRoles(List.of(userRoleEntity));

        PlotUserEntity plotUserEntity = UserTestHelper.createPlotUserEntity(userEntity, postUserDto.getPlot_id()[0], postUserDto.getUserUpdateId());

        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(userEntity);
        when(dniTypeRepositoryMock.findById(1)).thenReturn(Optional.of(new DniTypeEntity()));
        when(roleRepositoryMock.findByDescription("Gerente")).thenReturn(roleEntity);
        when(userRoleRepositoryMock.save(any(UserRoleEntity.class))).thenReturn(userRoleEntity);
        when(restContactMock.saveContact(userEntity.getId(), postUserDto.getEmail(), 1,1)).thenReturn(true);
        when(restContactMock.saveContact(userEntity.getId(), postUserDto.getPhone_number(), 2,1)).thenReturn(true);
        when(plotUserRepositoryMock.save(any(PlotUserEntity.class))).thenReturn(plotUserEntity);

        GetOwnerUserDto result = userServiceSpy.createOwnerUser(postUserDto);

        assertEquals(getUserDto.getName(), result.getName());
        assertEquals(getUserDto.getLastname(), result.getLastname());
        assertEquals(getUserDto.getUsername(), result.getUsername());
        assertEquals(getUserDto.getPassword(), result.getPassword());
        assertEquals(getUserDto.getDni(), result.getDni());
        assertEquals(getUserDto.getActive(), result.getActive());
        assertEquals(getUserDto.getAvatar_url(), result.getAvatar_url());
        assertEquals(getUserDto.getDatebirth(), result.getDatebirth());
        assertEquals(getUserDto.getPhone_number(), result.getPhone_number());
        assertArrayEquals(getUserDto.getPlot_id(), result.getPlot_id());
        assertArrayEquals(postUserDto.getRoles(), result.getRoles());
    }

    @Test
    void getAllPlotUsers_Success() {
        PlotUserEntity plotUser1 = new PlotUserEntity();
        plotUser1.setPlotId(101);
        UserEntity user1 = new UserEntity();
        user1.setId(1);
        plotUser1.setUser(user1);
        PlotUserEntity plotUser2 = new PlotUserEntity();
        plotUser2.setPlotId(102);
        UserEntity user2 = new UserEntity();
        user2.setId(2);
        plotUser2.setUser(user2);
        List<PlotUserEntity> plotUserEntities = Arrays.asList(plotUser1, plotUser2);
        when(plotUserRepositoryMock.findAll()).thenReturn(plotUserEntities);


        List<GetPlotUserDto> result = userServiceSpy.getAllPlotUsers();

        assertEquals(2, result.size());

        GetPlotUserDto resultDto1 = result.get(0);
        assertEquals(101, resultDto1.getPlot_id());
        assertEquals(1, resultDto1.getUser_id());

        GetPlotUserDto resultDto2 = result.get(1);
        assertEquals(102, resultDto2.getPlot_id());
        assertEquals(2, resultDto2.getUser_id());
    }

    @Test
    void createUser_UsernameInUse(){
        PostUserDto postUserDto = new PostUserDto();
        postUserDto.setUsername("JuanPa");

        when(userRepositoryMock.findByUsername("JuanPa")).thenReturn(Optional.of(new UserEntity()));

        assertThrows(IllegalArgumentException.class, () -> {
            userServiceSpy.createUser(postUserDto);
        });
    }

    @Test
    void createUser_EmailInUse(){
        PostUserDto postUserDto = new PostUserDto();
        postUserDto.setEmail("juan@pa.com");

        when(restContactMock.getAllEmails()).thenReturn(List.of("mail@mail.com", "juan@pa.com"));

        assertThrows(IllegalArgumentException.class, () -> {
            userServiceSpy.createUser(postUserDto);
        });
    }

    @Test
    void getUserByEmail_Success() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("Paola");
        userEntity.setDni("45646545");
        userEntity.setDniType(new DniTypeEntity());
        userEntity.setDatebirth(LocalDate.now());
        userEntity.setCreatedDate(LocalDateTime.now());
        DniTypeEntity dniTypeEntity = new DniTypeEntity();
        dniTypeEntity.setId(1);
        userEntity.setDniType(dniTypeEntity);
        userEntity.setActive(true);

        GetRoleDto roleDto = new GetRoleDto();
        roleDto.setId(12);
        roleDto.setDescription("descripcion");

        GetContactDto contactDto = new GetContactDto();
        contactDto.setType_contact(1);
        contactDto.setValue("hola@hola");

        List<GetContactDto> lcontacts = new ArrayList<>();
        lcontacts.add(contactDto);

        when(restContactMock.getUserIdByEmail("hola@hola")).thenReturn(1);
        when(userRepositoryMock.findById(1)).thenReturn(Optional.of(userEntity));
        when(dniTypeRepositoryMock.findById(1)).thenReturn(Optional.of(new DniTypeEntity()));
        when(restContactMock.getContactById(1)).thenReturn(lcontacts);

        GetUserDto result = userServiceSpy.getUserByEmail("hola@hola");
        System.out.println(result);
        assertNotNull(result);
        assertEquals(userEntity.getName(), result.getName());
        assertEquals(userEntity.getDatebirth(), result.getDatebirth());
        assertEquals(userEntity.getActive(), result.getActive());
        assertEquals("hola@hola", result.getEmail());
    }

    @Test
    void updateUser_Success(){
        Integer userId = 1;
        PutUserDto putUserDto = new PutUserDto();
        putUserDto.setName("New Name");
        putUserDto.setLastName("New Lastname");
        putUserDto.setDni("30752987");
        putUserDto.setDni_type_id(1);
        putUserDto.setAvatar_url("urlAvatar");
        putUserDto.setDatebirth(LocalDate.of(1997, 12, 3));
        putUserDto.setEmail("email@email");
        putUserDto.setPhoneNumber("12345678");
        putUserDto.setRoles(new String[]{"Admin"});
        putUserDto.setUserUpdateId(1);

        DniTypeEntity dniTypeEntity = new DniTypeEntity();
        dniTypeEntity.setId(1);
        dniTypeEntity.setDescription("DNI");

        UserEntity userToUpdated = new UserEntity();
        userToUpdated.setId(userId);
        userToUpdated.setDniType(dniTypeEntity);

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setDescription("Admin");


        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(userId);
        getUserDto.setName(putUserDto.getName());
        getUserDto.setLastname(putUserDto.getLastName());
        getUserDto.setDni(putUserDto.getDni());
        getUserDto.setAvatar_url(putUserDto.getAvatar_url());
        getUserDto.setDatebirth(putUserDto.getDatebirth());

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(userToUpdated));
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(userToUpdated);
        when(dniTypeRepositoryMock.findById(1)).thenReturn(Optional.of(dniTypeEntity));
        when(roleRepositoryMock.findByDescription("Admin")).thenReturn(roleEntity);
        when(modelMapperMock.map(userToUpdated, GetUserDto.class)).thenReturn(getUserDto);

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
        Integer userId = 1;
        PutUserDto putUserDto = new PutUserDto();
        putUserDto.setName("New Name");
        putUserDto.setLastName("New Lastname");
        putUserDto.setDni("30752987");
        putUserDto.setDni_type_id(1);
        putUserDto.setAvatar_url("urlAvatar");
        putUserDto.setDatebirth(LocalDate.of(1997, 12, 3));
        putUserDto.setEmail("email@email");
        putUserDto.setPhoneNumber("12345678");
        putUserDto.setRoles(new String[]{"Admins"});

        DniTypeEntity dniTypeEntity = new DniTypeEntity();
        dniTypeEntity.setId(1);
        dniTypeEntity.setDescription("DNI");

        UserEntity userToUpdated = new UserEntity();
        userToUpdated.setId(userId);
        userToUpdated.setDniType(dniTypeEntity);

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(userToUpdated));
        when(dniTypeRepositoryMock.findById(1)).thenReturn(Optional.of(dniTypeEntity));
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(userToUpdated);
        when(roleRepositoryMock.findByDescription("Admins")).thenReturn(null);


         Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            userServiceSpy.updateUser(userId, putUserDto);
        });

         assertEquals("Role not found with description: Admins", exception.getMessage());
    }

    @Test
    void  getUserByEmail_EntityNotFound(){
        when(restContactMock.getUserIdByEmail(anyString())).thenReturn(null);
        GetUserDto getUserDto = null;

        userServiceSpy.getUserByEmail("email@email.com");
        assertNull(getUserDto);
    }

    @Test
    void deleteUser_Success() {
        LocalDateTime oldDate = LocalDateTime.now().minus(Duration.ofDays(1));

        DniTypeEntity dniType = new DniTypeEntity();
        dniType.setId(1);
        dniType.setDescription("DNI");

        UserEntity userEntity = new UserEntity();
        userEntity.setActive(true);
        userEntity.setDniType(dniType);
        userEntity.setLastUpdatedDate(oldDate);

        when(userRepositoryMock.findById(1)).thenReturn(Optional.of(userEntity));

        userServiceSpy.deleteUser(1,1);

        Mockito.verify(userRepositoryMock, times(1)).save(userEntity);
        assertEquals(false, userEntity.getActive());
        assertEquals(1, userEntity.getLastUpdatedUser());
        assertNotEquals(oldDate, userEntity.getLastUpdatedDate());
    }

    @Test
    void deleteUser_EntityNotFound() {
        when(userRepositoryMock.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            userServiceSpy.deleteUser(1,1);
        });
    }

    @Test
    void getUsersByRole_Success() {
        GetUserDto userDto = new GetUserDto();
        userDto.setId(10);
        userDto.setName("Pablo");
        userDto.setLastname("Ortega");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(10);
        userEntity.setName("Pablo");
        userEntity.setLastname("Ortega");
        userEntity.setCreatedDate(LocalDateTime.now());
        DniTypeEntity dniTypeEntity = new DniTypeEntity();
        dniTypeEntity.setId(1);
        userEntity.setDniType(dniTypeEntity);
        userEntity.setActive(true);

        List<GetRoleDto> lgetRoleDtos = new ArrayList<>();
        GetRoleDto roleDto = new GetRoleDto();
        roleDto.setId(1);
        roleDto.setDescription("Admin");

        lgetRoleDtos.add(roleDto);

        List<UserRoleEntity> luserRolesEntity = new ArrayList<>();
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUser(userEntity);
        luserRolesEntity.add(userRoleEntity);

        when(userRoleRepositoryMock.findByRoleId(1)).thenReturn(Optional.of(luserRolesEntity));
        when(userRepositoryMock.findById(anyInt())).thenReturn(Optional.of(userEntity));
        when(dniTypeRepositoryMock.findById(1)).thenReturn(Optional.of(dniTypeEntity));
        when(roleServiceMock.getRolesByUser(anyInt())).thenReturn(lgetRoleDtos);

        List<GetUserDto> result = userServiceSpy.getUsersByRole(1);

        assertEquals(luserRolesEntity.size(), result.size());
        assertEquals(luserRolesEntity.get(0).getUser().getName(), result.get(0).getName());
        assertNotNull(result);
    }

    @Test
    void getUsersByRole_EntityNotFound() {
        when(userRoleRepositoryMock.findByRoleId(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->{
            userServiceSpy.getUsersByRole(1);
        });
    }

    @Test
    void getUserByStatus_EntityNotFound(){
        when(userRepositoryMock.findByActive(true)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->{
            userServiceSpy.getUsersByStatus(true);
        });
    }

    @Test
    void getUserById_Success(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(10);
        userEntity.setName("Pablo");
        userEntity.setDni("35654225");
        userEntity.setCreatedDate(LocalDateTime.now());
        DniTypeEntity dniTypeEntity = new DniTypeEntity();
        dniTypeEntity.setId(1);
        userEntity.setDniType(dniTypeEntity);
        userEntity.setActive(true);

        when(userRepositoryMock.findById(10)).thenReturn(Optional.of(userEntity));
        when(dniTypeRepositoryMock.findById(1)).thenReturn(Optional.of(dniTypeEntity));
        GetUserDto result = userServiceSpy.getUserById(10);

        assertNotNull(result);
        assertEquals(userEntity.getName(), result.getName());
        assertEquals(userEntity.getId(), result.getId());
    }

    @Test
    void getUserById_EntityNotFound(){
        when(userRepositoryMock.findById(10)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            userServiceSpy.getUserById(10);
        });
    }

    @Test
    void getAllUsers(){
        List<UserEntity> userEntityList = new ArrayList<>();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(10);
        userEntity.setName("Pablo");
        userEntity.setDni("35654225");
        userEntity.setCreatedDate(LocalDateTime.now());
        DniTypeEntity dniTypeEntity = new DniTypeEntity();
        dniTypeEntity.setId(1);
        userEntity.setDniType(dniTypeEntity);
        userEntity.setActive(true);

        userEntityList.add(userEntity);

        when(userRepositoryMock.findAllActives()).thenReturn(userEntityList);
        when(dniTypeRepositoryMock.findById(1)).thenReturn(Optional.of(dniTypeEntity));
        List<GetUserDto> result = userServiceSpy.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userEntity.getName(), result.get(0).getName());
    }

    @Test
    void validateEmail_True(){
        String email = "mail@mail.com";

        when(restContactMock.getAllEmails()).thenReturn(List.of("asd@asd.com"));

        assertDoesNotThrow(() -> {
            userServiceSpy.validateEmail(email);
        });


    }

    @Test
    void validateEmail_IllegalArgument() {
        String email = "mail@mail.com";

        when(restContactMock.getAllEmails()).thenReturn(List.of("mail@mail.com", "asd@asd.com"));

        assertThrows(IllegalArgumentException.class, () -> {
            userServiceSpy.validateEmail(email);
        });
    }

    @Test
    void validateUsername_True(){
        String username = "JuanPa";
        when(userRepositoryMock.findByUsername(username)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> {
            userServiceSpy.validateUsername(username);
        });
    }

    @Test
    void validateUsername_IllegalArgument() {
        String username = "JuanPa";

        when(userRepositoryMock.findByUsername(username)).thenReturn(Optional.of(new UserEntity()));

        assertThrows(IllegalArgumentException.class, () -> {
            userServiceSpy.validateUsername(username);
        });
    }

    @Test
    public void mapUserPostToUserEntity() {
        PostUserDto postUserDto = UserTestHelper.createPostUserDto();
        UserEntity userEntity = new UserEntity();

        when(dniTypeRepositoryMock.findById(1)).thenReturn(Optional.of(new DniTypeEntity()));

        userServiceSpy.mapUserPostToUserEntity(userEntity, postUserDto);

        assertEquals("Juan", userEntity.getName());
        assertEquals("Perez", userEntity.getLastname());
        assertEquals("JuanPa", userEntity.getUsername());
        assertEquals(true, userEntity.getActive());
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
        userEntity.setCreatedDate(LocalDateTime.now());
        DniTypeEntity dniTypeEntity = new DniTypeEntity();
        dniTypeEntity.setId(1);
        userEntity.setDniType(dniTypeEntity);

        GetUserDto getUserDto = new GetUserDto();

        when(dniTypeRepositoryMock.findById(1)).thenReturn(Optional.of(dniTypeEntity));

        userServiceSpy.mapUserEntityToGet(userEntity, getUserDto);

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
    }


    @Test
    void validateDni_DniAlreadyInUse() {
        String dni = "12345678";
        when(userRepositoryMock.findByDni(dni)).thenReturn(new UserEntity());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userServiceSpy.validateDni(dni);
        });

        assertEquals("Error creating user: dni already in use.", exception.getMessage());
    }

    @Test
    void validateDni_DniNotInUse() {
        String dni = "12345678";
        when(userRepositoryMock.findByDni(dni)).thenReturn(null);

        assertDoesNotThrow(() -> {
            userServiceSpy.validateDni(dni);
        });
    }

    @Test
    void getUserByUsername_UserFound() {
        String username = "testUser";
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        DniTypeEntity dniTypeEntity = new DniTypeEntity();
        dniTypeEntity.setId(1);
        dniTypeEntity.setDescription("DNI");
        userEntity.setDniType(dniTypeEntity);
        userEntity.setCreatedDate(LocalDateTime.now());

        when(userRepositoryMock.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(userRepositoryMock.findById(1)).thenReturn(Optional.of(userEntity));
        when(dniTypeRepositoryMock.findById(1)).thenReturn(Optional.of(dniTypeEntity));

        GetUserDto result = userServiceSpy.getUserByUsername(username);

        assertNotNull(result);
    }

    @Test
    void getUserByUsername_UserNotFound() {
        String username = "testUser";
        when(userRepositoryMock.findByUsername(username)).thenReturn(Optional.empty());

        GetUserDto result = userServiceSpy.getUserByUsername(username);

        assertNull(result);
    }

    @Test
    void getUserByPlotIdAndOwnerRole_UserFound() {
        Integer plotId = 1;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("Martin");
        userEntity.setLastname("Mas");
        userEntity.setCreatedDate(LocalDateTime.now());
        DniTypeEntity dniTypeEntity = new DniTypeEntity();
        dniTypeEntity.setId(1);
        dniTypeEntity.setDescription("DNI");
        userEntity.setDniType(dniTypeEntity);

        when(userRepositoryMock.findUserByPlotIdAndOwnerRole(plotId)).thenReturn(Optional.of(userEntity));
        when(dniTypeRepositoryMock.findById(1)).thenReturn(Optional.of(dniTypeEntity));

        GetUserDto result = userServiceSpy.getUserByPlotIdAndOwnerRole(plotId);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Martin", result.getName());
        assertEquals("Mas", result.getLastname());
    }

    @Test
    void getUserByPlotIdAndOwnerRole_UserNotFound() {
        Integer plotId = 1;

        when(userRepositoryMock.findUserByPlotIdAndOwnerRole(plotId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userServiceSpy.getUserByPlotIdAndOwnerRole(plotId);
        });

        assertEquals("User not found with plot id: " + plotId, exception.getMessage());
    }

    @Test
    void getAllUsersByPlotId_UsersFound() {
        Integer plotId = 1;
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setId(1);
        userEntity1.setCreatedDate(LocalDateTime.now());
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(2);
        userEntity2.setCreatedDate(LocalDateTime.now());

        DniTypeEntity dniTypeEntity = new DniTypeEntity();
        dniTypeEntity.setId(1);
        dniTypeEntity.setDescription("DNI");
        userEntity1.setDniType(dniTypeEntity);
        userEntity2.setDniType(dniTypeEntity);

        List<UserEntity> userEntities = Arrays.asList(userEntity1, userEntity2);

        when(userRepositoryMock.findUsersByPlotId(plotId)).thenReturn(Optional.of(userEntities));
        when(dniTypeRepositoryMock.findById(1)).thenReturn(Optional.of(dniTypeEntity));

        List<GetUserDto> result = userServiceSpy.getAllUsersByPlotId(plotId);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getAllUsersByPlotId_UsersNotFound() {
        Integer plotId = 1;

        when(userRepositoryMock.findUsersByPlotId(plotId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userServiceSpy.getAllUsersByPlotId(plotId);
        });

        assertEquals("Users not found with plot id: " + plotId, exception.getMessage());
    }

    @Test
    void updateUserOwner_Success() {
        Integer userId = 1;
        PutUserOwnerDto putUserDto = new PutUserOwnerDto();
        putUserDto.setName("New Name");
        putUserDto.setLastName("New Lastname");
        putUserDto.setDni("30752987");
        putUserDto.setDni_type_id(1);
        putUserDto.setAvatar_url("urlAvatar");
        putUserDto.setDatebirth(LocalDate.of(1997, 12, 3));
        putUserDto.setEmail("email@email.com");
        putUserDto.setPhoneNumber("12345678");
        putUserDto.setRoles(new String[]{"Admin"});
        putUserDto.setUserUpdateId(1);
        putUserDto.setPlot_id(new Integer[]{1, 2});

        DniTypeEntity dniTypeEntity = new DniTypeEntity();
        dniTypeEntity.setId(1);
        dniTypeEntity.setDescription("DNI");

        UserEntity userToUpdated = new UserEntity();
        userToUpdated.setId(userId);
        userToUpdated.setDniType(dniTypeEntity);

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setDescription("Admin");

        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(userId);
        getUserDto.setName(putUserDto.getName());
        getUserDto.setLastname(putUserDto.getLastName());
        getUserDto.setDni(putUserDto.getDni());
        getUserDto.setAvatar_url(putUserDto.getAvatar_url());
        getUserDto.setDatebirth(putUserDto.getDatebirth());

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(userToUpdated));
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(userToUpdated);
        when(dniTypeRepositoryMock.findById(1)).thenReturn(Optional.of(dniTypeEntity));
        when(roleRepositoryMock.findByDescription("Admin")).thenReturn(roleEntity);
        when(modelMapperMock.map(userToUpdated, GetUserDto.class)).thenReturn(getUserDto);

        GetUserDto result = userServiceSpy.updateUserOwner(userId, putUserDto);

        assertEquals(putUserDto.getName(), result.getName());
        assertEquals(putUserDto.getLastName(), result.getLastname());
        assertEquals(putUserDto.getDni(), result.getDni());
        assertEquals(putUserDto.getAvatar_url(), result.getAvatar_url());
        assertEquals(putUserDto.getDatebirth(), result.getDatebirth());
        assertEquals(putUserDto.getEmail(), result.getEmail());
    }

    @Test
    void updateUserOwner_UserNotFound() {
        Integer userId = 1;
        PutUserOwnerDto putUserDto = new PutUserOwnerDto();

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userServiceSpy.updateUserOwner(userId, putUserDto);
        });

        assertEquals("User not found with id: " + userId, exception.getMessage());
    }
}