package com.hcmute.finalproject.toeicapp.testing.van.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hcmute.finalproject.toeicapp.R;

import java.util.ArrayList;
import java.util.List;

public class VanTestRecyclerActivity extends AppCompatActivity {
    private static final int TYPE_QUESTION_HTML = 1;
    private static final int TYPE_QUESTION_SELECTION = 2;

    private List<RenderItem> renderItems = new ArrayList<>();

    private MyAdapter adapter;

    public class RenderItem {
        private int type;
        private Object data;

        public RenderItem() { }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_test_recycler);

        // 1 noi dung
        for (int i = 0; i < 5; ++i) {
            RenderItem a1 = new RenderItem();
            a1.setType(TYPE_QUESTION_HTML);
            a1.setData("<h1>Ê Bảo Ngọc</h1>");
            this.renderItems.add(a1);
        }

        // 3 cau tra loi
        for (int i = 0; i < 30; ++i) {
            RenderItem a2 = new RenderItem();
            a2.setType(TYPE_QUESTION_SELECTION);
            a2.setData("Lựa chọn " + (i + 1));
            this.renderItems.add(a2);
        }

        // adapter + recycler view
        this.adapter = new MyAdapter();
        RecyclerView recyclerView = findViewById(R.id.activity_van_test_recycler_view_01);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(this.adapter);
    }

    public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        //trả về giá trị cho viewType ở dưới
        @Override
        public int getItemViewType(int position) {
            return renderItems.get(position).getType();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(VanTestRecyclerActivity.this);
            if (viewType == TYPE_QUESTION_HTML) {
                View view = inflater.inflate(R.layout.activity_van_test_recycler_test_html_item, parent, false);
                return new HtmlViewHolder(view);
            }
            else {
                View view = inflater.inflate(R.layout.activity_van_test_recycler_test_selection, parent, false);
                return new SelectionViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return renderItems.size();
        }

        // VIEW HOLDER -> HTML
        private class HtmlViewHolder extends RecyclerView.ViewHolder {

            public HtmlViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }

        // VIEW HOLDER -> SELECTION
        private class SelectionViewHolder extends RecyclerView.ViewHolder {

            public SelectionViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}