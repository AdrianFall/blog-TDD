package af.core.repository.impl.jpa;

import af.core.repository.AccountRepo;
import af.mvc.entity.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Adrian on 08/05/2015.
 */
@Repository
public class AccountRepoImpl implements AccountRepo{

    @PersistenceContext // By default is transaction scoped
    private EntityManager emgr;

    @Override
    public Account find(Long id) {
        return emgr.find(Account.class, id);
    }

    @Override
    public Account findAccountByName(String name) {
        Query query = emgr.createQuery("SELECT a FROM Account a WHERE a.name = :name");
        query.setParameter("name", name);
        return (Account) query.getSingleResult();
    }

    @Override
    public Account createAccount(Account data) {
        emgr.persist(data);
        return data;
    }

    @Override
    public List<Account> findAllAccounts() {
        Query query = emgr.createQuery("SELECT a FROM Account a");
        return query.getResultList();
    }
}
