package com.example.EventTicketingSystem.ConfigurationForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/configuration")
public class ConfigController {

    private final ConfigurationService configurationService;

    @Autowired
    public ConfigController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @PostMapping("/save")
    public String saveConfig(@RequestBody ConfigurationForm configurationForm) {
        try {
            // Save the configuration in both JSON and text formats
            configurationService.saveJson(configurationForm);
            configurationService.saveText(configurationForm);

            // Return a success message
            return "Configuration saved successfully in both JSON and text formats.";
        } catch (IOException e) {
            // Return an error message in case of failure
            return "Error occurred while saving configuration: " + e.getMessage();
        }
    }
}
