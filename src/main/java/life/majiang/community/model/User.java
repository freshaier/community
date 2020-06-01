package life.majiang.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountID;
    private String token;
    private String bio;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
