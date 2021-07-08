package com.kog.mypage.novel.payload.request;

import com.kog.mypage.novel.enumeration.AgeGrade;
import com.kog.mypage.novel.enumeration.Genre;
import com.kog.mypage.novel.enumeration.SerialCycle;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@ToString
public class CreateNovelRequest{

    private UserInfo userInfo;

    @NotBlank
    private String title;

    private String description;

    private int ageGrade;

    private String hidden;

    private boolean exclusive;

    private List<String> genre;

    private List<String> serialCycle;

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

    public AgeGrade getAgeGrade(){
        return AgeGrade.toEnum(this.ageGrade);
    }

    public List<Genre> getGenre(){
        return convert(genre, i -> Genre.toEnum(i.toLowerCase()));
    }

    public List<SerialCycle> getSerialCycle(){
        return convert(serialCycle, i -> SerialCycle.toEnum(i));
    }

    private <T> List<T> convert(List<String> list, Function<String, T> Function){
        return list.stream()
                .map( i -> Function.apply(i) ).collect(Collectors.toList());
    }
}
