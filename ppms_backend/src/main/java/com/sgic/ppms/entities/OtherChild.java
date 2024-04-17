package com.sgic.ppms.entities;

import com.sgic.ppms.enums.Illness;
import com.sgic.ppms.enums.OtherChildAge;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OtherChild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "family_and_social_history_(fk)")
    private Family_And_Social_History familyAndSocialHistory;

    private Integer childAge;
    private OtherChildAge otherChildAge;
    private String school;
    private Illness illness;

    private String if_yes;
}
