package com.noblel.coolview;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

/**
 * @author Noblel
 */
public class PageFragment extends Fragment {
    @LayoutRes
    int layoutRes;

    public static PageFragment newInstance(@LayoutRes int res) {
        PageFragment pageFragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt("productLayoutRes", res);
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        ViewStub viewStub = view.findViewById(R.id.sampleStub);
        viewStub.setLayoutResource(layoutRes);
        viewStub.inflate();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            layoutRes = bundle.getInt("productLayoutRes");
        }
    }
}
