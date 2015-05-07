package af.mvc.rest.resource.asm;

import af.mvc.entity.BlogEntry;
import af.mvc.rest.BlogEntryController;
import af.mvc.rest.resource.BlogEntryResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * Created by Adrian on 06/05/2015.
 */
public class BlogEntryResourceAsm extends ResourceAssemblerSupport<BlogEntry, BlogEntryResource> {

    public BlogEntryResourceAsm() {
        super(BlogEntryController.class, BlogEntryResource.class);
    }


    @Override
    public BlogEntryResource toResource(BlogEntry blogEntry) {
        BlogEntryResource res = new BlogEntryResource();
        res.setBlogId(blogEntry.getId());
        res.setBlogTitle(blogEntry.getTitle());
        Link link = linkTo(methodOn(BlogEntryController.class).getBlogEntry(blogEntry.getId())).withSelfRel();
        res.add(link);
        return res;

    }
}
