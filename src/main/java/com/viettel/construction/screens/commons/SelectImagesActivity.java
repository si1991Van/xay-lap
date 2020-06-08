package com.viettel.construction.screens.commons;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.db.BacDzDbHelper;
import com.viettel.construction.model.db.ImageCapture;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public class SelectImagesActivity extends AppCompatActivity {

    @BindView(R.id.imgSendImage)
    View imgSendImage;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.grdViewData)
    GridView grdData;

    @BindView(R.id.btnDeleteImage)
    Button btnDeleteImage;

    @BindView(R.id.tvNodata)
    TextView tvNoData;

    private ArrayList<ImageCapture> datas;
    private SelectImageAdapter adapter;
    private AlertDialog alertDialogDelImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_images);
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        imgSendImage.setVisibility(View.INVISIBLE);
        txtHeader.setText(R.string.selectImage);
        datas = BacDzDbHelper.getInstance(this).getAllImage();
        adapter = new SelectImageAdapter(datas, this);
        grdData.setAdapter(adapter);

        grdData.setOnItemClickListener((adapterView, view, position, id) -> {
            try {
                adapter.getDatas().get(position).setSelected(!adapter.getDatas().get(position).isSelected());
                adapter.notifyDataSetChanged();
                ImageView imageView = view.findViewById(R.id.imgPhoto);
                if (imageView != null) {
                    ValueAnimator animator;
                    if (adapter.getDatas().get(position).isSelected()) {
                        animator = ValueAnimator.ofFloat(1f, 0.8f);
                    } else {
                        animator = ValueAnimator.ofFloat(0.8f, 1f);
                    }
                    animator.addUpdateListener((animation) -> {
                        imageView.setAlpha((Float) animation.getAnimatedValue());
                    });
                    animator.setDuration(500);
                    animator.start();
                }

                int totalImagSelected = Observable.from(adapter.datas).filter(x -> x.isSelected()).count().toBlocking().first();
                if (totalImagSelected > 0) {
                    imgSendImage.setVisibility(View.VISIBLE);
                    btnDeleteImage.setVisibility(View.VISIBLE);
                } else {
                    imgSendImage.setVisibility(View.INVISIBLE);
                    btnDeleteImage.setVisibility(View.GONE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        if (adapter.datas.size() == 0) {
            tvNoData.setVisibility(View.VISIBLE);
            grdData.setVisibility(View.GONE);
        } else {
            tvNoData.setVisibility(View.GONE);
            grdData.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btnDeleteImage)
    public void deleteImage() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(SelectImagesActivity.this);
            builder.setMessage("Bạn có muốn xóa các ảnh đã chọn khỏi bộ nhớ không?");
            builder.setPositiveButton("Có", (dialogInterface, i) -> {
                alertDialogDelImage.dismiss();
                for (int idex = 0; idex < adapter.getCount(); idex++) {
                    if (adapter.getDatas().get(idex).isSelected()) {
                        BacDzDbHelper.getInstance(this).deleteImage(adapter.getDatas().get(idex).getId());
                    }
                }
                //Load lại
                adapter.datas = BacDzDbHelper.getInstance(this).getAllImage();
                adapter.notifyDataSetChanged();
                alertDialogDelImage = null;

                int totalImagSelected = Observable.from(adapter.datas).filter(x -> x.isSelected()).count().toBlocking().first();
                if (totalImagSelected > 0) {
                    imgSendImage.setVisibility(View.VISIBLE);
                    btnDeleteImage.setVisibility(View.VISIBLE);
                } else {
                    imgSendImage.setVisibility(View.INVISIBLE);
                    btnDeleteImage.setVisibility(View.GONE);
                }

                if (adapter.datas.size() == 0) {
                    tvNoData.setVisibility(View.VISIBLE);
                    grdData.setVisibility(View.GONE);
                } else {
                    tvNoData.setVisibility(View.GONE);
                    grdData.setVisibility(View.VISIBLE);
                }
            });
            builder.setNegativeButton("Không", (dialogInterface, i) -> {
                alertDialogDelImage.dismiss();
                alertDialogDelImage = null;
            });
            alertDialogDelImage = builder.create();
            alertDialogDelImage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.imgSendImage)
    public void sendImageSelected() {
        List<ImageCapture> data =
                Observable.from(adapter.datas).filter(x -> x.isSelected()).toList().toBlocking().first();

        Intent back = new Intent();
        back.putExtra(ParramConstant.SelectedImage_Key, new ArrayList<>(data));
        setResult(ParramConstant.TaskContinueToStop_ResultCode, back);
        //
        finish();
    }

    @OnClick(R.id.imgClose)
    public void onClose() {
        finish();
    }


    class SelectImageAdapter extends BaseAdapter {

        private ArrayList<ImageCapture> datas;
        private Context context;

        public ArrayList<ImageCapture> getDatas() {
            return datas;
        }

        public SelectImageAdapter(ArrayList<ImageCapture> datas, Context context) {
            this.datas = datas;
            this.context = context;
        }


        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public ImageCapture getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null)
                view = LayoutInflater.from(context).inflate(R.layout.image_item, null);
            ImageCapture vm = getItem(position);
            ImageView imgPhoto = view.findViewById(R.id.imgPhoto);
            ImageView imgCheck = view.findViewById(R.id.imgCheck);

            Bitmap bitmap = StringUtil.setImage(vm.getImageData());
            BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
            imgPhoto.setBackground(ob);
            if (vm.isSelected()) {
                imgCheck.setVisibility(View.VISIBLE);
            } else {
                imgCheck.setVisibility(View.GONE);
            }
            return view;
        }
    }
}
