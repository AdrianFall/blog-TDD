package af.af.service;

import af.af.service.exception.AccountExistsException;
import af.mvc.entity.Account;
import af.mvc.entity.BlogEntry;

/**
 * Created by Adrian on 06/05/2015.
 */
public interface AccountService {
    public Account find(Long id);
    public Account createAccount(Account data) throws AccountExistsException;
}
