package af.mvc.rest;

import af.af.service.BlogEntryService;
import af.mvc.entity.BlogEntry;
import af.mvc.rest.resource.BlogEntryResource;
import af.mvc.rest.resource.asm.BlogEntryResourceAsm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Adrian on 03/05/2015.
 */
@Controller
@RequestMapping("/rest/blog-entries")
public class BlogEntryController {

    private BlogEntryService service;

    public BlogEntryController(BlogEntryService service) {
        this.service = service;
    }

    @RequestMapping(value="/{blogEntryId}", method = RequestMethod.GET)
    public ResponseEntity<BlogEntryResource> getBlogEntry(@PathVariable Long blogEntryId) {
        BlogEntry entry = service.find(blogEntryId);
        if (entry != null) {
            BlogEntryResource res = new BlogEntryResourceAsm().toResource(entry);
            return new ResponseEntity<BlogEntryResource>(res, HttpStatus.OK);
        } else
            return new ResponseEntity<BlogEntryResource>(HttpStatus.NOT_FOUND);
    }
}
