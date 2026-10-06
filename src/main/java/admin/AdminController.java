package admin;

import io.CommonInput;

import java.util.regex.Pattern;

/* Admin 컨트롤러, 비즈니스 로직 등 수행 */
/* 가벼운 로직이므로 클래스 하나로 설계 */
public class AdminController {

    private static final int RETRY_COUNT = 3;

    /* 이후 변경될 가능성을 고려하여 가변 */
    private String adminPassword;
    private CommonInput input;

    /* 비밀번호 검증 규칙 (숫자 6~20자리) */
    private static final Pattern ADMIN_PASSWORD_PATTERN = Pattern.compile("[0-9]{6,20}");

    /* 유효성 및 비즈니스 규칙 검증 후 생성 */
    public AdminController(String adminPassword, CommonInput input) {
        if (adminPassword == null || adminPassword.isBlank()) {
            throw new IllegalArgumentException("유효하지 않은 입력");
        }

        if (!ADMIN_PASSWORD_PATTERN.matcher(adminPassword).matches()) {
            throw new IllegalArgumentException("규칙 불일치");
        }
        this.adminPassword = adminPassword;
        this.input = input;
    }

    /* 관리자 인증 로직 */
    public boolean authenticate() {
        for (int i = 0; i < RETRY_COUNT; i++) {
            printPasswordRequest();
            String inputPassword = input.readString();
            if (isAdmin(inputPassword)) {
                return true;
            }
        }
        printFailAuthenticate();
        return false;
    }

    private boolean isAdmin(String adminPassword) {
        return this.adminPassword.equals(adminPassword);
    }

    /* 출력 로직 가벼워서 우선 해당 클래스에 배치 */
    private static void printPasswordRequest() {
        System.out.println("관리자 비밀번호를 입력해주세요:");
    }

    private static void printFailAuthenticate() {
        System.out.println("관리자 인증에 실패하였습니다.\n");
    }
}