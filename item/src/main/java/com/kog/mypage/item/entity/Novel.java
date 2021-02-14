package com.kog.mypage.item.entity;

import com.kog.mypage.item.entity.enumerate.AgeGrade;
import com.kog.mypage.item.entity.enumerate.Genre;
import com.kog.mypage.item.entity.enumerate.SerialCycle;
import com.kog.mypage.item.entity.enumerate.SerialCycleConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Novel {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ITEM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name ="AUTOR_ID")
    private Author author;

    @Column(unique = true)
    private String tite;

    @Column(name = "price")
    private int pricePerEpisode;

    @Enumerated(EnumType.STRING)
    private AgeGrade agetGrade;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Convert(converter = SerialCycleConverter.class)
    private List<SerialCycle> serialCycle;

    @CreatedDate
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "novel")
    private List<Episode> episode = new ArrayList<Episode>();

}
