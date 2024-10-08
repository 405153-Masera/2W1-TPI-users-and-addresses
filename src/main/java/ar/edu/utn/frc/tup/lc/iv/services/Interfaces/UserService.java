package ar.edu.utn.frc.tup.lc.iv.services.Interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutUserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    GetUserDto createUser(PostUserDto postUserDto);
    List<GetUserDto> getAllUsers();
    GetUserDto updateUser(PutUserDto putUserDto);
    List<GetUserDto> getUsersByStatus(boolean active);
    void deleteUser(Integer userId);
    Optional<GetUserDto> getUserByEmail(String email);
}
