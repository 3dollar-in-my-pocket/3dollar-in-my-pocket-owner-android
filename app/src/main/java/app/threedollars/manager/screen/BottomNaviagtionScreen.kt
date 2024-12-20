package app.threedollars.manager.screen

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import app.threedollars.common.ui.Gray100
import app.threedollars.common.ui.Gray50
import app.threedollars.common.ui.White
import app.threedollars.manager.R
import app.threedollars.manager.navigation.factory.TabType
import kotlinx.collections.immutable.toPersistentList

@Composable
fun BottomNavigation(
    currentTab: TabType?,
    onTabSelected: (TabType) -> Unit,
) {
    val tabs = TabType.entries.toPersistentList()

    NavigationBar(
        containerColor = if (currentTab == TabType.SETTING) Gray100 else White,
        contentColor = Color.Black
    ) {
        tabs.forEach { tab ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = tab.icon), contentDescription = null) },
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedIconColor = colorResource(id = R.color.green500),
                    selectedIndicatorColor = Color.Transparent,
                    unselectedIconColor = Gray50
                ),
                alwaysShowLabel = true,
                selected = currentTab == tab,
                onClick = {
                    onTabSelected(tab)
                }
            )
        }
    }
}