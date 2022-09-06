package com.project.sooktoring.contest.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Schema(description = "공모전 모집 등록, 수정 시 정보 전달하는 DTO")
@Getter
public class ContestRequest {

    @Schema(description = "공모전 이름", example = "교내 해커톤")
    @NotBlank(message = "NO_CONTEST_NAME")
    private String contestName;

    @Schema(description = "모집기한", example = "2022/09/06 18:57:00")
    @NotNull(message = "NULL_DEADLINE") @Future(message = "PAST_DEADLINE")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", shape = STRING)
    private LocalDateTime deadline;

    @Schema(description = "공모전 모집역할 리스트")
    @Valid
    @NotNull(message = "NULL_CONTEST_ROLES") @Size(min = 1, message = "ZERO_CONTEST_ROLE")
    private List<ContestRoleRequest> contestRoles;
}
