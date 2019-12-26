package dennis.sung.custombottomdialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dennis on 2019-12-20
 */
public class CustomBottomDialog extends BottomSheetDialog {
    private Context context;
    private List<String> items;
    private RecyclerView recyclerView;
    private BottomItemAdapter bottomItemAdapter;
    private OnListItemClickedListener onListItemClickedListener;

    public CustomBottomDialog(@NonNull Context context, List<String> items) {
        super(context);
        this.context = context;
        this.items = items;
        bottomItemAdapter = new BottomItemAdapter(this.items);
        setContentView(R.layout.bottom_list_dialog);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResId) {
        super.setContentView(LayoutInflater.from(context).inflate(layoutResId, null, false));

        iniView();
    }

    private void iniView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(bottomItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(
                new RecyclerViewColorDivider(
                        context, android.R.color.transparent
                        , 1
                        , 0, 0
                        , 0, 0
                        , LinearLayoutManager.VERTICAL));
        ViewGroup viewGroup = (ViewGroup) recyclerView.getParent().getParent();
        viewGroup.setBackgroundResource(android.R.color.transparent);

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                if(items.size() <= 0){
                    recyclerView.setVisibility(View.GONE);
                }else if (itemH == 0){
                    itemH = recyclerView.getChildAt(0).getMeasuredHeight();
                    setListHeight();
                }
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomBottomDialog.this.dismiss();
            }
        });
    }

    private int itemH;
    private void setListHeight(){
        int dp30 = DisplayUtil.dip2px(context, 30.0f);
        int dividerH = DisplayUtil.dip2px(context, 1.0f) * items.size();
        int cancelH = findViewById(R.id.cancel).getMeasuredHeight();
        Point size = new Point();
        getWindow().getWindowManager().getDefaultDisplay().getSize(size);
        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
        int listMaxH = (size.y - dividerH - dp30 - cancelH) / 4 * 3;
        int allItemsH = itemH * items.size() + dividerH;
        if(allItemsH > listMaxH){
            layoutParams.height = itemH * (listMaxH / itemH);
        }else {
            layoutParams.height = allItemsH;
        }
        recyclerView.setLayoutParams(layoutParams);
    }

    public void setAllTextColor(int color){
        bottomItemAdapter.setAllTextColor(color);
    }

    public void setAllTextBold(boolean isBold){
        bottomItemAdapter.setAllTextBold(isBold);
    }

    public void setTextColor(int color, int position){
        bottomItemAdapter.setTextColor(color, position);
    }

    public void setTextBold(boolean isBold, int position){
        bottomItemAdapter.setTextBold(isBold, position);
    }

    public void setBottomText(String text){
        ((TextView)findViewById(R.id.cancel)).setText(text);
    }

    public void setBottomTextColor(int color){
        ((TextView)findViewById(R.id.cancel)).setTextColor(color);
    }

    public void setBottomTextBold(boolean setBold){
        if (setBold){
            ((TextView)findViewById(R.id.cancel)).setTypeface(Typeface.DEFAULT_BOLD);
        }else {
            ((TextView)findViewById(R.id.cancel)).setTypeface(Typeface.DEFAULT);
        }
    }

    public void setOnListItemClickedListener(OnListItemClickedListener onListItemClickedListener){
        this.onListItemClickedListener = onListItemClickedListener;
    }

    public interface OnListItemClickedListener{
        void onListItemClicked(int position);
    }

    private class BottomItemAdapter extends RecyclerView.Adapter<BottomItemAdapter.ViewHolder>{
        private final int FIRST = 0, OTHER = 1, LAST = 2;
        private List<Item> datas;

        public BottomItemAdapter(List<String> datas){
            if(this.datas == null)
                this.datas = new ArrayList<>();
            for(String s : datas){
                this.datas.add(new Item(s));
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.textView);
            }

            void bindView(final int position){
                textView.setText(datas.get(position).getText());
                textView.setTextColor(datas.get(position).getTextColor());
                if (datas.get(position).isBold()){
                    textView.setTypeface(Typeface.DEFAULT_BOLD);
                }else {
                    textView.setTypeface(Typeface.DEFAULT);
                }
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onListItemClickedListener.onListItemClicked(position);
                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            if(position==0){
                return FIRST;
            }else if(position==datas.size()-1){
                return LAST;
            }else {
                return OTHER;
            }
        }

        @NonNull
        @Override
        public BottomItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            switch (viewType){
                case FIRST:
                    return new ViewHolder(layoutInflater.inflate(R.layout.item_first, parent, false));
                case LAST:
                    return new ViewHolder(layoutInflater.inflate(R.layout.item_last, parent, false));
                case OTHER:
                    return new ViewHolder(layoutInflater.inflate(R.layout.item, parent, false));
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(@NonNull final BottomItemAdapter.ViewHolder holder, int position) {
            if(holder != null){
                holder.bindView(position);
            }
        }

        @Override
        public int getItemCount() {
            return datas.size()<=0 ? 0 : datas.size();
        }

        public void setAllTextBold(boolean isBold){
            if(datas.size() > 0){
                for(Item item : datas){
                    item.setBold(isBold);
                }
                notifyItemRangeChanged(0, datas.size());
            }
        }

        public void setAllTextColor(int color){
            if(datas.size() > 0){
                for(Item item : datas){
                    item.setTextColor(color);
                }
                notifyItemRangeChanged(0, datas.size());
            }
        }

        public void setTextColor(int color, int position){
            if(datas.size() > 0){
                datas.get(position).setTextColor(color);
                notifyItemChanged(position);
            }
        }

        public void setTextBold(boolean isBold, int position){
            if(datas.size() > 0){
                datas.get(position).setBold(isBold);
                notifyItemChanged(position);
            }
        }

        private class Item{
            private int textColor = context.getResources().getColor(R.color.colorBlack);
            private String text;
            private boolean isBold = false;

            Item(String text){
                this.text = text;
            }

            public int getTextColor(){
                return textColor;
            }

            public void setTextColor(int textColor) {
                this.textColor = textColor;
            }

            public String getText(){
                return text;
            }

            public boolean isBold(){
                return isBold;
            }

            public void setBold(boolean isBold){
                this.isBold = isBold;
            }
        }
    }
}
