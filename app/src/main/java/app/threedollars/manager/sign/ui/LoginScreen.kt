package app.threedollars.manager.sign.ui

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import app.threedollars.common.ui.Yellow
import app.threedollars.manager.MainActivity
import app.threedollars.manager.R
import app.threedollars.manager.sign.LoginNavItem
import app.threedollars.manager.sign.viewmodel.LoginViewModel
import app.threedollars.manager.util.findActivity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    loginKakao: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val navItem by viewModel.loginNavItem.collectAsStateWithLifecycle(null)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val notificationPermissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
        if (!notificationPermissionState.status.isGranted) {
            SideEffect { notificationPermissionState.launchPermissionRequest() }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.ic_login_bg), contentDescription = null)
        SocialLoginButton {
            loginKakao()
        }
    }
    LaunchedEffect(navItem) {
        navItem?.let {
            if (it == LoginNavItem.Home) {
                context.startActivity(Intent(context, MainActivity::class.java))
                context.findActivity().finish()
            } else {
                navController.popBackStack()
                navController.navigate(it.screenRoute)
            }
        }
    }
}

@Composable
fun SocialLoginButton(
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier
                .padding(vertical = 24.dp)
                .fillMaxWidth(),
            onClick = { onClick() },
            shape = RoundedCornerShape(23.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Yellow
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_kakao),
                    contentDescription = null,

                    )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(id = R.string.kakao_login),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}

