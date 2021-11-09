package com.azimolabs.conditionwatcher

/**
 * Created by F1sherKK on 08/10/15.
 * Converted to Kotlin by Yamil Medina on 09/11/21.
 */
object ConditionWatcher {
    private const val CONDITION_NOT_MET = 0
    private const val CONDITION_MET = 1
    private const val TIMEOUT = 2

    private const val DEFAULT_TIMEOUT_LIMIT = 1000 * 60
    private const val DEFAULT_INTERVAL = 250

    @Throws(Exception::class)
    fun waitForCondition(
        instruction: Instruction,
        timeoutLimit: Int = DEFAULT_TIMEOUT_LIMIT,
        watchInterval: Int = DEFAULT_INTERVAL
    ) {

        var status = CONDITION_NOT_MET
        var elapsedTime = 0
        do {
            if (instruction.checkCondition()) {
                status = CONDITION_MET
            } else {
                elapsedTime += watchInterval
                Thread.sleep(watchInterval.toLong())
            }

            if (elapsedTime >= timeoutLimit) {
                status = TIMEOUT
                break
            }
        } while (status != CONDITION_MET)

        if (status == TIMEOUT) {
            throw Exception(instruction.getDescription() + " - took more than " + timeoutLimit / 1000 + " seconds. Test stopped.")
        }
    }
}
