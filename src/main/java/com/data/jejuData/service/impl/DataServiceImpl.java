package com.data.jejuData.service.impl;

import com.data.jejuData.config.ApiProperties;
import com.data.jejuData.dto.DataDto;
import com.data.jejuData.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public HttpURLConnection getAutoKioskService(DataDto dataDto) throws IOException {

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

        return con;
    }
}
