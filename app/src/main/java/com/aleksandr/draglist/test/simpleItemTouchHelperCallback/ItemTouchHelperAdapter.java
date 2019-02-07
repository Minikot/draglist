package com.aleksandr.draglist.test.simpleItemTouchHelperCallback;

public interface ItemTouchHelperAdapter {

    /**
     * Данный интерфейс и класс SimpleItemTouchHelperCallback нужны для манипуляций с item в списке
     */

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);

//    void onItemChanged(int position);
}
