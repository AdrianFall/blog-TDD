package af.mvc.rest;

import af.core.service.AccountService;
import af.core.service.exception.AccountExistsException;
import af.mvc.entity.Account;
import af.mvc.rest.exceptions.ConflictException;
import af.mvc.rest.resource.AccountResource;
import af.mvc.rest.resource.asm.AccountResourceAsm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URI;

/**
 * Created by Adrian on 03/05/2015.
 */
@Controller
@RequestMapping("/rest/account")
public class AccountController {

    private AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @RequestMapping(value="/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<AccountResource> getAccount(@PathVariable Long accountId) {
        Account acc = service.find(accountId);
        if (acc != null) {
            AccountResource res = new AccountResourceAsm().toResource(acc);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AccountResource> createAccount(@RequestBody AccountResource requestedAccount) throws ConflictException {
        try {
            Account createdAccount = service.createAccount(requestedAccount.toEntity());
            AccountResource accountResource = new AccountResourceAsm().toResource(createdAccount);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(accountResource.getLink("self").getHref()));
            return new ResponseEntity<>(accountResource, headers, HttpStatus.CREATED);
        } catch (AccountExistsException e) {
            throw new ConflictException(e);
        }
    }
}
