package com.data.jejuData.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecvDataDto {

    List<ResponseDto> response;

    List<BodyDto> body;

}
