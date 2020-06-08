package com.viettel.construction.screens.home;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.common.PrefManager;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.screens.custom.dialog.CustomProgress;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.viettel.construction.R;
import com.viettel.construction.common.ErrorMessage;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.AuthenticationInfo;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.SysUserDTO;
import com.viettel.construction.model.api.SysUserRequest;
import com.viettel.construction.model.api.login.LoginResult;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.util.ValidationUtil;

public class LoginCameraActivity extends BaseCameraActivity implements ErrorMessage {

    @BindView(R.id.edt_user_name)
    EditText edtUserName;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.prg_loading)
    CustomProgress progress;
    @BindView(R.id.cb_login)
    CheckBox cbLogin;
    private boolean showPass = false;
    private final String TAG = "VTLoginCameraActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progress.setLoading(false);
        hideKeyBoard();


        cbLogin.setOnClickListener((v) ->
                showPassword()
        );
        if (PrefManager.getInstance().getString(VConstant.UserName) != null)
            edtUserName.setText(PrefManager.getInstance().getString(VConstant.UserName));

        if (VConstant.Debug) {
            //1. Test
            //Quan ly
//            edtUserName.setText("097159");
//            edtPassword.setText("Doipass911@");

            //Nhan vien
            edtUserName.setText("069579");
            edtPassword.setText("Quan@220912");

            //2. TK that
//            edtUserName.setText("longnt12");
//            edtPassword.setText("Viettel2018a@");

//            edtUserName.setText("vuongpv4");
//            edtPassword.setText("Chinhanh2@");

            //Thuy
//            edtUserName.setText("thuynt112");
//            edtPassword.setText("Nhatminh2017#@");

            //User
//            edtUserName.setText("007573");
//            edtPassword.setText("Ctct123a@@");
        }
    }

    private void showPassword() {
        if (cbLogin.isChecked()) {
            edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    @OnClick(R.id.btn_login)
    public void onClickLogin() {
        try {
            final String userName = edtUserName.getText().toString().trim();
            final String password = edtPassword.getText().toString();
            String checkAccResult = checkLogin(userName, password);
            Log.d(TAG, "checkAccResult : " + checkAccResult.toString());
            btnLogin.setClickable(false);
            switch (checkAccResult) {
                case I001:
                    progress.setLoading(true);

                    ApiManager.getInstance().login(userName, password, "", LoginResult.class, new IOnRequestListener() {
                        @Override
                        public <T> void onResponse(T result) {
                            try {
                                LoginResult user = LoginResult.class.cast(result);
                                ResultInfo resultInfo = user.getResultInfo();
                                Log.d(TAG, "resultInfo : " + resultInfo.getStatus().toString());
                                // resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK) - thay bang true => check accout
                                if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                                    SysUserDTO dto = user.getUserLogin();
                                    final SysUserRequest request = new SysUserRequest();

                                    request.setAuthenticationInfo(new AuthenticationInfo(userName, password, ""));
                                    request.setSysUserId(dto.getSysUserId());
                                    request.setDepartmentId(dto.getDepartmentId());
                                    request.setSysGroupId(dto.getDepartmentId());

                                    Gson gson = new Gson();
                                    String json = gson.toJson(request);
                                    String data = gson.toJson(dto);
                                    PrefManager.getInstance().put(VConstant.KEY_LOGIN_USER, json);
                                    PrefManager.getInstance().put(VConstant.KEY_REMEMBER_LOGIN, data);
                                    PrefManager.getInstance().put(VConstant.UserName, userName);

                                    //===================================================
                                    launcherNextScreen();

                                } else {
                                    btnLogin.setClickable(true);
                                    progress.setLoading(false);
                                    //                            edtUserName.setText("");
                                    //                            edtPassword.setText("");
                                    Toast.makeText(LoginCameraActivity.this, getString(R.string.wrong), Toast.LENGTH_SHORT).show();
                                    edtUserName.requestFocus();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(int statusCode) {
                            btnLogin.setClickable(true);
                            progress.setLoading(false);
                            Toast.makeText(LoginCameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                            edtUserName.requestFocus();
                        }
                    });
                    break;
                case E001:
                    setInputTypeErrorState(edtUserName, getString(R.string.error_user_name));
                    if (edtUserName != null)
                        Toast.makeText(getApplicationContext(), R.string.e_001, Toast.LENGTH_SHORT).show();
                    edtUserName.setText("");
                    btnLogin.setClickable(true);
                    progress.setLoading(false);
                    break;
                case E002:
                    setInputTypeErrorState(edtPassword, getString(R.string.error_pass_word));
                    if (edtPassword != null)
                        Toast.makeText(getApplicationContext(), R.string.e_002, Toast.LENGTH_SHORT).
                                show();
                    edtPassword.setText("");
                    btnLogin.setClickable(true);
                    progress.setLoading(false);
                    break;
                default:
                    break;
            }
            hideKeyBoard();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void launcherNextScreen() {
        Class clazz = HomeCameraActivity.class;
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    public String checkLogin(String email, String password) {
        String validateMailResult = ValidationUtil.validateAccount(email);
        String validatePassResult = ValidationUtil.validateAccount(password);
        // Validate email
        if (validateMailResult.equals(I001) && validatePassResult.equals(I001)) {
            // Validate password
            return I001;
        } else
            return E001;
    }

    public void setInputTypeErrorState(EditText et, String message) {
        if (et == null)
            return;
        et.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            et.setBackgroundTintList(getColorStateList(R.color.c3));
            et.setError(message);
        }
        et.requestFocus();
        hideKeyBoard();
    }

}