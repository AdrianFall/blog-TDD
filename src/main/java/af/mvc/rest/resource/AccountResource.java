package af.mvc.rest.resource;

import af.mvc.entity.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by Adrian on 06/05/2015.
 */
public class AccountResource extends ResourceSupport {
    private Long accountId;
    private String accountName;
    private String accountPassword;

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    @JsonIgnore
    public String getAccountPassword() { return accountPassword; }
    @JsonProperty
    public void setAccountPassword(String accountPassword) { this.accountPassword = accountPassword; }

    public Account toEntity() {
        Account account = new Account();
        account.setPassword(accountPassword);
        account.setName(accountName);
        return account;
    }
}
