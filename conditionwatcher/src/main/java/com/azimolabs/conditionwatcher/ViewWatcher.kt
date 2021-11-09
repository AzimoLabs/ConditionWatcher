package com.azimolabs.conditionwatcher

import android.app.Activity
import android.view.View

/**
 * Helper for any [View] to wait for a [ConditionWatcher] until satisfies the [checkCondition]
 *
 * Created by Yamil Medina on 09/11/21.
 */
class ViewWatcher(
    private val activity: Activity?,
    private val viewId: Int = 0,
    private val visibility: Int = View.VISIBLE
) : Instruction() {

    override fun getDescription(): String {
        return "Wait for view to be with expected visibility"
    }

    override fun checkCondition(): Boolean {
        val view: View? = activity?.findViewById(viewId)
        return view?.visibility == visibility
    }
}
