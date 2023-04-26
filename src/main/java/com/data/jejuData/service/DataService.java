package com.data.jejuData.service;

import com.data.jejuData.dto.DataDto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public interface DataService {
    public HttpURLConnection getAutoKioskService(DataDto dataDto) throws URISyntaxException, IOException;
}
