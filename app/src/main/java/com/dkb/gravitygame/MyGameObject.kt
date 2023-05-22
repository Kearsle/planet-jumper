package com.dkb.gravitygame

import android.graphics.Canvas
import android.graphics.RectF
import android.graphics.drawable.Drawable

open class MyGameObject (open var x:Int, open var y:Int, var dx:Int, open var dy:Int, var image: Drawable, var coin: Boolean){
    open var width:Int = 100
    open var height:Int = 100

    open fun outOfBounds(canvas: Canvas): Boolean {
        return ((x > (canvas.width-width) || x < 0) || (y > (canvas.height-height) || y < 0))
    }

    open fun move (canvas: Canvas)
    {
        x += dx
        y += dy
        image.setBounds(x, y, x + width, y + height)
        image.draw(canvas)
    }

    open fun getRect (): RectF {
        return RectF(x.toFloat(), y.toFloat(), x + width.toFloat(), y+height.toFloat())
    }
}