package com.overplay.test

import com.overplay.test.base.BaseOverplayApplication
import com.overplay.test.data.sessioncounter.impl.di.dataSessionCounterModule
import com.overplay.test.data.sessioncounter.local.impl.di.dataLocalSessionCounterModule
import com.overplay.test.di.appModule
import com.overplay.test.feature.main.di.featureMainModule

@Suppress("unused")
class OverplayApplication : BaseOverplayApplication() {

    override val diModules = listOf(
        dataLocalSessionCounterModule,
        dataSessionCounterModule,
        featureMainModule,
        appModule,
    )
}