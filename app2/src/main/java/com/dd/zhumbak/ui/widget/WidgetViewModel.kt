package com.dd.zhumbak.ui.widget

import android.content.Context
import com.dd.domain.usecase.GetLocalRandomZhumbakUseCase
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class WidgetViewModel(private val context: Context) : KodeinAware {
    /**
     * Default variables
     */
    override val kodein: Kodein by closestKodein(context)

    /**
     * Custom variables
     */
    private val getLocalRandomZhumbakUseCase: GetLocalRandomZhumbakUseCase by instance()

    /**
     * Custom functions
     */
    suspend fun getRandomMakal(): String {

        val randomMakal = getLocalRandomZhumbakUseCase.execute(Unit)
        return randomMakal.zhumbakModel.question
    }
}