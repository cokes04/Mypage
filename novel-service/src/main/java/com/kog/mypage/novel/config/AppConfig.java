package com.kog.mypage.novel.config;


import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app")
@Getter
public class AppConfig {

    private NovelConfig novelConfig;
    private EpisodeConfig episodeConfig;

    @Getter
    private static class NovelConfig{
        private int maxGenreCount;
        private int minGenreCount;

        private int maxDescriptionLength;
        private int minDescriptionLength;

        private int defaultPricePerEpisode;
    }

    @Getter static class EpisodeConfig{

    }
}
