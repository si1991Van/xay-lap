package com.viettel.construction.screens.custom.dialog;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.viettel.construction.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment {
    @BindView(R.id.tv_change)
    Button mTVChange;
    @BindView(R.id.tv_cancel)
    Button mTVCancel;
    public BottomSheetDialogFragmentOnClick mOnClick;
    private Object selectedItem;



    public void setSelectedItem(Object selectedItem) {
        this.selectedItem = selectedItem;
    }

    public Object getSelectedItem() {
        return selectedItem;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try {
//            mOnClick = (BottomSheetDialogFragmentOnClick) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " must implement TextClicked");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        mOnClick = null; // => avoid leaking, thanks @Deepscorn
//        super.onDetach();
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_change_person_in_charge, container, false);
        ButterKnife.bind(this, view);
//        if (getArguments() != null)
//            position = getArguments().getInt("POSITION_CATEGORY_HANDOVER");
        return view;
    }

    @OnClick(R.id.tv_change)
    public void onClickChange() {
        try {
            if (mOnClick != null)
                mOnClick.onClickBottonSheetChange(selectedItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tv_cancel)
    public void onClickCancelAction() {
        try {
            if (mOnClick != null)
                mOnClick.onClickBottomSheetCancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface BottomSheetDialogFragmentOnClick {
        void onClickBottonSheetChange(Object selectedItem);
        void onClickBottomSheetCancel();
    }
}
