package com.project.sooktoring.exception.domain.mentoring;

import com.project.sooktoring.mentoring.dto.request.MtrRequest;
import lombok.Getter;
import org.springframework.core.NestedRuntimeException;
import org.springframework.lang.Nullable;

@Getter
public class MtrTargetException extends NestedRuntimeException {

    private final MtrRequest request;

    public MtrTargetException(String msg) {
        super(msg);
        this.request = null;
    }

    public MtrTargetException(@Nullable String msg, @Nullable Throwable cause) {
        super(msg, cause);
        this.request = null;
    }

    public MtrTargetException(String msg, MtrRequest request) {
        super(msg);
        this.request = request;
    }
}
