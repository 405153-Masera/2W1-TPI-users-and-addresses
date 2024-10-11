package ar.edu.utn.frc.tup.lc.iv.services.Interfaces;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetRoleDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostRoleDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostUserDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface RoleService {
    List<GetRoleDto> getAllRoles();
    List<GetRoleDto> getRolesByUser(int userId);
    GetRoleDto createRole(PostRoleDto postRoleDto);
}
