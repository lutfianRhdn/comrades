package id.comrade.reminder.edit;

import java.util.List;

import id.comrade.base.BaseViewState;
import id.comrade.model.Obat;

class EditReminderViewState extends BaseViewState {
    private String message;
    private List<Obat> obats;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Obat> getObats() {
        return obats;
    }

    public void setObats(List<Obat> obats) {
        this.obats = obats;
    }
}
