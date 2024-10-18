package ar.edu.utn.frc.tup.lc.iv.services.Implementation;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetRoleDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostLoginDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutUserDto;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotUserEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.RoleEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserRoleEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotUserRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.RoleRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.UserRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.UserRoleRepository;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.GetContactDto;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.RestContact;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.RoleService;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PlotUserRepository plotUserRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RestContact restContact;

    @Override
    @Transactional
    public GetUserDto createUser(PostUserDto postUserDto) {

        // Validaciones por si el username o el email ya existen
        validateUsername(postUserDto.getUsername());
        validateEmail(postUserDto.getEmail());

        // Crear un nuevo UserEntity y asignar los valores del DTO
        UserEntity userEntity = new UserEntity();
        //Mapeamos con el metodo
        mapUserPostToUserEntity(userEntity, postUserDto);
        System.out.println(userEntity);
        // Guardar el usuario en la base de datos
        UserEntity savedUser = userRepository.save(userEntity);
        System.out.println(savedUser);

        // Obtener los roles del PostUserDto
        String[] roleDescriptions = postUserDto.getRoles();

        // Lista para almacenar los roles encontrados
        List<String> assignedRoles = new ArrayList<>();

        // Asociar roles al usuario
        for (String roleDesc : roleDescriptions) {
            // Buscar el rol por su descripción
            RoleEntity roleEntity = roleRepository.findByDescription(roleDesc);

            if (roleEntity != null) {
                // Crear una nueva relación en la tabla intermedia UserRoles
                UserRoleEntity userRoleEntity = new UserRoleEntity();
                mapUserRolEntity (userRoleEntity,savedUser,roleEntity);
                userRoleEntity.setLastUpdatedUser(postUserDto.getUserUpdateId());
                userRoleEntity.setCreatedUser(postUserDto.getUserUpdateId());
                // Guardar la relación en la tabla intermedia
                userRoleRepository.save(userRoleEntity);
                // Agregar la descripción del rol a la lista de roles asignados
                assignedRoles.add(roleDesc);
            } else {
                // Si no se encuentra el rol, lanzar una excepción
                throw new EntityNotFoundException("Role not found with description: " + roleDesc);
            }
        }

        // Guardar relacion plotUser
        PlotUserEntity plotUserEntity = new PlotUserEntity();
        mapPostToPlotUserEntity(plotUserEntity,postUserDto,savedUser);
        plotUserRepository.save(plotUserEntity);

        //guardar contactos
        boolean emailSaved = restContact.saveContact(savedUser.getId(), postUserDto.getEmail(), 1);
        boolean phoneSaved = restContact.saveContact(savedUser.getId(), postUserDto.getPhone_number(), 2);

        if (!emailSaved && !phoneSaved) {
            throw new RuntimeException("Failed to save contact information.");
        }

        // Mapear el UserEntity guardado a GetUserDto
        GetUserDto getUserDto = modelMapper.map(savedUser, GetUserDto.class);
        getUserDto.setRoles(assignedRoles.toArray(new String[0]));  // Asignar los roles encontrados al DTO
        getUserDto.setEmail(postUserDto.getEmail());
        getUserDto.setPhone_number(postUserDto.getPhone_number());
        getUserDto.setPlot_id(postUserDto.getPlot_id());

        return getUserDto;
    }

    public void mapPostToPlotUserEntity(PlotUserEntity plotUserEntity, PostUserDto postUserDto, UserEntity savedUser) {
        plotUserEntity.setPlotId(postUserDto.getPlot_id());
        plotUserEntity.setUser(savedUser);
        plotUserEntity.setCreatedDate(LocalDateTime.now());
        plotUserEntity.setLastUpdatedDate(LocalDateTime.now());
        plotUserEntity.setCreatedUser(postUserDto.getUserUpdateId());
        plotUserEntity.setLastUpdatedUser(postUserDto.getUserUpdateId());
    };

    // Metodo
    public void mapUserPostToUserEntity(UserEntity userEntity , PostUserDto postUserDto) {

        userEntity.setName(postUserDto.getName());
        userEntity.setLastname(postUserDto.getLastname());
        userEntity.setUsername(postUserDto.getUsername());
        userEntity.setPassword(postUserDto.getPassword());
        userEntity.setDni(postUserDto.getDni());
        userEntity.setActive(postUserDto.getActive());
        userEntity.setAvatar_url(postUserDto.getAvatar_url());
        userEntity.setDatebirth(postUserDto.getDatebirth());
        userEntity.setCreatedDate(LocalDateTime.now());
        userEntity.setCreatedUser(postUserDto.getUserUpdateId());  // ID del usuario creador
        userEntity.setLastUpdatedDate(LocalDateTime.now());
        userEntity.setLastUpdatedUser(postUserDto.getUserUpdateId());  // ID del usuario que realiza la actualización
        userEntity.setTelegram_id(postUserDto.getTelegram_id());
    }

    // Metodo para mepear el UserEntity a GetUserDto
    public GetUserDto mapUserEntityToGet(UserEntity userEntity , GetUserDto getUserDto) {
        getUserDto.setId(userEntity.getId());
        getUserDto.setName(userEntity.getName());
        getUserDto.setLastname(userEntity.getLastname());
        getUserDto.setUsername(userEntity.getUsername());
        getUserDto.setPassword(userEntity.getPassword());
        getUserDto.setDni(userEntity.getDni());
        getUserDto.setActive(userEntity.getActive());
        getUserDto.setAvatar_url(userEntity.getAvatar_url());
        getUserDto.setDatebirth(userEntity.getDatebirth());
        getUserDto.setTelegram_id(userEntity.getTelegram_id());

        PlotUserEntity plotUserEntity = plotUserRepository.findByUser(userEntity);
        if(plotUserEntity != null){
            getUserDto.setPlot_id(plotUserEntity.getPlotId());
        }

        return getUserDto;
    }

    // Metodo para mapear los Roles y Contactos de un usuario
    public void mapUserRolesAndContacts(UserEntity userEntity , GetUserDto getUserDto) {
        List<GetRoleDto> roleDtos = roleService.getRolesByUser(userEntity.getId());

        // Convierto la lista de GetRoleDto a un arreglo de String[] (solo para ver los nombres)
        String[] roles = roleDtos.stream()
                .map(GetRoleDto::getDescription) // Mapeamos cada GetRoleDto a su descripción
                .toArray(String[]::new); // Convertimos el Stream a un arreglo de String

        getUserDto.setRoles(roles);  // Asignar los roles como String[] al DTO

        // Buscamos los contactos del usuario y los asignamos
        List<GetContactDto> contactDtos = restContact.getContactById(userEntity.getId());
        for (GetContactDto contactDto : contactDtos) {
            if(contactDto.getType_contact() == 1){ // Si el valor es 1, es un email
                getUserDto.setEmail(contactDto.getValue().toLowerCase()); //Guardamos email en minuscula
            }else{ // Si no, es un teléfono
                getUserDto.setPhone_number(contactDto.getValue()); //Guardamos el telefono
            }
        }
    }

    // Metodo para mapear la entidad UserRole
    public void mapUserRolEntity(UserRoleEntity userRoleEntity , UserEntity userEntity , RoleEntity roleEntity) {
        userRoleEntity.setUser(userEntity);  // Usuario recién guardado
        userRoleEntity.setRole(roleEntity);  // Rol encontrado
        userRoleEntity.setCreatedDate(LocalDateTime.now()); //Pongo la fecha de ahora
        userRoleEntity.setLastUpdatedDate(LocalDateTime.now());
    }

    //Metodo para validar si existe alguien con ese username
    public void validateUsername(String username) {
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Error creating user: username already in use.");
        }
    }

    // Metodo para validar si existe alguien con ese email
    public void validateEmail(String email) {
        List<String> emails = restContact.getAllEmails();   // Obtener todos los emails
        if (emails.contains(email)) {
            throw new IllegalArgumentException("Error creating user: email already in use.");
        }
    }

    public List<GetUserDto> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAllActives();

        List<GetUserDto> getUserDtos = userEntities.stream()
                .map(userEntity -> {
                    GetUserDto getUserDto = new GetUserDto();
                    mapUserEntityToGet(userEntity,getUserDto);
                    mapUserRolesAndContacts(userEntity,getUserDto);

                    return getUserDto;
                })
                .collect(Collectors.toList());

        return getUserDtos;
    }

    @Override
    public GetUserDto getUserById(Integer userId) {

        UserEntity  userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        GetUserDto getUserDto = new GetUserDto();
        mapUserEntityToGet(userEntity,getUserDto);
        mapUserRolesAndContacts(userEntity,getUserDto);

        return getUserDto;
    }

    public void mapPutUserToUserEntiy(PutUserDto putUserDto, UserEntity userEntity) {
        userEntity.setName(putUserDto.getName());
        userEntity.setLastname(putUserDto.getLastName());
        userEntity.setDni(putUserDto.getDni());
        userEntity.setAvatar_url(putUserDto.getAvatar_url());
        userEntity.setDatebirth(putUserDto.getDatebirth());
        userEntity.setLastUpdatedDate(LocalDateTime.now());
        userEntity.setLastUpdatedUser(putUserDto.getUserUpdateId());
        userEntity.setTelegram_id(putUserDto.getTelegram_id());
    }

    @Override
    @Transactional
    public GetUserDto updateUser(Integer userId,PutUserDto putUserDto) {

        Optional<UserEntity> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty()){
            throw new EntityNotFoundException("User not found with id: " + userId);
        }

        UserEntity user = optionalUser.get();
        //Utilizamos el metodo para mapear el user
        mapPutUserToUserEntiy(putUserDto,user);
        UserEntity userSaved = userRepository.save(user);

        //Borramos la relacion de la tabla intermedia entre Rol y User
        userRoleRepository.deleteByUser(user);

        String[] roles = putUserDto.getRoles();

        // Asignar los nuevos roles al usuario
        List<String> assignedRoles = new ArrayList<>();
        for (String roleDesc : roles) {
            // Buscar el rol por su descripción
            RoleEntity role = roleRepository.findByDescription(roleDesc);
            if (role == null) {
                throw new EntityNotFoundException("Role not found with description: " + roleDesc);
            }

            // Crear una nueva relación en la tabla intermedia UserRoles
            UserRoleEntity userRoleEntity = new UserRoleEntity();

            //Mapeamos la entidad UserRoleEntity
            userRoleEntity.setUser(user);
            userRoleEntity.setRole(role);
            userRoleEntity.setCreatedDate(LocalDateTime.now());
            userRoleEntity.setCreatedUser(putUserDto.getUserUpdateId());
            userRoleEntity.setLastUpdatedDate(LocalDateTime.now());
            userRoleEntity.setLastUpdatedUser(putUserDto.getUserUpdateId());

            // Guardar la relación en la tabla intermedia
            userRoleRepository.save(userRoleEntity);

            // Agregar la descripción del rol a la lista de roles asignados
            assignedRoles.add(roleDesc);
        }
        
        // Actualizar los contactos del usuario
        restContact.updateContact(userSaved.getId(), putUserDto.getEmail(), 1);
        restContact.updateContact(userSaved.getId(), putUserDto.getPhoneNumber(), 2);

        // Mapear el usuario actualizado a GetUserDto
        GetUserDto getUserDto = modelMapper.map(userSaved, GetUserDto.class);
        getUserDto.setRoles(assignedRoles.toArray(new String[0])); // Asignar los roles al DTO

        // Asignar los contactos actualizados
        getUserDto.setEmail(putUserDto.getEmail());
        getUserDto.setPhone_number(putUserDto.getPhoneNumber());

        return getUserDto;
    }

    @Override
    public List<GetUserDto> getUsersByStatus(boolean isActive){
        List<UserEntity> userEntities = userRepository.findByActive(isActive)
                .orElseThrow(() -> new EntityNotFoundException("Users not found"));

        return userEntities.stream()
                .map(userEntity -> {
                    GetUserDto getUserDto = new GetUserDto();
                    mapUserEntityToGet(userEntity,getUserDto);
                    mapUserRolesAndContacts(userEntity,getUserDto);

                    return getUserDto;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId,Integer userUpdateId) {
        // Buscar el usuario por su ID
        Optional<UserEntity> optionalUser = userRepository.findById(userId);

        // Si no se encuentra el usuario, lanzar una excepción
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("Usuario no encontrado con la id: " + userId);
        }
        // Obtener el usuario
        UserEntity userEntity = optionalUser.get();
        // Realizar la baja lógica: marcar el usuario como inactivo
        userEntity.setActive(false);
        // Actualizar la fecha y usuario que realiza la baja
        userEntity.setLastUpdatedDate(LocalDateTime.now());
        userEntity.setLastUpdatedUser(userUpdateId);
        // Guardar los cambios en la base de datos
        userRepository.save(userEntity);
    }

    @Override
    public GetUserDto getUserByEmail(String email) {

        Integer userId = restContact.getUserIdByEmail(email);
        if (userId == null) {
            throw new EntityNotFoundException("User not found with email: " + email);
        }

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        GetUserDto getUserDto = new GetUserDto();
        mapUserEntityToGet(userEntity,getUserDto);
        mapUserRolesAndContacts(userEntity,getUserDto);
        return getUserDto;
    }

    @Override
    public GetUserDto getUserByPlotIdAndOwnerRole(Integer plotId) {
        Optional<UserEntity> userEntity = userRepository.findUsersByPlotIdAndOwnerRole(plotId);
        if (userEntity.isEmpty()) {
            throw new EntityNotFoundException("User not found with plot id: " + plotId);
        }

        UserEntity user = userEntity.get();
        GetUserDto getUserDto = new GetUserDto();
        mapUserEntityToGet(user,getUserDto);
        mapUserRolesAndContacts(user,getUserDto);
        return getUserDto;
    }

    @Override
    public List<GetUserDto> getUsersByRole(Integer roleId) {

        List<UserRoleEntity> usersRole = userRoleRepository.findByRoleId(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Users not found with role id: " + roleId));

        List<GetUserDto> usersDto = new ArrayList<>();

        for (UserRoleEntity userRoleEntity : usersRole){
            UserEntity userEntity = userRepository.findById(userRoleEntity.getUser().getId()).get();

            GetUserDto userDto = modelMapper.map(userEntity, GetUserDto.class);
            mapUserEntityToGet(userEntity, userDto);
            mapUserRolesAndContacts(userEntity, userDto);
            usersDto.add(userDto);
        }

        return usersDto;
    }

    @Override
    public boolean verifyLogin(PostLoginDto postLoginDto) {
        GetUserDto user = this.getUserByEmail(postLoginDto.getEmail());
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(postLoginDto.getPassword());

    }
}



