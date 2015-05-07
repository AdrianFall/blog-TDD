package af.mvc.rest.resource;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by Adrian on 06/05/2015.
 */
public class BlogEntryResource extends ResourceSupport {
    private Long blogId;
    private String blogTitle;

    public String getBlogTitle() {
        return blogTitle;
    }
    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public Long getBlogId() {
        return blogId;
    }
    public void setBlogId(Long blogId) { this.blogId = blogId; }
}
