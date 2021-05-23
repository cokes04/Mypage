package com.kog.mypage.novel.controller;

import com.kog.mypage.novel.dto.CreateNovelDto;
import com.kog.mypage.novel.dto.UpdateNovelDto;
import com.kog.mypage.novel.dto.UserInfoDto;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.enumerate.NovelSort;
import com.kog.mypage.novel.payload.request.*;
import com.kog.mypage.novel.payload.response.ApiResponse;
import com.kog.mypage.novel.payload.response.NovelInformationResponse;
import com.kog.mypage.novel.payload.response.NovelSearchResponse;
import com.kog.mypage.novel.service.NovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/novel")
public class NovelController {

    private final NovelService novelService;

    @GetMapping("/{novelId}") //       /novel/100?userId=1&email=abc@gmail.com&roles=USER,ADMIN&expired=false
    public ResponseEntity<?> getNoveInformation(@Validated @PathVariable("novelId") Long novelId,
                                                @Validated UserInfo userInfo) {

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(userInfo.getUserId())
                .roles(userInfo.getRoles())
                .build();

        Novel novel = novelService.getNovel(novelId, userInfoDto);

            return ResponseEntity.ok()
                    .body(NovelInformationResponse.of(true, "성공", novel));
    }

    @GetMapping("/serch/{word}") //     /novel/serch/짱구/page=0&size=50&standard=newest
    public ResponseEntity<?> searchNovel(@Validated @PathVariable("word") String word,
                                         @Validated NovelPageRequest pageRequest) {

        int page = pageRequest.getPage();
        int size = pageRequest.getSize();
        Sort sort = NovelSort.valueOf(pageRequest.getStandard().toUpperCase()).getSort();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Novel> novels = novelService.searchNovel(word, pageable);
        return ResponseEntity.ok()
                .body(NovelSearchResponse.of(true, "성공", novels));
    }

    @PostMapping
    public ResponseEntity<?> createNovel(@Validated @RequestBody CreateNovelRequest request) {
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(request.getUserInfo().getUserId())
                .roles(request.getUserInfo().getRoles())
                .build();

        CreateNovelDto createNovelDto = CreateNovelDto.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .ageGrade(request.getAgeGrade())
                .genre(request.getGenre())
                .serialCycle(request.getSerialCycle())
                .hidden(request.isHidden())
                .build();

        Novel novel = novelService.createNovel(createNovelDto, userInfoDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "성공"));
    }

    @PutMapping("/hidden")
    public ResponseEntity<?> changeHidden(@Validated @RequestBody ChangeHiddenOfNovelRequest request){
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(request.getUserInfo().getUserId())
                .roles(request.getUserInfo().getRoles())
                .build();

            Long novelId = request.getNovelId();

            novelService.changeHidden(novelId, request.isHidden(), userInfoDto);

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "성공"));
    }


    @PutMapping
    public ResponseEntity<?> updateNovel(@Validated @RequestBody UpdateNovelRequest request) {
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(request.getUserInfo().getUserId())
                .roles(request.getUserInfo().getRoles())
                .build();

        UpdateNovelDto updateNovelDto = UpdateNovelDto.builder()
                .novelId(request.getNovelId())
                .title(request.getTitle())
                .description(request.getDescription())
                .hidden(request.getHidden())
                .ageGrade(request.getAgeGrade())
                .genre(request.getGenre())
                .serialCycle(request.getSerialCycle())
                .ageGrade(request.getAgeGrade())
                .build();

        Novel updateNovel = novelService.updateNovel(updateNovelDto, userInfoDto);
        return ResponseEntity.ok()
                .body(new ApiResponse(true, "성공"));
    }

    @DeleteMapping("/{novelId}")
    public ResponseEntity<?> deleteNovel(@Validated @PathVariable("novelId") Long novelId,
                                         @Validated UserInfo userInfo){

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(userInfo.getUserId())
                .roles(userInfo.getRoles())
                .build();

        Novel novel = novelService.getNovel(novelId, userInfoDto);
        novelService.deleteNovel(novelId, userInfoDto);
        return ResponseEntity.ok()
                .body(new ApiResponse(true, "성공"));
    }
}