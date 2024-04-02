import java.util.Objects;

public class Login {
    private final String userId;
    private final String logTime;
    private final Status status;

    public Login(String userId, String logTime, Status status) {
        this.userId = userId;
        this.logTime = logTime;
        this.status = status;
    }
    public String getUserId() {
        return userId;
    }
    public String getLogTime() {
        return logTime;
    }
    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return Objects.equals(userId, login.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "Log{" +
                "logTime=" + logTime +
                ", userId='" + userId +
                ", status=" + status +
                '}';
    }
}
