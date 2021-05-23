package com.kog.mypage.novel.payload.request;

import lombok.Getter;

import java.util.Optional;

@Getter
public class UpdateEpisodeRequest {

    private UserInfo userInfo;

    private Long episodeId;

    private String title;

    private String description;

    private String content;

    private String hidden;

    public Optional<String> getTitle() {
        return Optional.ofNullable(title);
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<String> getContent() {
        return Optional.ofNullable(content);
    }

    public Optional<Boolean> getHidden() {
        if ( hidden==null || hidden.equals("") )
            return Optional.empty();
        else if (hidden.equals("Y"))
            return Optional.of(true);
        else if (hidden.equals("N"))
            return Optional.of(false);

        return Optional.empty();
    }
}
