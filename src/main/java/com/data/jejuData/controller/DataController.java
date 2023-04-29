package com.data.jejuData.controller;

import com.data.jejuData.dto.*;
import com.data.jejuData.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        RecvDataDto recvDataDto = new RecvDataDto();

        JSONObject dataResponse = dataJson.getJSONObject("response");

        /**
         * HEADER 영역
         * */
        // header 인스턴스 생성
        List<ResponseDto> responseDtoList = new ArrayList<>();
        ResponseDto responseDto = new ResponseDto();
        List<HeaderDto> headerDtoList = new ArrayList<>();
        HeaderDto headerDto = new HeaderDto();


        JSONObject dataHeader = dataResponse.getJSONObject("header");
        String resultCode = dataHeader.getString("resultCode");
        String resultMsg = dataHeader.getString("resultMsg");

        headerDto.setResultCode(resultCode);
        headerDto.setResultMsg(resultMsg);

        if (!resultCode.equals("00")) {
            new ResponseEntity(null,HttpStatus.BAD_REQUEST);
        }

        headerDtoList.add(headerDto);

        responseDto.setHeader(headerDtoList);
        responseDtoList.add(responseDto);
        /**
         * BODY 영역
         * */
        // body 인스턴스 생성
        List<BodyDto> bodyDtoList = new ArrayList<>();
        BodyDto bodyDto = new BodyDto();
        List<itemDto> itemDtoList = new ArrayList<>();
        itemDto itemDto = new itemDto();

        JSONObject dataBody = dataResponse.getJSONObject("body");
        String dataPageNo = dataBody.getString("pageNo");
        String datatotalCount = dataBody.getString("totalCount");
        JSONObject dataItems = dataBody.getJSONObject("items");
        String dataCd = "";
        String regDt= "";
        String laCrdnt= "";
        String loCrdnt= "";
        String instlPlaceNm= "";
        String operTimeInfo= "";
        String mngrTelno= "";
        String rnAdres= "";
        String etcCn= "";
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
        String dataNumOfRows = dataBody.getString("numOfRows");

        bodyDto.setPageNo(dataPageNo);
        bodyDto.setTotalCount(datatotalCount);
        bodyDto.setNumOfRows(dataNumOfRows);
        itemDto.setDataCd(dataCd);
        itemDto.setRegDt(regDt);
        itemDto.setLaCrdnt(laCrdnt);
        itemDto.setLoCrdnt(loCrdnt);
        itemDto.setInstlPlaceNm(instlPlaceNm);
        itemDto.setOperTimeInfo(operTimeInfo);
        itemDto.setMngrTelno(mngrTelno);
        itemDto.setRnAdres(rnAdres);
        itemDto.setEtcCn(etcCn);

        itemDtoList.add(itemDto);

        bodyDto.setItems(itemDtoList);
        bodyDtoList.add(bodyDto);

        recvDataDto.setResponse(responseDtoList);
        recvDataDto.setBody(bodyDtoList);

        logger.info("Response : {}", dataResponse);
        logger.info("Header : {}", dataHeader);
        logger.info("Body : {}", dataBody);
        logger.info("resultCode : {}" ,resultCode);
        logger.info("resultCode : {}" ,resultMsg);

        logger.info("RECV_DATA : {}", recvDataDto);






        logger.info("RESULT_JSON : {}", dataJson);

        return new ResponseEntity(recvDataDto, HttpStatus.OK);
    }
}
