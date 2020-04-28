package com.example.androidtutorialrecyclerview;

import android.content.Context;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final String[] names = new String[100];
		for (int i = 0; i < names.length; i++) {
			names[i] = "Item #" + i;
		}

		final RecyclerView recyclerView = findViewById(R.id.view_list);
		recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
		//recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
		recyclerView.setAdapter(new ListAdapter(this, names));
	}

	private static class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

		private final LayoutInflater mInflater;
		private final String[] mData;

		public ListAdapter(final Context context, final String[] data) {
			mInflater = LayoutInflater.from(context);
			mData = data;
		}

		@Override
		public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
			final View view = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
			return new ViewHolder(view);
		}

		@Override
		public void onBindViewHolder(final ViewHolder holder, final int position) {
			holder.textView.setText(mData[position]);
		}

		@Override
		public int getItemCount() {
			return mData.length;
		}

		class ViewHolder extends RecyclerView.ViewHolder {

			public TextView textView;

			public ViewHolder(final View itemView) {
				super(itemView);

				textView = itemView.findViewById(android.R.id.text1);
			}
		}
	}
}
