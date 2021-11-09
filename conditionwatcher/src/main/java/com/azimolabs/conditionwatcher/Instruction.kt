package com.azimolabs.conditionwatcher

import android.os.Bundle

/**
 * Created by F1sherKK on 16/12/15.
 * Converted to Kotlin by Yamil Medina on 09/11/21.
 */
abstract class Instruction(var dataContainer: Bundle = Bundle()) {
    abstract fun getDescription(): String
    abstract fun checkCondition(): Boolean
}
