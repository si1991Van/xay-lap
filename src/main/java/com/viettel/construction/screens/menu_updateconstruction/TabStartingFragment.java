package com.viettel.construction.screens.menu_updateconstruction;


import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.model.constructionextra.ConstructionExtraDTORequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabStartingFragment extends Fragment {

    @BindView(R.id.tvTime)
    TextView tvTime;
    private Date currentDate = new Date();

    private ConstructionExtraDTORequest currentConstruction = new ConstructionExtraDTORequest();





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_starting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
    }

    public void reloadData(ConstructionExtraDTORequest data){
        if (data!=null) {
            currentConstruction = data;
            if (data.getStarting_date()!=null){
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                    Date _date = data.getStarting_date();
                    if (_date != null) {
                        currentDate = _date;
                        tvTime.setText(format.format(_date));
                    }


              }else{
                currentDate = new Date();
                tvTime.setText(null);
            }
        }
    }


    @OnClick(R.id.tvTime)
    public void selectTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(getContext(), (datePicker, year, month, day) ->
                {
                    cal.set(year,month,day);
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    tvTime.setText(format.format(cal.getTime()));
                    currentDate = cal.getTime();
                    currentConstruction.setStarting_date(currentDate);
                }, cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
