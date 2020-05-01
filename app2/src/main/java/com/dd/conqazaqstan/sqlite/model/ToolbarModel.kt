package com.dd.conqazaqstan.sqlite.model

import com.carmabs.ema.core.constants.STRING_EMPTY

/**
 * Model to represent the toolbar
 *
 * <p>
 * Copyright (c) 2019, Babel Sistemas de Información. All rights reserved.
 * </p>
 *
 * @author <a href=“mailto:carlos.mateo@babel.es”>Carlos Mateo</a>
 */
data class ToolbarModel(
        val telegramButton: TelegramButton? = null,
        val searchButton: SearchButton? = null,
        val toolbarTitle: String = STRING_EMPTY,
        val toolbarTitleVisibility: Boolean = true,
        val toolbarLogoOrBackVisibility: Boolean = true,
        val toolbarVisibility: Boolean = true,
        val toolbarElevation: Boolean = false
) {
    data class SearchButton(
            val visibility: Boolean = true,
            val searchViewText: String = STRING_EMPTY,
            val setOnQueryTextFocusChangeListener: ((queryText: String) -> Unit)? = null
    )

    data class TelegramButton(
            val visibility: Boolean = true
    )
}