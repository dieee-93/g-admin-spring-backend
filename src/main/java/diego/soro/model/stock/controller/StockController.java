package diego.soro.model.stock.controller;

import diego.soro.model.stock.StockEntry;
import diego.soro.model.stock.StockSnapshot;
import diego.soro.model.stock.service.StockEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {

    @Autowired
    private final StockEntryService stockEntryService;

    @PostMapping("/entries")
    public StockEntry createStockMovement(@RequestBody Map<String, Object> request) {
        Long rawMaterialId = Long.valueOf(request.get("rawMaterialId").toString());
        Double quantity = Double.valueOf(request.get("quantity").toString());
        Double cost = request.get("cost") != null ? Double.valueOf(request.get("cost").toString()) : null;
        String reason = request.get("reason").toString();
        String notes = request.get("notes") != null ? request.get("notes").toString() : null;

        return stockEntryService.registerStockMovement(rawMaterialId, quantity, cost, reason, notes);
    }

    @GetMapping("/snapshot/{rawMaterialId}")
    public StockSnapshot getStockSnapshot(@PathVariable Long rawMaterialId) {
        return stockEntryService.getSnapshot(rawMaterialId);
    }
}