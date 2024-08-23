package com.handongapp.handongutcarpool.dto;

import com.handongapp.handongutcarpool.domain.Tbgroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


public class TbgroupDto {
    @Builder
    @Schema (name = "TbGroupDto.CreateReqDto")
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateReqDto{
        @Schema(description = "group leader", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbuserId;

        @Schema(description = "fromLocation", example="한동대 택시정류장")
        @NotNull
        @NotEmpty
        @Size(max=100)
        private String fromLocation;

        @Schema(description = "toLocation", example="양덕 유야")
        @NotNull
        @NotEmpty
        @Size(max=100)
        private String toLocation;

        @Schema(description = "detail", example="SUV 차량입니다. 캐리어 가능합니다.")
        @NotNull
        @NotEmpty
        @Size(max=200)
        private String detail;

        @Schema(description = "maxCount", example="4")
        @NotNull
        @NotEmpty
        private Integer maxCount;

        public Tbgroup toEntity() {
            return Tbgroup.of(tbuserId, fromLocation, toLocation, detail, maxCount);
        }
    }

    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateResDto{
        @Schema(description = "id", example="UUID")
        private String id;
    }


}
