package tech.michaeloverman.android.stickit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tech.michaeloverman.android.stickit.pojos.PieceOfMusic;
import tech.michaeloverman.android.stickit.pojos.TitleKeyObject;

/**
 * Created by Michael on 2/24/2017.
 */

public class PreprogrammedMetronomeFragment extends Fragment
        implements WorksListAdapter.WorksListAdapterOnClickHandler,
        SelectComposerFragment.ComposerCallback {
    private static final String TAG = PreprogrammedMetronomeFragment.class.getSimpleName();
    private static final int NEW_COMPOSER_REQUEST_CODE = 1992;

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

    public static Fragment newInstance() {
        return new PreprogrammedMetronomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        mMetronome = new Metronome(getActivity());
        mMetronomeRunning = false;

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

//        mCurrentComposer = "Cirone, Anthony";
        composerSelected(mCurrentComposer);

        return view;
    }

    @OnClick(R.id.tempo_up_button)
    public void increaseTempo() {
        mCurrentTempo++;
        updateTempoView();
    }

    private void updateTempoView() {
        mTVCurrentTempo.setText(Integer.toString(mCurrentTempo));
    }

    @OnClick(R.id.tempo_down_button)
    public void decreaseTempo() {
        mCurrentTempo--;
        updateTempoView();
    }

    @OnClick(R.id.start_stop_button)
    public void startStop() {
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
                        // TODO: set list on the adapter
                        ArrayList<TitleKeyObject> list = new ArrayList<>();
                        for (DataSnapshot snap : pieceList) {
                            list.add(new TitleKeyObject(snap.getKey(), snap.getValue().toString()));
                        }
                        Collections.sort(list);
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
                        updateProgram();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void updateProgram() {
        // TODO set TitleViews etc
        mTVCurrentPiece.setText(mCurrentPiece.getTitle());

    }


    @Override
    public void newComposer(String name) {
        Log.d(TAG, "newComposer() callback");
        mCurrentComposer = name;
    }
}
