package com.adsf.minilog.util;


import com.adsf.minilog.dto.ArticleResponseDto;
import com.adsf.minilog.dto.FollowResponseDto;
import com.adsf.minilog.dto.UserRequestDto;
import com.adsf.minilog.dto.UserResponseDto;
import com.adsf.minilog.entity.Article;
import com.adsf.minilog.entity.Follow;
import com.adsf.minilog.entity.User;

public class EntityDtoMapper {
    public static ArticleResponseDto toDto(Article article){
        return ArticleResponseDto.builder()
                .articleId(article.getId())
                .content(article.getContent())
                .authorId(article.getAuthor().getId())
                .authorName(article.getAuthor().getUsername())
                .createdAt(article.getCreatedAt())
                .build();
    }

    public static FollowResponseDto toDto(Follow follow){
        return FollowResponseDto.builder()
                .followerId(follow.getFollwer().getId())
                .followeeId(follow.getFollwee().getId())
                .build();
    }

    public static UserResponseDto toDto(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

    public static Follow toEntity(Long followerId, Long followeeId){
        return Follow.builder()
                .follwer(User.builder().id(followeeId).build())
                .follwee(User.builder().id(followeeId).build())
                .build();
    }
}
