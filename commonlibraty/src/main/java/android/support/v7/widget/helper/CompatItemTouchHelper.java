package android.support.v7.widget.helper;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Muhaitian on 2017/12/29.
 */

public abstract class CompatItemTouchHelper extends ItemTouchHelper {
    /**
     * Creates an ItemTouchHelper that will work with the given Callback.
     * <p>
     * You can attach ItemTouchHelper to a RecyclerView via
     * {@link #attachToRecyclerView(RecyclerView)}. Upon attaching, it will add an item decoration,
     * an onItemTouchListener and a Child attach / detach listener to the RecyclerView.
     *
     * @param callback The Callback which controls the behavior of this touch helper.
     */
    public CompatItemTouchHelper(Callback callback) {
        super(callback);
    }


    public ItemTouchHelper.Callback getCallback() {
        return mCallback;
    }

}
