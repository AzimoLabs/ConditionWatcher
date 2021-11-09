package com.azimolabs.conditionwatcher

import android.os.Bundle

/**
 * Created by F1sherKK on 16/12/15.
 */
abstract class Instruction {
    var dataContainer = Bundle()
        private set

    fun setData(dataContainer: Bundle) {
        this.dataContainer = dataContainer
    }

    abstract val description: String?
    abstract fun checkCondition(): Boolean
}