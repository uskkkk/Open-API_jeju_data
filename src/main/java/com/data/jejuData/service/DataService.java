package com.data.jejuData.service;

import com.data.jejuData.dto.DataDto;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface DataService {
    public JSONObject getAutoKioskService(DataDto dataDto) throws Exception;
}
