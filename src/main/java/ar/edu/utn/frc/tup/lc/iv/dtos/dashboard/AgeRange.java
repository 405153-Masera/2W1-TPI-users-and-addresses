package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgeRange {
    private String ageRange;
    private Long count;
    private Double percentage;
}
