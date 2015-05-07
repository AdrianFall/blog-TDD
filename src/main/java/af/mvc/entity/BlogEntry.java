package af.mvc.entity;

/**
 * Created by Adrian on 03/05/2015.
 */
public class BlogEntry {
    private Long id;
    private String title;
    private String secret;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
