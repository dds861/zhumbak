package com.dd.conqazaqstan.sqlite.ui.widget

import android.content.Context
import com.dd.domain.usecase.GetLocalRandomMakalUseCase
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
    private val getLocalRandomMakalUseCase: GetLocalRandomMakalUseCase by instance()

    /**
     * Custom functions
     */
    suspend fun getRandomMakal(): String {

        val randomMakal = getLocalRandomMakalUseCase.execute(Unit)
        return randomMakal.randomMakal
    }
}