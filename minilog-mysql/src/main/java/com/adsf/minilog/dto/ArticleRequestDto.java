package com.adsf.minilog.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class ArticleRequestDto {

    @NonNull private String content;
    @NonNull private Long authorId;
}
