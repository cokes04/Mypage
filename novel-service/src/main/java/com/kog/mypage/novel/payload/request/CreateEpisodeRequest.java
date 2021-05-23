package com.kog.mypage.novel.payload.request;

import lombok.Getter;

@Getter
public class CreateEpisodeRequest {

    private UserInfo userInfo;

    private Long novelId;

    private String title;

    private String description;

    private String content;

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

