package org.mybank.impl.soap;


import org.mybank.api.model.ContractCurrentAccount;
import org.mybank.api.persistence.CurrentAccountDAO;
import org.mybank.api.request.CreateContractCurrentAccountRequest;
import org.mybank.api.request.UpdateCurrentAccountRequest;
import org.mybank.currentAccount.soap.CurrentAccount;
import org.mybank.currentAccount.soap.request.*;
import org.mybank.currentAccount.soap.response.ContractCurrentAccountType;
import org.mybank.currentAccount.soap.response.GetAvailableContractsResponseType;
import org.mybank.currentAccount.soap.response.GetContractCurrentAccountsResponseType;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import java.util.List;


public class CurrentAccountSoapImpl implements CurrentAccount {

    private CurrentAccountDAO currentAccountDAO;

    public CurrentAccountDAO getCurrentAccountDAO() {
        return currentAccountDAO;
    }

    public void setCurrentAccountDAO(CurrentAccountDAO currentAccountDAO) {
        this.currentAccountDAO = currentAccountDAO;
    }

    @Override
    public GetContractCurrentAccountsResponseType getContractCurrentAccounts(GetContractCurrentAccountsRequestType parameters) {

        GetContractCurrentAccountsResponseType responseType = new GetContractCurrentAccountsResponseType();

        ServiceReference<CurrentAccountDAO> sr_currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext()
                .getServiceReference(CurrentAccountDAO.class);

        this.currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext().getService(sr_currentAccountDAO);

        List<ContractCurrentAccount> contractCurrentAccounts = currentAccountDAO.getContractCurrentAccounts(parameters.getContractId());

        if (contractCurrentAccounts != null && !contractCurrentAccounts.isEmpty()) {

            for (ContractCurrentAccount contractCurrentAccount : contractCurrentAccounts) {
                ContractCurrentAccountType currentAccountType = new ContractCurrentAccountType();
                currentAccountType.setCurrentAccountId(contractCurrentAccount.getCurrentAccountId());
                currentAccountType.setContractId(contractCurrentAccount.getContractId());
                currentAccountType.setContractDescription(contractCurrentAccount.getContractDescription());

                responseType.getContractCurrentAccount().add(currentAccountType);
            }

        }

        return responseType;
    }

    @Override
    public void createContractCurrentAccount(CreateContractCurrentAccountRequestType parameters) {

        ServiceReference<CurrentAccountDAO> sr_currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext()
                .getServiceReference(CurrentAccountDAO.class);

        this.currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext().getService(sr_currentAccountDAO);

        CreateContractCurrentAccountRequest createContractCurrentAccountRequest = new CreateContractCurrentAccountRequest();

        createContractCurrentAccountRequest.setContractId(parameters.getContractId());
        createContractCurrentAccountRequest.setContractDescription(parameters.getContractDescription());

        currentAccountDAO.createCurrentAccount(createContractCurrentAccountRequest);


    }

    @Override
    public void updateContractCurrentAccount(UpdateContractCurrentAccountRequestType parameters) {

        ServiceReference<CurrentAccountDAO> sr_currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext()
                .getServiceReference(CurrentAccountDAO.class);

        this.currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext().getService(sr_currentAccountDAO);

        UpdateCurrentAccountRequest updateCurrentAccountRequest = new UpdateCurrentAccountRequest();

        if (parameters.getCurrentAccount() != null) {
            updateCurrentAccountRequest.setCurrentAccountId(parameters.getCurrentAccount().getCurrentAccountId());
            updateCurrentAccountRequest.setContractId(parameters.getCurrentAccount().getContractId());
            updateCurrentAccountRequest.setContractDescription(parameters.getCurrentAccount().getContractDescription());
        }

        currentAccountDAO.updateCurrentAccount(updateCurrentAccountRequest);

    }

    @Override
    public void deleteContractCurrentAccount(DeleteContractCurrentAccountRequestType parameters) {

        ServiceReference<CurrentAccountDAO> sr_currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext()
                .getServiceReference(CurrentAccountDAO.class);

        this.currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext().getService(sr_currentAccountDAO);

        currentAccountDAO.deleteCurrentAccount(parameters.getCurrentAccountId());

    }

    @Override
    public GetAvailableContractsResponseType getAvailableContracts(GetAvailableContractsRequest parameters) {

        GetAvailableContractsResponseType responseType = new GetAvailableContractsResponseType();

        ServiceReference<CurrentAccountDAO> sr_currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext()
                .getServiceReference(CurrentAccountDAO.class);

        this.currentAccountDAO = FrameworkUtil.getBundle(this.getClass()).getBundleContext().getService(sr_currentAccountDAO);

        List<String> contracts = currentAccountDAO.getAvailableContracts();

        if (contracts != null && !contracts.isEmpty())
            responseType.getContractId().addAll(contracts);

        return responseType;
    }
}
