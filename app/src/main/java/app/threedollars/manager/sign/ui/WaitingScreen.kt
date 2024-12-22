package app.threedollars.manager.sign.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import app.threedollars.common.ext.getInstalledInfo
import app.threedollars.common.ui.*
import app.threedollars.manager.R
import app.threedollars.manager.sign.viewmodel.WaitingViewModel

@Composable
fun WaitingScreen(
    navController: NavHostController,
    viewModel: WaitingViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val navItem by viewModel.loginNavItem.collectAsStateWithLifecycle(null)
    val context = LocalContext.current
    val selectorIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
    }
    val intent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.help_feedback_title))
        putExtra(Intent.EXTRA_EMAIL, arrayOf(context.getString(R.string.official_email)))
        putExtra(Intent.EXTRA_TEXT, context.getInstalledInfo())
        selector = selectorIntent
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WaitingTop()
            WaitingBottom()
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Green),
                onClick = {
                    startActivity(context, intent, null)
                }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = stringResource(id = R.string.contact_email),
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 44.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Gray95),
                onClick = {
                    viewModel.logout()
                }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = stringResource(id = R.string.logout),
                    fontSize = 14.sp,
                    color = Gray40,
                    textAlign = TextAlign.Center
                )
            }

        }
    }

    LaunchedEffect(navItem) {
        navItem?.let {
            navController.popBackStack()
            navController.navigate(it.screenRoute)
        }
    }
}

@Composable
private fun WaitingBottom() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 32.dp)
            .background(Gray95, RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExplanationTextAndImage(
            textPadding = 56,
            stringRes = R.string.waiting_text_1,
            painterRes = R.drawable.ic_waiting_explanation_1
        )
        Text(
            modifier = Modifier.padding(top = 86.dp),
            text = stringResource(id = R.string.waiting_text_2),
            fontSize = 18.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        ) {
            CircleBackgroundImageAndTitleColumn(
                modifier = Modifier.weight(1f),
                painterRes = R.drawable.ic_pin,
                stringRes = R.string.shop_location
            )
            CircleBackgroundImageAndTitleColumn(
                modifier = Modifier.weight(1f),
                painterRes = R.drawable.ic_calendar,
                stringRes = R.string.business_schedule
            )
            CircleBackgroundImageAndTitleColumn(
                modifier = Modifier.weight(1f),
                painterRes = R.drawable.ic_note,
                stringRes = R.string.menu_information
            )
        }
        ExplanationTextAndImage(
            textPadding = 70,
            stringRes = R.string.waiting_text_3,
            painterRes = R.drawable.ic_waiting_explanation_2
        )
        Spacer(modifier = Modifier.height(160.dp))
    }
}

@Composable
private fun WaitingTop() {
    Text(
        modifier = Modifier.padding(top = 80.dp),
        text = stringResource(id = R.string.completed_application),
        fontSize = 30.sp,
        color = Color.White
    )
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = Gray30)) {
                append(stringResource(id = R.string.waiting_explanation_1))
            }
            withStyle(style = SpanStyle(color = Green)) {
                append(stringResource(id = R.string.waiting_explanation_2))
            }
            withStyle(style = SpanStyle(color = Gray30)) {
                append(stringResource(id = R.string.waiting_explanation_3))
            }
        },
        modifier = Modifier.padding(top = 16.dp),
        fontSize = 14.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun ExplanationTextAndImage(textPadding: Int, stringRes: Int, painterRes: Int) {
    Text(
        modifier = Modifier.padding(top = textPadding.dp),
        text = stringResource(id = stringRes),
        fontSize = 18.sp,
        color = Color.White,
        textAlign = TextAlign.Center
    )
    Image(
        painter = painterResource(id = painterRes), contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 28.dp),
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun CircleBackgroundImageAndTitleColumn(modifier: Modifier = Modifier, painterRes: Int, stringRes: Int) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(Gray80),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = painterRes), contentDescription = null,
                modifier = Modifier.size(44.dp),
                contentScale = ContentScale.FillBounds
            )
        }
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = stringResource(id = stringRes),
            fontSize = 12.sp,
            color = Gray30,
            textAlign = TextAlign.Center
        )
    }
}