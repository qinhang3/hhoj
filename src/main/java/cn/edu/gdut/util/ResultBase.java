package cn.edu.gdut.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.gson.Gson;

public class ResultBase<T> {
	private boolean success;
	private T value;
	private String msg;
	
	public ResultBase() {
		this.setSuccess(false);
		this.setMsg("");
		this.setValue(null);
	}
	
	public ResultBase<T> setRightValueReturn(T value){
		this.setSuccess(true);
		this.setValue(value);
		return this;
	}
	
	public ResultBase<T> setErrorMsgReturn(String msg){
		this.setMsg(msg);
		this.setSuccess(false);
		return this;
	}
	
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	/**
	 * 能直接传递给用户，不包含敏感信息
	 * @return 错误详情
	 */
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String toJson(Class<T> clazz) {
        Gson gson = new Gson();
        Type objectType = type(ResultBase.class, clazz);
        return gson.toJson(this, objectType);
    }
 
    private static ParameterizedType type(@SuppressWarnings("rawtypes") final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
	
}
