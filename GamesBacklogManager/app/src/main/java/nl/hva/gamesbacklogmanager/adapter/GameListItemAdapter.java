package nl.hva.gamesbacklogmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import nl.hva.gamesbacklogmanager.R;
import nl.hva.gamesbacklogmanager.activity.GameDetailsActivity;
import nl.hva.gamesbacklogmanager.model.Game;

/**
 * Created by Raoul on 15-4-2016.
 */
public class GameListItemAdapter extends RecyclerView.Adapter<GameListItemAdapter.ViewHolder>  {

    private List<Game> gameArrayList;
    private Context context;
    private LayoutInflater inflater;

    public GameListItemAdapter(List<Game> list, Context context) {
        this.gameArrayList = list;
        this.context = context;

        inflater = LayoutInflater.from(context);
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
                .inflate(R.layout.single_game_item, parent, false);

//        View view = LayoutInflater.from(mContext).inflate(R.layout.myview, parent, false);
//        view.setOnClickListener(mOnClickListener);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Populate the row
        holder.populateRow(getItem(position));

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private TextView platform;
        private TextView status;
        private TextView date;

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
        public void onClick(View view) {
            Log.d("hey", "onClick " + getPosition() + " ");

            Intent intent = new Intent(context, GameDetailsActivity.class);
            //Get the correct game based on which listitem got clicked, and put it as parameter in the intent
            Game selectedGame = (Game) getItem(getAdapterPosition());
            intent.putExtra("selectedGame", selectedGame);
            //Open GameDetailsActivity
            context.startActivity(intent);
        }

        public void populateRow(Game game) {
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

