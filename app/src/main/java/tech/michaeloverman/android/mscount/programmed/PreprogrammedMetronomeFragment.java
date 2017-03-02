package tech.michaeloverman.android.mscount.programmed;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tech.michaeloverman.android.mscount.Metronome;
import tech.michaeloverman.android.mscount.R;
import tech.michaeloverman.android.mscount.pojos.PieceOfMusic;
import tech.michaeloverman.android.mscount.utils.MetronomeListener;

/**
 * Created by Michael on 2/24/2017.
 */

public class PreprogrammedMetronomeFragment extends Fragment
        implements ProgramSelectFragment.ProgramCallback, MetronomeListener {

    private static final String TAG = PreprogrammedMetronomeFragment.class.getSimpleName();
    private static final boolean UP = true;
    private static final boolean DOWN = false;
    private static final int MAXIMUM_TEMPO = 350;
    private static final int MINIMUM_TEMPO = 1;
    private static final String CURRENT_PIECE_KEY = "current_piece_key";
    private static final String CURRENT_TEMPO_KEY = "current_tempo_key";
    private static final String CURRENT_COMPOSER_KEY = "current_composer_key";

    private PieceOfMusic mCurrentPiece;
    private int mCurrentTempo;
    private String mCurrentComposer;
    private Metronome mMetronome;
    private boolean mMetronomeRunning;

    @BindView(R.id.current_composer_name) TextView mTVCurrentComposer;
    @BindView(R.id.current_program_title) TextView mTVCurrentPiece;
    @BindView(R.id.current_tempo_setting) TextView mTVCurrentTempo;
    @BindView(R.id.start_stop_button) Button mStartStopButton;
    @BindView(R.id.tempo_up_button) ImageButton mTempoUpButton;
    @BindView(R.id.tempo_down_button) ImageButton mTempoDownButton;
    @BindView(R.id.current_measure_number) TextView mCurrentMeasureNumber;

    private Handler mRunnableHandler;
    private Runnable mDownRunnable;
    private Runnable mUpRunnable;
    private static final int INITIAL_TEMPO_CHANGE_DELAY = 400;
    private static final int FIRST_FASTER_SPEED_DELAY = 80;
    private static final int RATE_OF_DELAY_CHANGE = 2;
    private static int mTempoChangeDelay;
    private static final int ONE_LESS = INITIAL_TEMPO_CHANGE_DELAY - 2;
    private static final int MIN_TEMPO_CHANGE_DELAY = 20;

    public static Fragment newInstance() {
        return new PreprogrammedMetronomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        if(savedInstanceState != null) {
            mCurrentTempo = savedInstanceState.getInt(CURRENT_TEMPO_KEY);
//            mCurrentPiece = savedInstanceState.getString(CURRENT_PIECE_KEY);
            mCurrentComposer = savedInstanceState.getString(CURRENT_COMPOSER_KEY);
        } else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            mCurrentTempo = prefs.getInt(CURRENT_TEMPO_KEY, 120);
            mCurrentComposer = prefs.getString(CURRENT_COMPOSER_KEY, "Overman, Michael");
        }

        mMetronome = new Metronome(getActivity(), this);
        mMetronomeRunning = false;

        mRunnableHandler = new Handler();
        mTempoChangeDelay = INITIAL_TEMPO_CHANGE_DELAY;
        mDownRunnable = new Runnable() {
            @Override
            public void run() {
                if(mTempoChangeDelay == ONE_LESS) mTempoChangeDelay = FIRST_FASTER_SPEED_DELAY;
                else if (mTempoChangeDelay < MIN_TEMPO_CHANGE_DELAY) mTempoChangeDelay = MIN_TEMPO_CHANGE_DELAY;
                changeTempo(DOWN);
                mRunnableHandler.postDelayed(this, mTempoChangeDelay--);
            }
        };
        mUpRunnable = new Runnable() {
            @Override
            public void run() {
                if(mTempoChangeDelay == ONE_LESS) mTempoChangeDelay = FIRST_FASTER_SPEED_DELAY;
                else if (mTempoChangeDelay < MIN_TEMPO_CHANGE_DELAY) mTempoChangeDelay = MIN_TEMPO_CHANGE_DELAY;
                changeTempo(UP);
                mRunnableHandler.postDelayed(this, mTempoChangeDelay -= RATE_OF_DELAY_CHANGE);
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.programmed_fragment, container, false);
        ButterKnife.bind(this, view);

//        composerSelected(mCurrentComposer);

//        mAdapter = new WorksListAdapter(this.getActivity(), mPiecesList, this);
//        mPiecesRecyclerView.setAdapter(mAdapter);
//        mPiecesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        mTempoDownButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mRunnableHandler.post(mDownRunnable);
                        break;
                    case MotionEvent.ACTION_UP:
                        mRunnableHandler.removeCallbacks(mDownRunnable);
                        mTempoChangeDelay = INITIAL_TEMPO_CHANGE_DELAY;
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        mTempoUpButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mRunnableHandler.post(mUpRunnable);
                        break;
                    case MotionEvent.ACTION_UP:
                        mRunnableHandler.removeCallbacks(mUpRunnable);
                        mTempoChangeDelay = INITIAL_TEMPO_CHANGE_DELAY;
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

//        mCurrentComposer = "Cirone, Anthony";
        if(mCurrentPiece != null) {
            updateGUI();
        }
//        updateTempoView();
        return view;
    }

    private void changeTempo(boolean direction) {
        if(direction) {
            mCurrentTempo++;
        } else {
            mCurrentTempo--;
        }
        if(mCurrentTempo < MINIMUM_TEMPO) {
            mCurrentTempo = MINIMUM_TEMPO;
        } else if(mCurrentTempo > MAXIMUM_TEMPO) {
            mCurrentTempo = MAXIMUM_TEMPO;
        }
        updateTempoView();
    }

    private void updateTempoView() {
        mTVCurrentTempo.setText(Integer.toString(mCurrentTempo));
    }

    @OnClick( { R.id.current_program_label, R.id.current_program_title } )
    public void selectNewProgram() {
        mCurrentPiece = null;
        Fragment fragment = ProgramSelectFragment.newInstance(this, mCurrentComposer);
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.fragment_container, fragment);
        trans.addToBackStack(null);
        trans.commit();
    }

    @OnClick(R.id.start_stop_button)
    public void metronomeStartStop() {
        if(mMetronomeRunning) {
            Log.d(TAG, "metronomeStop() " + mCurrentComposer);
            mMetronome.stop();
            mMetronomeRunning = false;
            mStartStopButton.setText("Start");
            mCurrentMeasureNumber.setText("--");
        } else {
            Log.d(TAG, "metronomeStart() " + mCurrentPiece.getTitle());
            if(mCurrentPiece == null) {
                Toast.makeText(this.getContext(), "Select a piece to program metronome", Toast.LENGTH_SHORT).show();
                return;
            }
            mMetronomeRunning = true;
            mStartStopButton.setText("Stop");
            mMetronome.play(mCurrentPiece, mCurrentTempo);
        }
    }

    @Override
    public void metronomeMeasureNumber(String mm) {
        mCurrentMeasureNumber.setText(mm);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState() " + mCurrentPiece.getTitle() + " by " + mCurrentComposer);
        Log.d(TAG, "..... Current Tempo: " + mCurrentTempo);
        outState.putString(CURRENT_PIECE_KEY, mCurrentPiece.getTitle());
        outState.putInt(CURRENT_TEMPO_KEY, mCurrentTempo);
        outState.putString(CURRENT_COMPOSER_KEY, mCurrentComposer);

        super.onSaveInstanceState(outState);


    }

    private void updateGUI() {
        // TODO set TitleViews etc
        Log.d(TAG, "updateGUI() " + mCurrentPiece.getAuthor() + ": " + mCurrentPiece.getTitle());
        mTVCurrentPiece.setText(mCurrentPiece.getTitle());
        mTVCurrentComposer.setText(mCurrentComposer);
//        mCurrentProgramLabel.setText("Now isn't this fun.");
        updateTempoView();
    }

    @Override
    public void newPiece(PieceOfMusic piece) {
        Log.d(TAG, "newPiece() " + piece.getTitle());
        mCurrentPiece = piece;
        mCurrentComposer = mCurrentPiece.getAuthor();
//        mCurrentProgramLabel.setText("A barrel of laughs");
//        updateGUI();
    }
}
