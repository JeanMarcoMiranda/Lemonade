package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonApp()
            }
        }
    }
}

@Preview
@Composable
fun LemonApp() {
    Column {
        MyTopAppBar()
        LemonadeWithTextAndButtonImage(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
fun MyTopAppBar(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxWidth()
        .background(Color(0xFFf9e44c))
        .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 25.sp
        )
    }
}

@Composable
fun LemonadeWithTextAndButtonImage(modifier: Modifier = Modifier) {
    var stepNumber by remember {
        mutableIntStateOf(1)
    }
    var squeezedTimes by remember {
        mutableIntStateOf(0)
    }
    var timesToSqueeze = (2..4).random()

    val onRefresh: () -> Unit = {
        timesToSqueeze = (2..4).random()
        stepNumber = 1
    }
    val onNextStep: () -> Unit = {
        stepNumber++
    }
    val onSqueeze: () -> Unit = {
        if(squeezedTimes < timesToSqueeze) {
            squeezedTimes++
        } else {
            squeezedTimes = 0
            stepNumber++
        }
    }

    val handleClick = when(stepNumber) {
        2 -> onSqueeze
        4 -> onRefresh
        else -> onNextStep
    }

    val stepText = when(stepNumber) {
        1 -> R.string.select_lemon
        2 -> R.string.squeeze_lemon
        3 -> R.string.drink_lemonade
        else -> R.string.restart
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LemonadeImageButton(stepNumber, onClick = handleClick)
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(id = stepText),
            fontSize = 18.sp
        )
    }
}

@Composable
fun LemonadeImageButton(stepNumber: Int, onClick: () -> Unit) {
    val stepImage = when(stepNumber) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    Button(
       onClick,
       modifier = Modifier
           .height(250.dp)
           .width(250.dp),
       shape = RoundedCornerShape(40.dp),
       colors = ButtonDefaults.buttonColors(
           containerColor = Color(0xFFc3ecd2)
       )
    ){
       Image(
           painter = painterResource(id = stepImage),
           contentDescription = "1"
       )
    }
}