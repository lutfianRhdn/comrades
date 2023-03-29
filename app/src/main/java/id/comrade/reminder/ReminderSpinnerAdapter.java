package id.comrade.reminder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import id.comrade.R;
import id.comrade.model.Obat;

public class ReminderSpinnerAdapter extends ArrayAdapter<Obat> {
    private List<Obat> obats;

    public ReminderSpinnerAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public int getCount() {
        return obats == null ? 0 : obats.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, parent);
    }

    @Nullable
    @Override
    public Obat getItem(int position) {
        return obats.get(position);
    }

    public void setObat(List<Obat> obats) {
        this.obats = obats;
        notifyDataSetChanged();
    }

    private View initView(int position, ViewGroup parent) {
        Obat obat = obats.get(position);

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.reminder_form_spinner, parent, false);

        TextView tvSpinnerItem = view.findViewById(R.id.tv_reminder_form_spinner_obat_name);
        tvSpinnerItem.setText(obat.getMerek());
        return view;
    }
}
