package com.azimolabs.conditionwatcher

import android.app.Activity
import android.view.View
import kotlin.properties.Delegates

/**
 * Helper for any [View] to wait for a [ConditionWatcher] until satisfies the [checkCondition]
 *
 * Usage: ViewWatcher(someActivity).forView(R.id.someId).withVisibility(View.GONE)
 *
 * Created by Yamil Medina on 09/11/21.
 */
class ViewWatcher(
    private val activity: Activity?
) : Instruction() {

    private var viewId by Delegates.notNull<Int>()
    private var visibility by Delegates.notNull<Int>()

    fun forView(viewId: Int): ViewWatcher {
        this.viewId = viewId
        return this
    }

    fun withVisibility(visibility: Int = View.VISIBLE): ViewWatcher {
        this.visibility = visibility
        return this
    }

    override fun getDescription(): String {
        return "Wait for view to be with expected visibility"
    }

    override fun checkCondition(): Boolean {
        val view: View? = activity?.findViewById(viewId)
        return view?.visibility == visibility
    }
}
