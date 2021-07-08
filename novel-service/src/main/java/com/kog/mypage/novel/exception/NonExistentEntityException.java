package com.kog.mypage.novel.exception;
import com.kog.mypage.novel.exception.client.NotFoundException;
import lombok.Getter;

@Getter
public class NonExistentEntityException extends NotFoundException {
    public NonExistentEntityException() {
    }

    public NonExistentEntityException(String message) {
        super(message);
    }
}
