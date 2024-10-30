package ar.edu.utn.frc.tup.lc.iv.services.Interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetDniTypeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DniTypeService {
    List<GetDniTypeDto> findAll();
    GetDniTypeDto findById(Integer id);
    GetDniTypeDto create(GetDniTypeDto dniTypeDto);
    GetDniTypeDto update(Integer id, GetDniTypeDto dniTypeDto);
}
