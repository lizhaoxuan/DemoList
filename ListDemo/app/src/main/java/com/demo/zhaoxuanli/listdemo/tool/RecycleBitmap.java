package com.demo.zhaoxuanli.listdemo.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import me.ele.crowdsource.context.TrackerApp;

/**
 * Created by lizhaoxuan on 2017/6/5.
 */

public class RecycleBitmap {
    private static final String TAG = RecycleBitmap.class.getSimpleName();
    private static final int RECYCLE_BITMAP_KEY = -1;

    private Map<Integer, Cake> cakeMap;

    public static RecycleBitmap newInstance() {
        return new RecycleBitmap();
    }

    private RecycleBitmap() {
    }

    public void setImageForViewOnPost(final ImageView view, final Builder builder) {
        view.post(new Runnable() {
            @Override
            public void run() {
                if (builder.needAsync) {
                    new CreateBitmapTask(view, RecycleBitmap.this).execute(builder);
                } else {
                    view.setImageBitmap(builder.createBitmap(RecycleBitmap.this));
                }
            }
        });
    }

    public Bitmap createBitmap(Builder builder) {
        return builder.createBitmap(this);
    }

    public void recycle(ImageView view) {
        recycle(view.hashCode());
    }

    public void recycle(int uuid) {
        getCakeMap().put(RECYCLE_BITMAP_KEY, getCakeMap().get(uuid));
        getCakeMap().put(uuid, null);
    }

    public void destroy() {
        for (Cake cake : getCakeMap().values()) {
            if (cake != null) {
                cake.bitmap = null;
            }
        }
        getCakeMap().clear();
    }

    private Map<Integer, Cake> getCakeMap() {
        if (cakeMap == null) {
            cakeMap = new HashMap<>();
        }
        return cakeMap;
    }

    private Bitmap createBitmap(int width, int height, int uuid, boolean overturn, OnInputStream onInputStream, Bitmap.Config bitmapConfig) {
        boolean isRecycleCake = false;
        InputStream inputStream = onInputStream.getInputStream();
        if (inputStream == null) {
            return null;
        }
        BitmapFactory.Options options = getOptions(width, height, overturn, inputStream, bitmapConfig);
        Cake cake = getCakeMap().get(uuid);
        if (cake == null) {
            //尝试从利用最近已废弃的Bitmap
            cake = getCakeMap().get(RECYCLE_BITMAP_KEY);
            if (cake == null) {
                cake = new Cake();
            } else {
                isRecycleCake = true;
            }
        }
        options.inBitmap = cake.bitmap;
        try {
            cake.bitmap = BitmapFactory.decodeStream(onInputStream.getInputStream(), null, options);
            if (cake.bitmap == null) {
                TrackerApp.trackerBugError(TrackerApp.PAGE_MAIN, "RecycleBitmap", null);
                return createBitmapForByte(uuid, onInputStream, options);
            }
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "复用失败：" + e.getMessage());
            //复用失败重新创建
            return newCakeAndRecycleOld(uuid, onInputStream, options);
        }
        cake.initMM();
        getCakeMap().put(uuid, cake);
        if (isRecycleCake) {
            //缓存的废弃Bitmap已被重用，踢出Map
            getCakeMap().put(RECYCLE_BITMAP_KEY, null);
        }
        return cake.bitmap;
    }

    private BitmapFactory.Options getOptions(int width, int height, boolean overturn, InputStream inputStream, Bitmap.Config bitmapConfig) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = bitmapConfig;
        options.inMutable = true;
        if (width == 0) {
            return options;
        }
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        int outHeight, outWidth;
        if (overturn) {
            outWidth = options.outWidth;
            outHeight = options.outHeight;
        } else {
            outHeight = options.outWidth;
            outWidth = options.outHeight;
        }

        int scale = 1;
        while (true) {
            if (outWidth / 2 < width || outHeight / 2 < height) {
                break;
            }
            outWidth /= 2;
            outHeight /= 2;
            scale *= 2;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = scale;
        return options;
    }

    /**
     * 复用失败，创建新的Cake,废弃旧的Cake
     */
    private Bitmap newCakeAndRecycleOld(int hashCode, OnInputStream onInputStream, BitmapFactory.Options options) {
        options.inBitmap = null;
        Cake cake = new Cake();
        cake.bitmap = BitmapFactory.decodeStream(onInputStream.getInputStream(), null, options);
        if (cake.bitmap == null) {
            TrackerApp.trackerBugError(TrackerApp.PAGE_MAIN, "newCakeAndRecycleOld", null);
            return createBitmapForByte(hashCode, onInputStream, options);
        }
        cake.initMM();
        getCakeMap().put(hashCode, cake);
        return cake.bitmap;
    }

    private Bitmap createBitmapForByte(int hashCode, OnInputStream onInputStream, BitmapFactory.Options options) {
        options.inBitmap = null;
        Cake cake = new Cake();

        byte[] data = inputStreamToByte(onInputStream);
        if (data == null || data.length == 0) {
            TrackerApp.trackerBugError(TrackerApp.PAGE_MAIN, "byte==null", null);
            return null;
        }
        cake.bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        if (cake.bitmap == null) {
            TrackerApp.trackerBugError(TrackerApp.PAGE_MAIN, "createBitmapForByte", null);
        }
        cake.initMM();
        getCakeMap().put(hashCode, cake);
        return cake.bitmap;
    }

    private static byte[] inputStreamToByte(OnInputStream onInputStream) {
        try {
            InputStream in = onInputStream.getInputStream();
            if (in == null) {
                return null;
            }
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            in.close();
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private class Cake {
        Bitmap bitmap;
        int height;
        int width;

        void initMM() {
            if (bitmap != null && (height == 0 || width == 0)) {
                height = bitmap.getHeight();
                width = bitmap.getWidth();
            }
        }
    }

    private interface OnInputStream {
        InputStream getInputStream();
    }

    public static class Builder {

        private OnInputStream onInputStream;
        private int height;
        private int width;
        private int uuid;
        private boolean overturn;
        private boolean needAsync;
        private Bitmap.Config bitmapConfig = Bitmap.Config.RGB_565;

        public Builder(ImageView imageView) {
            uuid = imageView.hashCode();
            height = imageView.getHeight();
            width = imageView.getWidth();
        }

        public Builder(int height, int width, int uuid) {
            this.height = height;
            this.width = width;
            this.uuid = uuid;
        }

        public Builder addSource(final Context context, final int drawableId) {
            onInputStream = new OnInputStream() {
                @Override
                public InputStream getInputStream() {
                    return context.getResources().openRawResource(drawableId);
                }
            };
            return this;
        }

        public Builder addSource(final String path) {
            onInputStream = new OnInputStream() {
                @Override
                public InputStream getInputStream() {
                    try {
                        return new FileInputStream(new File(path));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            };
            return this;
        }

        public Builder setUuid(int uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setBitmapConfig(Bitmap.Config bitmapConfig) {
            this.bitmapConfig = bitmapConfig;
            return this;
        }

        public Builder needOverturn(boolean overturn) {
            this.overturn = overturn;
            return this;
        }

        public Builder needAsync(boolean needAsync) {
            this.needAsync = needAsync;
            return this;
        }

        private Bitmap createBitmap(RecycleBitmap recycleBitmap) {
            if (onInputStream == null) {
                Log.d(TAG, "no source or invalid source");
                return null;
            }
            return recycleBitmap.createBitmap(width, height, uuid, overturn, onInputStream, bitmapConfig);
        }
    }

    private class CreateBitmapTask extends AsyncTask<Builder, Integer, Bitmap> {

        private WeakReference<ImageView> imageViewWeakReference;
        private WeakReference<RecycleBitmap> recycleBitmapWeakReference;

        public CreateBitmapTask(ImageView view, RecycleBitmap recycleBitmap) {
            this.imageViewWeakReference = new WeakReference<>(view);
            this.recycleBitmapWeakReference = new WeakReference<>(recycleBitmap);
        }

        @Override
        protected Bitmap doInBackground(Builder... params) {
            if (recycleBitmapWeakReference.get() != null && params.length > 0 && params[0] != null) {
                return params[0].createBitmap(recycleBitmapWeakReference.get());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewWeakReference.get() != null && bitmap != null) {
                imageViewWeakReference.get().setImageBitmap(bitmap);
            }
        }
    }
}
