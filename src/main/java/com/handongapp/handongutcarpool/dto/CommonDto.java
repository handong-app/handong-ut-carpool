package com.handongapp.handongutcarpool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class BasicDto {
    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IdReqDto{
        @Schema(description = "id", example="UUID")
        private String id;
    }

    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IdResDto{
        @Schema(description = "id", example="UUID")
        private String id;
    }
}
