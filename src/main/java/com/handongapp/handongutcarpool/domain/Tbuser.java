package com.handongapp.handongutcarpool.domain;

import com.handongapp.handongutcarpool.dto.CommonDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Table(indexes = {
        @Index(columnList = "deleted"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "modifiedAt")
})
@Entity
public class Tbuser extends AuditingFields{
    @Setter @Column(nullable = false, unique = true) private String hakbun;
    @Setter @Column(nullable = false, unique = false) private String name;
    @Setter @Column(nullable = false, unique = true) private String phoneNumber;
    @Setter @Column(nullable = true, unique = false) private LocalDateTime penaltyUntil;

    @OneToMany(mappedBy = "tbuser", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<TbuserRoleType> tbuserRoleType = new ArrayList<>();

    public List<TbuserRoleType> getRoleList(){
        if(!this.tbuserRoleType.isEmpty()){
            return tbuserRoleType;
        }
        return new ArrayList<>();
    }

    protected Tbuser() {}
    public Tbuser(String hakbun, String name, String phoneNumber,  LocalDateTime penaltyUntil ) {
        this.hakbun = hakbun;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.penaltyUntil = penaltyUntil;
    }
    public static Tbuser of(String hakbun, String name, String phoneNumber, LocalDateTime penaltyUntil) {
        return new Tbuser(hakbun, name, phoneNumber, penaltyUntil);
    }

    public CommonDto.IdResDto toIdResDto() {
        return CommonDto.IdResDto.builder().id(this.getId()).build();
    }
}
