package com.viettel.construction.appbase;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/***
 * KePham
 */
public class ViewModelFragmentListBase extends RecyclerView.ViewHolder {
    public ViewModelFragmentListBase(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
