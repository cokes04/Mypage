package com.kog.mypage.novel.repository;

import com.kog.mypage.novel.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}
