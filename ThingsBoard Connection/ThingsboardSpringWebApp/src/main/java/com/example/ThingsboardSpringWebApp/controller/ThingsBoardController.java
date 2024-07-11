package com.example.ThingsboardSpringWebApp.controller;

import com.example.thingsboard.service.ThingsBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/thingsboard")
public class ThingsBoardController {

    @Autowired
    private ThingsBoardService thingsBoardService;

    @GetMapping("/telemetry")
    public String getTelemetryData(
            @RequestParam String entityType,
            @RequestParam String entityId,
            @RequestParam String keys,
            @RequestParam long startTs,
            @RequestParam long endTs,
            @RequestParam int limit,
            @RequestParam String orderBy) {
        return thingsBoardService.getTelemetryData(entityType, entityId, keys, startTs, endTs, limit, orderBy);
    }

    @PostMapping("/telemetry/{entityId}")
    public void sendTelemetryData(@PathVariable String entityId, @RequestBody String telemetryData) {
        thingsBoardService.sendTelemetryData(entityId, telemetryData);
    }
}
