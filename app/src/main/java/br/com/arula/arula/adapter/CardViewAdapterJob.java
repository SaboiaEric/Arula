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
import br.com.arula.arula.model.Job;
import br.com.arula.arula.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rafael Felipe on 03/10/2017.
 */

public class CardViewAdapterJob extends RecyclerView.Adapter<CardViewAdapterJob.ViewHolder> {

    public Job[] jobs;
    private static RecyclerViewClickPosition mPositionInterface;



    @BindView(R.id.jobName)
    TextView name;
    @BindView(R.id.jobImage)
    ImageView image;
    @BindView(R.id.jobCompanyName)
    TextView company;
    @BindView(R.id.jobSalary)
    TextView salary;
    @BindView(R.id.jobReq)
    TextView req;


    public CardViewAdapterJob(List<Job> jobs, RecyclerViewClickPosition positionInterface) {
        this.jobs = jobs.toArray(new Job[jobs.size()]);
        mPositionInterface = positionInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_job, null);
        ButterKnife.bind(this, itemLayoutView);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        name.setText(jobs[position].getName());
        setImage(jobs[position]);
        company.setText(jobs[position].getCompanyName());
        salary.setText(jobs[position].getSalary().toString());
        req.setText(jobs[position].getReq());

    }

    public void setImage(Job job) {
//        if (job.getImage().equals("1"))
//            image.setImageResource(R.drawable.j1);
//        if (job.getImage().equals("2"))
//            image.setImageResource(R.drawable.j2);
//        if (job.getImage().equals("3"))
//            image.setImageResource(R.drawable.j3);
//        if (job.getImage().equals("4"))
//            image.setImageResource(R.drawable.j4);
//        if (job.getImage().equals("5"))
//            image.setImageResource(R.drawable.j5);
//        if (job.getImage().equals("6"))
//            image.setImageResource(R.drawable.j6);
//        if (job.getImage().equals("7"))
//            image.setImageResource(R.drawable.j7);
//        if (job.getImage().equals("8"))
//            image.setImageResource(R.drawable.j8);
//        if (job.getImage().equals("9"))
//            image.setImageResource(R.drawable.j9);
//        if (job.getImage().equals("10"))
//            image.setImageResource(R.drawable.j10);
    }

    @Override
    public int getItemCount() {
        return jobs.length;
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