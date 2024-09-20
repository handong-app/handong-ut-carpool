package com.handongapp.handongutcarpool.domain;

import com.handongapp.handongutcarpool.dto.TbgroupTbuserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Table(
        name = "tbgroup_tbuser",
        indexes = {
            @Index(columnList = "deleted"),
            @Index(columnList = "createdAt"),
            @Index(columnList = "modifiedAt")
        }
)
@Entity
public class TbgroupTbuser extends AuditingFields{
    @Setter @Column(nullable = false, unique = false) private String tbgroupId;
    @Setter @Column(nullable = false, unique = false) private String tbuserId;
    @Setter @Column(nullable = false, unique = false) private String role;
    @Setter @Column(nullable = false, unique = false) private Integer luggageCount;

    protected TbgroupTbuser() {}
    public TbgroupTbuser(String tbgroupId, String tbuserId, String role, Integer luggageCount) {
        this.tbgroupId = tbgroupId;
        this.tbuserId = tbuserId;
        this.role = role;
        this.luggageCount = luggageCount;
    }
    public static TbgroupTbuser of(String tbgroupId, String tbuserId, String role, Integer luggageCount) {
        return new TbgroupTbuser(tbgroupId, tbuserId, role, luggageCount);
    }

    public TbgroupTbuserDto.EnterGroupResDto toEnterGroupResDto() {
        return TbgroupTbuserDto.EnterGroupResDto.builder().tbgroupId(this.tbgroupId).tbuserId(this.tbuserId).role(this.role).message("success").build();
    }
}
