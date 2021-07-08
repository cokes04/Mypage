package com.kog.mypage.novel.exception;

import com.kog.mypage.novel.exception.client.ForbiddenException;
import lombok.Getter;

@Getter
public class NoPermissionException extends ForbiddenException {
    public NoPermissionException() {
    }

    public NoPermissionException(String message) {
        super(message);
    }
}
