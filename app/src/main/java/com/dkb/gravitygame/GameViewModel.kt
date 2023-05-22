package com.dkb.gravitygame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    val gameLiveModel = MutableLiveData<GameModel>()

    init {
        gameLiveModel.value = GameModel(1, -1, -1, -1, 0)
    }
}