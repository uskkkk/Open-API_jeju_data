package com.data.jejuData.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@NoArgsConstructor
public class DataDto {

    @Value("${service.key}")
    String serviceKey;

    String pageNo;

    String numOfRows;

    String instlPlaceNm;
}
