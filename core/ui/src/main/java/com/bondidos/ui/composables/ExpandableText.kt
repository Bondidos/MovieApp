package com.bondidos.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize

@Composable
fun ExpandableText(
    text: String,
    textStyle: TextStyle,
    textColor: Color,
    linkTextColor: Color,
    modifier: Modifier = Modifier,
    minimizedMaxLines: Int = 1,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    SubcomposeLayout(modifier) { constraints ->
        var textLayoutResultVar: TextLayoutResult? = null
        var seeMoreSizeVar: IntSize? = null
        var textComposable = subcompose("full text") {
            Text(
                text = text,
                style = textStyle,
                color = textColor,
                onTextLayout = { textLayoutResultVar = it },
            )
        }.first().measure(constraints)
        val seeMoreText = subcompose("see more") {
            Text(
                "... See more",
                style = textStyle,
                color = linkTextColor,
                textDecoration = TextDecoration.Underline,
                onTextLayout = { seeMoreSizeVar = it.size },
                modifier = Modifier
                    .clickable {
                        expanded = true
                    }
            )
        }.first().measure(Constraints())

        val textLayoutResult = textLayoutResultVar
        val seeMoreSize = seeMoreSizeVar

        val textWidth = textComposable.width
        val lastLineIndex = minimizedMaxLines - 1
        val seeMoreOffset: Offset?
        if (!expanded && textLayoutResult != null && seeMoreSize != null
            && textLayoutResult.lineCount > lastLineIndex
        ) {
            var lastCharIndex = textLayoutResult.getLineEnd(lastLineIndex, visibleEnd = true) + 1
            var charRect: Rect
            do {
                lastCharIndex -= 1
                charRect = textLayoutResult.getCursorRect(lastCharIndex)
            } while (
                charRect.left > textLayoutResult.size.width - seeMoreSize.width
            )
            seeMoreOffset = Offset(charRect.left, charRect.bottom - seeMoreSize.height)
            textComposable = subcompose("cutText") {
                Text(
                    text = text.substring(startIndex = 0, endIndex = lastCharIndex),
                    style = textStyle,
                    color = textColor,
                )
            }.first().measure(constraints)
        } else {
            seeMoreOffset = null
        }

        layout(textWidth, textComposable.height) {
            textComposable.place(0, 0)
            if (seeMoreOffset != null) {
                seeMoreText.place(
                    x = seeMoreOffset.x.toInt(),
                    y = seeMoreOffset.y.toInt()
                )
            }
        }
    }
}