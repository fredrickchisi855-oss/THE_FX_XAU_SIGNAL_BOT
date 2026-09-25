package com.thefxxau.bot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val Black = Color(0xFF050505)
private val Panel = Color(0xFF111111)
private val Gold = Color(0xFFD9A441)
private val GoldSoft = Color(0xFF8B6A2B)
private val Green = Color(0xFF35D07F)
private val Red = Color(0xFFFF5A5F)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { XauBotApp() }
    }
}

@androidx.compose.runtime.Composable
private fun XauBotApp() {
    var showSplash by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) { delay(2400); showSplash = false }

    MaterialTheme(colorScheme = darkColorScheme(background = Black, surface = Panel, primary = Gold)) {
        AnimatedVisibility(visible = showSplash, enter = fadeIn(), exit = fadeOut(animationSpec = tween(500))) {
            SplashScreen()
        }
        AnimatedVisibility(visible = !showSplash, enter = fadeIn(animationSpec = tween(450, easing = LinearOutSlowInEasing))) {
            Dashboard()
        }
    }
}

@androidx.compose.runtime.Composable
private fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(Black).statusBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(com.thefxxau.bot.R.drawable.xau_bot_logo),
                contentDescription = "THE_FX_XAU_BOT logo",
                modifier = Modifier.size(190.dp)
            )
            Spacer(Modifier.height(20.dp))
            Text("THE_FX_XAU_BOT", color = Gold, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("XAUUSD SIGNAL BOT", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))
            Text("STRICTLY XAUUSD ONLY", color = GoldSoft, fontSize = 11.sp)
            Spacer(Modifier.height(42.dp))
            Text("INITIALIZING SIGNAL ENGINE...", color = Color.LightGray, fontSize = 11.sp)
            Spacer(Modifier.height(12.dp))
            Box(Modifier.width(180.dp).height(3.dp).background(Gold, RoundedCornerShape(50)))
            Spacer(Modifier.height(32.dp))
            Text("© THE_FX_XAU_TRADERS", color = Color.Gray, fontSize = 10.sp)
        }
    }
}

@androidx.compose.runtime.Composable
private fun Dashboard() {
    var selectedTf by remember { mutableStateOf("M15") }
    var tab by remember { mutableStateOf(0) }
    val timeframes = listOf("M1", "M3", "M5", "M15", "M30", "H1", "H2", "H4", "H6", "H8", "H12", "D1", "W1", "MN")

    Scaffold(
        containerColor = Black,
        bottomBar = {
            NavigationBar(containerColor = Panel, modifier = Modifier.navigationBarsPadding()) {
                listOf("Home", "Signals", "Chart", "News", "More").forEachIndexed { index, label ->
                    NavigationBarItem(
                        selected = tab == index,
                        onClick = { tab = index },
                        icon = { Text(label.take(1), color = if (tab == index) Gold else Color.Gray) },
                        label = { Text(label, fontSize = 9.sp) }
                    )
                }
            }
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState())) {
            Header()
            if (tab == 0) {
                HomeContent(selectedTf, timeframes) { selectedTf = it }
            } else {
                PlaceholderTab(label = listOf("Home", "Signals", "Chart", "News", "More")[tab])
            }
        }
    }
}

@androidx.compose.runtime.Composable
private fun Header() {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painterResource(R.drawable.xau_bot_logo), null, Modifier.size(44.dp))
            Spacer(Modifier.width(10.dp))
            Column {
                Text("XAUUSD", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("SIGNAL BOT", color = Gold, fontSize = 10.sp)
            }
        }
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF10251A)), shape = RoundedCornerShape(50)) {
            Text("● LIVE", color = Green, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp))
        }
    }
}

@androidx.compose.runtime.Composable
private fun HomeContent(selectedTf: String, timeframes: List<String>, onTf: (String) -> Unit) {
    MarketStatusCard()
    SectionTitle("NEXT XAUUSD SIGNAL")
    SignalCard(selectedTf)
    SectionTitle("TIMEFRAMES")
    Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 16.dp)) {
        timeframes.forEach { tf ->
            val active = tf == selectedTf
            Button(
                onClick = { onTf(tf) },
                modifier = Modifier.padding(end = 7.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (active) Gold else Panel, contentColor = if (active) Black else Color.White),
                shape = RoundedCornerShape(12.dp)
            ) { Text(tf, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        }
    }
    SectionTitle("MARKET ENGINE")
    InfoGrid()
    SectionTitle("INDICATORS")
    IndicatorsCard()
    SectionTitle("XAUUSD NEWS")
    NewsCard()
    Spacer(Modifier.height(20.dp))
    Text("© THE_FX_XAU_TRADERS • +263 784 552 452", color = Color.Gray, fontSize = 10.sp, modifier = Modifier.padding(16.dp))
}

@androidx.compose.runtime.Composable
private fun MarketStatusCard() {
    Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(20.dp)) {
        Column(Modifier.padding(18.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("MARKET STATUS", color = Color.Gray, fontSize = 10.sp)
                    Text("MARKET OPEN", color = Green, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                }
                Text("XAUUSD", color = Gold, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(12.dp))
            Text("Next signal • M15", color = Color.White, fontSize = 12.sp)
            Text("Starts 14:30 • Ends 14:45 • Countdown 08:24", color = Color.LightGray, fontSize = 11.sp)
        }
    }
}

@androidx.compose.runtime.Composable
private fun SignalCard(tf: String) {
    Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF15120B)), shape = RoundedCornerShape(22.dp)) {
        Column(Modifier.padding(18.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("BUY XAUUSD", color = Green, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                Text(tf, color = Gold, fontWeight = FontWeight.Bold)
            }
            Text("Signal window: 14:30 → 14:45", color = Color.White, fontSize = 12.sp)
            Spacer(Modifier.height(14.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Metric("ENTRY", "—")
                Metric("SL", "—")
                Metric("TP1", "—")
                Metric("TP2", "—")
            }
            Spacer(Modifier.height(14.dp))
            Text("Waiting for live Deriv data and signal confirmation", color = Color.Gray, fontSize = 10.sp)
        }
    }
}

@androidx.compose.runtime.Composable
private fun Metric(label: String, value: String) {
    Column {
        Text(label, color = Color.Gray, fontSize = 9.sp)
        Text(value, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@androidx.compose.runtime.Composable
private fun InfoGrid() {
    Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        MiniCard("TREND", "BULLISH", Green, Modifier.weight(1f))
        MiniCard("MOMENTUM", "STRONG", Gold, Modifier.weight(1f))
        MiniCard("RISK", "NORMAL", Color.White, Modifier.weight(1f))
    }
}

@androidx.compose.runtime.Composable
private fun MiniCard(title: String, value: String, valueColor: Color, modifier: Modifier) {
    Card(modifier, colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(12.dp)) {
            Text(title, color = Color.Gray, fontSize = 8.sp)
            Spacer(Modifier.height(5.dp))
            Text(value, color = valueColor, fontWeight = FontWeight.Bold, fontSize = 11.sp)
        }
    }
}

@androidx.compose.runtime.Composable
private fun IndicatorsCard() {
    Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
        Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            Text("EMA ✓", color = Green, fontSize = 11.sp)
            Text("RSI ✓", color = Green, fontSize = 11.sp)
            Text("MACD ✓", color = Green, fontSize = 11.sp)
            Text("ATR ✓", color = Green, fontSize = 11.sp)
        }
    }
}

@androidx.compose.runtime.Composable
private fun NewsCard() {
    Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.padding(16.dp)) {
            Text("⚠ HIGH-IMPACT NEWS", color = Red, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Spacer(Modifier.height(6.dp))
            Text("News feed will be connected to the XAUUSD/USD economic-news source.", color = Color.LightGray, fontSize = 11.sp)
        }
    }
}

@androidx.compose.runtime.Composable
private fun SectionTitle(text: String) {
    Text(text, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.padding(start = 16.dp, top = 22.dp, bottom = 10.dp))
}

@androidx.compose.runtime.Composable
private fun PlaceholderTab(label: String) {
    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        Text(label.uppercase(), color = Gold, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(Modifier.height(12.dp))
        Text("This module is reserved for the next build stage.", color = Color.LightGray)
    }
}
