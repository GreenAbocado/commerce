package admin;

import java.util.regex.Pattern;

/* Admin 도메인 및 비즈니스 로직 */
public class AdminService {
    private String adminPassword;
    private static final Pattern ADMIN_PASSWORD_PATTERN = Pattern.compile("[0-9]{6,20}");

    public AdminService(String adminPassword) {
        if (!ADMIN_PASSWORD_PATTERN.matcher(adminPassword).matches()) {
            throw new IllegalArgumentException();
        }
        this.adminPassword = adminPassword;
    }

    public boolean authenticate(String adminPassword) {
        return this.adminPassword.equals(adminPassword);
    }
}
