package common.response;

import common.models.Model;

import java.io.Serializable;

public class Response implements Serializable {
    protected String answer;
    protected boolean result;
    protected String error;

    public Response(String answer, boolean result) {
        this.answer = answer;
        this.result = result;
    }

    public Response(boolean result, String error) {
        this("", result);
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean getResult() {
        return result;
    }
}
