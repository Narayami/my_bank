package org.mybank.api.persistence;

import org.mybank.api.model.ContractCurrentAccount;
import org.mybank.api.request.CreateContractCurrentAccountRequest;
import org.mybank.api.request.UpdateCurrentAccountRequest;
import org.mybank.api.soap.error.ValidationFault;

import java.util.List;

public interface CurrentAccountDAO {

    List<ContractCurrentAccount> getContractCurrentAccounts(String contractId);
    void createCurrentAccount(CreateContractCurrentAccountRequest createContractCurrentAccountRequest);
    void updateCurrentAccount(UpdateCurrentAccountRequest updateCurrentAccountRequest);
    void deleteCurrentAccount(Long currentAccountId);

    List<String> getAvailableContracts();

}
