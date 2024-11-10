package com.dicoding.jetreward.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.jetreward.R
import com.dicoding.jetreward.ui.theme.JetProductTheme
import com.dicoding.jetreward.ui.theme.Shapes
import com.dicoding.jetreward.util.formatRupiah

@Composable
fun ProductItem(
    image: Int,
    title: String,
    price: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(170.dp)
                .clip(Shapes.medium)
        )
        Text(
            text = title,
            maxLines = 2,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = formatRupiah(price),
            style = MaterialTheme.typography.titleSmall,
            color = Color.White,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ProductItemPreview() {
    JetProductTheme {
        ProductItem(R.drawable.product_4, "Organic Cotton Tote Bag", 25000)
    }
}