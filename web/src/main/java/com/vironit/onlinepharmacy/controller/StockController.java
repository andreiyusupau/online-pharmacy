package com.vironit.onlinepharmacy.controller;

import com.vironit.onlinepharmacy.dto.PositionDto;
import com.vironit.onlinepharmacy.model.StockPosition;
import com.vironit.onlinepharmacy.service.stock.StockService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
        public Collection<StockPosition> getAll() {
            return stockService.getAll();
        }

        @PostMapping
        public long add(@RequestBody  @Valid PositionDto positionDto) {
            return stockService.add(positionDto);
        }

        @GetMapping("/{id}")
        public StockPosition get(@PathVariable Long id) {
            return stockService.get(id);
        }

        @PutMapping("/{id}")
        public void update(@RequestBody  @Valid PositionDto positionDto, @PathVariable Long id) {
            positionDto.setId(id);
            stockService.update(positionDto);
        }

        @DeleteMapping("/{id}")
        public void remove(@PathVariable Long id) {
            stockService.remove(id);
        }

    }
