package com.vironit.onlinepharmacy.controller;

import com.vironit.onlinepharmacy.dto.ProcurementDto;
import com.vironit.onlinepharmacy.model.Procurement;
import com.vironit.onlinepharmacy.service.procurement.ProcurementService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/procurements")
public class ProcurementController {

    private final ProcurementService procurementService;

    public ProcurementController(ProcurementService procurementService) {
        this.procurementService = procurementService;
    }

    @GetMapping
    public Collection<Procurement> getAll() {
        return procurementService.getAll();
    }

    @PostMapping
    public long add(@RequestBody ProcurementDto procurementDto) {
        return procurementService.add(procurementDto);
    }

    @GetMapping("/{id}")
    public Procurement get(@PathVariable Long id) {
        return procurementService.get(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody ProcurementDto procurementDto, @PathVariable Long id) {
        procurementDto.setId(id);
        procurementService.update(procurementDto);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        procurementService.remove(id);
    }

    @PatchMapping("/{id}/confirm")
    public void approve(@PathVariable Long id) {
        procurementService.approveProcurement(id);
    }

    @PatchMapping("/{id}/complete")
    public void complete(@PathVariable Long id) {
        procurementService.completeProcurement(id);
    }

    @PatchMapping("/{id}/cancel")
    public void cancel(@PathVariable Long id) {
        procurementService.cancelProcurement(id);
    }
}