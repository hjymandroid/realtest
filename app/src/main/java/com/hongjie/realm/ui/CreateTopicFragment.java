package com.hongjie.realm.ui;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.hongjie.realm.DMApplication;
import com.hongjie.realm.R;
import com.hongjie.realm.model.Topic;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hongjiedong on 6/21/16.
 */
public class CreateTopicFragment extends BottomSheetDialogFragment {

    private EditText mTitle;
    private EditText mDesc;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.post_topic, null);
        dialog.setContentView(contentView);
        mTitle = (EditText) contentView.findViewById(R.id.title);
        mDesc = (EditText) contentView.findViewById(R.id.desc);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
        contentView.findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post();
            }
        });
    }

    public void post() {
        String title = mTitle.getText().toString();
        String desc = mDesc.getText().toString();
        final HashMap<String, String> data = new HashMap<>();
        data.put("tab", "forum");
        data.put("category", "all");
        data.put("accesstoken", "f774d468-1891-43e6-a030-a1b2574780a0");

        data.put("title", title);
        data.put("content", desc);

        Call<Topic> call = DMApplication.getApiService().post(data);
        call.enqueue(new Callback<Topic>() {
            @Override
            public void onResponse(Call<Topic> call, Response<Topic> response) {
                Log.e("TAG", response.body().toString());
                DMApplication.getRealm().beginTransaction();
                DMApplication.getRealm().copyToRealmOrUpdate(response.body());
                DMApplication.getRealm().commitTransaction();
            }

            @Override
            public void onFailure(Call<Topic> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
