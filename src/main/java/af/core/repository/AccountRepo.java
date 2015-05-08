package af.core.repository;

import af.mvc.entity.Account;

import java.util.List;

/**
 * Created by Adrian on 08/05/2015.
 */
public interface AccountRepo {
    public Account find(Long id);
    public Account findAccountByName(String name);
    public Account createAccount(Account data);
    public List<Account> findAllAccounts();
}
