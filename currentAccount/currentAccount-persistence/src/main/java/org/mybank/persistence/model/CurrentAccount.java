package org.mybank.persistence.model;

import javax.persistence.*;

@Entity
@Table(name="current_account")
public class CurrentAccount {

    public CurrentAccount() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long currentAccountId;
    @Column
    private String contractId;
    @Column
    private String contractDescription;

    public Long getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Long currentAccountId) {
        this.currentAccountId = currentAccountId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractDescription() {
        return contractDescription;
    }

    public void setContractDescription(String contractDescription) {
        this.contractDescription = contractDescription;
    }
}
