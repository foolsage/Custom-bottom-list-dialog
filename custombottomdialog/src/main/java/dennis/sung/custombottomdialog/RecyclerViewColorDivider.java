package dennis.sung.custombottomdialog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Dennis on 2019-12-24
 */
public class RecyclerViewColorDivider extends RecyclerView.ItemDecoration {
    private final Drawable mDivider;
    private final int mSize;
    private final int mDividerPaddingLeft, mDividerPaddingTop, mDividerPaddingRight, mDividerPaddingBottom;
    private final int mOrientation;

    public RecyclerViewColorDivider(Context context,
                                    int color,
                                    int dp_h,
                                    int dp_padding_left,
                                    int dp_padding_top,
                                    int dp_padding_right,
                                    int dp_padding_bottom,
                                    int orientation) {
        Resources resources = context.getResources();
        mDivider = new ColorDrawable(resources.getColor(color));
        mSize = DisplayUtil.dip2px(context, dp_h);
        mDividerPaddingLeft = DisplayUtil.dip2px(context, dp_padding_left);
        mDividerPaddingTop = DisplayUtil.dip2px(context, dp_padding_top);
        mDividerPaddingRight = DisplayUtil.dip2px(context, dp_padding_right);
        mDividerPaddingBottom = DisplayUtil.dip2px(context, dp_padding_bottom);
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left;
        int right;
        int top;
        int bottom;
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            top = parent.getPaddingTop() + mDividerPaddingTop;
            bottom = parent.getHeight() - parent.getPaddingBottom() - mDividerPaddingBottom;
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount - 1; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params =
                        (RecyclerView.LayoutParams) child.getLayoutParams();
                left = child.getRight() + params.rightMargin;
                right = left + mSize;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        } else {
            left = parent.getPaddingLeft() + mDividerPaddingLeft;
            right = parent.getWidth() - parent.getPaddingRight() - mDividerPaddingRight;
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount - 1; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params =
                        (RecyclerView.LayoutParams) child.getLayoutParams();
                top = child.getBottom() + params.bottomMargin;
                bottom = top + mSize;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            outRect.set(0, 0, mSize, 0);
        } else {
            outRect.set(0, 0, 0, mSize);
        }
    }
}
