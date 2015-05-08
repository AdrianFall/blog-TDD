package af.core.service;

import af.core.service.exception.AccountExistsException;
import af.mvc.entity.Account;

/**
 * Created by Adrian on 06/05/2015.
 */
public interface AccountService {
    public Account find(Long id);
    public Account createAccount(Account data) throws AccountExistsException;
}
