package com.viettel.construction.screens.menu_bgmb;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.R;
import com.viettel.construction.appbase.ImageCompleteBgmbAdapter;
import com.viettel.construction.common.App;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionBGMBDTO;
import com.viettel.construction.model.api.ConstructionBGMBResponse;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.screens.custom.dialog.CustomProgress;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BgmbTuyenActivity extends AppCompatActivity {
    private final String TAG = "VTBgmbTuyen";
    private ImageCompleteBgmbAdapter mAdapter;
    public static ArrayList<ConstructionImageInfo> mListUrl;
    private ConstructionBGMBDTO constructionBGMBDTO;
    private long id;
    private long stationType;

    @BindView(R.id.rDQuangNgamUpdate)
    RadioButton rDQuangNgamUpdate;
    @BindView(R.id.rDQuangTreoUpdate)
    RadioButton rDQuangTreoUpdate;
    @BindView(R.id.edtDodaiTuyenQN)
    EditText edtDodaiTuyenQN;
    @BindView(R.id.edtDodaiTuyenQT)
    EditText edtDodaiTuyenQT;
    @BindView(R.id.edtDoDaiTrucTiep)
    EditText edtDoDaiTrucTiep;
    @BindView(R.id.edtCapBexay)
    EditText edtCapBexay;
    @BindView(R.id.edtCapBeCoSan)
    EditText edtCapBeCoSan;
    @BindView(R.id.txtHeader_update)
    TextView txtHeader_update;
    @BindView(R.id.checkboxVuongUpdate)
    CheckBox checkboxVuongUpdate;
    @BindView(R.id.titleVuongUpdate)
    TextView titleVuongUpdate;
    @BindView(R.id.noidungVuongUpdate)
    EditText noidungVuongUpdate;
    @BindView(R.id.rv_list_image_update)
    RecyclerView rv_list_image_update;
    @BindView(R.id.prg_loading_update)
    CustomProgress progress;
    @BindView(R.id.layoutQuangNgam)
    LinearLayout layoutQuangNgam;
    @BindView(R.id.layoutQuangTreo)
    LinearLayout layoutQuangTreo;
    @BindView(R.id.edtCotCosan)
    EditText edtCotCosan;
    @BindView(R.id.edtSoCot)
    EditText edtSoCot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgmb_tuyen);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ((App) getApplication()).setNeedUpdateBGMB(false);
        progress.setLoading(true);
        loadData();
        progress.setLoading(false);
    }

    private void initListImageFromServer() {
        mListUrl = new ArrayList<>();
        ApiManager.getInstance().loadImageBGMBTuyen(id, ConstructionBGMBResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ConstructionBGMBResponse resultApi = ConstructionBGMBResponse.class.cast(result);
                    if (resultApi.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        Log.d(TAG, "initListImageFromServer - RESULT_STATUS_OK ");
                        if (resultApi.getConstructionImageInfo() != null && resultApi.getConstructionImageInfo().size() > 0) {
                            // add image to list
                            mListUrl.addAll(resultApi.getConstructionImageInfo());
                            Log.d(TAG, "initListImageFromServer - size : " + mListUrl.size());
                            setupRecyclerView();
                            progress.setLoading(false);
                        } else {
                            progress.setLoading(false);
                            if (resultApi.getResultInfo().getMessage() != null && resultApi.getResultInfo().getMessage().length() > 0) {
                                Toast.makeText(BgmbTuyenActivity.this, resultApi.getResultInfo().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                progress.setLoading(false);
                Toast.makeText(BgmbTuyenActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkAllView() {
        txtHeader_update.setText(constructionBGMBDTO.getConstructionCode());

        if (stationType == 3) {
            layoutQuangNgam.setVisibility(View.VISIBLE);
            layoutQuangTreo.setVisibility(View.GONE);
            rDQuangNgamUpdate.setChecked(true);
            rDQuangTreoUpdate.setChecked(false);
            rDQuangTreoUpdate.setClickable(false);
            rDQuangNgamUpdate.setClickable(false);
            edtDodaiTuyenQN.setClickable(false);
            edtDodaiTuyenQN.setFocusable(false);
            edtDodaiTuyenQN.setEnabled(false);
            if (constructionBGMBDTO.getTotalLength() != null) {
                edtDodaiTuyenQN.setText((constructionBGMBDTO.getTotalLength().replace(".0", "")));
            } else {
                edtDodaiTuyenQN.setText("0");
            }

            edtDoDaiTrucTiep.setClickable(false);
            edtDoDaiTrucTiep.setFocusable(false);
            edtDoDaiTrucTiep.setEnabled(false);
            if (constructionBGMBDTO.getHiddenImmediacy() != null) {
                edtDoDaiTrucTiep.setText((constructionBGMBDTO.getHiddenImmediacy().replace(".0", "")));
            } else {
                edtDoDaiTrucTiep.setText("0");
            }

            edtCapBexay.setClickable(false);
            edtCapBexay.setFocusable(false);
            edtCapBexay.setEnabled(false);

            if (constructionBGMBDTO.getCableInTank() != null) {
                edtCapBexay.setText((constructionBGMBDTO.getCableInTank().replace(".0", "")));
            } else {
                edtCapBexay.setText("0");
            }

            edtCapBeCoSan.setClickable(false);
            edtCapBeCoSan.setFocusable(false);
            edtCapBeCoSan.setEnabled(false);
            if (constructionBGMBDTO.getCableInTankDrain() != null) {
                edtCapBeCoSan.setText((constructionBGMBDTO.getCableInTankDrain().replace(".0", "")));
            } else {
                edtCapBeCoSan.setText("0");
            }

        } else if (stationType == 4) {
            layoutQuangNgam.setVisibility(View.GONE);
            layoutQuangTreo.setVisibility(View.VISIBLE);
            rDQuangNgamUpdate.setChecked(false);
            rDQuangTreoUpdate.setChecked(true);
            rDQuangTreoUpdate.setClickable(false);
            rDQuangNgamUpdate.setClickable(false);

            edtDodaiTuyenQT.setClickable(false);
            edtDodaiTuyenQT.setFocusable(false);
            edtDodaiTuyenQT.setEnabled(false);
            if (constructionBGMBDTO.getTotalLength() != null) {
                edtDodaiTuyenQT.setText((constructionBGMBDTO.getTotalLength().replace(".0", "")));
            } else {
                edtDodaiTuyenQT.setText("0");
            }

            edtSoCot.setClickable(false);
            edtSoCot.setFocusable(false);
            edtSoCot.setEnabled(false);
            if (constructionBGMBDTO.getPlantColunms() != null) {
                edtSoCot.setText((constructionBGMBDTO.getPlantColunms().replace(".0", "")));
            } else {
                edtSoCot.setText("0");
            }

            edtCotCosan.setClickable(false);
            edtCotCosan.setFocusable(false);
            edtCotCosan.setEnabled(false);
            if (constructionBGMBDTO.getAvailableColumns() != null) {
                edtCotCosan.setText((constructionBGMBDTO.getAvailableColumns().replace(".0", "")));
            } else {
                edtCotCosan.setText("0");
            }
        }
        // noi dung vuong
        checkboxVuongUpdate.setClickable(false);
        titleVuongUpdate.setVisibility(View.GONE);
        noidungVuongUpdate.setVisibility(View.GONE);
        if (constructionBGMBDTO.getReceivedObstructContent() == null || constructionBGMBDTO.getReceivedObstructContent().length() == 0) {
            checkboxVuongUpdate.setChecked(false);
        } else {
            checkboxVuongUpdate.setChecked(true);
            titleVuongUpdate.setVisibility(View.VISIBLE);
            noidungVuongUpdate.setVisibility(View.VISIBLE);
            noidungVuongUpdate.setText(constructionBGMBDTO.getReceivedObstructContent());
            noidungVuongUpdate.setClickable(false);
            noidungVuongUpdate.setFocusable(false);
            noidungVuongUpdate.setEnabled(false);
        }


    }

    private void loadData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            constructionBGMBDTO = (ConstructionBGMBDTO) bundle.getSerializable(VConstant.BUNDLE_KEY_OBJECT_BGMB);
            id = constructionBGMBDTO.getAssignHandoverId();
            stationType = constructionBGMBDTO.getStationType();
            checkAllView();
            initListImageFromServer();
        }
    }


    private void setupRecyclerView() {
        mAdapter = new ImageCompleteBgmbAdapter(2, mListUrl, this, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_list_image_update.setLayoutManager(linearLayoutManager);
        rv_list_image_update.setAdapter(mAdapter);
    }

    @OnClick(R.id.imgBack_update)
    public void onClickBack() {
        finish();
    }

}
