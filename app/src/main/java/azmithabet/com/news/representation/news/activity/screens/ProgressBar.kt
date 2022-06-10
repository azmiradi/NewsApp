package azmithabet.com.news.representation.news.activity.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import azmithabet.com.news.representation.theme.NotoFamily
import bumblebee.io.mid.ui.theme.GreenColor
import com.azmithabet.restaurantsguide.R
@Composable
fun ProgressBar(
    isShow: MutableState<Boolean>,
    message: String = stringResource(id = R.string.loading)
) {

    if (isShow.value) {
        Dialog(
            onDismissRequest = { isShow.value = false },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = GreenColor)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = message, textAlign = TextAlign.Center,
                        fontFamily = NotoFamily,
                        fontWeight = FontWeight.Normal
                    )

                }

            }
        }
    }
}