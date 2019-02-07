package com.aleksandr.draglist.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleksandr.draglist.R;
import com.aleksandr.draglist.test.simpleItemTouchHelperCallback.ItemTouchHelperAdapter;
import com.aleksandr.draglist.test.simpleItemTouchHelperCallback.ItemTouchHelperViewHolder;

import java.util.ArrayList;
import java.util.Collections;

public class Test3MoveListAdapter extends RecyclerView.Adapter<Test3MoveListAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private OnSelectedAttributeListener onSelectedAttributeListener;
    private ArrayList<Position> positions = new ArrayList<>();

    private Context context;

    public Test3MoveListAdapter(OnSelectedAttributeListener onSelectedAttributeListener, Context context) {
        this.onSelectedAttributeListener = onSelectedAttributeListener;
        this.context = context;
    }

    public void dataUpdate(ArrayList<Position> positions) {
        this.positions.clear();
        if (positions != null) {
            this.positions.addAll(positions);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_test3_move_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindTo(positions.get(viewHolder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return positions.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        System.out.println("CARL fromPosition - " + fromPosition);
        System.out.println("CARL toPosition - " + toPosition);

        if (positions.get(fromPosition).isSelected() != positions.get(toPosition).isSelected())
            return false;
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(positions, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(positions, i, i - 1);
            }
        }
        onSelectedAttributeListener.onItemMove(fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        positions.remove(position);
        notifyItemRemoved(position);
        onSelectedAttributeListener.onItemDismiss(position);
    }
//
//    @Override
//    public void onItemChanged(int position) {
//        notifyItemChanged(position);
//    }


    class ViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        private TextView tvTest3Title;
        private ImageView handleView;

        @SuppressLint("ClickableViewAccessibility")
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTest3Title = itemView.findViewById(R.id.tv_test3_item_title);
            handleView = itemView.findViewById(R.id.iv_handle);

            itemView.setOnClickListener(v -> {
                if (!positions.get(getAdapterPosition()).isSelected()) {
//                    tvTest1Title.setPaintFlags(tvTest1Title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    positions.get(getAdapterPosition()).switchSelected();
                    onSelectedAttributeListener.onViewSelected(getAdapterPosition(), positions.get(getAdapterPosition()), true);
                } else {
//                    tvTest1Title.setPaintFlags(tvTest1Title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    positions.get(getAdapterPosition()).switchSelected();
                    onSelectedAttributeListener.onViewSelected(getAdapterPosition(), positions.get(getAdapterPosition()), false);
                }
            });

            handleView.setOnTouchListener((view, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    onSelectedAttributeListener.onStartDrag(this);
                }
                return true;
            });
        }

        public void bindTo(Position position) {
            tvTest3Title.setText(position.getTitle());
            if (positions.get(getAdapterPosition()).isSelected()) {
                tvTest3Title.setPaintFlags(tvTest3Title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                tvTest3Title.setTextColor(ContextCompat.getColor(context, (R.color.color_inactive_text)));
            } else {
                tvTest3Title.setPaintFlags(tvTest3Title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                tvTest3Title.setTextColor(ContextCompat.getColor(context, (R.color.color_text)));
            }
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.background3));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.background2));
        }
    }

    public interface OnSelectedAttributeListener {
        void onViewSelected(int pos, Position position, boolean plusMinus);

        void onItemDismiss(int removedPosition);

        void onItemMove(int fromPosition, int toPosition);

        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }
}
