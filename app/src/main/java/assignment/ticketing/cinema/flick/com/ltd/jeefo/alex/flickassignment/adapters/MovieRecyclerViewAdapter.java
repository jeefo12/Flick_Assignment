package assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.R;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.Movie;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.utils.IItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Alex on 13/03/18.
 */

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder>{
    private final List<Movie> movies;
    private final Context context;
    private final IItemClickListener selectorListener;

    public MovieRecyclerViewAdapter(Context context, IItemClickListener selectorListener, List<Movie> movies) {
        this.movies = movies;
        this.context = context;
        this.selectorListener = selectorListener;
        Picasso.with(context).setLoggingEnabled(true);
    }

    @Override
    public MovieRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieRecyclerViewAdapter.ViewHolder holder, int position) {
        Movie movie = movies.get(position);

        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());
        holder.tvTicketTypeAndPrice.setText(String.format(Locale.UK,
                "%s: %.2f£",
                movie.getTicketType().toString(),
                movie.getTicketType().getPrice()
        ));

        if (movie.hasPoster()) {
            Log.d("path", movie.getPosterPath());
            Picasso.with(context).load(movie.getPosterPath()).fit().centerCrop().into(holder.ivMovieImage);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public Movie getMovie(int position) {
        return movies.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.ivMovie)
        CircleImageView ivMovieImage;
        @BindView(R.id.tvMovieName)
        TextView tvTitle;
        @BindView(R.id.tvMovieOverview)
        TextView tvOverview;
        @BindView(R.id.tvTicketTypeAndPrice)
        TextView tvTicketTypeAndPrice;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            selectorListener.onItemSelected(getAdapterPosition());
        }
    }
}
