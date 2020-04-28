package com.example.androidtutorialrecyclerviewadv;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private RecyclerView mRecyclerView;
	private ProgressBar mProgressBar;
	private ListAdapter mAdapter;
	private int numberOfItemsToShow = 0;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mProgressBar = findViewById(R.id.view_progressbar);

		mRecyclerView = findViewById(R.id.view_list);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mAdapter = new ListAdapter(this);
		mRecyclerView.setAdapter(mAdapter);

		mProgressBar.setVisibility(View.GONE);
		mRecyclerView.setVisibility(View.VISIBLE);



		findViewById(R.id.view_fab).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View v) {
				mProgressBar.setVisibility(View.VISIBLE);
				mRecyclerView.setVisibility(View.GONE);
				numberOfItemsToShow++;
				new LoadGoogleReposTask().execute();
			}
		});

	}

	private List<String> getGoogleReposNames() {
		final List<String> names = new ArrayList<>();

		final URL url;
		try {
			url = new URL("https://api.github.com/users/google/repos");
		} catch (final MalformedURLException e) {
			throw new RuntimeException(e);
		}

		final HttpURLConnection urlConnection;
		final StringBuilder sb = new StringBuilder();
		try {
			urlConnection = (HttpURLConnection) url.openConnection();
			final BufferedReader reader =
					new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line;
			do {
				line = reader.readLine();
				sb.append(line);
			} while (line != null);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		urlConnection.disconnect();

		try {
			final JSONArray reposJsonArray = new JSONArray(sb.toString());
			for (int i = 0; i < numberOfItemsToShow; i++) {
				names.add(reposJsonArray.getJSONObject(i).getString("name"));
			}
		} catch (final JSONException e) {
			throw new RuntimeException(e);
		}
		return names;
	}

	private class LoadGoogleReposTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(final Void... params) {
			final List<String> names = getGoogleReposNames();
			return names.toArray(new String[names.size()]);
		}

		@Override
		protected void onPostExecute(final String[] strings) {

			if (strings[0].length() == numberOfItemsToShow) {
				Toast.makeText(MainActivity.this, "No more items to show", Toast.LENGTH_SHORT).show();
			} else {
				mProgressBar.setVisibility(View.GONE);
				mRecyclerView.setVisibility(View.VISIBLE);
			}

			mAdapter.setData(strings);
			mAdapter.notifyDataSetChanged();
		}
	}

	private static class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

		private final LayoutInflater mInflater;
		private String[] mData;

		public ListAdapter(final Context context) {
			mInflater = LayoutInflater.from(context);
		}

		public void setData(final String[] data) {
			mData = data;
		}

		@Override
		public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
			final View view = mInflater.inflate(R.layout.view_list_item, parent, false);
			return new ViewHolder(view);
		}

		@Override
		public void onBindViewHolder(final ViewHolder holder, final int position) {
			holder.titleTextView.setText(mData[position]);
		}

		@Override
		public int getItemCount() {
			return mData == null ? 0 : mData.length;
		}

		class ViewHolder extends RecyclerView.ViewHolder {

			public TextView titleTextView;

			public ViewHolder(final View itemView) {
				super(itemView);

				titleTextView = itemView.findViewById(R.id.view_title);
			}
		}
	}
}
