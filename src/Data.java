public class Data {
    public static Integer CODE_ERROR = -1;
    public static Integer CODE_ARAB = 1;
    public static Integer CODE_RIM = 2;
    private Integer result; // содержит результатное значение
    private Integer code; // код ошибки

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
