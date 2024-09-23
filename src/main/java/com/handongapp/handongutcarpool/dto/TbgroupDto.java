package com.handongapp.handongutcarpool.dto;

import com.handongapp.handongutcarpool.domain.Tbgroup;
import com.handongapp.handongutcarpool.domain.TbgroupTbuser;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;


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
        private Integer maxPassengers;

        @Schema(description = "maxLuggage", example="4")
        @NotNull
        @NotEmpty
        private Integer maxLuggage;

        @Schema(description = "departureAt", example="2024-09-01T19:37:30")
        @NotNull
        @NotEmpty
        private LocalDateTime departureAt;

        public Tbgroup toEntity() {
            return Tbgroup.of(tbuserId, fromLocation, toLocation, detail, maxPassengers, maxLuggage, departureAt);
        }
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnterGroupAdminReqDto {
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
            // 보통 최대 짐 개수는 방장의 것을 빼고 설정하므로 짐 개수를 0으로 설정
            return TbgroupTbuser.of(this.tbgroupId, this.tbuserId, "group_admin", 0);
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

    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailReqDto{
        @Schema(description = "TbgorupId", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbgroupId;
    }

    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailResDto{
        @Schema(description = "group leader hakbun", example = "22000000")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String groupLeaderHakbun;


        @Schema(description = "group leader name", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String groupLeaderName;


        @Schema(description = "detail", example="SUV 차량입니다. 캐리어 가능합니다.")
        @NotNull
        @NotEmpty
        @Size(max=200)
        private String detail;


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


        @Schema(description = "maxCount", example="4")
        @NotNull
        @NotEmpty
        private Integer maxPassengers;


        @Schema(description = "currentCount", example="1")
        @NotNull
        @NotEmpty
        private Integer currentPassengers;


        @Schema(description = "maxLuggage", example="4")
        @NotNull
        @NotEmpty
        private Integer maxLuggage;


        @Schema(description = "departureAt", example="2024-09-01T19:37:30")
        @NotNull
        @NotEmpty
        private LocalDateTime departureAt;
    }
}
