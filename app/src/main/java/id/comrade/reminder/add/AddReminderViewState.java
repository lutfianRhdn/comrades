package id.comrade.reminder.add;

import java.util.List;

import id.comrade.base.BaseViewState;
import id.comrade.model.Obat;

public class AddReminderViewState extends BaseViewState {
    private boolean finish;
    private List<Obat> obats;

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public List<Obat> getObats() {
        return obats;
    }

    public void setObats(List<Obat> obats) {
        this.obats = obats;
    }
}
