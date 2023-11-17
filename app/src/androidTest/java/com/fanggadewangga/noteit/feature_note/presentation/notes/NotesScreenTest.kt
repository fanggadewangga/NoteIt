package com.fanggadewangga.noteit.feature_note.presentation.notes

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.fanggadewangga.noteit.R
import com.fanggadewangga.noteit.core.TestTags
import com.fanggadewangga.noteit.di.AppModule
import com.fanggadewangga.noteit.feature_note.presentation.MainActivity
import com.fanggadewangga.noteit.feature_note.presentation.util.Screen
import com.fanggadewangga.noteit.ui.theme.NoteItTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        // Inject dependencies that we will use in test case
        hiltRule.inject()

        // set content to specific composable
        composeRule.activity.setContent {
            val navController = rememberNavController()
            NoteItTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        // get a context in a test
        val context = ApplicationProvider.getApplicationContext<Context>()

        // find specific composable to test (e.g : order section button) and do some test cases
        // example expected scenario :order section is hidden -> click sort icon button -> order section is displayed
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION_TEST_TAG).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort)).performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION_TEST_TAG).assertIsDisplayed()
    }
}