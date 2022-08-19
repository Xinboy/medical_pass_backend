package result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xinbo
 */
@Data
@ApiModel(value = "全局请求回调接口模型")
public class Result<T> {

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "回调消息")
    private String message;

    @ApiModelProperty(value = "回调数据")
    private T data;

    public Result() { }

    /**
     * @param data 回调数据
     * @return result.Result<T>
     */
    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<>();
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    protected  static <T> Result<T> build(T data, ResultCodeEnum codeEnum) {
        Result <T> result = build(data);
        result.setCode(codeEnum.getCode());
        result.setMessage(codeEnum.getMessage());
        return result;
    }

    /**
     * 无回调数据的自定义返回码对象
     * @param code 返回码
     * @param message 返回结果
     * @return result.Result<T>
     */
    public static <T> Result<T> build(Integer code, String message) {
        Result <T> result = build(null);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 无回调数据的成功返回码对象
     */
    public static<T> Result<T> ok() {
        return Result.ok(null);
    }

    /**
     * 有回调数据的成功返回码对象
     */
    public static<T> Result<T> ok(T data){
        return build(data, ResultCodeEnum.SUCCESS);
    }

    public static<T> Result<T> fail(){
        return Result.fail(null);
    }

    /**
     * 有回调数据的失败返回码对象
     */
    public static<T> Result<T> fail(T data){
        return build(data, ResultCodeEnum.FAIL);
    }

    /**
     * 重新设置返回码
     */
    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    /**
     * 重新设置回调消息
     */
    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    /**
     * 接口是否是成功回调
     */
    public boolean isOk() {
        if(this.getCode().intValue() == ResultCodeEnum.SUCCESS.getCode().intValue()) {
            return true;
        }
        return false;
    }
}
