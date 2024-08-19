package com.handongapp.handongutcarpool.dto;

import com.handongapp.handongutcarpool.domain.Tbuser;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

public class TbuserDto {
    @Builder
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateReqDto{
        @Schema(description = "hakbun", example = "22000001")
        @NotNull
        @NotEmpty
        @Size(max = 50)
        private String hakbun;

        @Schema(description = "name", example="김한동")
        @NotNull
        @NotEmpty
        @Size(max=100)
        private String name;

        @Schema(description = "phoneNumber", example="010-0000-0000")
        @NotNull
        @NotEmpty
        @Size(max=100)
        private String phoneNumber;

        public Tbuser toEntity() {
            return Tbuser.of(hakbun, name, phoneNumber, "idle", null);
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
