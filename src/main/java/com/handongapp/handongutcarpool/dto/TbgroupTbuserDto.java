package com.handongapp.handongutcarpool.dto;

import com.handongapp.handongutcarpool.domain.TbgroupTbuser;
import io.swagger.v3.oas.annotations.media.Schema;
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

        public TbgroupTbuser toEntity() {
            return TbgroupTbuser.of(this.tbgroupId, this.tbuserId, "group_member");
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
    public static class IsUserInGroupServDto {
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
    public static class UserCountResDto {
        @Schema(description = "tbuser_count", example = "1")
        @NotNull
        @NotEmpty
        private Integer tbuserCount;
    }

    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IsFullServDto {
        @Schema(description = "is_full", example = "true")
        @NotNull
        @NotEmpty
        private Boolean isFull;
    }

    @Builder
    @Getter
    public static class IsLockServDto {
        @Schema(description = "is_lock", example = "true")
        @NotNull
        @NotEmpty
        private Boolean isLock;
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
