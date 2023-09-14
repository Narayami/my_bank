package org.mybank.impl.rest;


import org.mybank.api.model.ContractCurrentAccount;
import org.mybank.api.persistence.CurrentAccountDAO;
import org.mybank.api.request.CreateContractCurrentAccountRequest;
import org.mybank.api.request.UpdateCurrentAccountRequest;
import org.mybank.currentAccount.rest.CurrentAccountApi;
import org.mybank.currentAccount.rest.model.*;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import java.util.ArrayList;
import java.util.List;

public class CurrentAccountRestImpl implements CurrentAccountApi {

    private CurrentAccountDAO currentAccountDAO;

    public CurrentAccountDAO getCurrentAccountDAO() {
        return currentAccountDAO;
    }

    public void setCurrentAccountDAO(CurrentAccountDAO currentAccountDAO) {
        this.currentAccountDAO = currentAccountDAO;
    }

    @Override
    public void createContractCurrentAccount(CreateContractCurrentAccountRequestType createContractCurrentAccountRequestType) {

        ServiceReference<CurrentAccountDAO> sr_currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext()
                .getServiceReference(CurrentAccountDAO.class);

        this.currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext().getService(sr_currentAccountDAO);

        CreateContractCurrentAccountRequest createContractCurrentAccountRequest = new CreateContractCurrentAccountRequest();

        if (createContractCurrentAccountRequestType != null) {
            createContractCurrentAccountRequest.setContractId(createContractCurrentAccountRequestType.getCurrentAccount().getContractId());
            createContractCurrentAccountRequest.setContractDescription(createContractCurrentAccountRequestType.getCurrentAccount().getContractDescription());
        }

        currentAccountDAO.createCurrentAccount(createContractCurrentAccountRequest);


    }

    @Override
    public void deleteContractCurrentAccount(Long currentAccountId) {

        ServiceReference<CurrentAccountDAO> sr_currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext()
                .getServiceReference(CurrentAccountDAO.class);

        this.currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext().getService(sr_currentAccountDAO);

        currentAccountDAO.deleteCurrentAccount(currentAccountId);

    }

    @Override
    public void updateContractCurrentAccount(Long currentAccountId, UpdateContractCurrentAccountRequestType updateContractCurrentAccountRequestType) {

        ServiceReference<CurrentAccountDAO> sr_currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext()
                .getServiceReference(CurrentAccountDAO.class);

        this.currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext().getService(sr_currentAccountDAO);

        UpdateCurrentAccountRequest updateCurrentAccountRequest = new UpdateCurrentAccountRequest();

        if (updateContractCurrentAccountRequestType.getCurrentAccount() != null && currentAccountId != null) {
            updateCurrentAccountRequest.setCurrentAccountId(currentAccountId);
            updateCurrentAccountRequest.setContractId(updateContractCurrentAccountRequestType.getCurrentAccount().getContractId());
            updateCurrentAccountRequest.setContractDescription(updateContractCurrentAccountRequestType.getCurrentAccount().getContractDescription());
        }

        currentAccountDAO.updateCurrentAccount(updateCurrentAccountRequest);

    }

    @Override
    public GetAvailableContractsResponseType getAvailableContracts() {

        GetAvailableContractsResponseType responseType = new GetAvailableContractsResponseType();
        responseType.setContracts(new ArrayList<>());

        ServiceReference<CurrentAccountDAO> sr_currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext()
                .getServiceReference(CurrentAccountDAO.class);

        this.currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext().getService(sr_currentAccountDAO);

        List<String> contracts = currentAccountDAO.getAvailableContracts();

        if (contracts != null && !contracts.isEmpty())
            responseType.getContracts().addAll(contracts);

        return responseType;
    }

    @Override
    public GetContractCurrentAccountsResponseType getContractCurrentAccounts(String contractId) {

        GetContractCurrentAccountsResponseType responseType = new GetContractCurrentAccountsResponseType();
        responseType.setCurrentAccounts(new ArrayList<>());

        ServiceReference<CurrentAccountDAO> sr_currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext()
                .getServiceReference(CurrentAccountDAO.class);

        this.currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext().getService(sr_currentAccountDAO);

        List<ContractCurrentAccount> contractCurrentAccounts = currentAccountDAO.getContractCurrentAccounts(contractId);

        if (contractCurrentAccounts != null && !contractCurrentAccounts.isEmpty()) {

            for (ContractCurrentAccount contractCurrentAccount : contractCurrentAccounts) {
                ContractCurrentAccountType currentAccountType = new ContractCurrentAccountType();
                currentAccountType.setCurrentAccountId(contractCurrentAccount.getCurrentAccountId());
                currentAccountType.setContractId(contractCurrentAccount.getContractId());
                currentAccountType.setContractDescription(contractCurrentAccount.getContractDescription());

                responseType.getCurrentAccounts().add(currentAccountType);
            }

        }

        return responseType;
    }
}
