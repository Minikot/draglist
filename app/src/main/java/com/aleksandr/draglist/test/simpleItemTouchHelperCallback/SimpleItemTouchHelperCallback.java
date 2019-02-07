package com.aleksandr.draglist.test.simpleItemTouchHelperCallback;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Данный интерфейс и класс SimpleItemTouchHelperCallback нужны для манипуляций с item в списке
 */

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter itemTouchHelperAdapter;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter itemTouchHelperAdapter) {
        this.itemTouchHelperAdapter = itemTouchHelperAdapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        itemTouchHelperAdapter.onItemMove(viewHolder.getAdapterPosition(), viewHolder1.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        itemTouchHelperAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof ItemTouchHelperViewHolder) {
                ItemTouchHelperViewHolder itemTouchHelperViewHolder = (ItemTouchHelperViewHolder) viewHolder;
                itemTouchHelperViewHolder.onItemSelected();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof ItemTouchHelperViewHolder) {
            ItemTouchHelperViewHolder itemTouchHelperViewHolder = (ItemTouchHelperViewHolder) viewHolder;
            itemTouchHelperViewHolder.onItemClear();
        }
        super.clearView(recyclerView, viewHolder);
    }
}
