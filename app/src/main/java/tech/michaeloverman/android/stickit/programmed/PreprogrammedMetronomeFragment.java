package tech.michaeloverman.android.stickit.programmed;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tech.michaeloverman.android.stickit.Metronome;
import tech.michaeloverman.android.stickit.R;
import tech.michaeloverman.android.stickit.pojos.PieceOfMusic;
import tech.michaeloverman.android.stickit.pojos.TitleKeyObject;
import tech.michaeloverman.android.stickit.utils.MetronomeListener;

/**
 * Created by Michael on 2/24/2017.
 */

public class PreprogrammedMetronomeFragment extends Fragment
        implements WorksListAdapter.WorksListAdapterOnClickHandler,
        SelectComposerFragment.ComposerCallback, MetronomeListener {
    private static final String TAG = PreprogrammedMetronomeFragment.class.getSimpleName();
    private static final boolean UP = true;
    private static final boolean DOWN = false;
    private static final int MAXIMUM_TEMPO = 350;
    private static final int MINIMUM_TEMPO = 1;

    private PieceOfMusic mCurrentPiece;
    private List<TitleKeyObject> mPiecesList;
    private int mCurrentTempo = 120;
    private String mCurrentComposer = "Cirone, Anthony";
    private Metronome mMetronome;
    private boolean mMetronomeRunning;

    @BindView(R.id.current_program_title)
    TextView mTVCurrentPiece;
    @BindView(R.id.current_tempo_setting)
    TextView mTVCurrentTempo;
    @BindView(R.id.start_stop_button)
    Button mStartStopButton;
    @BindView(R.id.tempo_up_button)
    ImageButton mTempoUpButton;
    @BindView(R.id.tempo_down_button)
    ImageButton mTempoDownButton;
    @BindView(R.id.other_pieces_label)
    TextView mWorkTitlesLabel;
    @BindView(R.id.piece_list_recycler_view)
    RecyclerView mPiecesRecyclerView;
    @BindView(R.id.select_composer_button)
    Button mSelectComposer;

    private WorksListAdapter mAdapter;
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

        // update label
        // set current piece - last one used...


        // set current tempo
        // update marking

        // get list of piece options

        mAdapter = new WorksListAdapter(this.getActivity(), mPiecesList, this);
        mPiecesRecyclerView.setAdapter(mAdapter);
        mPiecesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

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
        composerSelected(mCurrentComposer);

        return view;
    }

    @OnClick(R.id.tempo_up_button)
    public void increaseTempo() {
        changeTempo(UP);
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

    @OnClick(R.id.start_stop_button)
    public void metronomeStartStop() {
        if(mMetronomeRunning) {
            mMetronome.stop();
            mMetronomeRunning = false;
            mStartStopButton.setText("Start");
        } else {
            if(mCurrentPiece == null) {
                Toast.makeText(this.getContext(), "Select a piece to program metronome", Toast.LENGTH_SHORT).show();
                return;
            }
            mMetronomeRunning = true;
            mStartStopButton.setText("Stop");
            mMetronome.play(mCurrentPiece, mCurrentTempo);
        }
    }

    @OnClick(R.id.select_composer_button)
    public void selectComposer() {
        mCurrentPiece = null;
        Fragment fragment = SelectComposerFragment.newInstance(this);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // TODO save current piece

        // TODO save current tempo setting

    }

    private void composerSelected(String composer) {
        mCurrentComposer = composer;
        FirebaseDatabase.getInstance().getReference().child("composers").child(mCurrentComposer)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> pieceList = dataSnapshot.getChildren();
                        ArrayList<TitleKeyObject> list = new ArrayList<>();
                        for (DataSnapshot snap : pieceList) {
                            list.add(new TitleKeyObject(snap.getKey(), snap.getValue().toString()));
                        }
//                        Collections.sort(list);
                        mAdapter.setTitles(list);
                        mWorkTitlesLabel.setText(getString(R.string.work_titles_label, mCurrentComposer));
                        onClick(list.get(0).getKey());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    @Override
    public void onClick(String pieceId) {
        FirebaseDatabase.getInstance().getReference().child("pieces").child(pieceId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mCurrentPiece = dataSnapshot.getValue(PieceOfMusic.class);
                        updateGUI();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
//        Log.d(TAG, mCurrentPiece.toString());
//        Log.d(TAG, mCurrentPiece.getAuthor());
//        Log.d(TAG, mCurrentPiece.getTitle());
//        Log.d(TAG, mCurrentPiece.getBeats().toString());
//        Log.d(TAG, mCurrentPiece.getDownBeats().toString());
//        Log.d(TAG, mCurrentPiece.getSubdivision() + "");
    }

    private void updateGUI() {
        // TODO set TitleViews etc
        mTVCurrentPiece.setText(mCurrentPiece.getTitle());

    }


    @Override
    public void newComposer(String name) {
        Log.d(TAG, "newComposer() callback");
        mCurrentComposer = name;
    }
}
