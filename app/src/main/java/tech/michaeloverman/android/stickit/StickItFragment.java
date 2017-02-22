package tech.michaeloverman.android.stickit;

import android.app.Activity;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import tech.michaeloverman.android.stickit.dialogs.PatternLengthDialogFragment;
import tech.michaeloverman.android.stickit.dialogs.PatternSpacingDialogFragment;
import tech.michaeloverman.android.stickit.dialogs.ProgrammedMetronomeDialogFragment;
import tech.michaeloverman.android.stickit.dialogs.StonePatternsDialogFragment;
import tech.michaeloverman.android.stickit.dialogs.TempoMarkingDialogFragment;
import tech.michaeloverman.android.stickit.pojos.PieceOfMusic;

/**
 * Created by Michael on 5/18/2016.
 */
public class StickItFragment extends Fragment {
    private static final int REQUEST_STONE_PATTERNS = 8;
    private static final String DIALOG_STONE = "DialogStone";
    private static final int REQUEST_TEMPO_MARKING = 120;
    private static final String DIALOG_TEMPO = "DialogTempo";
    private static final String TAG = StickItFragment.class.getSimpleName();

    private static final String DIALOG_PATTERN = "DialogPattern";
    private static final int REQUEST_PATTERN_LENGTH = 4;
    private static final String DIALOG_SPACING = "DialogSpacing";
    private static final int REQUEST_PATTERN_SPACING = 0;
    private static final int REQUEST_CUSTOM_METRONOME = 144;
    private static final String DIALOG_CUSTOM = "DialogCustomMetronome";

    private Sequence mSequence;
    private TextView mStickingView;
    private TextView mInfoView;
    private ImageButton mNextStoneButton, mPreviousStoneButton;
    private TextView mStoneLabel;
    private TextView mCountdownTimer;
    private boolean mTiming;
    private TextView mTempoMarking;

    private boolean mDoingStones;
    private int mTempo = 120;

    private Metronome mMetronome;
    private SoundPool mSoundPool;
    private Button mStartButton;
    private boolean mMetronomeRunning;

    private PieceOfMusic mCirone12;
    private PieceOfMusic getCirone12() {
        PieceOfMusic p = new PieceOfMusic("Portraits in Rhythm 12");
        p.setAuthor("Anthony Cirone");
        p.setSubdivision(4);
//        p.setBeats(HardData.testPatternBeats);
        p.setBeats(HardData.cirone16Beats);
//        p.setDownBeats(HardData.testPatternDownBeats);
        p.setDownBeats(HardData.cirone16DownBeats);
        return p;
    }

    public static Fragment newInstance() { return new StickItFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "StickItFragment onCreate()");
        setRetainInstance(true);   // see BeatBox for why this, what it does...
        setHasOptionsMenu(true);
        // get last times sequence length from savedInstanceState and use here:
        // mSequence = new Sequence(sequenceLength);
        mSequence = new Sequence(getContext(), 4);
        mDoingStones = false;
        mMetronome = new Metronome(getActivity());
        mMetronomeRunning = false;
        mCirone12 = getCirone12();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "StickItFragment onCreateView()");
        View view = inflater.inflate(R.layout.fragment_stickit, container, false);

        mCountdownTimer = (TextView) view.findViewById(R.id.countdown);
        mCountdownTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTiming) {
                    stopTimer();
                } else {
                    startTimer();
                }
            }
        });
        mTempoMarking = (TextView) view.findViewById(R.id.tempo_marking);
        mTempoMarking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTempoMarking();
            }
        });
        mStartButton = (Button) view.findViewById(R.id.start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopStartMetronome();
            }
        });
        mNextStoneButton = (ImageButton) view.findViewById(R.id.button_next_stone);
        mNextStoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStone();
            }
        });
        mPreviousStoneButton = (ImageButton) view.findViewById(R.id.button_previous_stone);
        mPreviousStoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousStone();
            }
        });
        mStoneLabel = (TextView) view.findViewById(R.id.stone_label);
        if(mDoingStones) {
            mPreviousStoneButton.setVisibility(View.VISIBLE);
            mNextStoneButton.setVisibility(View.VISIBLE);
            mStoneLabel.setVisibility(View.VISIBLE);
        } else {
            mPreviousStoneButton.setVisibility(View.INVISIBLE);
            mNextStoneButton.setVisibility(View.INVISIBLE);
            mStoneLabel.setVisibility(View.INVISIBLE);
        }
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

                       } else {

                            if (e1.getY() - e2.getY() > MIN_DISTANCE) {
                                cycleUp();
                            } else if (e2.getY() - e1.getY() > MIN_DISTANCE){
                                cycleDown();
                            }


                        }
                        //updateViews();

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

    private void stopStartMetronome() {

        if(mMetronomeRunning) {
            mMetronome.stop();
            mMetronomeRunning = false;
            mStartButton.setText(R.string.start);
        } else {
            mMetronomeRunning = true;
            mStartButton.setText(R.string.stop);
            mMetronome.play(mCirone12, mTempo);
        }
    }


    CountDownTimer mTimer;
    private void startTimer() {
        mTiming = true;
        mInfoView.setText("starting timer");
        mTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) millisUntilFinished / 60000;
                int seconds = (int) (millisUntilFinished % 60000) / 1000;
                String timerText = String.format("%d:%02d", minutes, seconds);
                mCountdownTimer.setText(timerText);
            }

            @Override
            public void onFinish() {
                mCountdownTimer.setText("Done!");
                logCompletedPattern();
            }
        };
        mTimer.start();
    }

    private void stopTimer() {
        mTiming = false;
        mInfoView.setText("stopping timer");
        mTimer.cancel();
    }

    private void logCompletedPattern() {
        // do stuff here to track progress - database, etc.
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_stickit, menu);
    }

    private void newTempoMarking() {
        FragmentManager manager = getFragmentManager();
        DialogFragment dialog = TempoMarkingDialogFragment.newInstance(mTempo);
        dialog.setTargetFragment(StickItFragment.this, REQUEST_TEMPO_MARKING);
        dialog.show(manager, DIALOG_TEMPO);

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
            case R.id.stone_patterns:
                dialog = new StonePatternsDialogFragment();
                dialog.setTargetFragment(StickItFragment.this, REQUEST_STONE_PATTERNS);
                dialog.show(manager, DIALOG_STONE);
                return true;
            case R.id.programmed_metronome:
                dialog = new ProgrammedMetronomeDialogFragment();
                dialog.setTargetFragment(StickItFragment.this, REQUEST_CUSTOM_METRONOME);
                dialog.show(manager, DIALOG_CUSTOM);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void updateViews() {
        mStickingView.setText(mSequence.toString());
        mInfoView.setText("Int: " + mSequence.getInt());
        if(mDoingStones && mSequence.isStone()) {
            mStoneLabel.setText("Stone " + mSequence.getCurrentStone());
        } else {
            mStoneLabel.setText("");
        }
        mTempoMarking.setText(Integer.toString(mTempo));
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

    public void nextStone() {
        mSequence.nextStone();
        updateViews();
    }
    public void previousStone() {
        mSequence.previousStone();
        updateViews();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        switch(requestCode) {
            case REQUEST_PATTERN_LENGTH:
                int newLength = (int) data.getSerializableExtra(PatternLengthDialogFragment.EXTRA_NEW_LENGTH);
                if (newLength == 11) newLength = 16;
                mSequence.newSequenceLength(newLength);
                updateViews();
                break;
            case REQUEST_PATTERN_SPACING:
                int newSpacing = (int) data.getSerializableExtra(PatternSpacingDialogFragment.EXTRA_SPACING);
                mSequence.setNewSpacing(newSpacing);
                updateViews();
                break;
            case REQUEST_STONE_PATTERNS:
                boolean response = (boolean) data.getSerializableExtra(StonePatternsDialogFragment.EXTRA_STONE_RESPONSE);
                if (response) {
                    mSequence.newSequenceLength(8);
                    mSequence.setNewSpacing(Sequence.OPEN_SPACING_BIG_SMALL);
                    mSequence.setDoingStones(true);
                    mDoingStones = true;
                    mNextStoneButton.setVisibility(View.VISIBLE);
                    mPreviousStoneButton.setVisibility(View.VISIBLE);
                    mStoneLabel.setVisibility(View.VISIBLE);
                } else {
                    mNextStoneButton.setVisibility(View.INVISIBLE);
                    mPreviousStoneButton.setVisibility(View.INVISIBLE);
                    mStoneLabel.setVisibility(View.INVISIBLE);
                    mSequence.setDoingStones(false);
                    mDoingStones = false;
                }
                updateViews();
                break;
            case REQUEST_TEMPO_MARKING:
                int newTempo = (int) data.getSerializableExtra(TempoMarkingDialogFragment.EXTRA_TEMPO);
                mTempo = newTempo;
                updateViews();
                break;
            case REQUEST_CUSTOM_METRONOME:


                updateViews();
                break;
            default:
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
