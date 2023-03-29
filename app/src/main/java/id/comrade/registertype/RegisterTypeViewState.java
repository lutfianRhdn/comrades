package id.comrade.registertype;

import id.comrade.base.BaseViewState;

class RegisterTypeViewState extends BaseViewState {
    public static final int PROCESS_USER = 0;
    public static final int PROCESS_FRIEND = 1;

    private String message;
    private int registerProcess;

    public int getRegisterProcess() {
        return registerProcess;
    }

    public void setRegisterProcess(int registerProcess) {
        this.registerProcess = registerProcess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLoading(boolean loading, int registerProcess) {
        setLoading(loading);
        this.registerProcess = registerProcess;
    }
}
