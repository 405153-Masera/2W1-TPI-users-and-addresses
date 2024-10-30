package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetDniTypeDto;
import ar.edu.utn.frc.tup.lc.iv.services.Interfaces.DniTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dniType")
public class DniTypeController {

    private final DniTypeService dniTypeService;

    public DniTypeController(DniTypeService dniTypeService){
        this.dniTypeService = dniTypeService;
    }

    @GetMapping
    public List<GetDniTypeDto> getAllDniTypes() {
        return dniTypeService.findAll();
    }

    @GetMapping("/{id}")
    public GetDniTypeDto getDniTypeById(@PathVariable Integer id) {
        return dniTypeService.findById(id);
    }

    @PostMapping
    public GetDniTypeDto createDniType(@RequestBody GetDniTypeDto dniTypeDto) {
        return dniTypeService.create(dniTypeDto);
    }

    @PutMapping("/{id}")
    public GetDniTypeDto updateDniType(@PathVariable Integer id, @RequestBody GetDniTypeDto dniTypeDto) {
        return dniTypeService.update(id, dniTypeDto);
    }

}
