package ar.edu.utn.frc.tup.lc.iv.helpers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.BasePostUser;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutUserDto;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotUserEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.RoleEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserTestHelper {

    public static PostUserDto createPostUserDto() {
        PostUserDto postUserDto = new PostUserDto();
        postUserDto.setName("Juan");
        postUserDto.setLastname("Perez");
        postUserDto.setUsername("JuanPa");
        postUserDto.setPassword("40770092");
        postUserDto.setEmail("juanpa@gmail.com");
        postUserDto.setPhone_number("45645465898");
        postUserDto.setDni_type_id(1);
        postUserDto.setDni("40770092");
        postUserDto.setActive(true);
        postUserDto.setAvatar_url("urlAvatar");
        postUserDto.setDatebirth(LocalDate.now());
        postUserDto.setRoles(new String[]{"Gerente"});
        postUserDto.setPlot_id(2);
        postUserDto.setTelegram_id(4);
        postUserDto.setUserUpdateId(1);
        return postUserDto;
    }

    public static PostOwnerUserDto createPostOwnerUserDto() {
        PostOwnerUserDto postOwnerUserDto = new PostOwnerUserDto();
        postOwnerUserDto.setName("Juan");
        postOwnerUserDto.setLastname("Perez");
        postOwnerUserDto.setUsername("JuanPa");
        postOwnerUserDto.setPassword("40770092");
        postOwnerUserDto.setEmail("juapa@gmail.com");
        postOwnerUserDto.setPhone_number("45645465898");
        postOwnerUserDto.setDni_type_id(1);
        postOwnerUserDto.setDni("40770092");
        postOwnerUserDto.setActive(true);
        postOwnerUserDto.setAvatar_url("urlAvatar");
        postOwnerUserDto.setDatebirth(LocalDate.now());
        postOwnerUserDto.setRoles(new String[]{"Gerente"});
        postOwnerUserDto.setPlot_id(new Integer[]{2,3});
        postOwnerUserDto.setTelegram_id(4);
        postOwnerUserDto.setUserUpdateId(1);
        return postOwnerUserDto;
    }

    public static UserEntity createUserEntity(BasePostUser postUserDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(3);
        userEntity.setName(postUserDto.getName());
        userEntity.setLastname(postUserDto.getLastname());
        userEntity.setUsername(postUserDto.getUsername());
        userEntity.setPassword(postUserDto.getPassword());
        userEntity.setDni(postUserDto.getDni());
        userEntity.setActive(postUserDto.getActive());
        userEntity.setAvatar_url(postUserDto.getAvatar_url());
        userEntity.setDatebirth(postUserDto.getDatebirth());

        userEntity.setCreatedDate(LocalDateTime.now());
        userEntity.setCreatedUser(postUserDto.getUserUpdateId());
        userEntity.setLastUpdatedDate(LocalDateTime.now());
        userEntity.setLastUpdatedUser(postUserDto.getUserUpdateId());
        return userEntity;
    }

    public static GetUserDto createGetUserDto(UserEntity userEntity, BasePostUser postUserDto) {
        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(userEntity.getId());
        getUserDto.setName(userEntity.getName());
        getUserDto.setLastname(userEntity.getLastname());
        getUserDto.setUsername(userEntity.getUsername());
        getUserDto.setPassword(userEntity.getPassword());
        getUserDto.setDni(userEntity.getDni());
        getUserDto.setActive(userEntity.getActive());
        getUserDto.setAvatar_url(userEntity.getAvatar_url());
        getUserDto.setDatebirth(userEntity.getDatebirth());
        getUserDto.setRoles(postUserDto.getRoles());
        getUserDto.setPhone_number(postUserDto.getPhone_number());

        if(postUserDto instanceof PostUserDto){
            List<Integer> plot = new ArrayList<>();
            plot.add(((PostUserDto) postUserDto).getPlot_id());
            getUserDto.setPlot_id(plot.toArray(new Integer[0]));
        }else{
            getUserDto.setPlot_id(((PostOwnerUserDto) postUserDto).getPlot_id());
        }
        return getUserDto;
    }

    public static GetUserDto createGetUserDto(){
        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(1);
        getUserDto.setName("Juan");
        getUserDto.setLastname("Perez");
        getUserDto.setUsername("JuanPa");
        getUserDto.setPassword("40770092");
        getUserDto.setEmail("juapa@gmail.com");
        getUserDto.setPhone_number("45645465898");
        getUserDto.setDni("40770092");
        getUserDto.setDni_type("DNI");
        getUserDto.setActive(true);
        getUserDto.setAvatar_url("urlAvatar");
        getUserDto.setDatebirth(LocalDate.now());
        getUserDto.setRoles(new String[]{"Gerente"});
        getUserDto.setPlot_id(new Integer[]{2,3});
        getUserDto.setTelegram_id(4);
        return getUserDto;
    }

    public static GetUserDto createGetUserDto(PutUserDto putUserDto, Integer userId) {
        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(userId);
        getUserDto.setName(putUserDto.getName());
        getUserDto.setLastname(putUserDto.getLastName());
        getUserDto.setEmail(putUserDto.getEmail());
        getUserDto.setPhone_number(putUserDto.getPhoneNumber());
        getUserDto.setDni(putUserDto.getDni());
        getUserDto.setAvatar_url(putUserDto.getAvatar_url());
        getUserDto.setDatebirth(putUserDto.getDatebirth());
        getUserDto.setRoles(putUserDto.getRoles());
        return getUserDto;
    }

    public static RoleEntity createRoleEntity(String description, int createdUserId) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(null);
        roleEntity.setDescription(description);
        roleEntity.setCreatedDate(LocalDateTime.now());
        roleEntity.setLastUpdatedDate(LocalDateTime.now());
        roleEntity.setCreatedUser(createdUserId);
        roleEntity.setLastUpdatedUser(createdUserId);
        return roleEntity;
    }


    public static PlotUserEntity createPlotUserEntity(UserEntity userEntity, int plotId, int createdUserId) {
        PlotUserEntity plotUserEntity = new PlotUserEntity();
        plotUserEntity.setId(null);
        plotUserEntity.setPlotId(plotId);
        plotUserEntity.setUser(userEntity);
        plotUserEntity.setCreatedDate(LocalDateTime.now());
        plotUserEntity.setLastUpdatedDate(LocalDateTime.now());
        plotUserEntity.setCreatedUser(createdUserId);
        plotUserEntity.setLastUpdatedUser(createdUserId);
        return plotUserEntity;
    }
}
