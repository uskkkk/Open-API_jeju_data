package com.data.jejuData.controller;

import com.data.jejuData.dto.DataDto;
import com.data.jejuData.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class DataController {
    Logger logger = LoggerFactory.getLogger(DataController.class);

    DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping(value = "/home")
    public ResponseEntity<String> home() {
            logger.debug("Here!");
        return ResponseEntity.ok("Here!");
    }

    @GetMapping(value = "/data")
    public ResponseEntity<String> getAutoKiosk(DataDto dataDto) throws URISyntaxException, IOException {
        HttpURLConnection con = dataService.getAutoKioskService(dataDto);

        BufferedReader rd;

        if (!(con.getResponseCode() == 200)) {
            rd = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }

        rd = new BufferedReader(new InputStreamReader(con.getInputStream()));

        StringBuffer sb = new StringBuffer();
        String line;

        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        con.disconnect();

        logger.info("RESULT : {}", sb.toString());



        return ResponseEntity.ok("Succes");
    }
}
