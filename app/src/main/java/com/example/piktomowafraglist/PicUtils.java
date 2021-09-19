package com.example.piktomowafraglist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PicUtils {

    static Bitmap decodePicture (String Path, int width, int height){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = calculate(options, width, height);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(Path, options);
    }

    private static int calculate(BitmapFactory.Options options, int width, int height) {

        final int Height = 4160; //options.outHeight;
        final int Width = 2336; //options.outWidth;
        int inSampleSize = 1;

        if (Height > height || Width > width){
            final int HalfHeight = Height / 2;
            final int HalfWidth = Width / 2;

            while ((HalfHeight / inSampleSize) >= height && (HalfWidth / inSampleSize) >= width){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

}
