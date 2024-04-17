package com.sgic.ppms.dto;

import com.sgic.ppms.enums.Illness;
import com.sgic.ppms.enums.OtherChildAge;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtherChildDTO {

    private Integer familyAndSocialHistoryId;
    private Integer childAge;
    private OtherChildAge otherChildAge;
    private String school;
    private Illness illness;
    private String if_yes;
}
