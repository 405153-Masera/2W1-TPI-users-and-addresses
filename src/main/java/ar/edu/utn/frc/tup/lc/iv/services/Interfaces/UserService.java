package ar.edu.utn.frc.tup.lc.iv.services.Interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostLoginDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutUserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    GetUserDto createUser(PostUserDto postUserDto);
    List<GetUserDto> getAllUsers();
    GetUserDto getUserById(Integer userId);
    GetUserDto updateUser(Integer userId,PutUserDto putUserDto);
    List<GetUserDto> getUsersByStatus(boolean active);
    void deleteUser(Integer userId,Integer userUpdateId);
    GetUserDto getUserByEmail(String email);
    GetUserDto getUserByPlotIdAndOwnerRole(Integer plotId);
    List<GetUserDto> getAllUsersByPlotId(Integer plotId);
    List<GetUserDto> getUsersByRole(Integer roleId);
    GetUserDto verifyLogin(PostLoginDto postLoginDto);
}
