package com.viettel.construction.screens.wrac;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viettel.construction.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BBillFragment extends TabDeliveryBillChildBase
         {
    @Override
    public boolean isABill() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_abill, container, false);
    }


}
