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

        /**
         * RESPONSE 영역
         * */
        JSONObject dataResponse = dataJson.getJSONObject("response");

        /**
         * HEADER 영역
         * */
        JSONObject dataHeader = dataResponse.getJSONObject("header");
        String resultCode = dataHeader.getString("resultCode");
        String resultMsg = dataHeader.getString("resultMsg");
        /**
         * BODY 영역
         * */
        JSONObject dataBody = dataResponse.getJSONObject("body");
        String dataPageNo = dataBody.getString("pageNo");
        String datatotalCount = dataBody.getString("totalCount");
        JSONObject dataItems = dataBody.getJSONObject("items");
        String dataCd;
        String regDt;
        String laCrdnt;
        String loCrdnt;
        String instlPlaceNm;
        String operTimeInfo;
        String mngrTelno;
        String rnAdres;
        String etcCn;
        JSONArray dataItemArrays = dataItems.getJSONArray("item");
        for (int i = 0 ; i < dataItemArrays.length() ; i++ ) {
            JSONObject itemList = (JSONObject) dataItemArrays.get(i);
            logger.info("ItemList : {}" , itemList);
            dataCd          = itemList.getString("dataCd");
            regDt           = itemList.getString("regDt");
            laCrdnt         = itemList.getString("laCrdnt");
            loCrdnt         = itemList.getString("loCrdnt");
            instlPlaceNm    = itemList.getString("instlPlaceNm");
            operTimeInfo    = itemList.getString("operTimeInfo");
            mngrTelno       = itemList.getString("mngrTelno");
            rnAdres         = itemList.getString("rnAdres");
            etcCn           = itemList.getString("etcCn");

        }

        logger.info("Response : {}", dataResponse);
        logger.info("Header : {}", dataHeader);
        logger.info("Body : {}", dataBody);
        logger.info("resultCode : {}" ,resultCode);
        logger.info("resultCode : {}" ,resultMsg);


        RecvDataDto recvDataDto = new RecvDataDto();


        logger.info("RESULT_JSON : {}", dataJson);

        return ResponseEntity.ok("Success");
    }
}
