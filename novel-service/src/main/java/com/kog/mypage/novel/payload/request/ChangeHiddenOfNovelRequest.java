package com.kog.mypage.novel.payload.request;

import lombok.Getter;

@Getter
public class ChangeHiddenOfNovelRequest {
    private UserInfo userInfo;

    private Long novelId;

    private String hidden;

    public boolean getHidden() {
        if (hidden.equals("Y"))
            return true;
        else if (hidden.equals("N"))
            return false;

        return  false;
    }

    public boolean isHidden(){
        return getHidden();
    }
}
