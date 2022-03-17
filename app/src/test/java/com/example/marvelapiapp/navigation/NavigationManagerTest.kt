package com.example.marvelapiapp.navigation

import com.example.marvelapiapp.MainActivity
import com.example.marvelapiapp.base.BaseTest
import com.example.marvelapiapp.view.characterInfo.CharacterInfoFragment
import com.example.marvelapiapp.view.characters.CharactersFragment
import com.example.marvelapiapp.view.overlay.fullerror.FullErrorFragment
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test

class NavigationManagerTest : BaseTest() {
    private lateinit var navigationManager: NavigationManager
    private val mainActivity = mockk<MainActivity>(relaxed = true)

    override fun testInit() {
        navigationManager = spyk(recordPrivateCalls = true)
        navigationManager.setTestValue("currentActivity", mainActivity)
    }

    @Test
    fun `test navigateToCharacterDetail`() {
        navigationManager.navigateToCharacterDetail(mockk(relaxed = true))

        verify(exactly = 1) { mainActivity.startChildFragment(any<CharacterInfoFragment>()) }
    }

    @Test
    fun `test navigateToCharacterDetail current fragment is characterInfoFragment`() {
        every {
            navigationManager["getCurrentFragment"]()
        } returns mockk<CharacterInfoFragment>(relaxed = true)

        navigationManager.navigateToCharacterDetail(mockk(relaxed = true))

        verify(exactly = 0) { mainActivity.startChildFragment(any<CharacterInfoFragment>()) }
    }

    @Test
    fun `test navigateToMainScreen`() {
        navigationManager.navigateToMainScreen()

        verify(exactly = 1) { mainActivity.startActivity(any()) }
        verify(exactly = 1) { mainActivity.overridePendingTransition(0, 0) }
        verify(exactly = 1) { mainActivity.finish() }
    }

    @Test
    fun `test navigateToMainScreen pending finish`() {
        every { mainActivity.isFinishing } returns true

        navigationManager.navigateToMainScreen()

        verify(exactly = 0) { mainActivity.startActivity(any()) }
        verify(exactly = 1) { mainActivity.overridePendingTransition(0, 0) }
        verify(exactly = 1) { mainActivity.finish() }
    }

    @Test
    fun `test navigateToMainFragment`() {
        navigationManager.navigateToMainFragment()

        verify(exactly = 1) { mainActivity.startMainFragment() }
    }

    @Test
    fun `test navigateToMainFragment current fragment is charactersFragment`() {
        every {
            navigationManager["getCurrentFragment"]()
        } returns mockk<CharactersFragment>(relaxed = true)

        navigationManager.navigateToMainFragment()

        verify(exactly = 0) { mainActivity.startMainFragment() }
    }

    @Test
    fun `test popBack`() {
        navigationManager.popBack()

        verify(exactly = 1) { mainActivity.supportFragmentManager.popBackStack() }
    }

    @Test
    fun `test showErrorOverlay`() {
        val fragmentName = "testFragment"
        mockkConstructor(FullErrorFragment::class)
        every { anyConstructed<FullErrorFragment>().getFragmentName() } returns fragmentName

        navigationManager.showErrorOverlay()

        verify(exactly = 1) {
            anyConstructed<FullErrorFragment>()
                .show(mainActivity.supportFragmentManager, fragmentName)
        }
    }

    @Test
    fun `test setCurrentActivity`() {
        val newActivityMock = mockk<MainActivity>(relaxed = true)
        navigationManager.setCurrentActivity(newActivityMock)
        val result = navigationManager.getTestValue<MainActivity>("currentActivity")
        Assert.assertEquals(newActivityMock, result)
    }
}
