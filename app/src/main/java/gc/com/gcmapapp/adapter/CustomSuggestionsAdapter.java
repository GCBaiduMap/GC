package gc.com.gcmapapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.ArrayList;

import gc.com.gcmapapp.R;

/**
 * Created by mancj on 27.01.17.
 */

public class CustomSuggestionsAdapter extends SuggestionsAdapter<String, CustomSuggestionsAdapter.SuggestionHolder> {

    OnSuggestSelectedListener onSuggestSelectedListener;

    public CustomSuggestionsAdapter(LayoutInflater inflater, OnSuggestSelectedListener onSuggestSelectedListener) {
        super(inflater);
        this.onSuggestSelectedListener = onSuggestSelectedListener;
    }

    @Override
    public int getSingleViewHeight() {
        return 80;
    }

    @Override
    public SuggestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.item_custom_suggestion, parent, false);
        return new SuggestionHolder(view);
    }

    @Override
    public void onBindSuggestionHolder(final String suggestion, SuggestionHolder holder, int position) {
        holder.title.setText(suggestion);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onSuggestSelectedListener != null){
                    onSuggestSelectedListener.onSuggestSelected(suggestion);
                }
            }
        });
    }



    static class SuggestionHolder extends RecyclerView.ViewHolder{
        protected TextView title;

        public SuggestionHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    public interface OnSuggestSelectedListener{
        void onSuggestSelected(String suggestion);
    }

}
