package af.test;


import af.af.service.AccountService;

import af.af.service.exception.AccountExistsException;
import af.mvc.entity.Account;
import af.mvc.rest.AccountController;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Adrian on 03/05/2015.
 */
public class AccountControllerTest {

    @InjectMocks
    private AccountController controller;

    @Mock
    private AccountService service;

    private MockMvc mockMvc;

    private ArgumentCaptor<Account> accountCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Instantiate the arguemnt captor for account class
        accountCaptor = ArgumentCaptor.forClass(Account.class);

    }

    @Test
    public void getNonExistingAccount() throws Exception {

        when(service.find(1L)).thenReturn(null);

        mockMvc.perform(get("/rest/account/1"))
               //.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getExistingAccount() throws  Exception {
        Account existingAccount = new Account();

        existingAccount.setId(1L);
        existingAccount.setName("adrian");
        existingAccount.setPassword("fall");
        when(service.find(1L)).thenReturn(existingAccount);

        mockMvc.perform(get("/rest/account/1"))
                //.andDo(print())
                .andExpect(jsonPath("$", hasKey("accountName")))
                .andExpect(jsonPath("$", hasKey("accountId")))
                .andExpect(jsonPath("$", not(hasKey("accountPassword"))))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/rest/account/1"))))
                .andExpect(status().isOk());
    }

    // Tests creating a new account, with non existing username
    @Test
    public void createNonExistingAccount() throws Exception {
        Account createdAccount = new Account();
        createdAccount.setId(2L);
        createdAccount.setName("aj");
        createdAccount.setPassword("fall");

        when(service.createAccount(any(Account.class))).thenReturn(createdAccount);

        mockMvc.perform(post("/rest/account")
                .content("{\"accountName\":\"aj\",\"accountPassword\":\"fall\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/rest/account/2")))
                .andExpect(jsonPath("$.accountName", is(createdAccount.getName())))
                .andExpect(status().isCreated());

        // Verify that the createAccount method was called on Account.class
        verify(service).createAccount(any(Account.class));

        // Capture what was passed to the mock object
        verify(service).createAccount(accountCaptor.capture());

        // Obtain the fields from the captured mock object
        Long cId = accountCaptor.getValue().getId();
        String cPassword = accountCaptor.getValue().getPassword();
        String cUsername = accountCaptor.getValue().getName();
        // Assert that the captured fields are the ones passed as json content
        assertEquals("fall", cPassword);
        assertEquals("aj", cUsername);
        assertEquals(null, cId);
    }

    @Test
    public void createExistingAccount() throws Exception {
        when(service.createAccount(any(Account.class))).thenThrow(new AccountExistsException());

        mockMvc.perform(post("/rest/account")
                .content("{\"accountName\":\"test\",\"accountPassword\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict());
    }
}
