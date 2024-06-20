package com.port.petfit.user.member.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import com.port.petfit.user.member.petdoc.reply.Reply;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "petfit_hospitals")
public class Hospital {

    @Id
    @Column(nullable = false, name = "hospital_Id")
    private String hospitalId;

    @Column(nullable = false)
    private String hospitalPw;

    @Column(nullable = false)
    private String hospitalName;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    @Column(nullable = false)
    private String hospitalPhone;

    @Column(nullable = false)
    private String hospitalAddress;

    @Column(nullable = false)
    private String hospitalEmail;

    @Column(nullable = false)
    private String businessRegistration;

    @Column(nullable = true)
    private String hospitalThumbnail;

    @Column(nullable = true)
    private String hospitalThumbnailUrl = "/images/default_thumbnail.png";  // 기본 이미지 URL 설정

    @Transient
    private MultipartFile hospitalThumbnailFile;

    @OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

    @Column(nullable = false)
    private boolean approved = false;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date a_registerDate;
}
