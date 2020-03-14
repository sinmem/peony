package com.sinmem.peony.web.conf;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.Msg;
import com.sinmem.peony.common.exception.ErrorPhoneNumberException;
import com.sinmem.peony.common.exception.SmsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.web.conf
 * @Author: sinmem
 * @CreateTime: 2019-10-19 10:36
 * @Description: 视图配置
 * @version: V1.0
 */
@ControllerAdvice
@Controller
public class WebErrorController implements ErrorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebErrorController.class);
    private static final String ERROR_PATH = "/error";

    @Autowired
    ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(ERROR_PATH)
    public @ResponseBody String error(HttpServletRequest request, HttpServletResponse response) {
        WebRequest req = new ServletWebRequest(request);
        Map<String, Object> attrs = errorAttributes.getErrorAttributes(req, true);
        Object obj = errorAttributes.getError(req);
        if (response.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
            return Result.error(404, "Not found!").toString();
        }
        return Result.error(response.getStatus(), "").setContent(attrs).toString();
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Result> bizException(ValidationException e) {
        // 发型是删除此句
        LOGGER.warn("数据校验失败: "+ e.getMessage());
        return new ResponseEntity<>(Result.error(Msg.E40011.code(), e.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();

        List<String> list = new ArrayList<>();
        for (ObjectError error : result.getAllErrors()) {
            FieldError fe = (FieldError) error;
            list.add(String.format("【%s.%s】%s", fe.getObjectName(), fe.getField(), fe.getDefaultMessage()));
        }
        return new ResponseEntity<>(Result.error(Msg.E40011).setMessages(list), HttpStatus.OK);
    }

    @ExceptionHandler(ErrorPhoneNumberException.class)
    public ResponseEntity<Result> ErrorPhoneNumberException(ErrorPhoneNumberException e){
        LOGGER.error("用户验证码发送失败: 无效的手机号");
        return new ResponseEntity<>(Result.error(Msg.E40010).setMessage("用户验证码发送失败: 无效的手机号"), HttpStatus.OK);
    }

    @ExceptionHandler(SmsException.class)
    public ResponseEntity<Result> SmsException(SmsException e){
        LOGGER.error("验证码发送失败: 未知的短信错误---"+e.getMessage());
        return new ResponseEntity<>(Result.error(Msg.E40010).setMessage("用户验证码发送失败: 未知的短信错误"), HttpStatus.OK);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Result> SQLException(SQLException e){
        LOGGER.error("SQLException 数据库异常");
        return new ResponseEntity<>(Result.error(Msg.E00002).setMessage("服务器内部错误"), HttpStatus.OK);
    }

    @ExceptionHandler(RedisConnectionFailureException.class)
    public ResponseEntity<Result> redisConnectionFailureException(RedisConnectionFailureException e){
        LOGGER.error("RedisConnectionFailureException 请检查Redis是否已启动");
        return new ResponseEntity<>(Result.error(Msg.E00002).setMessage("服务器内部错误"), HttpStatus.OK);
    }

//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ResponseEntity<Result> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
//        Result result = Result.error(Msg.E40012.code(), e.getMessage());
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public ResponseEntity<Result> bizException(MaxUploadSizeExceededException e) {
//        Result result = Result.error(Msg.E40013.code(), e.getMessage());
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Result> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        LOGGER.error("不支持的请求方式: "+ e.getMessage(), e);
        Result result = Result.error(Msg.E50001).setMessage("不支持的请求方式").setContent(e.getClass().getName());
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(MysqlDataTruncation.class)
    public ResponseEntity<Result> mysqlDataTruncation(MysqlDataTruncation e) {
        LOGGER.error("MySQL参数长度错误: "+ e.getCause(), e);
        Result result = Result.error(Msg.E20006).setMessage("MySQL参数长度错误").setContent(e.getClass().getName());
        return new ResponseEntity<>(result, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> exception(Exception e) {

        LOGGER.error(">>> 未知异常信息：" + e.getMessage(), e);
        Result result = Result.error(Msg.E11111).setContent(e.getClass().getName());
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
