package org.mobile_development.marcellis.gamesbacklogmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.mobile_development.marcellis.gamesbacklogmanager.R;
import org.mobile_development.marcellis.gamesbacklogmanager.model.Game;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Marcellis on 12-2-2016.
 */
public class GameListItemAdapter extends BaseAdapter {

    private List<Game> gameArrayList;
    private Context context;
    private LayoutInflater inflater;

    public GameListItemAdapter(List<Game> list, Context context){
        this.gameArrayList = list;
        this.context = context;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return gameArrayList.size();
    }

    @Override
    public Game getItem(int position) {
        return gameArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return gameArrayList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        //Check if the row is new
        if (row == null) {
            //Inflate the layout if it didn't exist yet
            row = inflater.inflate(R.layout.single_game_item, parent, false);

            //Create a new view holder instance
            holder = new ViewHolder(row);

            //set the holder as a tag so we can get it back later
            row.setTag(holder);
        } else {
            //The row isn't new so we can reuse the view holder
            holder = (ViewHolder) row.getTag();
        }

        //Populate the row
        holder.populateRow(getItem(position));

        return row;
    }

    class ViewHolder{
        private TextView title;
        private TextView platform;
        private TextView status;
        private TextView date;


        //initialize the variables
        public ViewHolder(View view){
            title = (TextView) view.findViewById(R.id.gameTitle);
            platform = (TextView) view.findViewById(R.id.gamePlatform);
            status = (TextView) view.findViewById(R.id.gameStatus);
            date = (TextView) view.findViewById(R.id.gameDate);
        }

        public void populateRow(Game game){
            title.setText(game.getTitle());
            platform.setText(game.getPlatform());
            status.setText(game.getGameStatus());

            //Convert Date object to String by formatting it
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = format.format(game.getDateAdded());
            date.setText(dateString);
        }
    }
}
