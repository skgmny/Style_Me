package com.example.styleme


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.styleme.ui.theme.StyleMeTheme
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf

import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.unit.Dp





data class SavedOutfit(
    val topImage: Int,
    val bottomImage: Int,
    val shoesImage: Int,
    val name: String = "",
    val memo: String = ""
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StyleMeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    StyleMeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

//기본 프리뷰
@Preview(showBackground = true)
@Composable
fun StyleMeScreenPreview() {
    StyleMeTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            StyleMeScreen()
        }
    }
}

// 저장한 코디 프리뷰
@Preview(showBackground = true)
@Composable
fun SavedOutfitCardPreview() {
    StyleMeTheme {
        SavedOutfitCard(
            outfit = SavedOutfit(
                topImage = R.drawable.top_04,
                bottomImage = R.drawable.bottom_05,
                shoesImage = R.drawable.shoes_04,
                name = "My Outfit",
                memo = "What a masterpiece!"
            ),
            onNameChange = {},
            onMemoChange = {},
            modifier = Modifier.fillMaxWidth(),
            initiallyExpanded = true
        )
    }
}

//시작//
@Composable
fun StyleMeScreen(
    modifier: Modifier = Modifier
) {

    val topImages = listOf(
        R.drawable.top_01,
        R.drawable.top_02,
        R.drawable.top_03,
        R.drawable.top_04,
        R.drawable.top_05,
        R.drawable.top_06,
        R.drawable.top_07,
        R.drawable.top_08,
        R.drawable.top_09,
        R.drawable.top_10
    )

    var topIndex by remember { mutableStateOf(0) }

    val bottomImages = listOf(
        R.drawable.bottom_01,
        R.drawable.bottom_02,
        R.drawable.bottom_03,
        R.drawable.bottom_04,
        R.drawable.bottom_05,
        R.drawable.bottom_06,
        R.drawable.bottom_07,
        R.drawable.bottom_08,
        R.drawable.bottom_09,
        R.drawable.bottom_10
    )
    var bottomIndex by remember { mutableStateOf(0) }

    val shoesImages = listOf(
        R.drawable.shoes_01,
        R.drawable.shoes_02,
        R.drawable.shoes_03,
        R.drawable.shoes_04,
        R.drawable.shoes_05,
        R.drawable.shoes_06,
        R.drawable.shoes_07,
        R.drawable.shoes_08,
        R.drawable.shoes_09,
        R.drawable.shoes_10
    )
    var shoesIndex by remember { mutableStateOf(0) }

    val savedOutfits = remember {
        mutableStateListOf<SavedOutfit>()
    }

    Column( //전체를 세로로 배체
        modifier = modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(
                    dimensionResource(R.dimen.screen_padding)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.logo_spacing)
            )
        ) {
            Image(
                painter = painterResource(R.drawable.logo_style_me),
                contentDescription = null,
                modifier = Modifier.size(
                    dimensionResource(R.dimen.logo_icon_size)
                )
            )

            Text(
                text = stringResource(R.string.style_me_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }


        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(
                dimensionResource(R.dimen.screen_padding)
            ),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.section_spacing)
            )
        ) {
            //안내문구
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            dimensionResource(R.dimen.guide_height)
                        ),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = stringResource(R.string.outfit_guide),
                        textAlign = TextAlign.Center
                    )
                }
            }

            //상의 (상하의신발 다 똑같음)
            item {
                ClothesSelector(
                    title = stringResource(R.string.section_top),
                    imageRes = topImages[topIndex],
                    imageHeight = dimensionResource(R.dimen.clothing_image_size),
                    onPrevious = {
                        topIndex =
                            if (topIndex == 0) topImages.lastIndex
                            else topIndex - 1
                    },
                    onNext = {
                        topIndex =
                            if (topIndex == topImages.lastIndex) 0
                            else topIndex + 1
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            //하의
            item {
                ClothesSelector(
                    title = stringResource(R.string.section_bottom),
                    imageRes = bottomImages[bottomIndex],
                    imageHeight = dimensionResource(R.dimen.bottom_image_height),
                    onPrevious = {
                        bottomIndex =
                            if (bottomIndex == 0) bottomImages.lastIndex
                            else bottomIndex - 1
                    },
                    onNext = {
                        bottomIndex =
                            if (bottomIndex == bottomImages.lastIndex) 0
                            else bottomIndex + 1
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            //신발
            item {
                ClothesSelector(
                    title = stringResource(R.string.section_shoes),
                    imageRes = shoesImages[shoesIndex],
                    imageHeight = dimensionResource(R.dimen.shoes_image_height),
                    onPrevious = {
                        shoesIndex =
                            if (shoesIndex == 0) shoesImages.lastIndex
                            else shoesIndex - 1
                    },
                    onNext = {
                        shoesIndex =
                            if (shoesIndex == shoesImages.lastIndex) 0
                            else shoesIndex + 1
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            //저장 버튼
            item {
                Button(
                    onClick = {
                        val outfit = SavedOutfit(
                            topImage = topImages[topIndex],
                            bottomImage = bottomImages[bottomIndex],
                            shoesImage = shoesImages[shoesIndex],
                        )

                        savedOutfits.add(outfit)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.save_outfit))
                }
            }

            // 저장한 코디 개수
            item {
                Text(
                    text = stringResource(
                        R.string.saved_outfit_count,
                        savedOutfits.size
                    )
                )
            }

            itemsIndexed(savedOutfits) { index, outfit ->
                SavedOutfitCard(
                    outfit = outfit,
                    onNameChange = { newName ->
                        savedOutfits[index] = savedOutfits[index].copy(
                            name = newName
                        )
                    },
                    onMemoChange = { newText ->
                        savedOutfits[index] = savedOutfits[index].copy(
                            memo = newText
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun ClothesSelector(
    title: String,
    imageRes: Int,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    imageHeight: Dp,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = onPrevious) {
            Text(text = stringResource(R.string.previous))
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(imageRes),
                contentDescription = title,
                alignment = Alignment.Center,
                modifier = Modifier

                    .fillMaxWidth(
                        if (title == stringResource(R.string.section_shoes)) 0.55f
                        else 1f
                    )
                    .height(imageHeight)
            )

        }

        Button(onClick = onNext) {
            Text(text = stringResource(R.string.next))
        }
    }
}



@Composable
fun SavedOutfitCard(
    outfit: SavedOutfit,
    onNameChange: (String) -> Unit,
    onMemoChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    initiallyExpanded: Boolean = false
) {
    var expanded by remember {
        mutableStateOf(initiallyExpanded)
    }

    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(
                dimensionResource(R.dimen.screen_padding)
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.section_spacing)
            )
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (outfit.name.isEmpty()) {
                        stringResource(R.string.unnamed_outfit)
                    } else {
                        outfit.name
                    },
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyLarge
                )

                IconButton(
                    onClick = {
                        expanded = !expanded
                    }
                ) {
                    Icon(
                        imageVector = if (expanded) {
                            Icons.Filled.ExpandLess
                        } else {
                            Icons.Filled.ExpandMore
                        },
                        contentDescription = stringResource(
                            if (expanded) {
                                R.string.collapse_outfit
                            } else {
                                R.string.expand_outfit
                            }
                        )
                    )
                }
            }
            if(expanded) {
                TextField(
                    value = outfit.name,
                    onValueChange = onNameChange,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.outfit_name_label),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        textAlign = TextAlign.Center
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // 상하의신발 묶음
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(outfit.topImage),
                        contentDescription = stringResource(R.string.section_top),
                        alignment = Alignment.TopCenter,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(
                                dimensionResource(R.dimen.saved_top_height)
                            )
                    )

                    Image(
                        painter = painterResource(outfit.bottomImage),
                        contentDescription = stringResource(R.string.section_bottom),
                        alignment = Alignment.TopCenter,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(
                                dimensionResource(R.dimen.saved_bottom_height)
                            )
                    )

                    Image(
                        painter = painterResource(outfit.shoesImage),
                        contentDescription = stringResource(R.string.section_shoes),
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth(0.25f)
                            .height(
                                dimensionResource(R.dimen.saved_shoes_height)
                            )
                    )
                }
                //메모 란
                TextField(
                    value = outfit.memo,
                    onValueChange = onMemoChange,
                    label = {
                        Text(text = stringResource(R.string.memo_label))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            dimensionResource(R.dimen.memo_height)
                        )
                )
            }

        }
    }
}