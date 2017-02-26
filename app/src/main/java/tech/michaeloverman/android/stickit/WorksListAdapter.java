package tech.michaeloverman.android.stickit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.michaeloverman.android.stickit.pojos.TitleKeyObject;

/**
 * Created by Michael on 2/25/2017.
 */

public class WorksListAdapter extends RecyclerView.Adapter<WorksListAdapter.WorksViewHolder> {
    private static final String TAG = WorksListAdapter.class.getSimpleName();

    private final Context mContext;
    private List<TitleKeyObject> mTitles;
    private final WorksListAdapterOnClickHandler mClickHandler;

    public WorksListAdapter(@NonNull Context context, List<TitleKeyObject> titles,
                            WorksListAdapterOnClickHandler handler) {
        mContext = context;
        mTitles = titles;
        mClickHandler = handler;
    }

    interface WorksListAdapterOnClickHandler {
        void onClick(String title);
    }

    @Override
    public WorksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder()");
        View item = LayoutInflater.from(mContext).inflate(R.layout.list_item_work, parent, false);
        return new WorksViewHolder(item);
    }

    @Override
    public void onBindViewHolder(WorksViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder()");
        holder.title.setText(mTitles.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mTitles == null ? 0 : mTitles.size();
    }

    public void setTitles(List<TitleKeyObject> titles) {
        mTitles = titles;
        Log.d(TAG, "setTitles() - " + mTitles.size() + " titles...");
        notifyDataSetChanged();
    }

    class WorksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.work_title)
        TextView title;

        WorksViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "WorksViewHolder constructor()");
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "WorksViewHolder onClick()");
            int position = getAdapterPosition();
            String key = mTitles.get(position).getKey();
            mClickHandler.onClick(key);
        }
    }
}
