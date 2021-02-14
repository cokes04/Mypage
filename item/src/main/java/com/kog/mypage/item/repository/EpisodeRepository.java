package com.kog.mypage.item.repository;

import com.kog.mypage.item.entity.Author;
import com.kog.mypage.item.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}
