package com.dd.conqazaqstan.sqlite.base

import com.carmabs.ema.android.base.EmaApplication
import com.dd.conqazaqstan.sqlite.injection.appInjection
import com.dd.injection.appDataInjection
import org.kodein.di.Kodein


class MakalApplication : EmaApplication() {
    override fun injectAppModule(kodein: Kodein.MainBuilder): Kodein.Module? {
        kodein.import(appDataInjection())
        return appInjection(this)
    }
}