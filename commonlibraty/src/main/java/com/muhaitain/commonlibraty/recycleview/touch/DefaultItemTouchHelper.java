package com.muhaitain.commonlibraty.recycleview.touch;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.CompatItemTouchHelper;

/**
 * Created by Muhaitian on 2017/12/29.
 */

public class DefaultItemTouchHelper extends CompatItemTouchHelper {

    public DefaultItemTouchHelper(){
        this(new DefaultItemTouchHelperCallback());
    }

    /**
     * Creates an ItemTouchHelper that will work with the given Callback.
     * <p>
     * You can attach ItemTouchHelper to a RecyclerView via
     * {@link #attachToRecyclerView(RecyclerView)}. Upon attaching, it will add an item decoration,
     * an onItemTouchListener and a Child attach / detach listener to the RecyclerView.
     *
     * @param callback The Callback which controls the behavior of this touch helper.
     */
    public DefaultItemTouchHelper(DefaultItemTouchHelperCallback callback) {
        super(callback);
    }
}
