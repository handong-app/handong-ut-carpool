package com.handongapp.handongutcarpool.domain;

import com.handongapp.handongutcarpool.dto.CommonDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Table(indexes = {
        @Index(columnList = "deleted"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "modifiedAt")
})
@Entity
public class Tbgroup extends AuditingFields{
    @Setter @Column(nullable = false, unique = false) private String tbuserId; // 방장
    @Setter @Column(nullable = false, unique = false) private String fromLocation;
    @Setter @Column(nullable = false, unique = false) private String toLocation;
    @Setter @Column(nullable = false, unique = false) private String detail;
    @Setter @Column(nullable = false, unique = false) private Boolean locked;
    @Setter @Column(nullable = false, unique = false) private Integer maxCount;
    @Setter @Column(nullable = false, unique = false) private Integer maxLuggage;
    @Setter @Column(nullable = false, unique = false) private LocalDateTime departureAt;
    @Setter @Column(nullable = false, unique = false) private String status;

    protected Tbgroup() {}
    public Tbgroup(String tbuserId, String fromLocation, String toLocation, String detail, Boolean locked, Integer maxCount, Integer maxLuggage, LocalDateTime departureAt, String status) {
        this.tbuserId = tbuserId;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.detail = detail;
        this.locked = locked;
        this.maxCount = maxCount;
        this.maxLuggage = maxLuggage;
        this.departureAt = departureAt;
        this.status = status;
    }
    // create 에서 사용하는 메서드, 그룹 엔티티 초기화.
    public static Tbgroup of(String tbuserId, String fromLocation, String toLocation, String detail, Integer maxCount, Integer maxLuggage, LocalDateTime departureAt) {
        return new Tbgroup(tbuserId, fromLocation, toLocation, detail, false, maxCount, maxLuggage, departureAt, "recruiting");
    }

    public CommonDto.IdResDto toIdResDto() {
        return CommonDto.IdResDto.builder().id(this.getId()).build();
    }
}
