package com.henry.tesis.expirapp.Expirapp.textDetector;

import static java.lang.Math.floor;
import static java.lang.Math.max;
import static java.lang.Math.min;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.henry.tesis.expirapp.Expirapp.R;
import com.henry.tesis.expirapp.Expirapp.activity.StillImageActivity;
import com.henry.tesis.expirapp.Expirapp.utils.GraphicOverlay;
import com.henry.tesis.expirapp.Expirapp.utils.GraphicOverlay.Graphic;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.Text.Element;
import com.google.mlkit.vision.text.Text.Line;
import com.google.mlkit.vision.text.Text.TextBlock;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Graphic instance for rendering TextBlock position, size, and ID within an associated graphic
 * overlay view.
 */
public class TextGraphic extends Graphic {

    private static final String TAG = "TextGraphic";

    private static final int TEXT_COLOR = Color.BLACK;
    private static final int MARKER_COLOR = Color.WHITE;
    private static final float TEXT_SIZE = 54.0f;
    private static final float STROKE_WIDTH = 4.0f;


    private final Paint rectPaint;
    private final Paint textPaint;
    private final Paint labelPaint;
    private final Text text;

    TextGraphic(GraphicOverlay overlay, Text text) {
        super(overlay);

        this.text = text;
        rectPaint = new Paint();
        rectPaint.setColor(MARKER_COLOR);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(STROKE_WIDTH);

        textPaint = new Paint();
        textPaint.setColor(TEXT_COLOR);
        textPaint.setTextSize(TEXT_SIZE);

        labelPaint = new Paint();
        labelPaint.setColor(MARKER_COLOR);
        labelPaint.setStyle(Paint.Style.FILL);
        // Redraw the overlay, as this graphic has been added.
        postInvalidate();
    }


    /** Draws the text block annotations for position, size, and raw value on the supplied canvas. */
    @Override
    public void draw(Canvas canvas) {
        String reg0 = "^[0-3]?[0-9].[0-3]?[0-9].(?:[0-9]{2})?[0-9]{2}$";
        String reg1 ="^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
        String reg2= "[0-9]{1,2}/[a-zA-Z]{3}/[0-9]{2}";
        Pattern pattern0 = Pattern.compile(reg0);
        Pattern pattern1 = Pattern.compile(reg1);
        Pattern pattern2=Pattern.compile(reg2);


        for (TextBlock textBlock : text.getTextBlocks()) {
            for (Line line : textBlock.getLines()) {

                for (Element element : line.getElements()) {
                    String txtElement;
                    Matcher matcherElement0 = pattern0.matcher(element.getText());
                    Matcher matcherElement1 = pattern1.matcher(element.getText());
                    Matcher matcherElement2;
                    if(element.getText().startsWith("VEN:")){
                        txtElement=element.getText().substring(4);
                        matcherElement2 = pattern2.matcher(txtElement);
                    }
                    else {
                        matcherElement2 = pattern2.matcher(element.getText());
                        txtElement=element.getText();
                    }
                    if((matcherElement0.matches())||(matcherElement1.matches())||(matcherElement2.matches())){
                        Log.d(TAG, "Element text is: " +txtElement);
                        RectF rect = new RectF(element.getBoundingBox());
                        // If the image is flipped, the left will be translated to right, and the right to left.
                        float x0 = translateX(rect.left);
                        float x1 = translateX(rect.right);
                        rect.left = min(x0, x1);
                        rect.right = max(x0, x1);
                        rect.top = translateY(rect.top);
                        rect.bottom = translateY(rect.bottom);
                        //canvas.drawRect(rect, rectPaint);
                        float elementHeight = TEXT_SIZE + 2 * STROKE_WIDTH;
                        float textWidth = textPaint.measureText(txtElement);
                        canvas.drawRect(
                                rect.left - STROKE_WIDTH,
                                rect.top - elementHeight,
                                rect.left + textWidth + 2 * STROKE_WIDTH,
                                rect.top,
                                labelPaint);
                        // Renders the text at the bottom of the box.
                        canvas.drawText(txtElement, rect.left, rect.top - STROKE_WIDTH, textPaint);
                        RectF rectF=new RectF(rect.left,rect.top-STROKE_WIDTH,rect.right,rect.bottom-STROKE_WIDTH);
                        listaRectas.add(rectF);
                        listaTextos.add(txtElement);
                    }
                }
            }
        }
    }
   /* @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                //Check if the x and y position of the touch is inside the bitmap
                if( x > listaRectas.get(0).width()/2 && x < listaRectas.get(0).width()/2 + 200 && y > listaRectas.get(0).height()/2 && y < listaRectas.get(0).height()/2 + 200 )
                {
                    Log.e("TOUCHED", "X: " + x + " Y: " + y);
                    //Bitmap touched
                }
                return true;
        }
        return false;
    }*/
}
