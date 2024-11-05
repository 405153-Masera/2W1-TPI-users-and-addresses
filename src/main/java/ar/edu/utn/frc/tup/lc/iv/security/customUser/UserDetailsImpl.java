//package ar.edu.utn.frc.tup.lc.iv.security.customUser;
//
//import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import java.util.Collection;
//import java.util.Collections;
//
//public class UserDetailsImpl implements UserDetails {
//
//    private final GetUserDto user;
//
//    public UserDetailsImpl(GetUserDto user) {
//        this.user = user;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.emptyList(); // Sin roles, devuelve una lista vacía
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword(); // No lo devuelves en el DTO, pero podrías manejarlo en la entidad o un getter separado.
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getEmail();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return user.getActive();
//    }
//}