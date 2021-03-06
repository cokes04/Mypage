package com.kog.mypage.novel.controller;

import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.enumerate.Genre;
import com.kog.mypage.novel.payload.request.NovelPageRequest;
import com.kog.mypage.novel.payload.response.NovelResponse;
import com.kog.mypage.novel.service.NovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/novel")
public class NovelController {

    private NovelService novelService;

    @GetMapping("/all")
    public Page<Novel> getAllNovel(NovelPageRequest pageRequest) {
        return novelService.getAllNovel(pageRequest.of());
    }

    @GetMapping("/{id}")
    public NovelResponse getNovelById(@PathVariable("id") Long id){
        Optional<Novel> optionalNovel = novelService.getNovel(id);
        if(optionalNovel.isPresent()){
            return  NovelResponse.of(optionalNovel.get());
        }
        else {
            //일단
            return  NovelResponse.of(optionalNovel.get());
        }
    }

    @GetMapping("/title/{title}")
    public NovelResponse getNovelByTitle(@PathVariable("title") String title){
        Optional<Novel> optionalNovel = novelService.getNovelByTitle(title);
        if(optionalNovel.isPresent()){
            return  NovelResponse.of(optionalNovel.get());
        }
        else {
            //일단
            return  NovelResponse.of(optionalNovel.get());
        }
    }

    @GetMapping("/genre/{genreName}")
    public Page<NovelResponse> getNovelByGenre(@PathVariable("genrename") Genre genre, NovelPageRequest pageRequest){
        Page<Novel> page = novelService.getNovelByGenre(genre, pageRequest.of());
        return page.map(NovelResponse::of);
    }

    @GetMapping("/author/{author}")
    public List<NovelResponse> getNovelByAuthor(@PathVariable("autor") String author){
        List<Novel> novels = novelService.getNovelByAuthorName(author);
        return novels.stream()
                .map(NovelResponse::of)
                .collect(Collectors.toList());
    }

}
