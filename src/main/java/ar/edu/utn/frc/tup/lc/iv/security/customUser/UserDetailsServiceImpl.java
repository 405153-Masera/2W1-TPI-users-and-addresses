//package ar.edu.utn.frc.tup.lc.iv.security.customUser;
//
//import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetUserDto;
//import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        GetUserDto user = userService.getUserByEmail(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
//        }
//        return new UserDetailsImpl(user);
//    }
//}
