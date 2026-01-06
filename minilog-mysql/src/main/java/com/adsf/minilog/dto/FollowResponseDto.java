package com.adsf.minilog.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class FollowResponseDto {

    @NonNull private Long followerId;
    @NonNull private Long followeeId;
}
