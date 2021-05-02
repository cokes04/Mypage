package com.kog.mypage.novel.repository;

import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.entity.Novel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    Page<Episode> findByNovel_Id(Long novelId, Pageable pageable);
    List<Episode> findByNovel(Novel novel);
}
