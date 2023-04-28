package com.data.jejuData.dto;

import lombok.Data;

import java.util.List;

@Data
public class BodyDto {

    String pageNo;

    String totalCount;

    List<itemDto> items;

    String numOfRows;
}
