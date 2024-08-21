package com.handongapp.handongutcarpool.domain;

import com.handongapp.handongutcarpool.dto.TbuserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Table(indexes = {
        @Index(columnList = "deleted"),
        @Index(columnList = "process"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "modifiedAt")
})
@Entity
public class Tbuser extends AuditingFields{
    @Setter @Column(nullable = false, unique = true) private String hakbun;
    @Setter @Column(nullable = false, unique = false) private String name;
    @Setter @Column(nullable = false, unique = true) private String phoneNumber;
    @Setter @Column(nullable = true, unique = false) private LocalDateTime penaltyUntil;

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

    public TbuserDto.CreateResDto toCreateResDto() {
        return TbuserDto.CreateResDto.builder().id(this.getId()).build();
    }
}
