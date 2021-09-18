package org.jetbrains.hyperskill

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import org.jetbrains.hyperskill.model.HyperSkillUser
import org.jetbrains.hyperskill.model.HyperSkillUserStats
import org.jetbrains.hyperskill.network.ApiService
import org.jetbrains.hyperskill.ui.theme.HyperskillProfilesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //ProfileInfo(ApiService.getUserData("1283850"))
            Text(ApiService.login())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val url = "https://ucarecdn.com/efff3079-1b03-4f5c-bbf2-dd2a8d9d49e5/-/crop/1706x1707/436,0/-/preview/"
    ProfileInfo(user = HyperSkillUser(123, "Vladimir Klimov", url))
}

@ExperimentalCoilApi
@Composable
fun ProfileInfo(user: HyperSkillUser) {
    HyperskillProfilesTheme {
        BoxWithConstraints(modifier = Modifier.background(MaterialTheme.colors.background)) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(),
                //verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileHeader(user, this@BoxWithConstraints.maxHeight * 0.4f)
                StatsBlock(stats = HyperSkillUserStats(6, 0, 6))
                BoxRowWithContent {
                    Text(text = "Row")
                }

                BoxRowWithContent {
                    Text(text = "Row")
                }

                Row(modifier = Modifier.height(100.dp)) { Text(text = "Row") }

            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun ProfileHeader(user: HyperSkillUser, height: Dp) {
    val blueBackground = Color(12, 24, 159)
    Column(
        modifier = Modifier
            .background(color = blueBackground)
            .height(height)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(user.avatarUrl),
            contentDescription = "User profile picture",
            modifier = Modifier
                .size(height * 0.5f)
                .clip(CircleShape)
        )
        Text(
            user.name,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.White,
        )
    }
}

@Composable
fun StatsBlock(stats: HyperSkillUserStats) {
    @Composable
    fun RowScope.Block(@DrawableRes image: Int, count: Int, name: String, leftPadding: Boolean) {
        val textColor = Color(139, 145, 159)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(start = if (leftPadding) 10.dp else 0.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(40.dp).padding(5.dp),
                    painter = painterResource(id = image),
                    contentDescription = name
                )
                Column {
                    Text(text = count.toString())
                    Text(text = name, fontSize = 12.sp, color = textColor)
                }
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.9f)
            .offset(y = (-50).dp)
            .height(100.dp)
            .padding(top = 5.dp, bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Block(
            image = R.drawable.ic_projects_completed,
            count = stats.projectsCompleted,
            name = "Projects",
            leftPadding = false
        )
        Block(
            image = R.drawable.ic_tracks_completed,
            count = stats.tracksCompleted,
            name = "Tracks",
            leftPadding = true
        )
        Block(
            image = R.drawable.ic_badges_received,
            count = stats.badgesReceived,
            name = "Badges",
            leftPadding = true
        )
    }
}

@Composable
inline fun BoxRowWithContent(content: @Composable BoxScope.() -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-50).dp)
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9f)
                .fillMaxHeight()
                .padding(top = 5.dp, bottom = 5.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White),
            content = content
        )
    }
}


