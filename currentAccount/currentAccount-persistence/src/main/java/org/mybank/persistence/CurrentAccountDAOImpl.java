package org.mybank.persistence;

import com.querydsl.jpa.impl.JPAQuery;
import org.mybank.api.model.ContractCurrentAccount;
import org.mybank.api.persistence.CurrentAccountDAO;
import org.mybank.api.request.CreateContractCurrentAccountRequest;
import org.mybank.api.request.UpdateCurrentAccountRequest;
import org.mybank.persistence.model.CurrentAccount;
import org.mybank.persistence.model.QCurrentAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


public class CurrentAccountDAOImpl implements CurrentAccountDAO {
    private static final Logger logger = LoggerFactory.getLogger(CurrentAccountDAOImpl.class);

    @PersistenceContext(unitName = "my_bank")
    private EntityManager entityManager;


    @Override
    public List<ContractCurrentAccount> getContractCurrentAccounts(String contractId) {

        List<ContractCurrentAccount> result = new ArrayList<>();

        JPAQuery<CurrentAccount> query = new JPAQuery<>(entityManager);

        QCurrentAccount qCurrentAccounts = QCurrentAccount.currentAccount;

        List<CurrentAccount> resultDB = query.from(qCurrentAccounts).where(qCurrentAccounts.contractId.eq(contractId)).fetch();

        if (resultDB != null && !resultDB.isEmpty()) {
            for (CurrentAccount currentAccountDB : resultDB) {

                ContractCurrentAccount contractCurrentAccount = new ContractCurrentAccount();
                contractCurrentAccount.setCurrentAccountId(currentAccountDB.getCurrentAccountId());
                contractCurrentAccount.setContractId(currentAccountDB.getContractId());
                contractCurrentAccount.setContractDescription(currentAccountDB.getContractDescription());

                result.add(contractCurrentAccount);
            }
        }

        return result;
    }

    @Transactional
    @Override
    public void createCurrentAccount(CreateContractCurrentAccountRequest createContractCurrentAccountRequest) {
        try {
            CurrentAccount currentAccount = new CurrentAccount();
            currentAccount.setContractId(createContractCurrentAccountRequest.getContractId());
            currentAccount.setContractDescription(createContractCurrentAccountRequest.getContractDescription());

            entityManager.persist(currentAccount);
        } catch (Exception e) {
            logger.error("Error performing database operation: " + e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public void updateCurrentAccount(UpdateCurrentAccountRequest updateCurrentAccountRequest) {

        CurrentAccount currentAccount = entityManager.find(CurrentAccount.class, updateCurrentAccountRequest.getCurrentAccountId());

        if (currentAccount != null) {

            try {
                currentAccount.setContractId(updateCurrentAccountRequest.getContractId());
                currentAccount.setContractDescription(updateCurrentAccountRequest.getContractDescription());
                entityManager.merge(currentAccount);
            } catch (Exception e) {
                logger.error("Error performing database operation: " + e.getMessage(), e);
            }

        } else {
            logger.warn("currentAccountId not found.");
        }
    }

    @Transactional
    @Override
    public void deleteCurrentAccount(Long currentAccountId) {
        try {
            CurrentAccount currentAccount = entityManager.find(CurrentAccount.class, currentAccountId);

            if (currentAccount != null) {
                entityManager.remove(currentAccount);
            } else {
                logger.warn("currentAccountId not found.");
            }
        } catch (Exception e) {
            logger.error("Error performing database operation: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getAvailableContracts() {

        List<String> result = new ArrayList<>();

        JPAQuery<CurrentAccount> query = new JPAQuery<>(entityManager);

        QCurrentAccount qCurrentAccounts = QCurrentAccount.currentAccount;

        List<String> resultDB = query.select(qCurrentAccounts.contractId).from(qCurrentAccounts).fetch();

        if (resultDB != null && !resultDB.isEmpty())
            result.addAll(resultDB);

        return result;

    }
}
