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

        @Schema(description = "passengers", example = "1")
        @NotNull
        @Min(value = 0, message = "passengers must be a non-negative integer")
        private Integer passengers;

        @Schema(description = "luggage", example = "1")
        @NotNull
        @Min(value = 0, message = "Luggage must be a non-negative integer")
        private Integer luggage;

        public TbgroupTbuserDto.EnterGroupServDto toServDto(Integer groupMaxPassengers, Integer groupMaxLuggage, Boolean groupIsLock) {
            return EnterGroupServDto.builder()
                    .tbgroupId(this.tbgroupId)
                    .tbuserId(this.tbuserId)
                    .passengers(this.passengers)
                    .luggage(this.luggage)
                    .groupMaxPassengers(groupMaxPassengers)
                    .groupMaxLuggage(groupMaxLuggage)
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

        @Schema(description = "passengers", example = "1")
        @NotNull
        private Integer passengers;

        @Schema(description = "luggage", example = "1")
        @NotNull
        private Integer luggage;

        @Schema(description = "group_max_passengers", example = "4")
        @NotNull
        private Integer groupMaxPassengers;

        @Schema(description = "group_max_luggage", example = "4")
        @NotNull
        private Integer groupMaxLuggage;

        @Schema(description = "group_is_lock", example = "false")
        @NotNull
        private Boolean groupIsLock;

        public TbgroupTbuser toEntity() {
            return TbgroupTbuser.of(this.tbgroupId, this.tbuserId, "group_member", this.passengers, this.luggage);
        }

        public IsGroupOverFlowServDto toIsGroupOverFlowServDto(){
            return IsGroupOverFlowServDto.builder()
                    .tbgroupId(this.getTbgroupId())
                    .passengers(this.getPassengers())
                    .groupMaxPassengers(this.getGroupMaxPassengers())
                    .build();
        }

        public IsLuggageOverflowServDto toIsLuggageOverflowServDto() {
            return IsLuggageOverflowServDto.builder()
                    .tbgroupId(this.getTbgroupId())
                    .luggage(this.getLuggage())
                    .groupMaxLuggage(this.getGroupMaxLuggage())
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
    public static class PassengerCountResDto {
        @Schema(description = "passenger_count", example = "1")
        @NotNull
        private Integer passengerCount;
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

        @Schema(description = "passengers", example = "1")
        @NotNull
        private Integer passengers;

        @Schema(description = "group_max_passenger", example = "4")
        @NotNull
        private Integer groupMaxPassengers;

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

        @Schema(description = "luggage", example = "1")
        @NotNull
        private Integer luggage;

        @Schema(description = "group_max_luggage", example = "4")
        @NotNull
        private Integer groupMaxLuggage;

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

    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailFromGroupUserServDto {
        @Schema(description = "currentCount", example="1")
        @NotNull
        private Integer currentPassengers;

        @Schema(description = "currentLuggage", example="4")
        @NotNull
        private Integer currentLuggage;
    }

    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IsUserInGroupServDto {
        @Schema(description = "isUserInGroup", example="True")
        @NotNull
        private Boolean isUserInGroup;
    }
}
