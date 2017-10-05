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

public class CardViewAdapterQuestion extends RecyclerView.Adapter<CardViewAdapterQuestion.ViewHolder> {

    public Question[] questions;
    private static RecyclerViewClickPosition mPositionInterface;


    @BindView(R.id.questionName)
    TextView title;
    @BindView(R.id.jobImage)
    ImageView image;

    public CardViewAdapterQuestion(List<Question> questions, RecyclerViewClickPosition positionInterface) {
        this.questions = questions.toArray(new Question[questions.size()]);
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
        title.setText(questions[position].getName());
        //image.setVisibility((!jobs[position].getAudioPath().isEmpty()) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return questions.length;
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
