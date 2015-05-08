package af.mvc.rest;


import af.core.service.BlogEntryService;
import af.mvc.entity.BlogEntry;
import af.mvc.rest.BlogEntryController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

/**
 * Created by Adrian on 03/05/2015.
 */
public class BlogEntryControllerTest {

    @InjectMocks
    private BlogEntryController controller;

    @Mock
    private BlogEntryService service;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getExistingBlogEntry() throws Exception {
        BlogEntry entry = new BlogEntry();

        entry.setTitle("Blog Title");
        entry.setId(1L);
        entry.setSecret("Secret Text");
        when(service.find(1L)).thenReturn(entry);

        mockMvc.perform(get("/rest/blog-entries/1"))
                .andDo(print()) // Prints the mocked ServletRequest and ServletResponse
                .andExpect(jsonPath("$", hasKey("blogId"))) // Expects the json root to have blogId key
                .andExpect(jsonPath("$", not(hasKey("secret")))) // Expects the json root to NOT have the secret key
                // .andExpect(jsonPath("$.blogId").exists()) // Evaluates to - Expect jsonPath to not contain blogId value
                .andExpect(jsonPath("$.blogTitle", is(entry.getTitle()))) // Evaluates whether the blogTitle contained in the returned json is the entry title
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/blog-entries/1")))) // Evaluates whether the links array has an item which ends with "blog-entries/1"
                .andExpect(status().isOk()); // Expects status 200
    }

    @Test
    public void getNonExistingBlogEntry() throws Exception {

        when(service.find(1L)).thenReturn(null);

        mockMvc.perform(get("/rest/blog-entries/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}
