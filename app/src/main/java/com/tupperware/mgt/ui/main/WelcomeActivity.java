package com.tupperware.mgt.ui.main;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dhunter.common.easypermissions.EasyPermissions;
import com.tupperware.mgt.R;
import com.tupperware.mgt.base.BaseActivity;
import com.tupperware.mgt.config.Constant;
import com.tupperware.mgt.ui.login.activities.LoginSelectActivity;

import java.util.List;

/**
 * Created by dhunter on 2018/11/20.
 */

public class WelcomeActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private String[] permission_perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPermission();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initLayout() {


    }

    @Override
    protected void requestData() {

    }

    // Android 获取相关权限
    private void setPermission() {
        if(!EasyPermissions.hasPermissions(getApplicationContext(), permission_perms)) {
            EasyPermissions.requestPermissions(this, getString(R.string.get_permission),
                    Constant.LOCATION_PERMISSION_REQUESTCODE, permission_perms);
        } else {
            startToNext();
        }
    }

    private void startToNext() {
        Intent intent = new Intent(WelcomeActivity.this, LoginSelectActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.hasPermissions(getApplicationContext(), permission_perms)) {
            startToNext();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        toast("onPermissionsDenied: Please granted permission");
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
