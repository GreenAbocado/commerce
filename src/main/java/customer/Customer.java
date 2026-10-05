package customer;

import lombok.Builder;
import lombok.Getter;
import java.util.regex.Pattern;

/* 고객 도메인 모델 */
@Getter
public class Customer {

    /*
    * 현재 요구사항은 고객 확인 및 Grade 확인이므로,
    * 더미 고객 생성 및 유효성 검증, 등급별 확인을 위해 upGrade가 가능할 정도로만 구현
    * 이후 필요하다면 다른 로직 및 규칙 추가 예정
    */

    /* id를 불변으로 설정하려면 도메인 -> 레포지토리 의존 생성, 우선 가변으로 설정 */
    private Long id;
    private final String userName;
    private String password;
    private String name;
    private Grade grade;
    private int totalUsedPrice;

    /* 각 문자열 필드 검증 규칙 (정규표현식 간소하게 작성) */
    private static final Pattern USERNAME_PATTERN = Pattern.compile("[0-9A-Za-z]{4,20}");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("[0-9A-Za-z]{6,40}");
    private static final Pattern NAME_PATTERN = Pattern.compile("[A-Za-z가-힣]{1,10}");


    /* 클라이언트측 가독성 및 매개변수 순서 오류 방지를 위해 빌더 적용 */
    /* userName의 검증은 생성 시만 사용되므로 바로 검증 */
    @Builder
    private Customer(String userName, String password, String name) {
        if (userName == null || userName.isBlank()) { throw new IllegalArgumentException("사용자 id 입력 없"); }
        if (!USERNAME_PATTERN.matcher(userName).matches()) { throw new IllegalArgumentException("사용자 id가 규칙에 맞지 않"); }
        this.userName = userName;

        validatePassword(password);
        this.password = password;

        validateName(name);
        this.name = name;

        /* 가장 낮은 등급, 0원으로 시작 */
        this.grade = Grade.grades[0];
        this.totalUsedPrice = 0;
    }


    /* 상태 변경 로직 */

    public void initId(Long id) {
        if (this.id != null) { throw new IllegalStateException("id 이미 존재"); }

        if (id == null) { throw new IllegalArgumentException("유효하지 않은 id"); }

        this.id = id;
    }

    public void increaseTotalUsedPrice(int amount) {
        if (amount <= 0) { throw new IllegalArgumentException("누적 금액 합산을 위한 금액 잘못 입력"); }

        /* 오버플로우 체크 */
        if (amount > Integer.MAX_VALUE - totalUsedPrice) {
            totalUsedPrice = Integer.MAX_VALUE;
            return;
        }
        totalUsedPrice += amount;

        upGrade();
    }

    private void upGrade() {
        grade = grade.upGrade(totalUsedPrice);
    }


    /* 유효성 최종 검증 및 비즈니스 규칙 검증 */

    private static void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비번 입력 없음");
        }

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("비번 규칙 맞지 않음");
        }
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름 입력 없음");
        }

        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("이름 규칙 맞지 않음");
        }
    }
}
