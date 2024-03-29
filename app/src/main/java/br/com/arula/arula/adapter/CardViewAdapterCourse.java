
package br.com.arula.arula.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.arula.arula.R;
import br.com.arula.arula.RecyclerViewClickPosition;
import br.com.arula.arula.model.Question;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rafael Felipe on 04/10/2017.
 */

public class CardViewAdapterCourse extends RecyclerView.Adapter<CardViewAdapterCourse.ViewHolder> {

    public String[] courses;
    private static RecyclerViewClickPosition mPositionInterface;


    @BindView(R.id.questionName)
    TextView title;
    @BindView(R.id.jobImage)
    ImageView image;

    public CardViewAdapterCourse(List<String> courses, RecyclerViewClickPosition positionInterface) {
        this.courses = courses.toArray(new String[courses.size()]);
        mPositionInterface = positionInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_question, null);
        ButterKnife.bind(this, itemLayoutView);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        title.setText(courses[position]);
        //image.setVisibility((!jobs[position].getAudioPath().isEmpty()) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return courses.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mPositionInterface.getRecyclerViewAdapterPosition(this.getLayoutPosition());
        }
    }
}


