package tech.michaeloverman.android.stickit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Michael on 5/18/2016.
 */
public class StickItFragment extends Fragment {

    private StickIt mStickIt;

    public static Fragment newInstance() { return new StickItFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);   // don't remember why this is: see BeatBox

        mStickIt = new StickIt(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stickit, container, false);

//        RelativeLayout rl = ()view.findViewById(R.id.fragment_container);

        return view;
    }
}
