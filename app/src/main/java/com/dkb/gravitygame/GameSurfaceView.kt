package com.dkb.gravitygame

import android.content.Context
import android.graphics.*
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.graphics.blue
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class GameSurfaceView(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs), Runnable {
    var paint = Paint()
    var isRunning = true
    lateinit var myThread: Thread
    lateinit var myHolder: SurfaceHolder
    var myGameObjects = ArrayList<MyGameObject>()
    var startTime: Long = 750
    var lifeListener = MutableLiveData<Int>()
    var coinListener = MutableLiveData<Int>()
    var level = 0


    // Game Object Drawables
    val boulderImg = context!!.resources.getDrawable(R.drawable.boulder, null)
    val marsBoulderImg = context!!.resources.getDrawable(R.drawable.marsboulder, null)
    val playerImg = context!!.resources.getDrawable(R.drawable.player, null)
    val coinImg = context!!.resources.getDrawable(R.drawable.coin, null)

    var player = Player(200,1250,0,0,playerImg)

    init {
        myThread = Thread(this)
        myThread.start()
        myHolder = holder

        object : CountDownTimer(startTime, 1000) {
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                if (player.lives > 0) {
                    val canvas: Canvas = myHolder.lockCanvas()
                    if (myGameObjects.count() < 10)
                    {
                        if (Random.nextBoolean()) {
                            myGameObjects.add(MyGameObject(canvas.width - 100, Random.nextInt((canvas.height.toInt() * 0.2).toInt(), (canvas.height.toInt() * 0.8).toInt()), -15, 0, coinImg, true))
                        } else {
                            when (level) {
                                1 -> myGameObjects.add(MyGameObject(canvas.width - 100, Random.nextInt((canvas.height.toInt() * 0.2).toInt(), (canvas.height.toInt() * 0.8).toInt()), -15, 0, boulderImg, false))
                                2 -> myGameObjects.add(MyGameObject(canvas.width - 100, Random.nextInt((canvas.height.toInt() * 0.2).toInt(), (canvas.height.toInt() * 0.8).toInt()), -15, 0, boulderImg, false))
                                3 -> myGameObjects.add(MyGameObject(canvas.width - 100, Random.nextInt((canvas.height.toInt() * 0.2).toInt(), (canvas.height.toInt() * 0.8).toInt()), -15, 0, marsBoulderImg, false))
                            }
                        }
                        myHolder.unlockCanvasAndPost(canvas)
                        this.start()
                    }
                }
            }
        }.start()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event!!.action == MotionEvent.ACTION_DOWN)
            if (player.onGround()) {
                player.playerJump(level)
                invalidate()
            }
        return true
    }

    override fun run() {
        while(isRunning) {
            if(!myHolder.surface.isValid)
                continue
            val canvas: Canvas = myHolder.lockCanvas()
            when (level) {
                1 -> {
                    paint.setColor(Color.rgb(177, 235, 240))
                    canvas.drawRect(0f,0f,canvas.width.toFloat(),canvas.height.toFloat(),paint)
                }
                2 -> {
                    paint.setColor(Color.rgb(30, 30, 30))
                    canvas.drawRect(0f,0f,canvas.width.toFloat(),canvas.height.toFloat(),paint)
                }
                3 -> {
                    paint.setColor(Color.rgb(255, 211, 135))
                    canvas.drawRect(0f,0f,canvas.width.toFloat(),canvas.height.toFloat(),paint)
                }
            }

            // Player Gravity
            if (!player.onGround())
                player.implementGravity(level) else player.playerLand()

            player.move(canvas)

            var gameObjectsRemoveList = ArrayList<MyGameObject>()
            for (gameObject in myGameObjects) {
                if (gameObject.outOfBounds(canvas)) {
                    gameObjectsRemoveList.add(gameObject)
                }
                if (player.hit(gameObject, canvas))
                {
                    gameObjectsRemoveList.add(gameObject)
                    if (gameObject.coin) {
                        val coinSound = MediaPlayer.create(context, R.raw.coin)
                        coinSound.start()
                        coinListener.postValue(player.coins)
                    } else {
                        val hitSound = MediaPlayer.create(context, R.raw.hit)
                        hitSound.start()
                        lifeListener.postValue(player.lives)
                        if (player.lives == 0) {
                            break
                        }
                    }
                }
            }
            for (gameObject in gameObjectsRemoveList) {
                myGameObjects.remove(gameObject)
            }
            for (gameObject in myGameObjects)
                gameObject.move(canvas)

            myHolder.unlockCanvasAndPost(canvas)
        }
    }
}