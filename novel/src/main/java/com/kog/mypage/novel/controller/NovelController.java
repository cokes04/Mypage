package com.kog.mypage.novel.controller;

import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.payload.request.NovelSerchRequest;
import com.kog.mypage.novel.payload.response.ApiResponse;
import com.kog.mypage.novel.payload.response.NovelInformationResponse;
import com.kog.mypage.novel.payload.response.NovelSearchResponse;
import com.kog.mypage.novel.service.NovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/novel")
public class NovelController {

    private final NovelService novelService;

    @PostMapping
    public ResponseEntity<?> getNoveInformation(@RequestBody Map<String, Long> map){
        Long novelId = map.get("novelId");
        Optional<Novel> optionalNovel = novelService.getNovel(novelId);

        if(optionalNovel.isPresent()){
            Novel novel = optionalNovel.orElseThrow();
            return  ResponseEntity.ok()
                    .body(NovelInformationResponse.of(true, "성공", novel));
        }
        else {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false,"실패"));
        }
    }

    @PostMapping("/serch")
    public ResponseEntity<?> searchNovel(@Validated @RequestBody NovelSerchRequest novelSerchRequest) {
        String word = novelSerchRequest.getWord();
        Pageable pageable = novelSerchRequest.getPageble();

        Page<Novel> novels = novelService.searchNovel(word, pageable);
        return ResponseEntity.ok().body( NovelSearchResponse.of(true, "성공", novels));
    }


}
