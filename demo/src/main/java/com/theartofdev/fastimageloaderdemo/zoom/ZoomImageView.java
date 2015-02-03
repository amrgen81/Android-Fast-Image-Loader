// "Therefore those skilled at the unorthodox
// are infinite as heaven and earth,
// inexhaustible as the great rivers.
// When they come to an end,
// they begin again,
// like the days and months;
// they die and are reborn,
// like the four seasons."
//
// - Sun Tsu,
// "The Art of War"

package com.theartofdev.fastimageloaderdemo.zoom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.theartofdev.fastimageloader.LoadedFrom;
import com.theartofdev.fastimageloader.ReusableBitmap;
import com.theartofdev.fastimageloader.TargetImageViewBitmapHandler;
import com.theartofdev.fastimageloaderdemo.Specs;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public class ZoomImageView extends ImageViewTouch {

    private ProgressBar mProgressBar;

    /**
     * The target image handler to load the image and control its lifecycle.
     */
    private ZoomTargetImageViewBitmapHandler mHandler;

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHandler = new ZoomTargetImageViewBitmapHandler(this);
        setDisplayType(DisplayType.FIT_TO_SCREEN);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mHandler = new ZoomTargetImageViewBitmapHandler(this);
        setDisplayType(DisplayType.FIT_TO_SCREEN);
    }

    /**
     * Load the given image into the zoom image view.
     */
    public void loadImage(String url, ProgressBar progressBar) {
        mProgressBar = progressBar;
        mProgressBar.setVisibility(VISIBLE);
        mHandler.loadImage(url, Specs.ZOOM_IMAGE, Specs.IMAGE, false);
    }

    /**
     * On image view visibility change set show/hide on the image handler to it will update its in-use status.
     */
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            mHandler.onViewShown();
        } else {
            mHandler.onViewHidden();
        }
    }

    private final class ZoomTargetImageViewBitmapHandler extends TargetImageViewBitmapHandler {

        /**
         * @param imageView The image view to handle.
         */
        public ZoomTargetImageViewBitmapHandler(ImageView imageView) {
            super(imageView);
        }

        @Override
        protected void setImage(ReusableBitmap bitmap, LoadedFrom from) {
            super.setImage(bitmap, from);
            if (bitmap.getSpec().getKey().equals(Specs.ZOOM_IMAGE)) {
                mProgressBar.setVisibility(GONE);
            }
        }
    }
}
