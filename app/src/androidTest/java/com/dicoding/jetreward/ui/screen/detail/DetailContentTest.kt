package com.dicoding.jetreward.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.dicoding.jetreward.R
import com.dicoding.jetreward.model.OrderProduct
import com.dicoding.jetreward.model.Product
import com.dicoding.jetreward.onNodeWithStringId
import com.dicoding.jetreward.ui.theme.JetProductTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailContentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private val fakeOrderProduct = OrderProduct(
        product = Product(
            4, R.drawable.product_4, "Organic Cotton Tote Bag", 25000,
            "A tote bag made from 100% organic cotton. This versatile bag is perfect for grocery shopping, carrying books, or everyday use. It is durable, washable, and a great alternative to plastic bags."
        ),
        count = 0
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetProductTheme {
                DetailContent(
                    fakeOrderProduct.product.image,
                    fakeOrderProduct.product.title,
                    fakeOrderProduct.product.price,
                    fakeOrderProduct.product.description,
                    fakeOrderProduct.count,
                    onBackClick = {},
                    onAddToCart = {}
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_isDisplayed() {
        composeTestRule.onNodeWithText(fakeOrderProduct.product.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.required_point,
                fakeOrderProduct.product.price
            )
        ).assertIsDisplayed()
    }

    @Test
    fun increaseProduct_buttonEnabled() {
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsEnabled()
    }

    @Test
    fun increaseProduct_correctCounter() {
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick().performClick()
            .performClick()
        composeTestRule.onNodeWithText("3").assertIsDisplayed()
        composeTestRule.onNodeWithTag("count").assert(hasText("3"))
    }

    @Test
    fun decreaseProduct_stillZero() {
        composeTestRule.onNodeWithStringId(R.string.minus_symbol).performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("0"))
    }
}