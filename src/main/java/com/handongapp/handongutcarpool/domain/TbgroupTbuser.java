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
    @Setter @Column(nullable = false, unique = false) private Integer passengers;
    @Setter @Column(nullable = false, unique = false) private Integer luggage;

    protected TbgroupTbuser() {}
    public TbgroupTbuser(String tbgroupId, String tbuserId, String role, Integer passengers, Integer luggage) {
        this.tbgroupId = tbgroupId;
        this.tbuserId = tbuserId;
        this.role = role;
        this.passengers = passengers;
        this.luggage = luggage;
    }
    public static TbgroupTbuser of(String tbgroupId, String tbuserId, String role, Integer passengers, Integer luggage) {
        return new TbgroupTbuser(tbgroupId, tbuserId, role, passengers, luggage);
    }

    public TbgroupTbuser reEnter(TbgroupTbuserDto.EnterGroupServDto servDto){
        this.luggage = servDto.getLuggage();
        this.deleted = "N";
        return this;
    }

    public TbgroupTbuserDto.EnterGroupResDto toEnterGroupResDto() {
        return TbgroupTbuserDto.EnterGroupResDto.builder().tbgroupId(this.tbgroupId).tbuserId(this.tbuserId).role(this.role).message("success").build();
    }
}
