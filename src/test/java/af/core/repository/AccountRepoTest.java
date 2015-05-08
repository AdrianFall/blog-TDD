package af.core.repository;

import af.mvc.entity.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Adrian on 08/05/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/test-context.xml")
public class AccountRepoTest {

    @Autowired
    private AccountRepo repo;

    private Account acc;

    @Before
    @Transactional
    @Rollback(false) // Whether or not the transaction for the annotated method should be rolled back after the method has completed.
    public void setup() {
        acc = new Account();
        acc.setName("yo");
        acc.setPassword("pass");
        repo.createAccount(acc);
    }

    @Test
    @Transactional
    public void testFind() throws Exception {
        assertNotNull(repo.find(acc.getId()));
    }

    @Test
    @Transactional
    // Add one more account and then obtain the list of all accounts and make tests on it
    public void testCreateSecondAccountAndObtainAllAccounts() throws Exception {
        Account newAcc = new Account();
        newAcc.setName("SecondAcc");
        newAcc.setPassword("SecondPass");
        repo.createAccount(newAcc);
        assertNotNull(repo.find(newAcc.getId()));;

        // Obtain all accounts
        List<Account> accList = repo.findAllAccounts();
        // Expect 2 elements
        assertEquals(2, accList.size());
        // Test the first element in the list
        Account firstAccEl = accList.get(0);
        assertEquals(acc.getName(), firstAccEl.getName());
        assertEquals(acc.getId(), firstAccEl.getId());
        // Test the second element in the list
        Account secondAccEl = accList.get(1);
        assertEquals(newAcc.getName(), secondAccEl.getName());
        assertEquals(newAcc.getId(), secondAccEl.getId());
    }

    @Test
    @Transactional
    public void getAccountByName() {
        Account acc = repo.findAccountByName("yo");
        assertNotNull(acc);
    }


}
