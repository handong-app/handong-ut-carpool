package com.handongapp.handongutcarpool.dto;

import com.handongapp.handongutcarpool.domain.Tbgroup;
import com.handongapp.handongutcarpool.domain.TbgroupTbuser;
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
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnterGroupReqDto{
        @Schema(description = "group leader", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbuserId;

        @Schema(description = "TbgorupId", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbgroupId;

        public TbgroupTbuser toEntity() {
            return TbgroupTbuser.of(this.tbgroupId, this.tbuserId, "group_admin");
        }
    }

    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LockReqDto{
        @Schema(description = "TbgorupId", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbgroupId;

        @Schema(description = "TbuserId", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbuserId;
    }

    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateStatusReqDto{
        @Schema(description = "TbgorupId", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbgroupId;

        @Schema(description = "TbuserId", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbuserId;

        @Schema(description = "status", example = "recruiting")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String status;
    }


}
