package br.com.arula.arula.adapter;

<<<<<<< HEAD
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
=======
>>>>>>> dc13c0ca8fd3745f4996e2b8186ebb2c83bbb0c0
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

public class CardViewAdapterUser extends RecyclerView.Adapter<CardViewAdapterUser.ViewHolder> {

    public User[] users;
    private static RecyclerViewClickPosition mPositionInterface;



    @BindView(R.id.userName)
    TextView title;
    @BindView(R.id.userImage)
    ImageView image;
    @BindView(R.id.userScore)
    TextView score;
    @BindView(R.id.userReq)
    TextView req;


    public CardViewAdapterUser(List<User> users, RecyclerViewClickPosition positionInterface) {
        this.users = users.toArray(new User[users.size()]);
        mPositionInterface = positionInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, null);
        ButterKnife.bind(this, itemLayoutView);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        title.setText(users[position].getName());
<<<<<<< HEAD
        setImage(users[position]);
        score.setText(users[position].getScore().toString());
        req.setText(users[position].getReq());
        score.setText(users[position].getScore().toString());

    }

    public void setImage(User user) {
        if(user.getImage().equals("1"))
            image.setImageResource(R.drawable.a1);
        if(user.getImage().equals("2"))
            image.setImageResource(R.drawable.a2);
        if(user.getImage().equals("3"))
            image.setImageResource(R.drawable.a3);
        if(user.getImage().equals("4"))
            image.setImageResource(R.drawable.a4);
        if(user.getImage().equals("5"))
            image.setImageResource(R.drawable.a5);
        if(user.getImage().equals("6"))
            image.setImageResource(R.drawable.a6);
        if(user.getImage().equals("7"))
            image.setImageResource(R.drawable.a7);
        if(user.getImage().equals("8"))
            image.setImageResource(R.drawable.a8);
        if(user.getImage().equals("9"))
            image.setImageResource(R.drawable.a9);
        if(user.getImage().equals("10"))
            image.setImageResource(R.drawable.a10);
=======
        //image.setImageBitmap();
        score.setText(users[position].getScore().toString());
        req.setText(users[position].getReq());

>>>>>>> dc13c0ca8fd3745f4996e2b8186ebb2c83bbb0c0
    }

    @Override
    public int getItemCount() {
        return users.length;
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