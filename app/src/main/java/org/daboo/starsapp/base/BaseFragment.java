package org.daboo.starsapp.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.daboo.starsapp.app.GlobalApplication;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected GlobalApplication mApplication;

    protected BaseMainActivityInterface baseMainActivityInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getContext();
        mApplication = GlobalApplication.getInstance();

        if (!(getActivity() instanceof BaseMainActivityInterface)) {
            throw new ClassCastException("Hosting activity must implement BaseMainActivityInterface");
        } else {
            baseMainActivityInterface = (BaseMainActivityInterface) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onInitView();
    }

    public abstract void onInitView();

    public abstract int getLayout();
}
