package com.example.myapplication


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomAppBar
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FabPosition
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme(darkTheme = true) {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(65.dp)
                    .clip(RoundedCornerShape(20.dp)),
                backgroundColor = MaterialTheme.colorScheme.background,
                cutoutShape = RoundedCornerShape(20.dp),
            ) {
                BottomNavigationBar(navController = navController)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(NavItem.Home.route) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(20.dp)
            ) {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "ホーム"
                )
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        NavHost(
            navController,
            startDestination = NavItem.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(NavItem.Home.route) {
                HomeScreen()
            }
            composable(NavItem.Search.route) {
                SearchScreen()
            }
            composable(NavItem.Setting.route) {
                SettingScreen()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val views = listOf(
        NavItem.Search,
        NavItem.Setting,
    )
    val currentRoute = navController.currentBackStackEntryAsState()
        .value?.destination?.route

    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        views.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

sealed class NavItem(val route: String, val icon: ImageVector, val title: String) {
    data object Home : NavItem("Home", Icons.Default.Home, "ホーム")
    data object Search : NavItem("Search", Icons.Default.Search, "検索")
    data object Setting : NavItem("Setting", Icons.Default.Settings, "設定")
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    BaseScreen(
        modifier = modifier,
        text = "ホーム画面",
        icon = Icons.Default.Home
    )
}

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    BaseScreen(
        modifier = modifier,
        text = "検索画面",
        icon = Icons.Default.Search
    )
}

@Composable
fun SettingScreen(modifier: Modifier = Modifier) {
    BaseScreen(
        modifier = modifier,
        text = "設定画面",
        icon = Icons.Default.Settings
    )
}


@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .padding(bottom = 16.dp),
        )
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        MainScreen()
    }
}