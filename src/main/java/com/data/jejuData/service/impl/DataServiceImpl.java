package com.data.jejuData.service.impl;

import com.data.jejuData.config.ApiProperties;
import com.data.jejuData.dto.DataDto;
import com.data.jejuData.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;

@Service
public class DataServiceImpl implements DataService {
    private static final Logger logger = LoggerFactory.getLogger(DataService.class);

    private final ApiProperties properties;

    private static final String GET_AUTO_KIOSK_URI = "https://apis.data.go.kr/6510000/autoKioskService/getAutoKioskList";
    private static final String GET = "GET";
    private static final String CONTENT_TYPE = "Content-type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String UTF_8 = "UTF-8";

    public DataServiceImpl(ApiProperties properties) {
        this.properties = properties;
    }

    @Override
    public JSONObject getAutoKioskService(DataDto dataDto) throws Exception {

        StringBuilder uriBuilder = new StringBuilder(GET_AUTO_KIOSK_URI);
        uriBuilder.append("?" + URLEncoder.encode("serviceKey",UTF_8) + "=" + URLEncoder.encode(properties.getKey(),UTF_8));
        uriBuilder.append("&" + URLEncoder.encode("pageNo",UTF_8) + "=" + URLEncoder.encode(dataDto.getPageNo(),UTF_8));
        uriBuilder.append("&" + URLEncoder.encode("numOfRows",UTF_8) + "=" + URLEncoder.encode(dataDto.getNumOfRows(),UTF_8));
        uriBuilder.append("&" + URLEncoder.encode("instlPlaceNm",UTF_8) + "=" + URLEncoder.encode(dataDto.getInstlPlaceNm(),UTF_8));

        logger.info("uriBuilder : {}", uriBuilder.toString());

        URL uri = new URL(uriBuilder.toString());

        HttpURLConnection con = (HttpURLConnection) uri.openConnection();

        con.setRequestMethod(GET);

        con.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);

        logger.info("Response code : {}", con.getResponseCode());

        BufferedReader rd;

        JSONObject jsonObject = null;

        if (!(con.getResponseCode() == 200)) {
            rd = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            return jsonObject;
        }

        rd = new BufferedReader(new InputStreamReader(con.getInputStream()));

        StringBuffer sb = new StringBuffer();
        String line;

        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        con.disconnect();

        jsonObject = new JSONObject(sb.toString());

        logger.info("sb.toSting() : {} ", sb.toString());
        logger.info("RESULT : {}", jsonObject);


        return jsonObject;
    }
}
