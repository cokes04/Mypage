package com.kog.mypage.novel.payload.request;

import com.kog.mypage.novel.enumerate.AgeGrade;

import com.kog.mypage.novel.enumerate.Genre;
import com.kog.mypage.novel.enumerate.SerialCycle;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class UpdateNovelRequest {

    private UserInfo userInfo;

    @Max(Long.MAX_VALUE)
    @Min(1)
    private Long novelId;

    private String title;

    private String description;

    private int ageGrade;

    private String hidden; // Y OR N으로 정규식

    private List<String> genre;

    private List<String> serialCycle;

    public Optional<String> getTitle() {
        return Optional.ofNullable(title);
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
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

    public Optional<AgeGrade> getAgeGrade(){
        return Optional.ofNullable(AgeGrade.toEnum(this.ageGrade));
    }

    public Optional<List<Genre>> getGenre(){
        return Optional.ofNullable(convert(genre, i -> Genre.toEnum(i.toLowerCase())));
    }

    public Optional<List<SerialCycle>> getSerialCycle(){
        return Optional.ofNullable(convert(serialCycle, i -> SerialCycle.toEnum(i)));
    }

    private <T> List<T> convert(List<String> list, Function<String, T> Function){
        if (list == null || list.isEmpty())
            return null;
        return list.stream()
                .map( i -> Function.apply(i) ).collect(Collectors.toList());
    }
}
