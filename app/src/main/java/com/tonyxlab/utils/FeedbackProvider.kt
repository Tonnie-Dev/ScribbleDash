package com.tonyxlab.utils

import android.content.Context
import com.tonyxlab.scribbledash.R

object FeedbackProvider {

    private val oopsFeedback = listOf(
            R.string.feedback_oops_1,
            R.string.feedback_oops_2,
            R.string.feedback_oops_3,
            R.string.feedback_oops_4,
            R.string.feedback_oops_5,
            R.string.feedback_oops_6,
            R.string.feedback_oops_7,
            R.string.feedback_oops_8,
            R.string.feedback_oops_9,
            R.string.feedback_oops_10
    )

    private val goodFeedback = listOf(
            R.string.feedback_good_1,
            R.string.feedback_good_2,
            R.string.feedback_good_3,
            R.string.feedback_good_4,
            R.string.feedback_good_5,
            R.string.feedback_good_6,
            R.string.feedback_good_7,
            R.string.feedback_good_8,
            R.string.feedback_good_9,
            R.string.feedback_good_10
    )

    private val woohooFeedback = listOf(
            R.string.feedback_woohoo_1,
            R.string.feedback_woohoo_2,
            R.string.feedback_woohoo_3,
            R.string.feedback_woohoo_4,
            R.string.feedback_woohoo_5,
            R.string.feedback_woohoo_6,
            R.string.feedback_woohoo_7,
            R.string.feedback_woohoo_8,
            R.string.feedback_woohoo_9,
            R.string.feedback_woohoo_10
    )

    fun getFeedback(context: Context, score: Int) : Pair<String, String> {

        val feedback = when (score) {

            in 0..39 -> Pair("Oops",oopsFeedback)
            in 40 .. 69 -> Pair("Meh",oopsFeedback)
            in 70..79 -> Pair("Good",goodFeedback)
            in 80..89 -> Pair("Great",goodFeedback)
            else -> Pair("Woohoo!", woohooFeedback)

        }
        val resId = feedback.second.random()
        return Pair(feedback.first, context.getString(resId))
    }

}