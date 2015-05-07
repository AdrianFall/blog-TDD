package af.mvc.rest.resource.asm;

import af.mvc.entity.Account;
import af.mvc.rest.AccountController;
import af.mvc.rest.resource.AccountResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * Created by Adrian on 06/05/2015.
 */
public class AccountResourceAsm extends ResourceAssemblerSupport<Account, AccountResource> {

    public AccountResourceAsm() {
        super(AccountController.class, AccountResource.class);
    }


    @Override
    public AccountResource toResource(Account account) {
        AccountResource res = new AccountResource();
        res.setAccountId(account.getId());
        res.setAccountName(account.getName());
        Link link = linkTo(methodOn(AccountController.class).getAccount(account.getId())).withSelfRel();
        res.add(link);
        return res;

    }
}
