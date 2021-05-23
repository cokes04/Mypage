package com.kog.mypage.novel.service;

import com.kog.mypage.novel.dto.CreateEpisodeDto;
import com.kog.mypage.novel.dto.UpdateEpisodeDto;
import com.kog.mypage.novel.dto.UserInfoDto;
import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.exception.InaccessibleEntityException;
import com.kog.mypage.novel.exception.NotFoundEntityException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EpisodeService {

    Episode getEpisode(Long episodeId, UserInfoDto userInfoDto)
            throws NotFoundEntityException, InaccessibleEntityException;

    Page<Episode> getEpisodeOfNovel(Long novelId, Pageable pageable);

    Episode createEpisode(Episode episode);

    Episode createEpisode(CreateEpisodeDto dto, UserInfoDto userInfoDto);

    Episode updateEpisode(UpdateEpisodeDto dto, UserInfoDto userInfoDto)
            throws NotFoundEntityException, InaccessibleEntityException;

    void deleteEpisode(Long episodeId, UserInfoDto userInfoDto)
            throws NotFoundEntityException, InaccessibleEntityException;

    void deleteEpisodeOfNovel(Long novelId, UserInfoDto userInfoDto)
            throws NotFoundEntityException, InaccessibleEntityException;

    boolean existEpisodeOfNovel(Long novelId);

    Episode changeHidden(Long episodeId, UserInfoDto userInfoDto, boolean hidden)
            throws NotFoundEntityException, InaccessibleEntityException;

}
