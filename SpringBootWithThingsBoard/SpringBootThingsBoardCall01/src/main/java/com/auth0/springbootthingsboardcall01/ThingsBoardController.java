package com.auth0.springbootthingsboardcall01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
}
