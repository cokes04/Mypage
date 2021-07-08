package com.kog.mypage.novel.repository;

import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.entity.Novel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    Page<Episode> findByNovel_Id(Long novelId, Pageable pageable);

    Page<Episode> findByNovel_IdAndOpenDateIsNotNull(Long novelId, Pageable pageable);

    List<Episode> findByNovel(Novel novel);

    void deleteByNovel_Id(Long novelId);

    boolean existsByNovel_Id(Long novelId);

    int countByNovel_Id(Long novelId);
}
