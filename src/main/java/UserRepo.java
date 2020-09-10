import java.util.Objects;

/**
 * 作为查询每一个人在每一个项目的 4 种事件的数量的key
 */
public class UserRepo {
    private String user;
    private String repo;

    public UserRepo() {
    }

    public UserRepo(String user, String repo) {
        this.user = user;
        this.repo = repo;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRepo userRepo = (UserRepo) o;
        return Objects.equals(user, userRepo.user) &&
                Objects.equals(repo, userRepo.repo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, repo);
    }

    @Override
    public String toString() {
        return "UserRepo{" +
                "user='" + user + '\'' +
                ", repo='" + repo + '\'' +
                '}';
    }
}
