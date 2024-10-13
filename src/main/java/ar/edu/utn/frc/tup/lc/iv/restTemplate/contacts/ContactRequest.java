package ar.edu.utn.frc.tup.lc.iv.restTemplate.contacts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {
    private Integer userId;
    private Integer contactTypeId;
    private String value;
}
