package com.ianpedraza.tictactoe.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianpedraza.tictactoe.R

class GameViewModel : ViewModel() {

    private val _turn = MutableLiveData<Int>()
    val turn: LiveData<Int> = _turn

    private val _cell00 = MutableLiveData<Int>()
    val cell00: LiveData<Int> = _cell00

    private val _cell01 = MutableLiveData<Int>()
    val cell01: LiveData<Int> = _cell01

    private val _cell02 = MutableLiveData<Int>()
    val cell02: LiveData<Int> = _cell02

    private val _cell10 = MutableLiveData<Int>()
    val cell10: LiveData<Int> = _cell10

    private val _cell11 = MutableLiveData<Int>()
    val cell11: LiveData<Int> = _cell11

    private val _cell12 = MutableLiveData<Int>()
    val cell12: LiveData<Int> = _cell12

    private val _cell20 = MutableLiveData<Int>()
    val cell20: LiveData<Int> = _cell20

    private val _cell21 = MutableLiveData<Int>()
    val cell21: LiveData<Int> = _cell21

    private val _cell22 = MutableLiveData<Int>()
    val cell22: LiveData<Int> = _cell22

    private val _winnerMessage = MutableLiveData<String>()
    val winnerMessage: LiveData<String> = _winnerMessage

    init {
        reset()
    }

    fun onCell00Pressed() {
        throwTurn(_cell00)
    }

    fun onCell01Pressed() {
        throwTurn(_cell01)
    }

    fun onCell02Pressed() {
        throwTurn(_cell02)
    }

    fun onCell10Pressed() {
        throwTurn(_cell10)
    }

    fun onCell11Pressed() {
        throwTurn(_cell11)
    }

    fun onCell12Pressed() {
        throwTurn(_cell12)
    }

    fun onCell20Pressed() {
        throwTurn(_cell20)
    }

    fun onCell21Pressed() {
        throwTurn(_cell21)
    }

    fun onCell22Pressed() {
        throwTurn(_cell22)
    }

    fun reset() {
        _cell00.value = null
        _cell01.value = null
        _cell02.value = null
        _cell10.value = null
        _cell11.value = null
        _cell12.value = null
        _cell20.value = null
        _cell21.value = null
        _cell22.value = null
        _turn.value = CROSS_IMAGE
    }

    fun onWinnerShowed() {
        _winnerMessage.value = null
    }

    /* Private methods */

    private fun changeTurn() {
        _turn.value = if (_turn.value == CROSS_IMAGE) {
            CIRCLE_IMAGE
        } else {
            CROSS_IMAGE
        }
    }

    private fun throwTurn(cell: MutableLiveData<Int>) {
        if (cell.value == null) {
            cell.value = _turn.value
            changeTurn()
            checkWinner()
        }
    }

    private fun checkWinner() {
        /* Check Rows */

        if (check3(_cell00.value, _cell01.value, _cell02.value)) {
            determinateWinner(_cell00.value)
            return
        }

        if (check3(_cell10.value, _cell11.value, _cell12.value)) {
            determinateWinner(_cell10.value)
            return
        }

        if (check3(_cell20.value, _cell21.value, _cell22.value)) {
            determinateWinner(_cell20.value)
            return
        }

        /* Check Columns */

        if (check3(_cell00.value, _cell10.value, _cell20.value)) {
            determinateWinner(_cell00.value)
            return
        }

        if (check3(_cell01.value, _cell11.value, _cell21.value)) {
            determinateWinner(_cell01.value)
            return
        }

        if (check3(_cell02.value, _cell12.value, _cell22.value)) {
            determinateWinner(_cell02.value)
            return
        }

        /* Check Diagonals */

        if (check3(_cell00.value, _cell11.value, _cell22.value)) {
            determinateWinner(_cell00.value)
            return
        }

        if (check3(_cell02.value, _cell11.value, _cell20.value)) {
            determinateWinner(_cell02.value)
            return
        }
    }

    private fun check3(value1: Int?, value2: Int?, value3: Int?): Boolean {
        if (value1 == null || value2 == null || value3 == null) {
            return false
        }

        return value1 == value2 && value2 == value3
    }

    private fun determinateWinner(value: Int?) {
        if (value == null || value == EMPTY_IMAGE) return

        val winnerName = if (value == CROSS_IMAGE) {
            CROSS_NAME
        } else {
            CIRCLE_NAME
        }

        _winnerMessage.value = String.format("%s has won!", winnerName)
    }

    companion object {
        private const val CROSS_IMAGE = R.drawable.cross
        private const val CIRCLE_IMAGE = R.drawable.circle
        const val EMPTY_IMAGE = R.drawable.empty

        const val CROSS_NAME = "CROSS"
        const val CIRCLE_NAME = "CIRCLE"
    }

}