package com.project.sooktoring.dto.response;

import com.project.sooktoring.dto.request.MtrRequest;
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
