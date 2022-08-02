package com.project.sooktoring.common.exception.dto;

import com.project.sooktoring.mentoring.dto.request.MtrRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MtrExResponse {

    private int status;

    private String message;

    private MtrRequest request;
}
