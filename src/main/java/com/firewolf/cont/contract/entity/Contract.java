package com.firewolf.cont.contract.entity;

import com.firewolf.cont.contract.entity.enumtype.ContractType;
import com.firewolf.cont.global.BaseEntity;
import com.firewolf.cont.user.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.JOINED;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Contract extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "contract_id")
    private Long id;

    @Enumerated(STRING)
    private Legality legality;

    @Enumerated(STRING)
    private ContractType contractType;

//    @Column(length = 5000)
//    private String request_prompt;

//    @Column(length = 5000)
//    private String response_content;

    @OneToMany(mappedBy = "contract", cascade = PERSIST, orphanRemoval = true)
    private List<ContractDescription> contractDescriptions;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void addContractDescription(ContractDescription contractDescription){
        contractDescription.setContract(this);
        this.contractDescriptions.add(contractDescription);
    }

    public void addMember(Member member){
        this.member=member;
        member.getContracts().add(this);
    }

}
