package com.example.tradelearn.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tradelearn.SupabaseClient
import com.example.tradelearn.ui.HomeMapScreen // Updated!
import com.example.tradelearn.ui.LessonScreen // Updated!
import com.example.tradelearn.ui.LessonViewModel // Updated!
import com.example.tradelearn.ui.theme.*
import io.github.jan.supabase.gotrue.auth

// 1. Define the Routes (The URLs for your app screens)
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Onboarding : Screen("onboarding", "Onboarding", Icons.Default.Home)
    object Learn : Screen("learn", "Learn", Icons.Default.Home)
    object Leaderboard : Screen("leaderboard", "Rank", Icons.Default.Star) // NEW
    object Quests : Screen("quests", "Quests", Icons.Default.MailOutline) // NEW
    object Profile : Screen("profile", "Me", Icons.Default.Person)

    // The lesson route takes an ID so it knows which chapter to load from Supabase
    object Lesson : Screen("lesson/{chapterId}", "Lesson", Icons.Default.PlayArrow) {
        fun createRoute(chapterId: Int) = "lesson/$chapterId"
    }
}

// 2. The Master Layout (Holds the Bottom Bar and the Screens)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Only show the bottom bar on the main tabs, NOT inside a lesson!
    val showBottomBar =
            currentRoute in
                    listOf(
                            Screen.Learn.route,
                            Screen.Leaderboard.route,
                            Screen.Quests.route,
                            Screen.Profile.route
                    )

    Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    BottomNavigationBar(navController = navController, currentRoute = currentRoute)
                }
            }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).background(BackgroundWhite)) {
            NavigationGraph(navController = navController)
        }
    }
}

// 3. The Router (Swaps the screens based on the URL)
@Composable
fun NavigationGraph(navController: NavHostController) {
    // 1. Create a state to hold our starting screen
    var startScreen by remember { mutableStateOf<String?>(null) }

    // 2. Check if the user is logged in as soon as the app boots
    LaunchedEffect(Unit) {
        val session = SupabaseClient.client.auth.currentSessionOrNull()
        if (session != null) {
            startScreen = Screen.Learn.route // Logged in? Go to Map!
        } else {
            startScreen = Screen.Onboarding.route // Not logged in? Show Onboarding!
        }
    }

    // 3. Wait until we know which screen to show before drawing the NavHost
    if (startScreen != null) {
        NavHost(navController = navController, startDestination = startScreen!!) {

            // The Welcome Screen
            composable(Screen.Onboarding.route) {
                com.example.tradelearn.ui.OnboardingScreen(
                        onFinishOnboarding = { userName ->
                            // Go to the Map
                            navController.navigate(Screen.Learn.route) {
                                popUpTo(Screen.Onboarding.route) { inclusive = true }
                            }
                        }
                )
            }

            // Tab 1: The Learning Path (Map)
            composable(Screen.Learn.route) {
                HomeMapScreen(
                        onChapterClick = { chapterId ->
                            navController.navigate(Screen.Lesson.createRoute(chapterId))
                        }
                )
            }

            // Tab 2: Leaderboard
            composable(Screen.Leaderboard.route) {
                // TODO: com.example.tradelearn.ui.LeaderboardScreen()
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Leaderboard")
                }
            }

            // Tab 3: Quests
            composable(Screen.Quests.route) {
                // TODO: com.example.tradelearn.ui.QuestsScreen()
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Quests")
                }
            }

            // Tab 4: The Profile
            composable(Screen.Profile.route) { com.example.tradelearn.ui.ProfileScreen() }

            // The Lesson Engine
            composable(
                    route = Screen.Lesson.route,
                    arguments = listOf(navArgument("chapterId") { type = NavType.IntType })
            ) { backStackEntry ->
                val chapterId = backStackEntry.arguments?.getInt("chapterId") ?: 1

                val viewModel: LessonViewModel = viewModel()
                LaunchedEffect(chapterId) { viewModel.loadLesson(chapterId) }
                LessonScreen(viewModel = viewModel)
            }
        }
    } else {
        // Show a blank screen or a loading spinner for the split-second it takes to check
        Box(
                modifier = Modifier.fillMaxSize().background(BackgroundWhite),
                contentAlignment = Alignment.Center
        ) { CircularProgressIndicator(color = PrimaryIndigo) }
    }
}

// 4. The Bottom Tab Bar UI (Styled like your screenshots)
@Composable
fun BottomNavigationBar(navController: NavHostController, currentRoute: String?) {
    val tabs = listOf(Screen.Learn, Screen.Leaderboard, Screen.Quests, Screen.Profile)

    NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
        tabs.forEach { screen ->
            val isSelected = currentRoute == screen.route
            NavigationBarItem(
                    icon = { Icon(screen.icon, contentDescription = screen.title) },
                    selected = isSelected,
                    onClick = {
                        navController.navigate(screen.route) {
                            // Pop up to the start destination to avoid massive backstacks
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors =
                            NavigationBarItemDefaults.colors(
                                    selectedIconColor = Blue500,
                                    unselectedIconColor = Slate400,
                                    indicatorColor =
                                            Blue50 // Soft blue background for the selected icon
                            )
            )
        }
    }
}
