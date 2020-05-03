package com.dd.zhumbak.ui.category

import com.dd.zhumbak.base.BaseToolbarsViewModel
import com.dd.zhumbak.model.ToolbarModel
import com.dd.zhumbak.ui.main.MainToolbarsViewModel
import com.dd.domain.manager.ResourceManager
import com.dd.domain.usecase.GetLocalRandomZhumbakUseCase

class CategoryViewModel(
    private val resourceManager: ResourceManager,
    private val getLocalRandomZhumbakUseCase: GetLocalRandomZhumbakUseCase
) : BaseToolbarsViewModel<CategoryState, CategoryNavigator.Navigation>() {
    /**
     * Constants
     */
    companion object {
        const val TELEGRAM_CLICKED = 1
    }

    /**
     * Default variables
     */
    override val initialViewState: CategoryState =
        CategoryState()
    /**
     * Custom variables
     */
    /**
     * Default functions
     */
    override fun onConfigureToolbars(mainToolbarsVm: MainToolbarsViewModel) {
        mainToolbarsVm.onActionUpdateToolbar {
            it.copy(
                toolbarTitle = resourceManager.getToolbarTitle(),
                toolbarTitleVisibility = true,
                toolbarLogoOrBackVisibility = true,
                telegramButton = ToolbarModel.TelegramButton(
                    visibility = true
                ),
                searchButton = ToolbarModel.SearchButton(
                    visibility = true
                )
            )
        }
    }

    override fun onStartFirstTime(statePreloaded: Boolean) {
        executeUseCaseWithException(
            {
                val responseCategoryModel = getLocalRandomZhumbakUseCase.execute(Unit)
                updateToNormalState {
                    copy(
                        zhumbakModel = responseCategoryModel.zhumbakModel
                    )
                }
            },
            { e ->
                updateToErrorState(e)
            })
    }

    /**
     * Custom functions
     */
    fun onActionGenerateClick() {
        executeUseCaseWithException(
            {
                val responseCategoryModel = getLocalRandomZhumbakUseCase.execute(Unit)
                updateToNormalState {
                    copy(
                        zhumbakModel = responseCategoryModel.zhumbakModel
                    )
                }
            },
            { e ->
                updateToErrorState(e)
            })
    }
}