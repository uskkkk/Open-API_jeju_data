package com.data.jejuData.controller;

import com.data.jejuData.dto.DataDto;
import com.data.jejuData.dto.RecvDataDto;
import com.data.jejuData.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.Arrays;

@RestController
@RequestMapping("/api")
public class DataController {
    private static final Logger logger = LoggerFactory.getLogger(DataController.class);

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
    public ResponseEntity<String> getAutoKiosk(DataDto dataDto) throws Exception {
        JSONObject dataJson = dataService.getAutoKioskService(dataDto);
        JSONObject dataResponse = dataJson.getJSONObject("response");
        JSONObject dataHeader = dataResponse.getJSONObject("header");

        logger.info("Response : {}", dataResponse);
        logger.info("Header : {}", dataHeader);
        logger.info("resultCode : {}" ,dataHeader.getString("resultCode"));
        logger.info("resultCode : {}" ,dataHeader.getString("resultMsg"));


        RecvDataDto recvDataDto = new RecvDataDto();


        logger.info("RESULT_JSON : {}", dataJson);

        return ResponseEntity.ok("Success");
    }
}
