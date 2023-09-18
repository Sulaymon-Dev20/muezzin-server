package com.suyo.muezzin.model;

import com.batoulapps.adhan.Coordinates;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GeoIP {
    @Schema(title = "IP Address", example = "192.168.0.1", description = "The IP address of the device")
    private String ipAddress;
    @Schema(title = "Device", example = "Mobile", description = "The type of device")
    private String device;
    @Schema(title = "City", example = "New York", description = "The city associated with the IP address")
    private String city;
    @Schema(title = "Full Location", example = "America, New York, USA", description = "The full location associated with the IP address")
    private String fullLocation;
    @Schema(title = "Location", description = "The coordinates of the location")
    private Coordinates location;
}
