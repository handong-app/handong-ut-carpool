package com.handongapp.handongutcarpool.dto;

import com.handongapp.handongutcarpool.domain.TbgroupTbuser;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


public class TbgroupTbuserDto {

    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnterGroupReqDto {
        @Schema(description = "tbgroup_id", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbgroupId;

        @Schema(description = "tbuser_id", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbuserId;

        @Schema(description = "luggage_count", example = "1")
        @NotNull
        @Min(value = 0, message = "Luggage count must be a non-negative integer")
        private Integer luggageCount;

        public TbgroupTbuserDto.EnterGroupServDto toServDto(Integer groupMaxCount, Integer groupMaxLuggageCount, Boolean groupIsLock) {
            return TbgroupTbuserDto.EnterGroupServDto.builder()
                    .tbgroupId(this.tbgroupId)
                    .tbuserId(this.tbuserId)
                    .luggageCount(this.luggageCount)
                    .groupMaxCount(groupMaxCount)
                    .groupMaxLuggageCount(groupMaxLuggageCount)
                    .groupIsLock(groupIsLock)
                    .build();
        }
    }

    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnterGroupServDto {
        @Schema(description = "tbgroup_id", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbgroupId;

        @Schema(description = "tbuser_id", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbuserId;

        @Schema(description = "luggage_count", example = "1")
        @NotNull
        private Integer luggageCount;

        @Schema(description = "group_max_count", example = "4")
        @NotNull
        private Integer groupMaxCount;

        @Schema(description = "group_max_luggage_count", example = "4")
        @NotNull
        private Integer groupMaxLuggageCount;

        @Schema(description = "group_is_lock", example = "false")
        @NotNull
        private Boolean groupIsLock;

        public TbgroupTbuser toEntity() {
            return TbgroupTbuser.of(this.tbgroupId, this.tbuserId, "group_member", this.luggageCount);
        }

        public IsLuggageOverflowServDto toIsLuggageOverflowServDto() {
            return IsLuggageOverflowServDto.builder()
                    .tbgroupId(this.getTbgroupId())
                    .luggageCount(this.getLuggageCount())
                    .groupMaxLuggageCount(this.getGroupMaxLuggageCount())
                    .build();
        }
    }

    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnterGroupResDto {
        @Schema(description = "tbgroup_id", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbgroupId;

        @Schema(description = "tbuser_id", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbuserId;

        @Schema(description = "role", example = "group_member")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String role;

        @Schema(description = "message", example = "success")
        @NotNull
        @NotEmpty
        @Size(max = 100)
        private String message;
    }

    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInGroupServDto {
        @Schema(description = "is_record_present", example = "true")
        @NotNull
        private Boolean isRecordPresent;

        @Schema(description = "is_user_left", example = "true")
        @NotNull
        private Boolean isUserLeft;

        @Schema(description = "tbgroup_tbuser", example = "Object")
        private TbgroupTbuser existingTbgroupTbuser;

    }


    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserCountResDto {
        @Schema(description = "tbuser_count", example = "1")
        @NotNull
        private Integer tbuserCount;
    }

    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LuggageCountResDto {
        @Schema(description = "luggage_count", example = "1")
        @NotNull
        private Integer luggageCount;
    }

    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IsGroupOverFlowServDto {
        @Schema(description = "tbgroup_id", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbgroupId;



        @Schema(description = "group_max_count", example = "4")
        @NotNull
        private Integer groupMaxCount;

        public CommonDto.IdReqDto toIdReqDto() {
            return CommonDto.IdReqDto.builder().id(this.tbgroupId).build();
        }
    }


    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IsLuggageOverflowServDto {
        @Schema(description = "tbgroup_id", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbgroupId;

        @Schema(description = "luggage_count", example = "1")
        @NotNull
        private Integer luggageCount;

        @Schema(description = "group_max_luggage_count", example = "4")
        @NotNull
        private Integer groupMaxLuggageCount;

        public Boolean getIsLuggageOverflow(Integer currentLuggageCount) {
            return currentLuggageCount + this.luggageCount > this.groupMaxLuggageCount;
        }

        public CommonDto.IdReqDto toIdReqDto() {
            return CommonDto.IdReqDto.builder().id(this.tbgroupId).build();
        }
    }


    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LeaveGroupReqDto {
        @Schema(description = "tbgroup_id", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbgroupId;

        @Schema(description = "tbuser_id", example = "UUID")
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
    public static class LeaveGroupResDto {
        @Schema(description = "tbgroup_id", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbgroupId;

        @Schema(description = "tbuser_id", example = "UUID")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String tbuserId;

        @Schema(description = "message", example = "success")
        @NotNull
        @NotEmpty
        @Size(max = 100)
        private String message;
    }
}
