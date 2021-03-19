package com.ericktijerou.gitstar.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericktijerou.gitstar.R
import com.ericktijerou.gitstar.ui.theme.GitstarTheme

@Composable
fun LanguageIndicator(color: Color, label: String, modifier: Modifier, dotSize: Dp, tint: Color) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        LanguageCircle(
            color = color, modifier = Modifier
                .size(dotSize)
                .padding(end = 4.dp)
        )
        Text(
            text = label,
            style = GitstarTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            ),
            color = tint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun TextIndicator(painter: Painter, label: String, modifier: Modifier, tint: Color) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = stringResource(R.string.label_indicator),
            modifier = Modifier.padding(end = 4.dp),
            tint = tint
        )
        Text(
            text = label,
            style = GitstarTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            ),
            color = tint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun LanguageCircle(color: Color, modifier: Modifier) {
    Canvas(modifier = modifier, onDraw = {
        drawCircle(color = color)
    })
}

data class Indicator(val label: String, @DrawableRes val icon: Int)
