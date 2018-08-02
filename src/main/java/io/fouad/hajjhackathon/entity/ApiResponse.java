package io.fouad.hajjhackathon.entity;

import java.util.Objects;

public class ApiResponse<T>
{
    private String status;
    private T result;
    private String errorCode;

    private ApiResponse(String status, T result, String errorCode)
    {
        this.status = status;
        this.result = result;
        this.errorCode = errorCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiResponse<?> that = (ApiResponse<?>) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(result, that.result) &&
                Objects.equals(errorCode, that.errorCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, result, errorCode);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "status='" + status + '\'' +
                ", result=" + result +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }

    public static <T> ApiResponse<T> success(T result)
    {
        return new ApiResponse<>("success", result, null);
    }

    public static <T> ApiResponse<T> failure(String errorCode)
    {
        return new ApiResponse<>("failure", null, errorCode);
    }
}