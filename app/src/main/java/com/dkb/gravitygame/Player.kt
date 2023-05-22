package com.dkb.gravitygame

import android.graphics.Canvas
import android.graphics.RectF
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider

class Player(x:Int, y:Int, dx:Int, dy:Int, image: Drawable) : MyGameObject(x, y,dx, dy, image, false) {
    override var width:Int = 200
    override var height:Int = 400
    var lives:Int = 3
    var coins:Int = 0

    override fun move(canvas: Canvas) {
        y += dy
        image.setBounds(x, y, x + width, y + height)
        image.draw(canvas)
    }

    fun hit (gameObject: MyGameObject, canvas: Canvas) : Boolean {
        var playerRect: RectF = RectF(x.toFloat(), y.toFloat(), x + width.toFloat(), y+height.toFloat())
        var objectRect: RectF = gameObject.getRect()
        if (playerRect.intersect(objectRect))
        {
            if (gameObject.coin) {
                coins += 1
                return true
            } else {
                lives -= 1
                return true
            }
        }
        return false
    }

    open fun onGround(): Boolean {
        return (y >= 1250)
    }

    open fun playerLand() {
        y = 1250
        dy = 0
    }

    open fun playerJump(level: Int) {
        y = 1249
        when (level) {
            1 -> dy = -95
            2 -> dy = -45
            3 -> dy = -65
        }

    }

    open fun implementGravity(level: Int) {
        when (level) {
            1 -> dy += 4
            2 -> dy += 1
            3 -> dy += 2
        }
    }
}