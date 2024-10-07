package ar.edu.utn.frc.tup.lc.iv.services.Implementation;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetRoleDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostUserDto;
import ar.edu.utn.frc.tup.lc.iv.entities.RoleEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserRoleEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.RoleRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.UserRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.UserRoleRepository;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.RoleService;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private RoleService roleService;

    public GetUserDto createUser(PostUserDto postUserDto) {

        // Validaciones por si el username o el email ya existen
        validateUsername(postUserDto.getUsername());
        validateEmail(postUserDto.getEmail());

        // Crear un nuevo UserEntity y asignar los valores del DTO
        UserEntity userEntity = new UserEntity();
        userEntity.setName(postUserDto.getName());
        userEntity.setLastname(postUserDto.getLastname());
        userEntity.setUsername(postUserDto.getUsername());
        userEntity.setPassword(postUserDto.getPassword());
        userEntity.setEmail(postUserDto.getEmail());
        userEntity.setDni(postUserDto.getDni());
        userEntity.setContact_id(postUserDto.getContact_id());
        userEntity.setActive(postUserDto.getActive());
        userEntity.setAvatar_url(postUserDto.getAvatar_url());
        userEntity.setDatebirth(postUserDto.getDatebirth());

        // Establecer valores de auditoría
        userEntity.setCreatedDate(LocalDateTime.now());
        userEntity.setCreatedUser(1);  // ID del usuario creador
        userEntity.setLastUpdatedDate(LocalDateTime.now());
        userEntity.setLastUpdatedUser(1);  // ID del usuario que realiza la actualización

        // Guardar el usuario en la base de datos
        UserEntity savedUser = userRepository.save(userEntity);

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
                userRoleEntity.setUser(savedUser);  // Usuario recién guardado
                userRoleEntity.setRole(roleEntity);  // Rol encontrado
                userRoleEntity.setCreatedDate(LocalDateTime.now()); //Pongo la fecha de ahora
                userRoleEntity.setCreatedUser(1);  // ID del usuario que realiza la operación
                userRoleEntity.setLastUpdatedDate(LocalDateTime.now());
                userRoleEntity.setLastUpdatedUser(1);  // ID del usuario que realiza la operación

                // Guardar la relación en la tabla intermedia
                userRoleRepository.save(userRoleEntity);

                // Agregar la descripción del rol a la lista de roles asignados
                assignedRoles.add(roleDesc);
            } else {
                // Si no se encuentra el rol, lanzar una excepción
                throw new RuntimeException("El rol con la descripción '" + roleDesc + "' no existe.");
            }
        }

        // Mapear el UserEntity guardado a GetUserDto
        GetUserDto getUserDto = modelMapper.map(savedUser, GetUserDto.class);
        getUserDto.setRoles(assignedRoles.toArray(new String[0]));  // Asignar los roles encontrados al DTO

        return getUserDto;
    }

    //Metodo para validar si existe alguien con ese username
    private void validateUsername(String username) {
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Error al crear el usuario: el nombre de usuario ya está en uso.");
        }
    }

    //Metodo para validar si existe alguien con ese email
    private void validateEmail(String email) {
        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Error al crear el usuario: el correo electrónico ya está en uso.");
        }
    }

    public List<GetUserDto> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();

        List<GetUserDto> getUserDtos = userEntities.stream()
                .map(userEntity -> {
                    GetUserDto getUserDto = modelMapper.map(userEntity, GetUserDto.class);

                    List<GetRoleDto> roleDtos = roleService.getRolesByUser(userEntity.getId());

                    // Convierto la lista de GetRoleDto a un arreglo de String[] (solo para ver los nombres)
                    String[] roles = roleDtos.stream()
                            .map(GetRoleDto::getDescription) // Mapeamos cada GetRoleDto a su descripción
                            .toArray(String[]::new); // Convertimos el Stream a un arreglo de String

                    getUserDto.setRoles(roles);  // Asignar los roles como String[] al DTO

                    return getUserDto;
                })
                .collect(Collectors.toList());

        return getUserDtos;
    }

}



