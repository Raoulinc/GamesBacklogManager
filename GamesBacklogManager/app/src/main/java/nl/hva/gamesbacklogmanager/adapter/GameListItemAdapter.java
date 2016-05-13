package nl.hva.gamesbacklogmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nl.hva.gamesbacklogmanager.R;
import nl.hva.gamesbacklogmanager.activity.GameDetailsActivity;
import nl.hva.gamesbacklogmanager.model.Game;

/**
 * Created by Raoul on 15-4-2016.
 */
public class GameListItemAdapter extends RecyclerView.Adapter<GameListItemAdapter.ViewHolder> {

    private final List<Game> gameArrayList;
    private final Context context;

    public GameListItemAdapter(List<Game> list, Context context) {
        gameArrayList = list;
        this.context = context;

        LayoutInflater inflater = LayoutInflater.from(context);
    }

    @Override
    public final int getItemCount() {
        return gameArrayList.size();
    }

    public final Game getItem(int position) {
        return gameArrayList.get(position);
    }

    @Override
    public final long getItemId(int position) {
        return gameArrayList.get(position).getId();
    }

    @Override
    public final GameListItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_game_item, parent, false);

//        View view = LayoutInflater.from(mContext).inflate(R.layout.myview, parent, false);
//        view.setOnClickListener(mOnClickListener);

        return new GameListItemAdapter.ViewHolder(itemView);
    }

    @Override
    public final void onBindViewHolder(GameListItemAdapter.ViewHolder holder, int position) {
        //Populate the row
        holder.populateRow(getItem(position));

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView title;
        private final TextView platform;
        private final TextView status;
        private final TextView date;

        //initialize the variables
        public ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.gameTitle);
            platform = (TextView) view.findViewById(R.id.gamePlatform);
            status = (TextView) view.findViewById(R.id.gameStatus);
            date = (TextView) view.findViewById(R.id.gameDate);

            view.setOnClickListener(this);
        }

        @Override
        public final void onClick(View view) {
            Log.d("hey", "onClick " + getAdapterPosition() + ' ');

            Intent intent = new Intent(context, GameDetailsActivity.class);
            //Get the correct game based on which listitem got clicked, and put it as parameter in the intent
            Game selectedGame = (Game) getItem(getAdapterPosition());
            intent.putExtra("selectedGame", selectedGame);
            //Open GameDetailsActivity
            context.startActivity(intent);
        }

        public final void populateRow(Game game) {
            title.setText(game.getTitle());
            platform.setText(game.getPlatform());
            status.setText(game.getGameStatus());

            //Convert Date object to String by formatting it
            //SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            //String dateString = format.format(game.getDateAdded());
            date.setText(game.getDateAdded());
        }
    }
}

