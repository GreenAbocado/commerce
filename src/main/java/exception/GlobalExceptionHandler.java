package exception;

/* 전역 예외 처리 */
public class GlobalExceptionHandler {

    public static void handleException (Runnable logic) {
        try {
            logic.run();
        } catch (IllegalArgumentException e) {    // 입력 예외
            System.out.printf("[입력 오류]: %s", e.getMessage());
        } catch (IllegalStateException e) {    // 상태 예외
            System.out.printf("[상태 오류]: %s", e.getMessage());
        }
    }

    private GlobalExceptionHandler() {} // 외부 객체 생성 방지
}
