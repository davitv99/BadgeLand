//package io.project.app.beans.accountdata;
//
//import io.project.app.beans.handlers.JSFContextHandler;
//import io.project.app.domain.Account;
//import io.project.app.http.clients.account.AccountDeleteClient;
//import io.project.app.http.clients.account.AccountValidationClient;
//import io.project.app.http.clients.account.ProfileUpdateClient;
//import io.project.app.security.SessionContext;
//import org.apache.log4j.Logger;
//
//import javax.annotation.PostConstruct;
//import javax.enterprise.context.SessionScoped;
//import javax.inject.Inject;
//import javax.inject.Named;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author davitv
// */
//@Named(value = "accountDataBean")
//@SessionScoped
//public class AccountDataBean implements Serializable {
//
//    private static final Logger LOGGER = Logger.getLogger(AccountDataBean.class);
//
//    private static final long serialVersionUID = 1L;
//
//    @Inject
//    private JSFContextHandler jSFContextHandler;
//
//    @Inject
//    private SessionContext sessionContext;
//
//    @Inject
//    private ProfileUpdateClient profileUpdateClient;
//
//    private Account account = new Account();
//
//    public AccountDataBean() {
//
//    }
//
//    @PostConstruct
//    public void init() {
//
//    }
//
//    public List<Account> getAccountList() {
//        return accountValidationClient.getAllAccounts().getAccountList();
//    }
//
//    public String findUser(String id) {
//        account = accountValidationClient.getAccountById(id).getAccount();
//        return "useredit";
//
//    }
//
//
//
//
//
//    public Account getAccount() {
//        return account;
//    }
//
//    public void setAccount(Account account) {
//        this.account = account;
//    }
//
//}
