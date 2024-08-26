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
            @Index(columnList = "process"),
            @Index(columnList = "createdAt"),
            @Index(columnList = "modifiedAt")
        }
)
@Entity
public class TbgroupTbuser extends AuditingFields{
    @Setter @Column(nullable = false, unique = false) private String tbgroupId;
    @Setter @Column(nullable = false, unique = false) private String tbuserId;
    @Setter @Column(nullable = false, unique = false) private String role;

    protected TbgroupTbuser() {}
    public TbgroupTbuser(String tbgroupId, String tbuserId, String role) {
        this.tbgroupId = tbgroupId;
        this.tbuserId = tbuserId;
        this.role = role;
    }
    public static TbgroupTbuser of(String tbgroupId, String tbuserId, String role) {
        return new TbgroupTbuser(tbgroupId, tbuserId, role);
    }

    public TbgroupTbuserDto.EnterGroupResDto toEnterGroupResDto() {
        return TbgroupTbuserDto.EnterGroupResDto.builder().tbgroupId(this.tbgroupId).tbuserId(this.tbuserId).role(this.role).build();
    }
}
