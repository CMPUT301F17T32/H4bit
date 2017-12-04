package h4bit.h4bit.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.Date;

/** HabitEvent class
 * version 1.0
 * 2017-10-20.
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class HabitEvent implements Comparable<HabitEvent> {

    private Habit habit;
    private Date date;
    private Location location;
    private String comment;
    private String image;

    /**
     *
     * @param habit habit
     */
    public HabitEvent(Habit habit, Location location) {
        this.habit = habit;
        this.date = new Date();
        this.location = location;
        this.comment="";
    }

    /**
     *
     * @param habit habit
     * @param comment comment
     */
    public HabitEvent(Habit habit, String comment, Location location) {
        this.habit = habit;
        this.date = new Date();
        this.comment = comment;
        this.location = location;

    }

    /**
     * for comparable interface for sorting
     * @param compareHabitEvent other
     * @return result
     */
    public int compareTo(HabitEvent compareHabitEvent) {

        Date compareQuantity = compareHabitEvent.getDate();
        //ascending order
        return compareQuantity.compareTo(this.getDate());

    }

    //https://stackoverflow.com/questions/15759195/reduce-size-of-bitmap-to-some-specified-pixel-in-android

    /**
     * reduces size of a bitmap to a specified dimension
     * @param image Bitmap
     * @param maxSize integer maximum size
     * @return
     */
    private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    //http://mobile.cs.fsu.edu/converting-images-to-json-objects/

    /**
     * converts bitmap into a string for json
     * @param bitmapPicture the image
     * @return the string
     */
    private String getStringFromBitmap(Bitmap bitmapPicture) {

        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    /**
     * converts string to bitmap of image
     * @param jsonString the string
     * @return the image
     */
    private Bitmap getBitmapFromString(String jsonString) {
        byte[] decodedString = Base64.decode(jsonString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public Bitmap getImage() {
        if(this.image != null){
            Bitmap imageBitmap = getBitmapFromString(image);
            return imageBitmap;
        } else {
            return null;
        }
    }

    public void setImage(Bitmap image) {
        Bitmap resizedImage = getResizedBitmap(image, 128);
        String imageString = getStringFromBitmap(resizedImage);
        this.image = imageString;
    }

    public Habit getHabit() {
        return habit;
    }

    public Date getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Location getLocation(){return this.location;}
}
