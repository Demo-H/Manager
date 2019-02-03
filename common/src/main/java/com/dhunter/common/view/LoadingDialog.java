package com.dhunter.common.view;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dhunter.common.R;
import com.dhunter.common.config.GlobalConfig;

/**
 * Created by dhunter on 2018/6/22.
 */

public class LoadingDialog extends DialogFragment {

    private String tip;
    private View parent;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    public static LoadingDialog newInstance(String title) {
        LoadingDialog loading = new LoadingDialog();
        Bundle args = new Bundle();
        args.putString(GlobalConfig.LOADING_TIP, title);
        loading.setArguments(args);
        return loading;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tip = getArguments().getString(GlobalConfig.LOADING_TIP);
        int style = DialogFragment.STYLE_NO_TITLE;
//        int theme = android.R.style.Theme_Holo_Light_Dialog;
        int theme = android.R.style.Theme_Holo_Dialog;
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        if(parent == null){
            parent = inflater.inflate(R.layout.dialog_loading, container, false);
        }
        if(parent != null){
            ViewGroup parentContainer = (ViewGroup) parent.getParent();
            if(parentContainer != null) {
                parentContainer.removeView(parent);
            }
        }
        TextView titleTextView = (TextView) parent.findViewById(R.id.loading_tip);
        titleTextView.setText(tip);
        return parent;
    }

}
