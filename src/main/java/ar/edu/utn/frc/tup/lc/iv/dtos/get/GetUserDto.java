package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDto {
    private Integer id;
    private String name;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String dni;
    private Integer contact_id;
    private Boolean active;
    private String avatar_url;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate datebirth;
    private String[] roles ;

}
