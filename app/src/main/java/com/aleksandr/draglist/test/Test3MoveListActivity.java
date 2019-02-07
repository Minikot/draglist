package com.aleksandr.draglist.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.EditText;
import android.widget.ImageView;

import com.aleksandr.draglist.R;
import com.aleksandr.draglist.test.simpleItemTouchHelperCallback.SimpleItemTouchHelperCallback;

import java.util.ArrayList;

public class Test3MoveListActivity extends AppCompatActivity {

    private ArrayList<Position> positions = new ArrayList<>();

    private EditText etTest3Data;
    private ImageView ivTest3AddData;
    private RecyclerView rvTest3DataList;

    private Test3MoveListAdapter test3MoveListAdapter;

    private ItemTouchHelper mItemTouchHelper;
    private int activeCount;
    private int inactiveCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3_move_list);

        etTest3Data = findViewById(R.id.et_test3_data);
        ivTest3AddData = findViewById(R.id.iv_test3_add);

        rvTest3DataList = findViewById(R.id.rv_test3_changeable_data);

        test3MoveListAdapter = new Test3MoveListAdapter(new Test3MoveListAdapter.OnSelectedAttributeListener() {
            @Override
            public void onViewSelected(int pos, Position position, boolean plusMinus) {
                changeList(pos, position, plusMinus);
            }

            @Override
            public void onItemDismiss(int removedPosition) {
                if (!positions.get(removedPosition).isSelected()) {
                    activeCount--;
                } else {
                    inactiveCount--;
                }
                positions.remove(removedPosition);
            }

            @Override
            public void onItemMove(int fromPosition, int toPosition) {
                Position position = positions.get(fromPosition);
                positions.remove(fromPosition);
                positions.add(toPosition, position);
            }

            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                mItemTouchHelper.startDrag(viewHolder);
            }
        }, this);

        rvTest3DataList.setLayoutManager(new LinearLayoutManager(this));
        rvTest3DataList.setAdapter(test3MoveListAdapter);
        /**
         *  Attach callback to recycler view. (Swipe, Drag)
         */
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(test3MoveListAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rvTest3DataList);

        ivTest3AddData.setOnClickListener(v -> {
            if (etTest3Data.length() != 0) {
                positions.add(activeCount, new Position(etTest3Data.getText().toString(), false));
                activeCount++;
                test3MoveListAdapter.dataUpdate(positions);
                etTest3Data.setText("");
            }
            System.out.println("CARL Active - " + activeCount);
            System.out.println("CARL InActive - " + inactiveCount);
        });
    }

    /**
     * Метод который перезаполняет список удаляя нажатую позицию и добавляет в самый низ списка принятый объекст с значением - Зачеркнут
     * Или если он был зачеркнут, то удаляет его и перемещает в низ активных позиций уже не зачеркнутым
     */
    private void changeList(int selectedPosition, Position position, boolean plusMinus) {
        positions.remove(selectedPosition);

        if (plusMinus) {
            positions.add(position);
            activeCount--;
            inactiveCount++;
        } else {
            positions.add(activeCount, position);
            activeCount++;
            inactiveCount--;
        }
        test3MoveListAdapter.dataUpdate(positions);
    }

}
