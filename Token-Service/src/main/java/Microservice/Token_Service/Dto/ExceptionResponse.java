package Microservice.Token_Service.Dto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {

	private String errorid;
	private int errorcode;
	private String message;
	private HttpStatus status;
	private String timestamp;
	
	public static String parseErrorMessage(String errorMessage) {
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(errorMessage);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return errorMessage;
	}
	
	
	
	public String getErrorid() {
		return errorid;
	}
	public void setErrorid(String errorid) {
		this.errorid = errorid;
	}
	public int getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public ExceptionResponse(String errorid, int errorcode, String message, HttpStatus status, String timestamp) {
		super();
		this.errorid = errorid;
		this.errorcode = errorcode;
		this.message = message;
		this.status = status;
		this.timestamp = timestamp;
	}
	public ExceptionResponse() {
	}



	@Override
	public String toString() {
		return "ExceptionResponse [errorid=" + errorid + ", errorcode=" + errorcode + ", message=" + message
				+ ", status=" + status + ", timestamp=" + timestamp + "]";
	}
	
	
}
