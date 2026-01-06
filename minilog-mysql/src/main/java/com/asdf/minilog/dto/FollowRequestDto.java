package com.asdf.minilog.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class FollowRequestDto {

    @NonNull private Long followerId;
    @NonNull private Long followeeId;
}
