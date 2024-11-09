package ar.edu.utn.frc.tup.lc.iv.services.Implementation;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.*;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.*;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutUserDto;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotUserEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.RoleEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserRoleEntity;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.notifications.RecoveryDto;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.notifications.RegisterDto;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.notifications.RestNotifications;
import ar.edu.utn.frc.tup.lc.iv.security.jwt.PasswordUtil;
import ar.edu.utn.frc.tup.lc.iv.repositories.*;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.GetContactDto;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.RestContact;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.access.RestAccess;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.plotOwner.RestPlotOwner;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.RoleService;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.UserService;
import ar.edu.utn.frc.tup.lc.iv.services.PasswordGenerator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementación de {@link UserService},
 * contiene toda la lógica relacionada con usuarios.
 */
@Service
@Data
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    /** Logger para la clase. */
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * Servicio para manejar el restTemplate de lotes de propietarios.
     */
    private final RestPlotOwner restPlotOwner;

    /**
     * Servicio para manejar el restTemplate de notificaciones
     */
    private final RestNotifications restNotifications;


    /**
     * ModelMapper para convertir entre entidades y dtos.
     */
    private final ModelMapper modelMapper;

    /**
     * Repositorio para manejar User entities.
     */
    private final UserRepository userRepository;

    /**
     * Repositorio para manejar Role entities.
     */
    private final RoleRepository roleRepository;

    /**
     * Repositorio para manejar DniType entities.
     */
    private final DniTypeRepository dniTypeRepository;

    /**
     * Repositorio para manejar UserRole entities.
     */
    private final UserRoleRepository userRoleRepository;

    /**
     * Repositorio para manejar PlotUser entities.
     */
    private final PlotUserRepository plotUserRepository;

    /** Servicio para manejar la lógica de roles. */
    private final RoleService roleService;

    /** Servicio para manejar el restTemplate de contactos. */
    private final RestContact restContact;

    /** Servicio para manejar el restTemplate de accesos. */
    private final RestAccess restAccess;

    /** Servicio para encriptar y desencriptar contraseñas. */
    private final PasswordUtil passwordEncoder;

    /**
     * Crea un usuario.
     *
     * @param postUserDto dto que contiene la información del usuario.
     * @return el usuario creado.
     */
    @Override
    @Transactional
    public GetUserDto createUser(PostUserDto postUserDto) {

        validateUser(postUserDto);
        String hashedPassword = passwordEncoder.hashPassword(postUserDto.getPassword());
        postUserDto.setPassword(hashedPassword);

        UserEntity userSaved = saveUserEntity(postUserDto);
        assignRolesToUser(userSaved, postUserDto.getRoles(), postUserDto.getUserUpdateId());
        savePlotUser(postUserDto, userSaved);
        saveContact(userSaved, postUserDto.getEmail(), postUserDto.getPhone_number());

        GetUserDto getUserDto = mapToGetUserDto(userSaved, postUserDto);
        List<Integer> plot = new ArrayList<>();
        plot.add(postUserDto.getPlot_id());
        getUserDto.setPlot_id(plot.toArray(new Integer[0]));

        //sendWelcomeEmail(postUserDto.getEmail());todo: Descomentar cuando se necesite postear a notificaciones

        // Hace el post al microservicio de accesos todo: Descomentar cuando se necesite postear a acceso
        //restAccess.registerUserAccess(postUserDto);
        return getUserDto;
    }

    /**
     * Crea un usuario de tipo owner.
     *
     * @param postOwnerUserDto dto que contiene la información del usuario.
     * @return el usuario creado.
     */
    @Override
    @Transactional
    public GetOwnerUserDto createOwnerUser(PostOwnerUserDto postOwnerUserDto) {

        validateUser(postOwnerUserDto);
        String hashedPassword = passwordEncoder.hashPassword(postOwnerUserDto.getPassword());
        postOwnerUserDto.setPassword(hashedPassword);

        UserEntity userSaved = saveUserEntity(postOwnerUserDto);
        assignRolesToUser(userSaved, postOwnerUserDto.getRoles(), postOwnerUserDto.getUserUpdateId());
        savePlotsUser(postOwnerUserDto, userSaved);
        saveContact(userSaved, postOwnerUserDto.getEmail(), postOwnerUserDto.getPhone_number());

        GetOwnerUserDto getUserDto = mapToGetOwnerUserDto(userSaved, postOwnerUserDto);
        getUserDto.setPlot_id(postOwnerUserDto.getPlot_id());
        //sendWelcomeEmail(postOwnerUserDto.getEmail());
        // Hace el post al microservicio de accesos todo: Descomentar cuando se necesite postear a acceso
        //restAccess.registerUserAccess(postOwnerUserDto); todo: Descomeentar cuando se necesite postear a notificaciones
        return getUserDto;
    }

    /**
     * Busca todos los usuarios asociados a un lote,
     * incluido el propietario.
     *
     * @return la lista de usuarios por lote.
     */
    @Override
    public List<GetPlotUserDto> getAllPlotUsers() {
        List<PlotUserEntity> plotUserEntities = plotUserRepository.findAll();
        return plotUserEntities.stream()
                .map(PUE -> {
                    GetPlotUserDto plotUserDto = new GetPlotUserDto();
                    plotUserDto.setPlot_id(PUE.getPlotId());
                    plotUserDto.setUser_id(PUE.getUser().getId());
                    return plotUserDto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Busca todos los usuarios (familiares) asociados a un propietario,
     * incluido el propietario.
     *
     * @param ownerId id del propietario.
     * @return la lista de usuarios por propietario.
     */
    @Override
    public List<GetUserDto> getUsersByOwner(Integer ownerId) {
        List<GetPlotOwnerDto> plotOwnerDtoList = restPlotOwner.getAllPlotOwner();
        List<GetPlotUserDto> plotUserDtoList = this.getAllPlotUsers();
        List<GetUserDto> userDtos = this.getAllUsers();
        List<Integer> plotsByOwner = new ArrayList<>();
        for (GetPlotOwnerDto getPlotOwnerDto : plotOwnerDtoList) {
            if (getPlotOwnerDto.getOwner_id().equals(ownerId)) {
                plotsByOwner.add(getPlotOwnerDto.getPlot_id());
            }
        }
        List<GetPlotUserDto> usersByPlots = new ArrayList<>();
        List<GetUserDto> userDtoByOwner = new ArrayList<>();
        if (!plotsByOwner.isEmpty()) {
            for (GetPlotUserDto getPlotUserDto : plotUserDtoList) {
                if (plotsByOwner.contains(getPlotUserDto.getPlot_id())) {
                    for (GetUserDto userDto : userDtos) {
                        if (userDto.getId().equals(getPlotUserDto.getUser_id())) {
                            userDtoByOwner.add(userDto);
                            break;
                        }
                    }
                }
            }
        }
        return userDtoByOwner;

    }

    /**
     * Busca todos los usuarios (familaires) asociados a un propietario,
     * SIN INCLUIR el propietario.
     *
     * @param ownerId
     * @return la lista de usuarios por propietario.
     */
    @Override
    public List<GetUserDto> getUsersByOwnerWithoutOwner(Integer ownerId) {
        List<GetPlotOwnerDto> plotOwnerDtoList = restPlotOwner.getAllPlotOwner();
        List<GetPlotUserDto> plotUserDtoList = this.getAllPlotUsers();
        List<GetUserDto> userDtos = this.getAllUsers();
        List<Integer> plotsByOwner = new ArrayList<>();

        // Obtiene los lotes asociados al propietario
        for (GetPlotOwnerDto PO : plotOwnerDtoList) {
            if (PO.getOwner_id().equals(ownerId)) {
                plotsByOwner.add(PO.getPlot_id());
            }
        }

        List<GetUserDto> userDtoByOwner = new ArrayList<>();
        if (!plotsByOwner.isEmpty()) {
            for (GetPlotUserDto PU : plotUserDtoList) {
                if (plotsByOwner.contains(PU.getPlot_id())) {
                    for (GetUserDto userDto : userDtos) {
                        if (userDto.getId().equals(PU.getUser_id())) {
                            // Filtra para no incluir al propietario
                            if (!Arrays.asList(userDto.getRoles()).contains("Propietario")) {
                                userDtoByOwner.add(userDto);
                            }
                            break;
                        }
                    }
                }
            }
        }
        return userDtoByOwner;
    }

    /**
     * Guarda al usuario en la base de datos.
     *
     * @param user dto que contiene la información del usuario.
     * @return el usuario guardado.
     */
    public UserEntity saveUserEntity(BasePostUser user) {
        UserEntity userEntity = new UserEntity();
        mapUserPostToUserEntity(userEntity, user);
        return userRepository.save(userEntity);
    }

    /**
     * Metodo para asignar roles a un usuario.
     *
     * @param userSaved usuario guardado.
     * @param roleDescriptions descripciones de los roles a asignar.
     * @param userId id del usuario que realiza la operación.
     * @throws EntityNotFoundException si no encuentra un rol con la descripción asignada.
     */
    public void assignRolesToUser(UserEntity userSaved, String[] roleDescriptions, Integer userId) {
        for (String roleDesc : roleDescriptions) {
            RoleEntity roleEntity = roleRepository.findByDescription(roleDesc);
            if (roleEntity != null) {
                saveUserRole(userSaved, roleEntity, userId);
            } else {
                throw new EntityNotFoundException("Role not found with description: " + roleDesc);
            }
        }
    }

    /**
     * Metodo para guardar el rol de un usuario.
     *
     * @param userSaved usuario guardado.
     * @param roleEntity rol del usuario.
     * @param userId id del usuario que realiza la operación.
     */
    public void saveUserRole(UserEntity userSaved, RoleEntity roleEntity, Integer userId) {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        mapUserRolEntity(userRoleEntity, userSaved, roleEntity);
        userRoleEntity.setCreatedUser(userId);
        userRoleEntity.setLastUpdatedUser(userId);
        userRoleRepository.save(userRoleEntity);
    }

    /**
     * Metodo para guardar el lote de un usuario.
     *
     * @param postUserDto dto que contiene la información del usuario.
     * @param userSaved usuario guardado.
     */
    public void savePlotUser(PostUserDto postUserDto, UserEntity userSaved) {
        PlotUserEntity plotUserEntity = new PlotUserEntity();
        mapPostToPlotUserEntity(plotUserEntity, postUserDto, userSaved);
        plotUserRepository.save(plotUserEntity);
    }

    /**
     * Metodo para guardar los lotes de un usuario de tipo propietario.
     *
     * @param postOwnerUserDto dto que contiene la información del usuario propietario.
     * @param userSaved usuario guardado.
     */
    public void savePlotsUser(PostOwnerUserDto postOwnerUserDto, UserEntity userSaved) {
        for (Integer plot : postOwnerUserDto.getPlot_id()) {
            PlotUserEntity plotUserEntity = new PlotUserEntity();
            mapPostOwnerToPlotUserEntity(plotUserEntity, postOwnerUserDto, userSaved, plot);
            plotUserRepository.save(plotUserEntity);
        }
    }

    /**
     * Metodo para guardar la información de contacto de un usuario.
     *
     * @param userSaved usuario guardado.
     * @param email email del usuario.
     * @param phone teléfono del usuario.
     * @throws IllegalStateException si falla el intento de guardar contactos.
     */
    public void saveContact(UserEntity userSaved, String email, String phone) {
        boolean emailSaved = restContact.saveContact(userSaved.getId(), email, 1, userSaved.getCreatedUser());
        boolean phoneSaved = restContact.saveContact(userSaved.getId(), phone, 2, userSaved.getCreatedUser());
        if (!emailSaved && !phoneSaved) {
            throw new IllegalStateException("Failed to save contact information.");
        }
    }

    /**
     * Metodo para crear un GetUserDto a partir de un UserEntity.
     *
     * @param userSaved usuario guardado.
     * @param user dto que contiene la información del usuario.
     * @return un getUserDto con la información del usuario.
     * @throws EntityNotFoundException si no encuentra un dniType con el id asignado.
     */
    public GetUserDto mapToGetUserDto(UserEntity userSaved, BasePostUser user) {
        GetUserDto getUserDto = modelMapper.map(userSaved, GetUserDto.class);
        getUserDto.setRoles(user.getRoles());
        getUserDto.setEmail(user.getEmail());
        getUserDto.setPhone_number(user.getPhone_number());
        getUserDto.setDni(user.getDni());
        getUserDto.setDni_type(dniTypeRepository.findById(user.getDni_type_id())
                .orElseThrow(() -> new EntityNotFoundException("DniType not found")).getDescription());
        getUserDto.setCreate_date(LocalDate.now());
        return getUserDto;
    }

    /**
     * Metodo para crear un GetOwnerUserDto a partir de un UserEntity.
     *
     * @param userSaved usuario guardado.
     * @param user dto que contiene la información del usuario.
     * @return un getUserDto con la información del usuario.
     * @throws EntityNotFoundException si no encuentra un dniType con el id asignado.
     */
    public GetOwnerUserDto mapToGetOwnerUserDto(UserEntity userSaved, BasePostUser user) {
        GetOwnerUserDto getUserDto = modelMapper.map(userSaved, GetOwnerUserDto.class);
        getUserDto.setRoles(user.getRoles());
        getUserDto.setEmail(user.getEmail());
        getUserDto.setPhone_number(user.getPhone_number());
        getUserDto.setDni(user.getDni());
        getUserDto.setDni_type(dniTypeRepository.findById(user.getDni_type_id())
                .orElseThrow(() -> new EntityNotFoundException("DniType not found")).getDescription());
        getUserDto.setCreate_date(LocalDate.now());
        return getUserDto;
    }

    /**
     * Metodo para mapear de un PostUserDto y un UserEntity
     * a un PlotUserEntity.
     *
     * @param plotUserEntity representa la relación entre un usuario y un lote.
     * @param postUserDto dto que contiene la información del usuario.
     * @param userSaved representa al usuario guardado.
     */
    public void mapPostToPlotUserEntity(PlotUserEntity plotUserEntity, PostUserDto postUserDto, UserEntity userSaved) {
        plotUserEntity.setPlotId(postUserDto.getPlot_id());
        plotUserEntity.setUser(userSaved);
        plotUserEntity.setCreatedDate(LocalDateTime.now());
        plotUserEntity.setLastUpdatedDate(LocalDateTime.now());
        plotUserEntity.setCreatedUser(postUserDto.getUserUpdateId());
        plotUserEntity.setLastUpdatedUser(postUserDto.getUserUpdateId());
    }

    /**
     * Metodo para mapear de un PostOwnerUserDto y un UserEntity
     * a un PlotUserEntity.
     *
     * @param plotUserEntity representa la relación entre un usuario y un lote.
     * @param postUserDto un dto que contiene la información del usuario.
     * @param userSaved un UserEntity que representa al usuario guardado.
     * @param plot representa el ID del lote.
     */
    public void mapPostOwnerToPlotUserEntity(PlotUserEntity plotUserEntity, PostOwnerUserDto postUserDto,
                                             UserEntity userSaved, Integer plot) {
        plotUserEntity.setPlotId(plot);
        plotUserEntity.setUser(userSaved);
        plotUserEntity.setCreatedDate(LocalDateTime.now());
        plotUserEntity.setLastUpdatedDate(LocalDateTime.now());
        plotUserEntity.setCreatedUser(postUserDto.getUserUpdateId());
        plotUserEntity.setLastUpdatedUser(postUserDto.getUserUpdateId());
    }

    /**
     * Metodo para mapear de un PostUserDto a un UserEntity.
     *
     * @param userEntity representa la entidad de un usuario.
     * @param user un dto que contiene la información del usuario.
     * @throws EntityNotFoundException si no encuentra un dniType con el id asignado.
     */
    public void mapUserPostToUserEntity(UserEntity userEntity, BasePostUser user) {

        userEntity.setName(user.getName());
        userEntity.setLastname(user.getLastname());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setDniType(dniTypeRepository.findById(user.getDni_type_id())
                .orElseThrow(() -> new EntityNotFoundException("DniType not found")));
        userEntity.setDni(user.getDni());
        userEntity.setActive(user.getActive());
        userEntity.setAvatar_url(user.getAvatar_url());
        userEntity.setDatebirth(user.getDatebirth());
        userEntity.setCreatedDate(LocalDateTime.now());
        userEntity.setCreatedUser(user.getUserUpdateId());
        userEntity.setLastUpdatedDate(LocalDateTime.now());
        userEntity.setLastUpdatedUser(user.getUserUpdateId());
        userEntity.setTelegram_id(user.getTelegram_id());
    }

    /**
     * Metodo para mapear de un UserEntity a un GetUserDto.
     *
     * @param userEntity representa la entidad de un usuario.
     * @param getUserDto dto que contiene la información del usuario.
     * @throws EntityNotFoundException si no encuentra el tipo de dni.
     */
    public void mapUserEntityToGet(UserEntity userEntity, GetUserDto getUserDto) {
        getUserDto.setId(userEntity.getId());
        getUserDto.setName(userEntity.getName());
        getUserDto.setLastname(userEntity.getLastname());
        getUserDto.setUsername(userEntity.getUsername());
        getUserDto.setPassword(userEntity.getPassword());
        getUserDto.setDni(userEntity.getDni());
        getUserDto.setDni_type(dniTypeRepository.findById(userEntity.getDniType().getId())
                .orElseThrow(() -> new EntityNotFoundException("DniType not found")).getDescription());
        getUserDto.setActive(userEntity.getActive());
        getUserDto.setAvatar_url(userEntity.getAvatar_url());
        getUserDto.setDatebirth(userEntity.getDatebirth());
        getUserDto.setTelegram_id(userEntity.getTelegram_id());
        getUserDto.setCreate_date(userEntity.getCreatedDate().toLocalDate());

        List<Integer> plotIds = plotUserRepository.findByUser(userEntity).stream()
                .map(PlotUserEntity::getPlotId)
                .toList();

        getUserDto.setPlot_id(plotIds.toArray(new Integer[0]));
    }

    /**
     * Metodo para mapear roles y contactos de un usuario a un GetUserDto.
     *
     * @param userEntity representa la entidad de un usuario.
     * @param getUserDto dto que contiene la información del usuario.
     */
    public void mapUserRolesAndContacts(UserEntity userEntity, GetUserDto getUserDto) {
        List<GetRoleDto> roleDtos = roleService.getRolesByUser(userEntity.getId());
        String[] roles = roleDtos.stream()
                .map(GetRoleDto::getDescription)
                .toArray(String[]::new);

        getUserDto.setRoles(roles);
        List<GetContactDto> contactDtos = restContact.getContactById(userEntity.getId());
        for (GetContactDto contactDto : contactDtos) {
            if (contactDto.getType_contact() == 1) {
                getUserDto.setEmail(contactDto.getValue().toLowerCase(Locale.forLanguageTag("es-ES")));
            } else {
                getUserDto.setPhone_number(contactDto.getValue());
            }
        }
    }

    /**
     * Metodo para mapear de un UserEntity y un RoleEntity a un UserRoleEntity.
     *
     * @param userEntity representa la entidad de un usuario.
     * @param roleEntity representa la entidad de un rol.
     * @param userRoleEntity representa la relación entre un usuario y un rol.
     */
    public void mapUserRolEntity(UserRoleEntity userRoleEntity, UserEntity userEntity, RoleEntity roleEntity) {
        userRoleEntity.setUser(userEntity);
        userRoleEntity.setRole(roleEntity);
        userRoleEntity.setCreatedDate(LocalDateTime.now());
        userRoleEntity.setLastUpdatedDate(LocalDateTime.now());
    }

    /**
     * Metodo que contiene las validaciones necesarias para crear un usuario.
     *
     * @param user datos del usuario a crear.
     */
    public void validateUser(BasePostUser user) {
        validateUsername(user.getUsername());
        validateEmail(user.getEmail());
        validateDni(user.getDni());
    }

    /**
     * Metodo para validar si existe un username igual guardado en la
     * base de datos.
     *
     * @param username un string que contiene el username a buscar.
     * @throws IllegalArgumentException si existe un username igual en la base de datos.
     */
    public void validateUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Error creating user: username already in use.");
        }
    }

    /**
     * Metodo para validar si existe un dni igual guardado en la
     * base de datos.
     *
     * @param dni un string que contiene el dni a buscar.
     * @throws IllegalArgumentException si existe un dni igual en la base de datos.
     */
    public void validateDni(String dni) {
        if (userRepository.findByDni(dni) != null) {
            throw new IllegalArgumentException("Error creating user: dni already in use.");
        }
    }

    /**
     * Metodo para validar si existe un email igual guardado en la
     * base de datos.
     *
     * @param email un string que contiene el email a buscar.
     * @throws IllegalArgumentException si existe un email igual en la base de datos.
     */
    public void validateEmail(String email) {
        List<String> emails = restContact.getAllEmails();   // Obtener todos los emails
        if (emails.contains(email)) {
            throw new IllegalArgumentException("Error creating user: email already in use.");
        }
    }

    /**
     * Obtener todos los usuarios.
     *
     * @return una lista con todos los usuarios.
     */
    @Override
    public List<GetUserDto> getAllUsers() {
        return userRepository.findAllActives().stream()
                .map(this::convertToUserDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener un usuario por medio de un userId.
     *
     * @param userId identificador de un usuario.
     * @return un usuario que coincida con el ID proporcionado.
     * @throws EntityNotFoundException si no encuentra un usuario con la misma id en la base de datos.
     */
    @Override
    public GetUserDto getUserById(Integer userId) {

        UserEntity  userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        return convertToUserDto(userEntity);
    }

    /**
     * Metodo para convertir un UserEntity a un GetUserDto.
     *
     * @param user representa la entidad de un usuario.
     * @return un GetUserDto con la información del usuario.
     */
    public GetUserDto convertToUserDto(UserEntity user) {
        GetUserDto userDto = new GetUserDto();
        mapUserEntityToGet(user, userDto);
        mapUserRolesAndContacts(user, userDto);
        return userDto;
    }

    /**
     * Metodo para mapear de un PutUserDto a un UserEntity.
     *
     * @param userEntity representa la entidad de un usuario.
     * @param putUserDto dto que contiene la información del usuario.
     * @throws EntityNotFoundException si no encuentra un dniType con el id asignado.
     */
    public void mapPutUserToUserEntity(PutUserDto putUserDto, UserEntity userEntity) {
        userEntity.setName(putUserDto.getName());
        userEntity.setLastname(putUserDto.getLastName());
        userEntity.setDni(putUserDto.getDni());
        userEntity.setDniType(dniTypeRepository.findById(putUserDto.getDni_type_id())
                .orElseThrow(() -> new EntityNotFoundException("DniType not found")));
        userEntity.setAvatar_url(putUserDto.getAvatar_url());
        userEntity.setDatebirth(putUserDto.getDatebirth());
        userEntity.setLastUpdatedDate(LocalDateTime.now());
        userEntity.setLastUpdatedUser(putUserDto.getUserUpdateId());
        userEntity.setTelegram_id(putUserDto.getTelegram_id());
    }

    /**
     * Actualiza un usuario.
     *
     * @param userId el ID del usuario a actualizar.
     * @param putUserDto el dto con la información necesaria para actualizar un usuario.
     * @throws EntityNotFoundException si no se encuentra un usuario con el ID proporcionado como parámetro
     * en la base de datos.
     * @return el usuario actualizado.
     */
    @Override
    @Transactional
    public GetUserDto updateUser(Integer userId, PutUserDto putUserDto) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        updateUserFields(user, putUserDto);
        updateUserRoles(user, putUserDto);
        updateUserContacts(user, putUserDto);

        GetUserDto getUserDto = modelMapper.map(user, GetUserDto.class);
        getUserDto.setRoles(putUserDto.getRoles());
        getUserDto.setEmail(putUserDto.getEmail());
        getUserDto.setPhone_number(putUserDto.getPhoneNumber());
        return getUserDto;
    }

    /**
     * Actualiza los campos de un usuario.
     *
     * @param user usuario a actualizar.
     * @param putUserDto con la información necesaria para actualizar un usuario.
     */
    public void updateUserFields(UserEntity user, PutUserDto putUserDto) {
        mapPutUserToUserEntity(putUserDto, user);
        userRepository.save(user);
    }

    /**
     * Actualiza los roles de un usuario.
     *
     * @param user usuario a actualizar los roles.
     * @param putUserDto con la información necesaria para actualizar los roles del usuario.
     */
    public void updateUserRoles(UserEntity user, PutUserDto putUserDto) {
        userRoleRepository.deleteByUser(user);
        assignRolesToUser(user, putUserDto.getRoles(), putUserDto.getUserUpdateId());
    }

    /**
     * Actualiza los contactos de un usuario.
     *
     * @param user usuario para actualizar los contactos.
     * @param putUserDto con la información necesaria para actualizar los contactos del usuario.
     */
    public void updateUserContacts(UserEntity user, PutUserDto putUserDto) {
        restContact.updateContact(user.getId(), putUserDto.getEmail(), 1, putUserDto.getUserUpdateId());
        restContact.updateContact(user.getId(), putUserDto.getPhoneNumber(), 2, putUserDto.getUserUpdateId());
    }

    /**
     * Obtener todos los usuarios por un estado.
     *
     * @param isActive representa el estado del usuario.
     * @return una lista de usuarios con ese estado.
     * @throws EntityNotFoundException si no se encuentra algún usuario con ese estado.
     */
    @Override
    public List<GetUserDto> getUsersByStatus(boolean isActive) {
        List<UserEntity> userEntities = userRepository.findByActive(isActive)
                .orElseThrow(() -> new EntityNotFoundException("Users not found"));

        return userEntities.stream()
                .map(this::convertToUserDto).
                collect(Collectors.toList());
    }

    /**
     * Realiza una baja lógica de un usuario.
     *
     * @param userId id del usuario a borrar.
     * @param userUpdateId id del usuario que realiza la acción.
     * @throws EntityNotFoundException si no encuentra un usuario asignado al ID pasado por parámetro.
     */
    @Override
    @Transactional
    public void deleteUser(Integer userId, Integer userUpdateId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        userEntity.setActive(false);
        userEntity.setLastUpdatedDate(LocalDateTime.now());
        userEntity.setLastUpdatedUser(userUpdateId);
        userRepository.save(userEntity);
        //restAccess.deleteAccess(userEntity.getDni()); todo: Descomentar cuando se necesite postear a acceso
    }

    /**
     * Obtener un usuario por email.
     * @param email correo electrónico de un usuario.
     * @throws EntityNotFoundException si no se encuentra un usuario con el email proporcionado por parámetro.
     * @throws EntityNotFoundException si no se encuentra un usuario con el ID coincidente al email.
     * @return un usuario si existe.
     */
    @Override
    public GetUserDto getUserByEmail(String email) {

        Integer userId = restContact.getUserIdByEmail(email);
        if (userId == null) {
            return null;
        }
        return getUserById(userId);
    }

    /**
     * Obtener un usuario por username.
     * @param username username de un usuario.
     * @throws EntityNotFoundException si no se encuentra un usuario con el username proporcionado por parámetro.
     * @throws EntityNotFoundException si no se encuentra un usuario con el ID coincidente al username.
     * @return un usuario si existe.
     */
    public GetUserDto getUserByUsername(String username) {
        // Usar orElse para devolver null si no se encuentra el usuario
        UserEntity userEntity = userRepository.findByUsername(username).orElse(null);

        // Si no se encuentra el usuario, simplemente retornar null
        if (userEntity == null) {
            return null;
        }

        // Si se encuentra el usuario, retornar el DTO correspondiente
        return getUserById(userEntity.getId());
    }

    /**
     * Obtener un usuario de rol "Owner" con un plotId especifíco.
     *
     * @param plotId identificador de un lote.
     * @throws EntityNotFoundException si no encuentra a un usuario asignado al lote según el ID del
     * lote proporcionado por parámetro.
     * @return un usuario si existe.
     */
    @Override
    public GetUserDto getUserByPlotIdAndOwnerRole(Integer plotId) {
        Optional<UserEntity> userEntity = userRepository.findUserByPlotIdAndOwnerRole(plotId);
        if (userEntity.isEmpty()) {
            throw new EntityNotFoundException("User not found with plot id: " + plotId);
        }
        UserEntity user = userEntity.get();
        return convertToUserDto(user);
    }

    /**
     * Obtener una lista de usuarios activos por lote.
     *
     * @param plotId identificador de un lote.
     * @throws EntityNotFoundException si no encuentra usuarios asignados al lote.
     * @return lista de GetUserDto.
     */
    @Override
    public List<GetUserDto> getAllUsersByPlotId(Integer plotId) {
        Optional<List<UserEntity>> userEntity = userRepository.findUsersByPlotId(plotId);
        if (userEntity.isEmpty()) {
            throw new EntityNotFoundException("Users not found with plot id: " + plotId);
        }
        List<UserEntity> users = userEntity.get();
        return users.stream()
                .map(this::convertToUserDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener un usuario por rol.
     * @param roleId identificador de rol.
     * @throws EntityNotFoundException si no se encuentra un usuario con el rol proporcionado por parámetro.
     * @return un usuario si existe.
     */
    @Override
    public List<GetUserDto> getUsersByRole(Integer roleId) {

        List<UserRoleEntity> usersRole = userRoleRepository.findByRoleId(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Users not found with role id: " + roleId));

        List<GetUserDto> usersDto = new ArrayList<>();

        for (UserRoleEntity userRoleEntity : usersRole) {
            UserEntity userEntity = userRepository.findById(userRoleEntity.getUser().getId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userRoleEntity.getUser().getId()));

            GetUserDto userDto = modelMapper.map(userEntity, GetUserDto.class);
            mapUserEntityToGet(userEntity, userDto);
            mapUserRolesAndContacts(userEntity, userDto);
            usersDto.add(userDto);
        }

        return usersDto;
    }

    /**
     * Verifica un inicio de sesión.
     *
     * @param postLoginDto dto con información requerida para verificar un inicio de sesión.
     * @return un booleano de confirmación.
     */
    @Override
    public GetUserDto verifyLogin(PostLoginDto postLoginDto) {
        GetUserDto user = this.getUserByUsername(postLoginDto.getEmail());

        // Si no se encuentra el usuario por email, buscar por nombre de usuario
        if (user == null) {
            user = this.getUserByEmail(postLoginDto.getEmail());
        }

        // Verificar si se encontró un usuario y si la contraseña es correcta
        if (user != null && passwordEncoder.checkPassword(postLoginDto.getPassword(), user.getPassword())) {
            return user;
        }

        // Retornar null si no se encontró el usuario o la contraseña es incorrecta
        return null;
    }

    /**
     * Cambia la contraseña de un usuario.
     * @param changePasswordDto DTO con las contraseñas actual y nueva.
     * @throws IllegalArgumentException si la contraseña actual es incorrecta.
     */
    @Transactional
    public void changePassword(ChangePassword changePasswordDto) {
        GetUserDto getUserDto = this.getUserByEmail(changePasswordDto.getEmail());
        if (getUserDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + changePasswordDto.getEmail());
        }
        UserEntity user = userRepository.findById(getUserDto.getId())
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found with email: " + changePasswordDto.getEmail()));


        if (!PasswordUtil.checkPassword(changePasswordDto.getCurrentPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Current password is incorrect.");
        }

        String hashedNewPassword = PasswordUtil.hashPassword(changePasswordDto.getNewPassword());
        user.setPassword(hashedNewPassword);

        userRepository.save(user);
    }

    /**
     * Cambia la contraseña de un usuario por una aleatoria.
     */
    @Transactional
    public void passwordRecovery(String userEmail){
        Integer userId = restContact.getUserIdByEmail(userEmail);
        if(userId == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User not found with email: " + userEmail);
        }
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found" ));

        String newPassword = PasswordGenerator.generateRandomPassword();
        String hashPassword = passwordEncoder.hashPassword(newPassword);
        userEntity.setPassword(hashPassword);
        userRepository.save(userEntity);

        RecoveryDto recoveryDto = new RecoveryDto();
        recoveryDto.setEmail(userEmail);
        recoveryDto.setPassword(newPassword);
        restNotifications.sendRecoveryEmail(recoveryDto);
    }

    /**
     * Envia un email de bienvenida a un usuario.
     * @param email email del usuario.
     */
    public void sendWelcomeEmail(String email){
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail(email);
        restNotifications.sendRegisterEmail(registerDto);
    }
}
