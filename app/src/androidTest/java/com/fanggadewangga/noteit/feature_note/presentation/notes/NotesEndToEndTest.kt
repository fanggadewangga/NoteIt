package com.fanggadewangga.noteit.feature_note.presentation.notes

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fanggadewangga.noteit.core.TestTags
import com.fanggadewangga.noteit.di.AppModule
import com.fanggadewangga.noteit.feature_note.presentation.MainActivity
import com.fanggadewangga.noteit.feature_note.presentation.add_edit_notes.AddEditNoteScreen
import com.fanggadewangga.noteit.feature_note.presentation.util.Screen
import com.fanggadewangga.noteit.ui.theme.NoteItTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            NoteItTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(
                        route = Screen.NotesScreen.route
                    ) {
                        NotesScreen(navController = navController)
                    }
                    composable(
                        route = Screen.AddEditNoteScreen.route +
                                "?noteId={noteId}&&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(
                                name = "noteId"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            },

                            navArgument(
                                name = "noteColor"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditNoteScreen(
                            navController = navController,
                            noteColor = color
                        )
                    }
                }
            }
        }
    }

    @Test
    fun saveNewNote_editAfterwards() {
        // Click on FAB to get to add note screen
        composeRule.onNodeWithContentDescription("Add").performClick()

        // Enter texts in title and content Text Field
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD_TEST_TAG)
            .performTextInput("test-title")
        composeRule
            .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD_TEST_TAG)
            .performTextInput("test-content")
        // Save the note
        composeRule.onNodeWithContentDescription("Save").performClick()

        // Make sure there's a note in the list with our title and content
        composeRule.onNodeWithText("test-title").assertIsDisplayed()
        // Click on note to edit it
        composeRule.onNodeWithText("test-title").performClick()

        // Make sure title and content text field contain note title and content
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD_TEST_TAG)
            .assertTextEquals("test-title")
        composeRule
            .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD_TEST_TAG)
            .assertTextEquals("test-content")
        // Add the text "-edited" to the title text field
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD_TEST_TAG)
            .performTextInput("test-title-2")
        // Update the note
        composeRule.onNodeWithContentDescription("Save").performClick()

        // Make sure the update was applied to the note
        composeRule.onNodeWithText("test-title-2").assertIsDisplayed()
    }

    @Test
    fun saveNewNotes_orderByTitleDescending() {

        // Add 3 notes
        for (i in 1..3) {
            // Click on FAB to get to add note screen
            composeRule.onNodeWithContentDescription("Add").performClick()

            // Enter texts in title and content Text Field
            composeRule
                .onNodeWithTag(TestTags.TITLE_TEXT_FIELD_TEST_TAG)
                .performTextInput("test-title-$i")
            composeRule
                .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD_TEST_TAG)
                .performTextInput("test-content-$i")
            // Save the note
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        // Make sure that 3 notes added
        composeRule.onNodeWithText("test-title-1").assertIsDisplayed()
        composeRule.onNodeWithText("test-title-2").assertIsDisplayed()
        composeRule.onNodeWithText("test-title-3").assertIsDisplayed()

        // Perform note sorting
        composeRule
            .onNodeWithContentDescription("Sort")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Title")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Descending")
            .performClick()

        //
        composeRule
            .onAllNodesWithTag(TestTags.NOTE_ITEM_TEST_TAG)[0]
                .assertTextContains("test-title-3")
        composeRule
            .onAllNodesWithTag(TestTags.NOTE_ITEM_TEST_TAG)[1]
            .assertTextContains("test-title-2")
        composeRule
            .onAllNodesWithTag(TestTags.NOTE_ITEM_TEST_TAG)[2]
            .assertTextContains("test-title-1")
    }
}