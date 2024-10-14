package ar.edu.utn.frc.tup.lc.iv.restTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetContactDto {
    private Integer type_contact;
    private String value;
}
