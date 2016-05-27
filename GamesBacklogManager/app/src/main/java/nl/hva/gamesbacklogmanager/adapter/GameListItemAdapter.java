package nl.hva.gamesbacklogmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

import nl.hva.gamesbacklogmanager.R;
import nl.hva.gamesbacklogmanager.R.id;
import nl.hva.gamesbacklogmanager.R.layout;
import nl.hva.gamesbacklogmanager.activity.GameDetailsActivity;
import nl.hva.gamesbacklogmanager.adapter.GameListItemAdapter.ViewHolder;
import nl.hva.gamesbacklogmanager.model.Game;

/**
 * Created by Raoul on 15-4-2016.
 */
public class GameListItemAdapter extends Adapter<ViewHolder> {

    private final List<Game> gameArrayList;
    private final Context context;

    public GameListItemAdapter(List<Game> list, Context context) {
        gameArrayList = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return gameArrayList.size();
    }

    public Game getItem(int position) {
        return gameArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return gameArrayList.get(position).getId();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout.single_game_item, parent, false);
        return new ViewHolder(itemView);
    }

    public void animate(RecyclerView.ViewHolder viewHolder) {
        Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Populate the row
        holder.populateRow(getItem(position));
        animate(holder);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        private final TextView title;
        private final TextView platform;
        private final TextView status;
        private final TextView date;

        //initialize the variables
        public ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(id.gameTitle);
            platform = (TextView) view.findViewById(id.gamePlatform);
            status = (TextView) view.findViewById(id.gameStatus);
            date = (TextView) view.findViewById(id.gameDate);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, GameDetailsActivity.class);
            // Get the correct game based on which listitem got clicked, and put it as parameter in the intent
            Game selectedGame = getItem(getOldPosition());
            intent.putExtra("selectedGame", selectedGame);
            // Open GameDetailsActivity
            context.startActivity(intent);
        }

        public void populateRow(Game game) {
            title.setText(game.getTitle());
            platform.setText(game.getPlatform());
            status.setText(game.getGameStatus());
            date.setText(game.getDateAdded());
        }
    }
}

