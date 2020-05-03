package com.dd.zhumbak.injection

import android.app.Application
import com.dd.domain.usecase.GetLocalRandomZhumbakUseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

fun appInjection(application: Application) = Kodein.Module(name = "AppModule") {
    bind<Application>() with singleton { application }

    bind<GetLocalRandomZhumbakUseCase>() with provider { GetLocalRandomZhumbakUseCase(instance()) }
}