package tech.michaeloverman.android.stickit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Michael on 5/18/2016.
 */
public class StickItFragment extends Fragment {
    private final String DEBUG_TAG = "StickItFragment";
    private static final String DIALOG_PATTERN = "DialogPattern";
    private static final int REQUEST_PATTERN_LENGTH = 4;
    private static final String DIALOG_SPACING = "DialogSpacing";
    private static final int REQUEST_PATTERN_SPACING = 0;
    private Sequence mSequence;
    private TextView mStickingView;
    private TextView mInfoView;

    public static Fragment newInstance() { return new StickItFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("StickItFragment onCreate()");
        setRetainInstance(true);   // see BeatBox for why this, what it does...
        setHasOptionsMenu(true);
        // get last times sequence length from savedInstanceState and use here:
        // mSequence = new Sequence(sequenceLength);
        mSequence = new Sequence(getContext(), 10);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("StickItFragment onCreateView()");
        View view = inflater.inflate(R.layout.fragment_stickit, container, false);

        mStickingView = (TextView) view.findViewById(R.id.sticking_view);
        mInfoView = (TextView) view.findViewById(R.id.info_view);

        final GestureDetector mDetector = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {
                    float startX = 0;
                    float startY = 0;
                    final int MIN_DISTANCE = 1;
                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

                        return super.onScroll(e1, e2, distanceX, distanceY);
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        if (Math.abs(velocityX) > Math.abs(velocityY)) {
                            if (e1.getX() - e2.getX() > MIN_DISTANCE) {
                                displaceL();
                            } else if (e2.getX() - e1.getX() > MIN_DISTANCE) {
                                displaceR();
                            }
                            mInfoView.setText("Int: " + mSequence.getInt());
                       } else {

                            if (e1.getY() - e2.getY() > MIN_DISTANCE) {
                                cycleUp();
                            } else if (e2.getY() - e1.getY() > MIN_DISTANCE){
                                cycleDown();
                            }
                            mInfoView.setText("Int: " + mSequence.getInt());

                        }


                        return super.onFling(e1, e2, velocityX, velocityY);
                    }

                    @Override
                    public boolean onDown(MotionEvent e) {
                        startX = e.getX();
                        startY = e.getY();
                        return true;
                    }

                });
    //    mStickingView.setOnTouchListener(this);
        mStickingView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mDetector.onTouchEvent(event);
            }
        });
        mStickingView.setText(mSequence.toString());
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_stickit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        FragmentManager manager = getFragmentManager();
        DialogFragment dialog;

        switch (item.getItemId()) {
            case R.id.set_pattern_length:
                dialog = new PatternLengthDialogFragment();
                dialog.setTargetFragment(StickItFragment.this, REQUEST_PATTERN_LENGTH);
                dialog.show(manager, DIALOG_PATTERN);
                return true;
            case R.id.set_pattern_spacing:
                dialog = new PatternSpacingDialogFragment();
                dialog.setTargetFragment(StickItFragment.this, REQUEST_PATTERN_SPACING);
                dialog.show(manager, DIALOG_SPACING);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void updateViews() {
        mStickingView.setText(mSequence.toString());
    }
    private void cycleUp() {
        mSequence.cycleUp();
        updateViews();
    }

    private void cycleDown() {
        mSequence.cycleDown();
        updateViews();
    }

    private void displaceL() {
        mSequence.rhythmicDisplaceL();
        updateViews();
    }

    private void displaceR() {
        mSequence.rhythmicDisplaceR();
        updateViews();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_PATTERN_LENGTH) {
            int newLength = (int) data.getSerializableExtra(PatternLengthDialogFragment.EXTRA_NEW_LENGTH);
            mSequence.newSequenceLength(newLength);
            updateViews();
            return;
        }

        if (requestCode == REQUEST_PATTERN_SPACING) {
            int newSpacing = (int) data.getSerializableExtra(PatternSpacingDialogFragment.EXTRA_SPACING);
            mSequence.setNewSpacing(newSpacing);
            updateViews();
            return;
        }
    }
/*
    @Override
    public void onItemClicked(int newLength) {
        Toast.makeText(getActivity(), "Dialog returned " + newLength, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClicked() {
        Toast.makeText(getActivity(), "Dialog canceled", Toast.LENGTH_SHORT).show();
    }
*/

    /*public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return this.getActivity().onTouchEvent(event);
    }*/

/*    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v != mStickingView) return false;

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Log.d(DEBUG_TAG,"Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.d(DEBUG_TAG,"Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.d(DEBUG_TAG,"Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                Log.d(DEBUG_TAG,"Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.d(DEBUG_TAG,"Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default :
                return false;
        }
    }*/

    /*@Override
    public boolean onDown(MotionEvent e) {
        Toast.makeText(this.getActivity(), "Down", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Toast.makeText(this.getActivity(), "Single Tap", LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        Log.d(DEBUG_TAG, "onScroll: " + e1.toString()+e2.toString());
        Toast.makeText(this.getActivity(), "Scrolling..", LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Toast.makeText(this.getActivity(), "Fling...", Toast.LENGTH_SHORT).show();
        return true;
    }*/

}
