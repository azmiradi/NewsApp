package azmithabet.com.news.representation.news.activity.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import azmithabet.com.news.representation.theme.NotoFamily
import azmithabet.com.news.representation.theme.Typography
import bumblebee.io.mid.ui.theme.BlueDark
import bumblebee.io.mid.ui.theme.YellowDark
import com.azmithabet.restaurantsguide.R

@Composable
fun AlertDialog(
    messageText:String,
    tryAgain: () -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(50.dp))

            .background(color = BlueDark)
           ,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier.size(150.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = messageText,
                style = Typography.h1,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = YellowDark
                ), onClick = tryAgain
            ) {
                Icon(Icons.Rounded.Warning, "")
                Text(
                    text = stringResource(id = R.string.tryAgain),
                    fontWeight = FontWeight.Normal,
                    fontFamily = NotoFamily
                )
            }

        }


    }

}