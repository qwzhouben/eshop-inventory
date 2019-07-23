package com.zben.eshop.inventory.vo;

/**
 * @DESC:
 * @author: jhon.zhou
 * @date: 2019/7/23 0023 11:27
 */
public class Response {

    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Response(String status) {
        this.status = status;
    }

    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public static Response success(String status) {
        return success(status, SUCCESS);
    }

    public static Response success(String status, String message) {
        return new Response(status, message);
    }

    public static Response fail(String status) {
        return fail(status, FAIL);
    }


    public static Response fail(String status, String message) {
        return new Response(status, message);
    }
}
