package ru.irinavb.skillboxrecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] names = new String[100];

        for (int i = 0; i < names.length; i++) {
            names[i] = "Item #" + i;
        }

        final RecyclerView recyclerView = findViewById(R.id.view_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ListAdapter(this, names));

    }

    private static class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
        private final LayoutInflater inflater;
        private final String[] data;

        public ListAdapter(Context context, String[] data) {
            this.inflater = LayoutInflater.from(context);
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(data[position]);
        }

        @Override
        public int getItemCount() {
            return data.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
