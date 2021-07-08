package com.kog.mypage.novel.controller;

import com.kog.mypage.novel.dto.CreateNovelDto;
import com.kog.mypage.novel.dto.UpdateNovelDto;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.enumeration.NovelSort;
import com.kog.mypage.novel.exception.client.BadRequestException;
import com.kog.mypage.novel.exception.client.ForbiddenException;
import com.kog.mypage.novel.exception.client.NotFoundException;
import com.kog.mypage.novel.payload.request.*;
import com.kog.mypage.novel.payload.response.NovelInformationResponse;
import com.kog.mypage.novel.payload.response.NovelSearchResponse;
import com.kog.mypage.novel.service.NovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/novel")
public class NovelController {

    private final NovelService novelService;

    @GetMapping("/serch/{word}")
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

    @GetMapping("/{novelId}")
    public ResponseEntity<?> getNovel(@Validated @PathVariable("novelId") Long novelId,
                                      @Validated UserInfo userInfo) {

        Novel novel = novelService.getNovel(novelId);

        if (novel.isHidden() && !novel.isOwner(userInfo.getUserId()))
            throw new NotFoundException();

        return ResponseEntity.ok()
                .body(NovelInformationResponse.of(true, "성공", novel));
    }

    @PostMapping
    public ResponseEntity<?> createNovel(@Validated @RequestBody CreateNovelRequest request) {
        CreateNovelDto createNovelDto;
        try {
            createNovelDto = CreateNovelDto.builder()
                    .userId(request.getUserInfo().getUserId())
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .ageGrade(request.getAgeGrade())
                    .genre(request.getGenre())
                    .serialCycle(request.getSerialCycle())
                    .hidden(request.isHidden())
                    .build();
        }catch (IllegalArgumentException e){
            throw new BadRequestException();
        }

        Novel novel = novelService.createNovel(createNovelDto);
        return ResponseEntity.created(URI.create("/novel/" + novel.getId())).build();
    }

    @PatchMapping("/hidden")
    public ResponseEntity<?> changeHidden(@Validated @RequestBody ChangeHiddenOfNovelRequest request){
        Long userId = request.getUserInfo().getUserId();
        Novel novel = novelService.getNovel(request.getNovelId());

        if (novel.isOwner(userId))
            novelService.changeHidden(novel, request.isHidden());
        else
            throw new ForbiddenException();

        return ResponseEntity.noContent().build();
    }


    @PutMapping
    public ResponseEntity<?> updateNovel(@Validated @RequestBody UpdateNovelRequest request) {
        Long userId = request.getUserInfo().getUserId();
        UpdateNovelDto updateNovelDto;
        try {
            updateNovelDto = UpdateNovelDto.builder()
                    .novelId(request.getNovelId())
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .hidden(request.getHidden())
                    .ageGrade(request.getAgeGrade())
                    .genre(request.getGenre())
                    .serialCycle(request.getSerialCycle())
                    .ageGrade(request.getAgeGrade())
                    .build();
        }catch (IllegalArgumentException e){
            throw new BadRequestException();
        }

        Novel deforeNovel = novelService.getNovel(request.getNovelId());
        Novel updateNovel;

        if (deforeNovel.isOwner(userId))
            updateNovel = novelService.updateNovel(deforeNovel, updateNovelDto);
        else
            throw new ForbiddenException();

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{novelId}")
    public ResponseEntity<?> deleteNovel(@Validated @PathVariable("novelId") Long novelId,
                                         @Validated UserInfo userInfo){

        Novel novel = novelService.getNovel(novelId);

        if (novel.isOwner(userInfo.getUserId()))
            novelService.deleteNovel(novel);
        else
            throw new ForbiddenException();

        return ResponseEntity.noContent().build();
    }
}